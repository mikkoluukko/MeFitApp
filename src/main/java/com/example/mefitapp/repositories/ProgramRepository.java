package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findAllByWorkouts(Workout workout);
    List<Program> findAllByProfiles(Profile profile);
}
