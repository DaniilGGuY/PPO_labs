package com.example.ppo.order;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import com.example.ppo.product.Product;
import com.example.ppo.orderproduct.OrderProduct;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

enum OrderStatus {
	PENDING,
	IN_PROGRESS,
	COMPLETED,
	CANCELLED	
}

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false)
	private double cost;
	
	@Column
	@Builder.Default
	private LocalDateTime datetime = LocalDateTime.now();
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	@Builder.Default
	private OrderStatus status = OrderStatus.PENDING;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	@Builder.Default
	private List<OrderProduct> orderProducts = new ArrayList<>();
	
	public void addProduct(Product product, int quantity) {
		product.addToOrder(this, quantity);
	}
	
	@PrePersist
    protected void prePersist() {
        if (this.datetime == null) {
            this.datetime = LocalDateTime.now();
        }
    }
}
