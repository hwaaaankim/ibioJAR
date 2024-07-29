package com.dev.IBIOECommerceJAR.service.shopping;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.IBIOECommerceJAR.dto.bankda.OrderDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.model.order.Order;
import com.dev.IBIOECommerceJAR.model.order.OrderDetail;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.repository.shopping.OrderRepository;

@Service
@Transactional
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository; // Assuming you have a ProductRepository

    public String generateOrderSummary(Order order) {
        StringBuilder summaryBuilder = new StringBuilder();

        for (OrderDetail detail : order.getOrderItems()) {
            String productName = detail.getOrderDetailProduct().getProductTitle();
            int quantity = detail.getOrderDetailQuantity();

            summaryBuilder.append(productName)
                          .append(" x")
                          .append(quantity)
                          .append(", ");
        }

        // Remove the last comma and space
        if (summaryBuilder.length() > 0) {
            summaryBuilder.setLength(summaryBuilder.length() - 2);
        }

        return summaryBuilder.toString();
    }
    
    public Order createOrder(List<Long> productIds, List<Integer> quantities, Member orderBuyer) {
        Order order = new Order();
        order.setOrderBuyer(orderBuyer);
        order.setOrderDate(ZonedDateTime.now());
        order.setOrderBillingName(orderBuyer.getName());
        order.setOrderSign(0); // Set as true or handle appropriately

        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (int i = 0; i < productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                                               .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setOrderDetailProduct(product);
            orderDetail.setOrderDetailQuantity(quantities.get(i));

            BigDecimal productPrice = BigDecimal.valueOf(product.getProductPrice()); // int를 BigDecimal로 변환
            BigDecimal quantity = BigDecimal.valueOf(quantities.get(i));
            BigDecimal itemTotalPrice = productPrice.multiply(quantity);
            totalPrice = totalPrice.add(itemTotalPrice);

            orderDetails.add(orderDetail);
        }

        order.setOrderItems(orderDetails);
        order.setOrderTotalPrice(totalPrice.toString()); // BigDecimal을 String으로 변환하여 저장

        // 저장은 한 번만 호출하여 order와 orderDetails를 모두 저장
        orderRepository.save(order);

        return order;
    }
    
    public List<OrderDTO> getUncheckedOrders() {
    	System.out.println("getUncheckedOrders");
        List<Order> orders = orderRepository.findByOrderSign(0);
        for(Order order : orders) {
        	System.out.println(order.getOrderBillingName());
        	System.out.println(order.getOrderSign());
        	System.out.println(order.getOrderId());
        }
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long orderId) {
    	System.out.println("getOrderById");
        return orderRepository.findById(orderId).map(this::convertToDTO);
    }
    
    public List<Map<String, String>> checkAndUpdateOrders(List<Map<String, Object>> requests) {
        System.out.println("checkAndUpdateOrders");
        System.out.println("Requests size: " + requests.size());
        System.out.println("Requests: " + requests.toString());

        List<Map<String, String>> errors = new ArrayList<>();
        for (Map<String, Object> request : requests) {
            Long orderId = null;
            try {
                System.out.println("Parsing order_id: " + request.get("order_id"));
                orderId = Long.valueOf((String) request.get("order_id"));
                System.out.println("Parsed order_id: " + orderId);
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException for order_id: " + request.get("order_id"));
                errors.add(Map.of("order_id", (String) request.get("order_id"), "description", "order_id 오류"));
                continue;
            }

            System.out.println("Finding order by ID: " + orderId);
            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                System.out.println("Order found with ID: " + orderId);
                Order order = orderOpt.get();
                if (order.getOrderSign() == 2) {
                    System.out.println("Order sign is 2 for order_id: " + orderId);
                    errors.add(Map.of("order_id", (String) request.get("order_id"), "description", "입금대기 상태 에러"));
                } else if (order.getOrderSign() == 0) {
                    System.out.println("Order sign is 0 for order_id: " + orderId);
                    updateOrderSign(orderId, 1);
                }
            } else {
                System.out.println("Order not found for order_id: " + orderId);
                errors.add(Map.of("order_id", (String) request.get("order_id"), "description", "order_id 오류"));
            }
        }

        System.out.println("Errors: " + errors);
        return errors;
    }

    public void updateOrderSign(Long orderId, int sign) {
        // 실제로 order의 sign을 업데이트하는 로직을 여기에 추가합니다.
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setOrderSign(sign);
            orderRepository.save(order);
            System.out.println("Order sign updated for order_id: " + orderId);
        } else {
            System.out.println("Order not found for update, order_id: " + orderId);
        }
    }
    
    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrder_id(order.getOrderId());
        dto.setBuyer_name(order.getOrderBuyer().getName());
        dto.setBilling_name(order.getOrderBuyer().getName());
        dto.setOrder_date(Date.from(order.getOrderDate().toInstant()));
        dto.setOrder_price_amount(new BigDecimal(order.getOrderTotalPrice()).intValue()); // 수정된 부분
        dto.setBuyer_email(order.getOrderBuyer().getEmail());
        dto.setBuyer_cellphone(order.getOrderBuyer().getPhone());

        List<Map<String, String>> items = order.getOrderItems().stream()
                .map(item -> Map.of("product_name", item.getOrderDetailProduct().getProductTitle()))
                .collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }
    
    
}
