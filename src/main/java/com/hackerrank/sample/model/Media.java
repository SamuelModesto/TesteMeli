package com.hackerrank.sample.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "media")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String coverImageUrl;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "media_image_ids", joinColumns = @JoinColumn(name = "media_id"))
    @Column(name = "image_id")
    private List<String> imageIds;
}
