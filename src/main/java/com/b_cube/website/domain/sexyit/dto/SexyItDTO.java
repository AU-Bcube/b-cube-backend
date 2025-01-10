package com.b_cube.website.domain.sexyit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SexyItDTO {

    private Long id;
    private LocalDate date;
    private String title;
    private String imagePath;
    private String url;


}
