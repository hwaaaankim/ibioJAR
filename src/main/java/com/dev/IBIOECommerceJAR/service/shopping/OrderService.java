package com.dev.IBIOECommerceJAR.service.shopping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.IBIOECommerceJAR.dto.bankda.OrderDTO;
import com.dev.IBIOECommerceJAR.dto.shopping.OrderViewDTO;
import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.model.order.Order;
import com.dev.IBIOECommerceJAR.model.order.OrderDetail;
import com.dev.IBIOECommerceJAR.model.product.Product;
import com.dev.IBIOECommerceJAR.repository.MemberRepository;
import com.dev.IBIOECommerceJAR.repository.product.ProductRepository;
import com.dev.IBIOECommerceJAR.repository.shopping.OrderRepository;
import com.dev.IBIOECommerceJAR.service.SMSService;

@Service
@Transactional
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository; 
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private SMSService smsService;
    
    public Page<OrderViewDTO> getOrders(Pageable pageable) {
        // 현재 로그인한 사용자의 권한을 가져옴
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        
        Page<Order> orders;
        if (isAdmin) {
            // 관리자라면 모든 거래 내역을 가져옴
            orders = orderRepository.findAll(pageable);
        } else {
            // 사용자의 ID를 가져옴
            String username = userDetails.getUsername();
            Member member = memberRepository.findByUsername(username).get();
            // 해당 사용자의 거래 내역만 가져옴
            orders = orderRepository.findByOrderBuyer(member, pageable);
        }

        return orders.map(order -> {
            OrderViewDTO orderViewDTO = new OrderViewDTO();
            orderViewDTO.setOrderId(order.getOrderId());
            orderViewDTO.setOrderBuyer(order.getOrderBuyer());
            orderViewDTO.setOrderBillingName(order.getOrderBillingName());
            orderViewDTO.setOrderDate(order.getOrderDate());
            orderViewDTO.setOrderChangeDate(order.getOrderChangeDate());
            orderViewDTO.setOrderTotalPrice(convertToWon(order.getOrderTotalPrice()));
            orderViewDTO.setOrderItems(order.getOrderItems());
            orderViewDTO.setOrderSign(order.getOrderSign());
            return orderViewDTO;
        });
    }
    private String convertToWon(String price) {
        BigDecimal priceDecimal = new BigDecimal(price);
        BigDecimal priceInWon = priceDecimal.setScale(0, RoundingMode.DOWN);
        return priceInWon.toString() + "원";
    }
    
    
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

        BigDecimal taxRate = BigDecimal.valueOf(0.1); // 10% 세금
        BigDecimal taxAmount = totalPrice.multiply(taxRate);
        BigDecimal finalPrice = totalPrice.add(taxAmount);

        order.setOrderItems(orderDetails);
        order.setOrderTotalPrice(finalPrice.toString()); // 세금이 포함된 최종 금액을 String으로 변환하여 저장

        // 저장은 한 번만 호출하여 order와 orderDetails를 모두 저장
        orderRepository.save(order);

        return order;
    }
    
    public List<OrderDTO> getUncheckedOrders() {
        List<Order> orders = orderRepository.findByOrderSign(0);
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDTO);
    }
    
    public List<Map<String, String>> checkAndUpdateOrders(List<Map<String, Object>> requests) throws EncoderException {

        List<Map<String, String>> errors = new ArrayList<>();
        for (Map<String, Object> request : requests) {
            Long orderId = null;
            try {
                Object orderIdObj = request.get("order_id");
                if (orderIdObj instanceof Number) {
                    orderId = ((Number) orderIdObj).longValue();
                } else if (orderIdObj instanceof String) {
                    orderId = Long.valueOf((String) orderIdObj);
                }
            } catch (NumberFormatException e) {
                errors.add(Map.of("order_id", String.valueOf(request.get("order_id")), "description", "order_id 오류"));
                continue;
            }

            Optional<Order> orderOpt = orderRepository.findById(orderId);
            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                if (order.getOrderSign() == 2) {
                    errors.add(Map.of("order_id", String.valueOf(request.get("order_id")), "description", "입금대기 상태 에러"));
                } else if (order.getOrderSign() == 0) {
                    updateOrderSign(orderId, 1);
                }
            } else {
                errors.add(Map.of("order_id", String.valueOf(request.get("order_id")), "description", "order_id 오류"));
            }
        }

        return errors;
    }


    public void updateOrderSign(Long orderId, int sign) throws EncoderException {
        // 실제로 order의 sign을 업데이트하는 로직
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            String msg = order.getOrderId() + "번 거래의 입금이 발생 하였습니다.";
            order.setOrderSign(sign);
            order.setOrderChangeDate(ZonedDateTime.now());
            orderRepository.save(order);
            smsService.sendMessage("010-3894-3849", msg, "L");
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
