package com.example.ppo.orderproduct;

import com.example.ppo.order.Order;
import com.example.ppo.product.Product;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "order_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderProduct {
	@EmbeddedId
	private OrderProductId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Column(nullable = false)
    private int quantity;
    
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private static class OrderProductId implements Serializable{
    	private long orderId;
    	private long productId;
    }
    
    public boolean isValid() {
        return quantity > 0;
    }
}