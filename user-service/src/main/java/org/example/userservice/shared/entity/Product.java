package org.example.userservice.shared.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;

    private String  name;
    private Float   oldPrice;
    private Float   currentPrice;
    private Integer discountPercent;
    private String  mainImageUrl;
    private String  shortProcessor;
    private String  shortDisplay;
    private String  shortCard;
    private String  shortWeight;

    private String  processor;
    private Integer cores;
    private Integer threads;
    private String  frequency;
    private String  boostFrequency;
    private String  cacheCapacity;

    private String memoryCapacity;
    private String memoryType;
    private String memoryBus;
    private String maxMemoryCapacity;
    private String storage;

    private String displaySize;
    private String resolution;
    private String refreshRate;
    private String colorGamut;
    private String panelType;
    private String touchScreen;

    private String graphicsCard;
    private String soundTechnology;

    private String wirelessConnectivity;
    private String sdCard;
    private String webcam;
    private String coolingFan;
    private String miscFeature;
    private String backlitKeyboard;

    private String dimensionWeight;
    private String material;

    private String  batteryCapacity;
    private String  chargerCapacity;
    private String  os;
    private Integer launchDate;

    private Integer dailySold;
    private Integer weeklySold;
    private Integer monthlySold;
    private Integer totalSold;

    private Integer imported;
    private Float   importPrice;

    private Integer remaining;

    private Integer dailyViews;
    private Integer weeklyViews;
    private Integer monthlyViews;
    private Integer totalViews;

    @Temporal(TemporalType.TIMESTAMP)
    private Date    whenCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date    lastUpdated;
    private Boolean hidden;
    private Boolean deleted;

    private String article;

    @JoinColumn(name = "brand_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Image> images;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<SimilarProduct> similarProducts;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;
    private Float          averageRating;
    private Integer        feedbacksCount;
}
