package org.example.brandservice.shared.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.brandservice.shared.entity.Product;

import java.util.Date;
import java.util.List;

@Table(name = "brands")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer id;
    private String  name;
    private String  image;
    @Temporal(TemporalType.TIMESTAMP)
    private Date    whenCreated;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    private List<Product> products;
}
