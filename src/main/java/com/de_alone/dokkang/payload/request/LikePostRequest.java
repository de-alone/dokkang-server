package com.de_alone.dokkang.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikePostRequest {
    private Long post_id;
    private Long user_id;
}
