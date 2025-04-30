package com.b_cube.website.domain.executives.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecutivesDTO {

    private Long id;
    private String role;
    private String studentId;
    private String name;
    private String imagePath;

}
