package com.siddhesh.blog.mappers;


import com.siddhesh.blog.domain.CreatePostRequest;
import com.siddhesh.blog.domain.UpdatePostRequest;
import com.siddhesh.blog.domain.dtos.CategoryDto;
import com.siddhesh.blog.domain.dtos.CreatePostRequestDto;
import com.siddhesh.blog.domain.dtos.PostDto;
import com.siddhesh.blog.domain.dtos.UpdatePostRequestDto;
import com.siddhesh.blog.domain.entities.Category;
import com.siddhesh.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "status", source = "status")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

    UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);




}
