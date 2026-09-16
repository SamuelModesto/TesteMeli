package com.hackerrank.sample.service;

import com.hackerrank.sample.dto.ProductResponseDto;
import com.hackerrank.sample.exception.ResourceNotFoundException;
import com.hackerrank.sample.mapper.ProductMapper;
import com.hackerrank.sample.model.*;
import com.hackerrank.sample.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, productMapper);
        product = new Product();
        product.setProductId("PRD-1");
        product.setTitle("Test Product");
        
        Category category = new Category();
        category.setId("CAT-1");
        category.setName("Category 1");
        product.setCategory(category);
        
        Media media = new Media();
        media.setCoverImageUrl("http://image.com");
        media.setImageIds(new ArrayList<>());
        product.setMedia(media);
        
        Pricing pricing = new Pricing();
        pricing.setCurrency("BRL");
        pricing.setPrice(BigDecimal.valueOf(100.0));
        product.setPricing(pricing);
        
        Availability availability = new Availability();
        availability.setStockStatus("IN_STOCK");
        availability.setQuantityAvailable(10);
        availability.setMaxPurchaseQty(1);
        product.setAvailability(availability);
        
        Seller seller = new Seller();
        seller.setId("SEL-1");
        seller.setNickname("Seller 1");
        seller.setReputationLevel("GOLD");
        seller.setPositiveFeedbackPercent(95.0);
        product.setSeller(seller);
        
        Rating rating = new Rating();
        rating.setAverage(4.5);
        rating.setTotalReviews(100);
        product.setRating(rating);
        
        Variation variation = new Variation();
        variation.setSelectedVariationId("VAR-1");
        variation.setAxes(new ArrayList<>());
        product.setVariation(variation);
    }

    @Test
    void whenProductExists_thenReturnProductDtoWithCorrectPath() {
        Category parent = new Category();
        parent.setId("PARENT");
        parent.setName("Parent Category");

        Category child = new Category();
        child.setId("CHILD");
        child.setName("Child Category");
        child.setParent(parent);

        product.setCategory(child);

        when(productRepository.findById("PRD-1")).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.findProductById("PRD-1");

        assertNotNull(result);
        assertEquals("CHIL", result.category().id());
        assertEquals(2, result.category().path().size());
        assertEquals("PARENT", result.category().path().get(0).id());
        assertEquals("Parent Category", result.category().path().get(0).name());
        assertEquals("CHILD", result.category().path().get(1).id());
        assertEquals("Child Category", result.category().path().get(1).name());
        assertEquals("CHILD", result.category().id());
    }

    @Test
    void whenProductDoesNotExist_thenThrowResourceNotFoundException() {
        when(productRepository.findById("NON-EXISTENT")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.findProductById("NON-EXISTENT"));
    }
}
