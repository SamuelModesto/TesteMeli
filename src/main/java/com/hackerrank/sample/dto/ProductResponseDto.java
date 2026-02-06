package com.hackerrank.sample.dto;

public record ProductResponseDto(
    String productId,
    String title,
    String subtitle,
    String condition,
    CategoryDto category,
    MediaDto media,
    PricingDto pricing,
    AvailabilityDto availability,
    SellerPreviewDto sellerPreview,
    RatingPreviewDto ratingPreview,
    VariationPreviewDto variationPreview
) {}
