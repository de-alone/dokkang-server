package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentStudyGroupRequest {
    private Long studygroup_id;
    private Long user_id;
    private String content;

    public CreateCommentStudyGroupRequest(Long studygroup_id, Long user_id, String content) {
        this.studygroup_id = studygroup_id;
        this.user_id = user_id;
        this.content = content;
    }
}
