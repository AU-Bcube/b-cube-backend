package com.b_cube.website.domain.activities.repository;

import com.b_cube.website.domain.activities.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesRepository extends JpaRepository<Activities, Long> {

}
