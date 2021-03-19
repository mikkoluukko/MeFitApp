package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Goal;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findAllByWorkouts(Workout workout);
    List<Program> findAllByProfiles(Profile profile);
    List<Program> findAllByGoals(Goal goal);
}
