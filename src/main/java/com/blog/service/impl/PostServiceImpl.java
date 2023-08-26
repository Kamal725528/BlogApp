package com.blog.service.impl;

import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto) {

        Post post=mapToEntity(postDto);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());


        Post newPost=postRepository.save(post);

//        PostDto dto=new PostDto();
//        dto.setId(newPost.getId());
//        dto.setTitle(newPost.getTitle());
//        dto.setDescription(newPost.getDescription());
//        dto.setContent(newPost.getContent());

        PostDto dto=mapToDto(newPost);

        return dto;
    }

    @Override
    public List<PostDto> listPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
//        Sort sort=Sort.by(sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize,sort);

        Page<Post> listOfposts=postRepository.findAll(pageable);
        List<Post> posts=listOfposts.getContent();

       List<PostDto> postDtos= posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post=postRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("post not found with id : "+id));

        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post=postRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("post not find with id : "+id));

        Post newPost=mapToEntity(postDto);
        newPost.setId(id);

        Post updatedPost=postRepository.save(newPost);

        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post=postRepository.findById(id).orElseThrow( ()-> new ResourceNotFoundException("post not find with id : "+id));
        postRepository.deleteById(id);
    }

    PostDto mapToDto(Post post){
//        PostDto dto=new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());

        PostDto dto=modelMapper.map(post,PostDto.class);

        return dto;
    }

    Post mapToEntity(PostDto postDto){
//        Post post=new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post post = modelMapper.map(postDto, Post.class);

        return post;
    }


}
