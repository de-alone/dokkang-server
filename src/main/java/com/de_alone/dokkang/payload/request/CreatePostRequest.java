package com.de_alone.dokkang.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreatePostRequest {
    private Long lecture_id;
    private Long user_id;
    private String title;
    private String content;
}
