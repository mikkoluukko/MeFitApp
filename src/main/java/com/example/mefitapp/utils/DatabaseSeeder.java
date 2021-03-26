package com.example.mefitapp.utils;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/populate-database")
public class DatabaseSeeder {

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
        Exercise exercise1 = new Exercise("Squat");
        Exercise exercise2 = new Exercise("Pushup");
        Exercise exercise3 = new Exercise("Plank");
        ExerciseSet set1 = new ExerciseSet(20, exercise1);
        ExerciseSet set2 = new ExerciseSet(10, exercise2);
        ExerciseSet set3 = new ExerciseSet(20, exercise2);
        ExerciseSet set4 = new ExerciseSet(3, exercise3);

        exerciseRepository.saveAll(Arrays.asList(exercise1, exercise2, exercise3));
        exerciseSetRepository.saveAll(Arrays.asList(set1, set2, set3, set4));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
