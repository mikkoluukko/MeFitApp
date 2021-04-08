package com.mefitapp.repositories;

import com.mefitapp.models.Profile;
import com.mefitapp.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {
}
