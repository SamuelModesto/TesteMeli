package com.hackerrank.sample.dto;

public record SellerPreviewDto(
    String id,
    String nickname,
    String reputationLevel,
    Double positiveFeedbackPercent
) {}
