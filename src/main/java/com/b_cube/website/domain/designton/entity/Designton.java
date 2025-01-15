package com.b_cube.website.domain.designton.entity;

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
@Table(name = "Designton")
public class Designton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "designton_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private String year;

    @Column(name = "participant")
    private String participant;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "pdf_path")
    private String pdfPath;

}
