package com.b_cube.website.domain.photo.repository;

import com.b_cube.website.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
