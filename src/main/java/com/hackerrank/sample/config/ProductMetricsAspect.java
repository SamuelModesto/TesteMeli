package com.hackerrank.sample.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.hackerrank.sample.exception.ResourceNotFoundException;

@Aspect
@Component
class ProductMetricsAspect {
    private final Counter successCounter;
    private final Counter notFoundCounter;

    ProductMetricsAspect(MeterRegistry registry) {
        this.successCounter = Counter.builder("product.search.total")
                .description("Total de buscas de produtos realizadas com sucesso")
                .tag("status", "success")
                .register(registry);

        this.notFoundCounter = Counter.builder("product.search.total")
                .description("Total de buscas de produtos que resultaram em não encontrado")
                .tag("status", "not_found")
                .register(registry);
    }

    @AfterReturning(pointcut = "execution(* com.hackerrank.sample.service.ProductService.findProductById(..))")
    void logSuccess() {
        successCounter.increment();
    }

    @AfterThrowing(pointcut = "execution(* com.hackerrank.sample.service.ProductService.findProductById(..))", throwing = "ex")
    void logFailure(ResourceNotFoundException ex) {
        notFoundCounter.increment();
    }
}
