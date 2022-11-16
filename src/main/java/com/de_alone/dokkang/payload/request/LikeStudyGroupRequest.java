package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeStudyGroupRequest {
    private Long studygroup_id;
    private Long user_id;
}
