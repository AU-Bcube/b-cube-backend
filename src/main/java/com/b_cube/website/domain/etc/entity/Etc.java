package com.b_cube.website.domain.etc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Etc")
public class Etc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "etc_id")
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "title")
    private String title;

    @Column(name = "participant")
    private String participant;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "pdf_path")
    private String pdfPath;

}
