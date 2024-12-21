package org.example.brandservice.shared.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.brandservice.shared.entity.Product;

@Entity
@Table(name = "images")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer id;
    private String  url;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
