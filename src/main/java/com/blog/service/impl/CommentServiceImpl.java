package com.blog.service.impl;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository,ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postid, CommentDto commentDto) {
        Post post=postRepository.findById(postid).orElseThrow( ()->new ResourceNotFoundException("post not found with id : "+postid));

        Comment comment=mapToEntity(commentDto);
        comment.setPost(post);

       Comment newComment= commentRepository.save(comment);
       CommentDto dto=mapToDto(newComment);

       return dto;

    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        Post post=postRepository.findById(postId).orElseThrow( ()->new ResourceNotFoundException("post not found with id : "+postId));
        List<Comment> comments=commentRepository.findByPostId(postId);

        List<CommentDto> dto=comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());

        return dto;
    }

    @Override
    public CommentDto getCommentByCommentId(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow( ()->new ResourceNotFoundException("post not found with id : "+postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow( ()->new ResourceNotFoundException("comment not found with id: "+commentId));
        return mapToDto(comment);
    }


    @Override
    public void deleteComment(long postId, long id) {
        Post post=postRepository.findById(postId).orElseThrow( ()->new ResourceNotFoundException("post is not found with id : "+postId));
         commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post not found with id : "+postId));
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment not found with id : "+commentId));
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment=commentRepository.save(comment);
        return mapToDto(updatedComment);
    }


    public Comment mapToEntity(CommentDto commentDto){
//        Comment c1=new Comment();
//        c1.setBody(commentDto.getBody());
//        c1.setName(commentDto.getName());
//        c1.setEmail(commentDto.getEmail());

        Comment c1=modelMapper.map(commentDto,Comment.class);

        return c1;
    }

    public CommentDto mapToDto(Comment comment){
//        CommentDto dto=new CommentDto();
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());
//        dto.setId(comment.getId());

        CommentDto dto=modelMapper.map(comment,CommentDto.class);
        return dto;

    }
}
