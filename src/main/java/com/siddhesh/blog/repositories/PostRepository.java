package com.siddhesh.blog.repositories;

import com.siddhesh.blog.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);

    List<Post> findAllByStatusAndCategory(PostStatus postStatus, Category category);

    List<Post> findAllByStatusAndTagsContaining(PostStatus postStatus, Tag tag);

    List<Post> findAllByStatus(PostStatus postStatus);

    List<Post> findAllByAuthorAndStatus(User user, PostStatus postStatus);
}
