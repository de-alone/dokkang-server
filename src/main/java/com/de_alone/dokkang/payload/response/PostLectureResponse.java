package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PostLectureResponse {
    private String status;

    private List<PostLecture> posts;

    private String before;
}
