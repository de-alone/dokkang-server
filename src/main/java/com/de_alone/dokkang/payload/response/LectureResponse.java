package com.de_alone.dokkang.payload.response;

import com.de_alone.dokkang.models.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LectureResponse {
    private String status;

    private List<Lecture> lectures;

}
