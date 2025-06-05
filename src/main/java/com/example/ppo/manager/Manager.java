package com.example.ppo.manager;

import com.example.ppo.product.Product;
import com.example.ppo.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
import java.util.ArrayList;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "manager")
@Document(collection = "manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@Schema(description = "Manager entity")
public class Manager extends User {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @DBRef(lazy = true)
    @Field("products")
    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }
}