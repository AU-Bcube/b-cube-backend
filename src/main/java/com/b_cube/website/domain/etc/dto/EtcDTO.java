package com.b_cube.website.domain.etc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtcDTO {

    private Long id;
    private String year;
    private String title;
    private String participant;
    private String imagePath;
    private String pdfPath;

}
