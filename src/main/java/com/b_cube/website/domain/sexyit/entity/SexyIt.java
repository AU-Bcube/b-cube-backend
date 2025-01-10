package com.b_cube.website.domain.sexyit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SexyIt")
public class SexyIt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sexyit_id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "title")
    private String title;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "url")
    private String url;

}
