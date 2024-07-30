package com.dev.IBIOECommerceJAR.dto.shopping;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.dev.IBIOECommerceJAR.model.authentication.Member;
import com.dev.IBIOECommerceJAR.model.order.OrderDetail;

import lombok.Data;

@Data
public class OrderViewDTO {
	private Long orderId;
    private Member orderBuyer;
    private String orderBillingName;
    private ZonedDateTime orderDate;
    private ZonedDateTime orderChangeDate;
    private String orderTotalPrice;
    private List<OrderDetail> orderItems;
    private int orderSign;
    
    public String getOrderBuyerRole() {
        return orderBuyer.getRole();
    }
    public String getProductNames() {
        return orderItems.stream()
                         .map(orderDetail -> orderDetail.getOrderDetailProduct().getProductTitle() + " : " + orderDetail.getOrderDetailQuantity() + "개")
                         .collect(Collectors.joining("\n"));
    }
}
