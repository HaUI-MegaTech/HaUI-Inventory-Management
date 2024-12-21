package org.example.brandservice.shared.entity;

import lombok.*;
import org.example.brandservice.shared.entity.Order;
import org.example.brandservice.shared.entity.Product;
import jakarta.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantity;
    private Float   price;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order   order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
