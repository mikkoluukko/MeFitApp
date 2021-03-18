package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/workout")
public class WorkoutController {

    @Autowired
    private WorkoutRepository workoutRepository;

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

    @PostMapping
    public ResponseEntity<Workout> addWorkout(@RequestBody Workout workout) {
        Workout returnWorkout = workoutRepository.save(workout);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnWorkout, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @RequestBody Workout workout) {
        Workout returnWorkout = new Workout();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!id.equals(workout.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnWorkout, status);
        }
        returnWorkout = workoutRepository.save(workout);
        status = HttpStatus.NO_CONTENT;
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
