package com.example.ppo.product;

import com.example.ppo.order.Order;
import com.example.ppo.orderproduct.OrderProduct;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private double cost;
	
	@Column(nullable = false)
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String about;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@Builder.Default
	private List<OrderProduct> orderProducts = new ArrayList<>(); 
	
	public void addToOrder(Order order, int quantity) {
		OrderProduct orderProduct = OrderProduct.builder().product(this).order(order).quantity(quantity).build();
		orderProducts.add(orderProduct);
		order.getOrderProducts().add(orderProduct);
	}
	
	public boolean isValid() {
        return name != null && !name.isBlank() && cost > 0;
    }
}