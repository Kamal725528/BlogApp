package com.blog.controller;

import com.blog.payload.CommentDto;
import com.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //http://localhost:8080/api/posts/2/comments

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> createComment(@PathVariable("postId") long postId, @Valid @RequestBody CommentDto commentDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getaAllCommentByPostId(@PathVariable("postId") long postId){
        return commentService.getCommentByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId){
        CommentDto dto=commentService.getCommentByCommentId(postId,commentId);
        return dto;
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId, @PathVariable("commentId") long commentId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto dto=commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,@PathVariable("id") long id){
        commentService.deleteComment(postId,id);
        return new ResponseEntity<>("comment deleted",HttpStatus.OK);
    }

}
