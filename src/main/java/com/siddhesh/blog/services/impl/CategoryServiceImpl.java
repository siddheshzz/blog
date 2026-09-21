package com.siddhesh.blog.services.impl;

import com.siddhesh.blog.domain.entities.Category;
import com.siddhesh.blog.repositories.CategoryRepository;
import com.siddhesh.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new IllegalArgumentException("Category already exists with name: "+ category.getName());
        }

        return categoryRepository.save((category));
    }
    @Override
    @Transactional
    public void deleteCategory(UUID id){
        Category category = getCategoryById(id);

        // Check if category has associated posts
        if (!category.getPosts().isEmpty()) {
            throw new IllegalStateException(
                    "Cannot delete category: " + category.getName() + ". It has associated posts.");
        }

        categoryRepository.delete(category);
    }

    public Category getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }


}
