package com.hackerrank.sample.dto;

import java.util.List;

public record VariationPreviewDto(
    String selectedVariationId,
    List<String> axes
) {}
