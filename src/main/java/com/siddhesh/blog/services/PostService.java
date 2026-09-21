package com.siddhesh.blog.services;


import com.siddhesh.blog.domain.CreatePostRequest;
import com.siddhesh.blog.domain.UpdatePostRequest;
import com.siddhesh.blog.domain.entities.Post;
import com.siddhesh.blog.domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<Post> getAllPosts(UUID categoryId, UUID tagId);

    Post getPost(UUID id);

    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);

    List<Post> getDraftPosts(User user);




}
