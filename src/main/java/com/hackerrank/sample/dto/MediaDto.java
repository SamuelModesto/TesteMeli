package com.hackerrank.sample.dto;

import java.util.List;

public record MediaDto(
    String coverImageUrl,
    List<String> imageIds
) {}
