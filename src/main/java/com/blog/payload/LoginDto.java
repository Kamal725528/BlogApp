package com.blog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {
    private String userNameOrEmail;
    private String password;
}
