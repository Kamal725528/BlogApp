package com.blog.service;

import com.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto);
    public List<PostDto> listPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostById(long id);
}
