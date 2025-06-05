package com.example.ppo.message;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "message")
@Document(collection = "message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Schema(description = "Message entity")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.annotation.Id
    private Long id;
    
    @Column
    @Field("datetime")
    @Builder.Default
    private LocalDateTime datetime = LocalDateTime.now();
    
    @Column(nullable = false, columnDefinition = "TEXT")
    @Field("text")
    private String text;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @DBRef(lazy = true)
    @Field("client")
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    @DBRef(lazy = true)
    @Field("consultant")
    private Consultant consultant;
    
    @Column(nullable = false)
    @Field("is_client")
    private Boolean isClient;

    public boolean isValid() {
        return text != null && !text.isBlank()
            && client != null && consultant != null 
            && datetime != null && isClient != null;
    }
}