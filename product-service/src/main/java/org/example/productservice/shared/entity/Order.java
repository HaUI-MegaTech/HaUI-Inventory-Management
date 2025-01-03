package org.example.productservice.shared.entity;

import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.example.productservice.shared.entity.OrderDetail;
import org.example.productservice.shared.entity.enums.OrderStatus;
import org.example.productservice.shared.entity.enums.PaymentMethod;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
public class Order {
    int deliverTime;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;
    private float   shippingCost;
    private float   subTotal;
    private float   tax;
    private float   total;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date payTime;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderTime;

    @DecimalMin(value = "0.0", message = "Trọng lượng đơn hàng không hợp lệ")
    private float orderWeight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
