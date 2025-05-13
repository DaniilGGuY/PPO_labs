package com.example.ppo.message;

import com.example.ppo.client.Client;
import com.example.ppo.consultant.Consultant;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private LocalDateTime datetime = LocalDateTime.now();
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String text;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "consultant_id")
	private Consultant consultant;
	
	@PrePersist
	public void onCreate() {
		if (this.datetime == null) {
			this.datetime = LocalDateTime.now();
		}
	}
	
	public boolean isValid() {
        return text != null && !text.isBlank();
    }
}