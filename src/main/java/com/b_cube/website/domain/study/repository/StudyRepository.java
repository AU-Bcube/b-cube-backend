package com.b_cube.website.domain.study.repository;

import com.b_cube.website.domain.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
