package com.example.ppo.product;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "product")
@Document(collection = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "Product entity")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;
    
    @Column(nullable = false)
    @Field("cost")
    @Builder.Default
    private Double cost = 0.0;
    
    @Column(nullable = false)
    @Field("name")
    private String name;
    
    @Column(columnDefinition = "TEXT")
    @Field("about")
    @Builder.Default
    private String about = "";

    public boolean isValid() {
        return cost != null && cost >= 0 
            && name != null && !name.isBlank()
            && about != null;
    }
}