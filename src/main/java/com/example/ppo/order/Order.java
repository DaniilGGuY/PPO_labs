package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.orderproduct.OrderProduct;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "orders")
@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "Order entity")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;
    
    @Column(nullable = false)
    @Field("cost")
    @Builder.Default
    private Double cost = 0.0;
    
    @Column
    @Field("datetime")
    @Builder.Default
    private LocalDateTime datetime = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @DBRef(lazy = true)
    @Field("client")
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    @DBRef(lazy = true)
    @Field("consultant")
    @Builder.Default
    private Consultant consultant = null;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Field("status")
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    @DBRef(lazy = true)
    @Field("order_products")
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct(OrderProduct op) {
        orderProducts.add(op);
        op.setOrder(this);
    }

    public boolean isValid() {
        return cost != null && cost >= 0 && 
               client != null && 
               datetime != null && 
               status != null;
    }
}