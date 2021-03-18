package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
