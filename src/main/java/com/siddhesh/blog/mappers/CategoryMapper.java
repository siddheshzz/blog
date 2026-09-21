package com.siddhesh.blog.mappers;

import com.siddhesh.blog.domain.dtos.CategoryDto;
import com.siddhesh.blog.domain.dtos.CreateCategoryRequest;
import com.siddhesh.blog.domain.entities.Category;
import com.siddhesh.blog.domain.entities.Post;
import com.siddhesh.blog.domain.entities.PostStatus;
import com.siddhesh.blog.repositories.CategoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto(Category category);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts) {
        if (posts == null) {
            return 0;
        }
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }

    public Category toEntity(CreateCategoryRequest createCategoryRequest);

}
