package com.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data                 //for getter and setter
@AllArgsConstructor    //const with arg.
@NoArgsConstructor
@Entity
@Table(name="posts",
       uniqueConstraints = @UniqueConstraint(columnNames = {"title"})
)
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="title", nullable=false )
    private String title;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name="content",nullable = false)
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="post")
    private List<Comment> comment;

}
