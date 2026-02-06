package com.hackerrank.sample.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "availability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String stockStatus;
    private Integer quantityAvailable;
    private Integer maxPurchaseQty;
}
