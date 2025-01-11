package com.b_cube.website.domain.photo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {

    private Long id;
    private String description;
    private String imagePath;

}
