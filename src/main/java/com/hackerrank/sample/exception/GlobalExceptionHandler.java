package com.hackerrank.sample.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String INVALID_REQUEST_MSG = "Requisição inválida. Verifique os campos enviados.";
    private static final String BUSINESS_ERROR_MSG = "Não foi possível processar as instruções contidas na requisição.";
    private static final String INTERNAL_SERVER_ERROR_MSG = "Ocorreu um erro interno no servidor. Tente novamente mais tarde.";
    private static final String RESOURCE_NOT_FOUND_LOG = "Produto não encontrado na URI: {}";
    private static final String UNEXPECTED_ERROR_MESSAGE = "Erro inesperado: {}, na URI: {}";

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ProblemDetailsResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        logger.warn(RESOURCE_NOT_FOUND_LOG,  request.getRequestURI());
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI(), null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<ProblemDetailsResponse> handleMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return createResponse(HttpStatus.BAD_REQUEST, INVALID_REQUEST_MSG, request.getRequestURI(), null);
    }

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ProblemDetailsResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        logger.error(UNEXPECTED_ERROR_MESSAGE, ex.getMessage(), request.getRequestURI());
        return createResponse(HttpStatus.UNPROCESSABLE_ENTITY, BUSINESS_ERROR_MSG, request.getRequestURI(), null);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ProblemDetailsResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG, request.getRequestURI(), null);
    }

    private ResponseEntity<ProblemDetailsResponse> createResponse(HttpStatus status, String title, String path, List<ProblemDetailsResponse.ErrorField> errors) {
        ProblemDetailsResponse body = new ProblemDetailsResponse(
            "about:blank",
            title,
            status.value(),
            title,
            path,
            OffsetDateTime.now(),
            errors
        );
        return ResponseEntity.status(status).body(body);
    }
}
