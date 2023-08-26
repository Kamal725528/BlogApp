package com.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="body")
    private String body;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
     private Post post;
}
