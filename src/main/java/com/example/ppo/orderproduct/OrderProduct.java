package com.example.ppo.orderproduct;

import com.example.ppo.order.Order;
import com.example.ppo.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "order_products")
@Document(collection = "order_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "OrderProduct entity")
public class OrderProduct {

    @EmbeddedId
    @org.springframework.data.annotation.Id
    private OrderProductId id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @DBRef(lazy = true)
    @Field("order")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @DBRef(lazy = true)
    @Field("product")
    private Product product;
    
    @Column(nullable = false)
    @Field("quantity")
    private Integer quantity;
    
    @Embeddable
    @Getter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class OrderProductId implements Serializable {
        private static final long serialVersionUID = 1L;
        private Long orderId;
        private Long productId;
    }

    public boolean isValid() {
        return order != null && 
               product != null && 
               quantity != null && quantity > 0;
    }
}