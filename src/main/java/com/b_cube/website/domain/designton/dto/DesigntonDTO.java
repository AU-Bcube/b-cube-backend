package com.b_cube.website.domain.designton.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DesigntonDTO {

    private Long id;
    private String title;
    private String year;
    private String participant;
    private String imagePath;
    private String pdfPath;

}
