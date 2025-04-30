package com.b_cube.website.domain.executives.entity;

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
@Table(name = "Executives")
public class Executives {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "executives_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "role")
    private String role;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "image_path")
    private String imagePath;
}
