package com.hackerrank.sample.dto;

public record AvailabilityDto(
    String stockStatus,
    Integer quantityAvailable,
    Integer maxPurchaseQty
) {}
