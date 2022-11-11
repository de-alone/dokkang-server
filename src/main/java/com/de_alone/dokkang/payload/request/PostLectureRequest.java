package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLectureRequest {
    private Integer limit;
    private String before;
}
