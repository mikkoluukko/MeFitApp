package com.example.mefitapp.repositories;

import com.example.mefitapp.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByWorkouts(Workout workout);
    List<Goal> findAllByProfile(Profile profile);
    List<Goal> findAllByPrograms(Program program);
}