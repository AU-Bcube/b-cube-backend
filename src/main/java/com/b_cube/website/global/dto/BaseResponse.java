package com.b_cube.website.global.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseResponse {
    private String message;
}
