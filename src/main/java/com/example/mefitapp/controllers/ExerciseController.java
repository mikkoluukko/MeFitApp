package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.repositories.ExerciseRepository;
import com.example.mefitapp.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/exercise")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping()
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(exercises, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExercise(@PathVariable Long id) {
        Exercise returnExercise = new Exercise();
        HttpStatus status;
        if (exerciseRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnExercise = exerciseRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnExercise, status);
    }

    // Get all the sets for an exercise
    @GetMapping("/{id}/sets")
    public ResponseEntity<List<ExerciseSet>> getExerciseSetsForExercise(@PathVariable Long id) {
        List<ExerciseSet> exerciseSetsForExercise = new ArrayList<>();
        HttpStatus status;
        if (exerciseRepository.existsById(id)) {
            status = HttpStatus.OK;
            exerciseSetsForExercise = exerciseService.getExerciseSetsForExercise(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(exerciseSetsForExercise, status);
    }

    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise returnExercise = exerciseRepository.save(exercise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnExercise, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exercise) {
        Exercise returnExercise = new Exercise();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!id.equals(exercise.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnExercise,status);
        }
        returnExercise = exerciseRepository.save(exercise);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnExercise, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExercise(@PathVariable Long id) {
        HttpStatus status;
        if (exerciseRepository.existsById(id)) {
            status = HttpStatus.OK;
            exerciseRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }


}
