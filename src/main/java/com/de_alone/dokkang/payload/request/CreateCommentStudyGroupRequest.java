package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentStudyGroupRequest {
    private Long studygroup_id;
    private Long user_id;
    private String content;
}
