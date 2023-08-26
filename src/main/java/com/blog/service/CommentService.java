package com.blog.service;

import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(long id,CommentDto commentDto);

    public List<CommentDto> getCommentByPostId(long postId);

    public CommentDto getCommentByCommentId(long postId, long commentId);

    void deleteComment(long postId, long id);


    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
}
