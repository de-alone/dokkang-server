package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLecture {
    private Long id;
    private Long lecture_id;
    private String title;
    private Integer num_likes;
    private Integer num_comments;
    private String created_at;
}
