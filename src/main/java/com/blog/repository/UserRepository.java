package com.blog.repository;

import com.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserNameOrEmail(String username,String email);
    Optional<User> findByUserName(String name);
    Boolean existsByUserName(String name);
    Boolean existsByEmail(String email);

}
