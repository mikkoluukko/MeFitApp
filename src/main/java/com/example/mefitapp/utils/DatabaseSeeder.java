package com.example.mefitapp.utils;

import com.example.mefitapp.models.AppUser;
import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/populate-database")
public class DatabaseSeeder {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @PostMapping("")
    public ResponseEntity<HttpStatus> populateDatabase() {
        AppUser user1 = new AppUser("admin@admin.com", "admin", true, true);
        AppUser user2 = new AppUser("contributor@contributor.com", "contributor", true, false);
        AppUser user3 = new AppUser("testuser1@test.com", "testuser1", false, false);
        AppUser user4 = new AppUser("testuser2@test.com", "testuser2", false, false);
        AppUser user5 = new AppUser("testuser3@test.com", "testuser3", false, false);
        AppUser user6 = new AppUser("testuser4@test.com", "testuser4", false, false);
        AppUser user7 = new AppUser("testuser5@test.com", "testuser5", false, false);
        Profile profile1 = new Profile("Antti", "Admin", 70, 170, user1);
        Profile profile2 = new Profile("Kalle", "Kontributor", 100, 170, user2);
        Profile profile3 = new Profile("Test-1", "User-1", 70, 200, user3);
        Profile profile4 = new Profile("Test-2", "User-2", 90, 155, user4);
        Profile profile5 = new Profile("Test-3", "User-3", 80, 180, user5);
        Profile profile6 = new Profile("Test-4", "User-4", 65, 165, user6);
        Exercise exercise1 = new Exercise("Squat");
        Exercise exercise2 = new Exercise("Pushup");
        Exercise exercise3 = new Exercise("Plank");
        ExerciseSet set1 = new ExerciseSet(20, exercise1);
        ExerciseSet set2 = new ExerciseSet(10, exercise2);
        ExerciseSet set3 = new ExerciseSet(20, exercise2);
        ExerciseSet set4 = new ExerciseSet(3, exercise3);

        appUserRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6, user7));
        profileRepository.saveAll(Arrays.asList(profile1, profile2, profile3, profile4, profile5, profile6));
        exerciseRepository.saveAll(Arrays.asList(exercise1, exercise2, exercise3));
        exerciseSetRepository.saveAll(Arrays.asList(set1, set2, set3, set4));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
