package com.b_cube.website.domain.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyDTO {

    private Long id;
    private LocalDate date;
    private String title;
    private String imagePath;

}
