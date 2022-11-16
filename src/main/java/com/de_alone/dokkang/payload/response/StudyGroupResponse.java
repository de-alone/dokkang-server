package com.de_alone.dokkang.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class StudyGroupResponse {
    private String status;
    private Long id;
    private Long lecture_id;
    private Long user_id;
    private String username;
    private String title;
    private String content;
    private String created_at;
    private Integer num_likes;
    private List<Comment> comments;
    private List<String> participants;
    private String studytime;
    private String studyplace;
    private Integer studycapacity;
}
