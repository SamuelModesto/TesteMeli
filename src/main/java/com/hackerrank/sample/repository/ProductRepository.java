package com.hackerrank.sample.repository;

import com.hackerrank.sample.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Override
    @EntityGraph(attributePaths = {
            "category",
            "category.parent",
            "category.parent.parent",
            "media",
            "pricing",
            "availability",
            "seller",
            "rating",
            "variation"
    })
    Optional<Product> findById(String id);

    @Query("SELECT p.productId FROM Product p")
    List<String> findFirstProductIds(Pageable pageable);
}
