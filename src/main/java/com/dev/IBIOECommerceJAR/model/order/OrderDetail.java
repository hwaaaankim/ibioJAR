package com.dev.IBIOECommerceJAR.model.order;

import com.dev.IBIOECommerceJAR.model.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ORDER_DETAIL_ID")
    private Long id;

    @Column(name="ORDER_DETAIL_QUANTITY")
    private int orderDetailQuantity;

    @OneToOne
    @JoinColumn(name="ORDER_DETAIL_PRODUCT_ID")
    private Product orderDetailProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORDER_ID")
    private Order order;
    
    public String getProductName() {
        return orderDetailProduct.getProductTitle();
    }
}