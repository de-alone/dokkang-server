package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private Long id;
    private Long user_id;
    private String content;
    private String created_at;
}
