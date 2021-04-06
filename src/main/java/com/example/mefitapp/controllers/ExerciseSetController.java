package com.example.mefitapp.controllers;

import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import com.example.mefitapp.repositories.WorkoutRepository;
import com.example.mefitapp.services.ExerciseSetService;
import com.example.mefitapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Autowired
    private SecurityService securityService;

    @Autowired
    private WorkoutRepository workoutRepository;

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
    @GetMapping("/{id}/workouts")
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
    public ResponseEntity<ExerciseSet> addExerciseSet(@RequestBody ExerciseSet exerciseSet,
            Authentication authentication) {
        if (securityService.isContributor(authentication) || securityService.isAdmin(authentication)) {
            ExerciseSet returnExercise = exerciseSetRepository.save(exerciseSet);
            HttpStatus status = HttpStatus.CREATED;
            return new ResponseEntity<>(returnExercise, status);
        } else {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExerciseSet> updateExerciseSet(@PathVariable Long id,
            @RequestBody Map<String, Object> updates, Authentication authentication) {
        ExerciseSet returnExerciseSet = new ExerciseSet();
        HttpStatus status;
        if (securityService.isContributor(authentication)) {
            if (exerciseSetRepository.existsById(id)) {
                status = HttpStatus.OK;
                ExerciseSet toBePatchedExerciseSet = exerciseSetRepository.findById(id).get();
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on exercise and set it to value v
                    Field field = ReflectionUtils.findField(ExerciseSet.class, k);
                    if (v instanceof ArrayList) {
                        ((ArrayList<?>) v).forEach((item) -> {
                            if (item instanceof LinkedHashMap) {
                                ((LinkedHashMap) item).forEach((k2, v2) -> {
                                    toBePatchedExerciseSet.getWorkouts().add(workoutRepository
                                            .findById(Long.valueOf(String.valueOf(v2))).get());
                                });
                            }
                        });
                    } else {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, toBePatchedExerciseSet, v);
                    }
                });
                exerciseSetRepository.save(toBePatchedExerciseSet);
                returnExerciseSet = toBePatchedExerciseSet;
            } else {
                status = HttpStatus.NOT_FOUND;
            }
            return new ResponseEntity<>(returnExerciseSet, status);
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExerciseSet(@PathVariable Long id, Authentication authentication) {
        HttpStatus status;
        if (securityService.isAdmin(authentication)) {
            if (exerciseSetRepository.existsById(id)) {
                status = HttpStatus.OK;
                exerciseSetRepository.deleteById(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(status);
    }


}
