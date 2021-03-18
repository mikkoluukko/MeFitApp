package com.example.mefitapp.controllers;

import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import com.example.mefitapp.services.ExerciseSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/sets")
public class ExerciseSetController {

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    @Autowired
    private ExerciseSetService exerciseSetService;

    @GetMapping()
    public ResponseEntity<List<ExerciseSet>> getAllExerciseSets() {
        List<ExerciseSet> exerciseSets = exerciseSetRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(exerciseSets, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseSet> getExerciseSet(@PathVariable Long id) {
        ExerciseSet returnExerciseSet = new ExerciseSet();
        HttpStatus status;
        if (exerciseSetRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnExerciseSet = exerciseSetRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnExerciseSet, status);
    }

    // Get all the workouts for a set
    @GetMapping("/{id}/sets")
    public ResponseEntity<List<Workout>> getWorkoutsByExerciseSet(@PathVariable Long id) {
        List<Workout> workoutsByExerciseSets = new ArrayList<>();
        HttpStatus status;
        if (exerciseSetRepository.existsById(id)) {
            status = HttpStatus.OK;
            workoutsByExerciseSets = exerciseSetService.getWorkoutsByExerciseSet(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(workoutsByExerciseSets, status);
    }

    @PostMapping
    public ResponseEntity<ExerciseSet> addExerciseSet(@RequestBody ExerciseSet exerciseSet) {
        ExerciseSet returnExercise = exerciseSetRepository.save(exerciseSet);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnExercise, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExerciseSet> updateExerciseSet(@PathVariable Long id, @RequestBody ExerciseSet exerciseSet) {
        ExerciseSet returnExerciseSet = new ExerciseSet();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!id.equals(exerciseSet.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnExerciseSet, status);
        }
        returnExerciseSet = exerciseSetRepository.save(exerciseSet);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnExerciseSet, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExerciseSet(@PathVariable Long id) {
        HttpStatus status;
        if (exerciseSetRepository.existsById(id)) {
            status = HttpStatus.OK;
            exerciseSetRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }


}
