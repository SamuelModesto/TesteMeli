package com.hackerrank.sample.dto;

import java.math.BigDecimal;

public record PricingDto(
    String currency,
    BigDecimal price
) {}
