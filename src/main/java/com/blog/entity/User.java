package com.blog.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username",unique = true)
    private String userName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name="name")
    private String name;

    @Column(name = "password")
    private String password;


   @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
   @JoinTable(name = "user_roles",joinColumns=@JoinColumn(name = "user_id",referencedColumnName = "id"),
                inverseJoinColumns=@JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Set<Role>roles;

}
