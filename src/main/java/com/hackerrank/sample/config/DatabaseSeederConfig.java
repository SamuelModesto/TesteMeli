package com.hackerrank.sample.config;

import com.hackerrank.sample.model.*;
import com.hackerrank.sample.repository.CategoryRepository;
import com.hackerrank.sample.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
class DatabaseSeederConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSeederConfig.class);
    private static final String SEEDING_MESSAGE = "Populando o banco de dados com dados de exemplo...";
    private static final String SEEDING_COMPLETED_MESSAGE = "Banco de dados populado com sucesso!";

    private final Random random = new Random();

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, CategoryRepository categoryRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                logger.info(SEEDING_MESSAGE);
                seedData(productRepository, categoryRepository);
                logger.info(SEEDING_COMPLETED_MESSAGE);
            }
        };
    }

    @Transactional
    void seedData(ProductRepository productRepository, CategoryRepository categoryRepository) {
        List<Category> categories = createCategories(categoryRepository);
        List<Seller> sellers = createSellers();
        
        String[] conditions = {"NEW", "USED", "REFURBISHED"};
        String[] currencies = {"BRL", "USD", "EUR"};
        String[] stockStatuses = {"IN_STOCK", "OUT_OF_STOCK", "LOW_STOCK"};

        // Usamos apenas as categorias folhas para os produtos para testar o path completo
        List<Category> leafCategories = categories.stream()
            .filter(c -> c.getId().equals("CELLPHONES_SMARTPHONES") || c.getId().equals("ROUPAS") || c.getId().equals("LIVROS"))
            .toList();

        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setProductId(String.format("PROD%03d", i));
            product.setTitle("Produto de Exemplo " + i);
            product.setSubtitle("Subtítulo do produto " + i);
            product.setCondition(conditions[random.nextInt(conditions.length)]);

            product.setCategory(leafCategories.get(random.nextInt(leafCategories.size())));
            product.setSeller(sellers.get(random.nextInt(sellers.size())));

            Media media = new Media();
            media.setCoverImageUrl("http://example.com/img" + i + ".jpg");
            media.setImageIds(List.of("IMG-" + i + "-1", "IMG-" + i + "-2"));
            product.setMedia(media);

            Pricing pricing = new Pricing();
            pricing.setCurrency(currencies[random.nextInt(currencies.length)]);
            pricing.setPrice(BigDecimal.valueOf(10.0 + (1000.0 - 10.0) * random.nextDouble()));
            product.setPricing(pricing);

            Availability availability = new Availability();
            availability.setStockStatus(stockStatuses[random.nextInt(stockStatuses.length)]);
            availability.setQuantityAvailable(random.nextInt(101));
            availability.setMaxPurchaseQty(5);
            product.setAvailability(availability);

            Rating rating = new Rating();
            rating.setAverage(1.0 + (5.0 - 1.0) * random.nextDouble());
            rating.setTotalReviews(random.nextInt(501));
            product.setRating(rating);

            Variation variation = new Variation();
            variation.setSelectedVariationId("VAR-" + i);
            variation.setAxes(List.of("Cor", "Tamanho"));
            product.setVariation(variation);

            productRepository.save(product);
        }
    }

    private List<Category> createCategories(CategoryRepository categoryRepository) {
        Category eletronicos = new Category();
        eletronicos.setId("ELETRONICOS");
        eletronicos.setName("Eletrônicos");
        categoryRepository.save(eletronicos);

        Category celulares = new Category();
        celulares.setId("CELLPHONES");
        celulares.setName("Celulares");
        celulares.setParent(eletronicos);
        categoryRepository.save(celulares);

        Category smartphones = new Category();
        smartphones.setId("CELLPHONES_SMARTPHONES");
        smartphones.setName("Smartphones");
        smartphones.setParent(celulares);
        categoryRepository.save(smartphones);

        Category roupas = new Category();
        roupas.setId("ROUPAS");
        roupas.setName("Roupas");
        categoryRepository.save(roupas);

        Category livros = new Category();
        livros.setId("LIVROS");
        livros.setName("Livros");
        categoryRepository.save(livros);

        return List.of(eletronicos, celulares, smartphones, roupas, livros);
    }

    private List<Seller> createSellers() {
        Object[][] sellerData = {
            {"S1", "TechStore", "Platinum", 99.5},
            {"S2", "FashionHub", "Gold", 98.2},
            {"S3", "BookWorm", "Silver", 95.0}
        };
        List<Seller> sellers = new ArrayList<>();
        for (Object[] data : sellerData) {
            Seller seller = new Seller();
            seller.setId((String) data[0]);
            seller.setNickname((String) data[1]);
            seller.setReputationLevel((String) data[2]);
            seller.setPositiveFeedbackPercent((Double) data[3]);
            sellers.add(seller);
        }
        return sellers;
    }
}
