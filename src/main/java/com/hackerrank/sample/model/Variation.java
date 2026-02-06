package com.hackerrank.sample.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "variations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String selectedVariationId;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "variation_axes", joinColumns = @JoinColumn(name = "variation_id"))
    @Column(name = "axis")
    private List<String> axes;
}
