package com.hackerrank.sample.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {
    @Id
    private String id;
    
    private String nickname;
    private String reputationLevel;
    private Double positiveFeedbackPercent;
}
