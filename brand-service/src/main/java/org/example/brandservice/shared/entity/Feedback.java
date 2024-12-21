package org.example.brandservice.shared.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.brandservice.shared.entity.Product;
import org.example.brandservice.shared.entity.User;

import java.util.Date;

@Entity
@Table(name = "feedbacks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Integer id;
    private String  alias;
    private String  content;
    private Byte    rating;
    private Date    whenCreated;
    private Date    lastUpdated;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
