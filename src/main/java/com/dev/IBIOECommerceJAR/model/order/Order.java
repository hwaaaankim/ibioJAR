package com.dev.IBIOECommerceJAR.model.order;

import java.time.ZonedDateTime;
import java.util.List;

import com.dev.IBIOECommerceJAR.model.authentication.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ORDER_ID")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_BUYER_ID")
    private Member orderBuyer;

    @Column(name="ORDER_BILLING_NAME")
    private String orderBillingName;

    @Column(name="ORDER_DATE")
    private ZonedDateTime orderDate;
    
    @Column(name="ORDER_CHANGE_DATE")
    private ZonedDateTime orderChangeDate;

    @Column(name="ORDER_TOTAL_PRICE")
    private String orderTotalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetail> orderItems;

    // 0 : 주문대기, 1 : 입금완료, 2 : 고객취소
    @Column(name="ORDER_SIGN")
    private int orderSign;
}
