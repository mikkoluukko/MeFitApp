package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.repositories.ExerciseRepository;
import com.example.mefitapp.services.ExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public ResponseEntity<List<ExerciseSet>> getExerciseSetsByExercise(@PathVariable Long id) {
        List<ExerciseSet> exerciseSetsByExercise = new ArrayList<>();
        HttpStatus status;
        if (exerciseRepository.existsById(id)) {
            status = HttpStatus.OK;
            exerciseSetsByExercise = exerciseService.getExerciseSetsByExercise(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(exerciseSetsByExercise, status);
    }

    @PostMapping
    public ResponseEntity<Exercise> addExercise(@RequestBody Exercise exercise) {
        Exercise returnExercise = exerciseRepository.save(exercise);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnExercise, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Exercise returnExercise = new Exercise();
        HttpStatus status;
        if (exerciseRepository.existsById(id)) {
            status = HttpStatus.OK;
            Exercise toBePatchedExercise = exerciseRepository.findById(id).get();
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on exercise and set it to value v
                Field field = ReflectionUtils.findField(Exercise.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, toBePatchedExercise, v);
            });
            exerciseRepository.save(toBePatchedExercise);
            returnExercise = toBePatchedExercise;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
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
