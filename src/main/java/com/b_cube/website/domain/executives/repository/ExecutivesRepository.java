package com.b_cube.website.domain.executives.repository;

import com.b_cube.website.domain.executives.entity.Executives;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutivesRepository extends JpaRepository<Executives, Long> {
}
