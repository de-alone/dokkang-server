package com.de_alone.dokkang.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLectureRequest {
    private Integer limit;
    private String before;

    public PostLectureRequest(Integer limit) {
        this.limit = limit;
        this.before = null;
    }
}
