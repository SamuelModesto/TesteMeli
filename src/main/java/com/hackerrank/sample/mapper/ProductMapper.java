package com.hackerrank.sample.mapper;

import com.hackerrank.sample.dto.CategoryDto;
import com.hackerrank.sample.dto.CategoryPathDto;
import com.hackerrank.sample.dto.ProductResponseDto;
import com.hackerrank.sample.model.Category;
import com.hackerrank.sample.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mapper
public interface ProductMapper {

    @Mapping(target = "sellerPreview", source = "seller")
    @Mapping(target = "ratingPreview", source = "rating")
    @Mapping(target = "variationPreview", source = "variation")
    ProductResponseDto toResponseDto(Product product);

    @Mapping(target = "path", source = "category", qualifiedByName = "mapCategoryPath")
    CategoryDto toCategoryDto(Category category);

    @Named("mapCategoryPath")
    default List<CategoryPathDto> mapCategoryPath(Category category) {
        if (category == null) {
            return Collections.emptyList();
        }
        List<CategoryPathDto> path = new ArrayList<>();
        Category current = category;
        while (current != null) {
            path.add(new CategoryPathDto(current.getId(), current.getName()));
            current = current.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
