package com.example.mefitapp.controllers;

import com.example.mefitapp.models.*;
import com.example.mefitapp.repositories.GoalRepository;
import com.example.mefitapp.repositories.WorkoutRepository;
import com.example.mefitapp.services.GoalService;
import com.example.mefitapp.services.WorkoutService;
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
@RequestMapping("/api/v1/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private GoalService goalService;

    @GetMapping()
    public ResponseEntity<List<Goal>> getAllGoals() {
        List<Goal> goals = goalRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(goals, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable Long id) {
        Goal returnGoal = new Goal();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnGoal = goalRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnGoal, status);
    }

    // Get all the workouts for a goal
    @GetMapping("/{id}/workouts")
    public ResponseEntity<List<Workout>> getWorkoutsByGoal(@PathVariable Long id) {
        List<Workout> workoutsByGoal = new ArrayList<>();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            workoutsByGoal = goalService.getWorkoutsByGoal(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(workoutsByGoal, status);
    }

    // Get all the profiles for a goal
    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfilesByGoal(@PathVariable Long id) {
        List<Profile> profilesByGoal = new ArrayList<>();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            profilesByGoal = goalService.getProfilesByGoal(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(profilesByGoal, status);
    }

    // Get all the programs for a goal
    @GetMapping("/{id}/programs")
    public ResponseEntity<List<Program>> getProgramsByGoal(@PathVariable Long id) {
        List<Program> programsByGoal = new ArrayList<>();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            programsByGoal = goalService.getProgramsByGoal(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(programsByGoal, status);
    }

    @PostMapping
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        Goal returnGoal = goalRepository.save(goal);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnGoal, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Goal returnGoal = new Goal();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            Goal toBePatchedGoal = goalRepository.findById(id).get();
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on exercise and set it to value v
                Field field = ReflectionUtils.findField(Goal.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, toBePatchedGoal, v);
            });
            goalRepository.save(toBePatchedGoal);
            returnGoal = toBePatchedGoal;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnGoal, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable Long id) {
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            status = HttpStatus.OK;
            goalRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }
}
