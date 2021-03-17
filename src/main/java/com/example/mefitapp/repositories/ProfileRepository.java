package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
