package com.b_cube.website.domain.interview.repository;

import com.b_cube.website.domain.interview.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
