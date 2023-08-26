package com.blog.controller;

import com.blog.payload.PostDto;
import com.blog.service.impl.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostServiceImpl postService;   // either use autowired or constructor to DI

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        PostDto dto=postService.createPost(postDto);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }

    // pagination added
    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=desc

    @GetMapping
    public List<PostDto> listAllPost(@RequestParam(value = "pageNo",defaultValue ="0",required = false) int pageNo,
                                     @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
                                     @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                     @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){

        List<PostDto> postDtos= postService.listPost(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostbyId(@PathVariable("id") long id){
        PostDto postDto=postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("id") long id){
        PostDto dto=postService.updatePost(postDto,id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

   @DeleteMapping("/{id}")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("post deleted",HttpStatus.OK);

    }




}
