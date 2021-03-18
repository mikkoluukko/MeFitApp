package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findAllByWorkouts(Workout workout);
}
