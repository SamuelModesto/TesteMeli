package com.hackerrank.sample.dto;

import java.util.List;

public record CategoryDto(
    String id,
    List<CategoryPathDto> path
) {}
