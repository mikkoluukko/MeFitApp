package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import com.example.mefitapp.services.ExerciseSetService;
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
    public ResponseEntity<ExerciseSet> updateExerciseSet(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ExerciseSet returnExerciseSet = new ExerciseSet();
        HttpStatus status;
        if (exerciseSetRepository.existsById(id)) {
            status = HttpStatus.OK;
            ExerciseSet toBePatchedExerciseSet = exerciseSetRepository.findById(id).get();
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on exercise and set it to value v
                Field field = ReflectionUtils.findField(ExerciseSet.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, toBePatchedExerciseSet, v);
            });
            exerciseSetRepository.save(toBePatchedExerciseSet);
            returnExerciseSet = toBePatchedExerciseSet;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
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
