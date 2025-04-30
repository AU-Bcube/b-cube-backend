package com.b_cube.website.domain.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDTO {

    private Long id;
    private String name;
    private int studentId;
    private String introduction;
    private String imagePath;

}

