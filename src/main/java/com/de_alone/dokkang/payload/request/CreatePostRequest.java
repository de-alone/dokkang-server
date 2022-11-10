package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreatePostRequest {
    @NotBlank
    private Long lecture_id;

    @NotBlank
    private Long user_id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
