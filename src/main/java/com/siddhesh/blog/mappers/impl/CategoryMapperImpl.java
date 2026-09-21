package com.siddhesh.blog.mappers.impl;

import com.siddhesh.blog.domain.dtos.CategoryDto;
import com.siddhesh.blog.domain.dtos.CreateCategoryRequest;
import com.siddhesh.blog.domain.entities.Category;
import com.siddhesh.blog.mappers.CategoryMapper;

public class CategoryMapperImpl implements CategoryMapper {

    public CategoryDto toDto(Category category){
        return CategoryDto.builder().name(category.getName()).build();
    }

    public Category toEntity(CreateCategoryRequest createCategoryRequest){
        return null;
    }

}
