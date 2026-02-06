package com.hackerrank.sample.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ProblemDetailsResponse(
    String type,
    String title,
    int status,
    String detail,
    String instance,
    OffsetDateTime timestamp,
    List<ErrorField> errors
) {
    public record ErrorField(String name, String message) {}
}
