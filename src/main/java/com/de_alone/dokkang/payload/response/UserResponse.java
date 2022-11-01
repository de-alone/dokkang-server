package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private String status;
    private Long id;
    private String username;
    private String email;
}