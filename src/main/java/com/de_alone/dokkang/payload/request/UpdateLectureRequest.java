package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateLectureRequest {
    private List<Long> lecture_ids;
}
