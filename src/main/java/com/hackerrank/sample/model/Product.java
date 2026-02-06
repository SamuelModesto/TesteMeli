package com.hackerrank.sample.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    private String productId;
    private String title;
    private String subtitle;
    private String condition;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id")
    private Media media;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pricing_id")
    private Pricing pricing;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "variation_id")
    private Variation variation;
}
