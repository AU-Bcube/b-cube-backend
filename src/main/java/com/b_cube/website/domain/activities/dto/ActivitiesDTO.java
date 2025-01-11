package com.b_cube.website.domain.activities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivitiesDTO {
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private String pdfPath;
}
