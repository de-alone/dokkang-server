package com.de_alone.dokkang.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateStudyGroupRequest {
    private Long lecture_id;
    private Long user_id;
    private String title;
    private String content;
    private Integer studycapacity;
    private String studytime;
    private String studyplace;
}
