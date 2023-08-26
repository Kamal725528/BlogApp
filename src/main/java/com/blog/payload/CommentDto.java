package com.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    @Size(min=5,message = "body should not be less than 5 character")
    private String body;
    private String name;
    @Email(message = "enter valid email")
    private String email;
}
