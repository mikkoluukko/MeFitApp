package com.example.mefitapp.controllers;

import com.example.mefitapp.models.*;
import com.example.mefitapp.repositories.WorkoutRepository;
import com.example.mefitapp.services.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/workouts")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private WorkoutService workoutService;

    @GetMapping()
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        List<Workout> workouts = workoutRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(workouts, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkout(@PathVariable Long id) {
        Workout returnWorkout = new Workout();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnWorkout = workoutRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnWorkout, status);
    }

    // Get all the sets for a workout
    @GetMapping("/{id}/sets")
    public ResponseEntity<List<ExerciseSet>> getExerciseSetsByWorkout(@PathVariable Long id) {
        List<ExerciseSet> exerciseSetsByWorkout = new ArrayList<>();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            exerciseSetsByWorkout = workoutService.getExerciseSetsByWorkout(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(exerciseSetsByWorkout, status);
    }

    // Get all the profiles for a workout
    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfilesByWorkout(@PathVariable Long id) {
        List<Profile> profilesByWorkout = new ArrayList<>();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            profilesByWorkout = workoutService.getProfilesByWorkout(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(profilesByWorkout, status);
    }

    // Get all the programs for a workout
    @GetMapping("/{id}/programs")
    public ResponseEntity<List<Program>> getProgramsByWorkout(@PathVariable Long id) {
        List<Program> programsByWorkout = new ArrayList<>();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            programsByWorkout = workoutService.getProgramsByWorkout(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(programsByWorkout, status);
    }

    // Get all the goals for a workout
    @GetMapping("/{id}/goals")
    public ResponseEntity<List<Goal>> getGoalsByWorkout(@PathVariable Long id) {
        List<Goal> goalsByWorkout = new ArrayList<>();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            goalsByWorkout = workoutService.getGoalsByWorkout(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(goalsByWorkout, status);
    }

    @PostMapping
    public ResponseEntity<Workout> addWorkout(@RequestBody Workout workout) {
        Workout returnWorkout = workoutRepository.save(workout);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnWorkout, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Workout returnWorkout = new Workout();
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            Workout toBePatchedWorkout = workoutRepository.findById(id).get();
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on exercise and set it to value v
                Field field = ReflectionUtils.findField(Workout.class, k);
                if (v instanceof ArrayList) {
                    ((ArrayList<?>) v).forEach((item) -> {
                        if (item instanceof LinkedHashMap) {
                            ((LinkedHashMap) item).forEach((itemKey, itemValue) -> {
                                workoutService.updateList(k, id, itemValue.toString());
                            });
                        }
                    });
                } else {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, toBePatchedWorkout, v);
                }
            });
            workoutRepository.save(toBePatchedWorkout);
            returnWorkout = toBePatchedWorkout;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnWorkout, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Long id) {
        HttpStatus status;
        if (workoutRepository.existsById(id)) {
            status = HttpStatus.OK;
            workoutRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }
}
