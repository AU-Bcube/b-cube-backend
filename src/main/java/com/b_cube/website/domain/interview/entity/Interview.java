package com.b_cube.website.domain.interview.entity;

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
@Table(name = "Interview")
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interview_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "student_id")
    private int studentId;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "image_path")
    private String imagePath;

}
