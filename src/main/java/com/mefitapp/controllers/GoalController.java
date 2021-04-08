package com.mefitapp.controllers;

import com.mefitapp.models.Workout;
import com.mefitapp.repositories.GoalRepository;
import com.mefitapp.services.GoalService;
import com.mefitapp.services.SecurityService;
import com.mefitapp.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

    @Autowired
    private SecurityService securityService;

    @GetMapping()
    public ResponseEntity<List<Goal>> getAllGoals(Authentication authentication) {
        if (securityService.isAdmin(authentication)) {
            List<Goal> goals = goalRepository.findAll();
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(goals, status);
        } else {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoal(@PathVariable Long id, Principal principal, Authentication authentication) {
        Goal returnGoal = new Goal();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            String ownerId = goalRepository.findById(id).get().getProfile().getId();
            if (securityService.isAdmin(authentication) || securityService.isOwner(ownerId, principal)) {
                status = HttpStatus.OK;
                returnGoal = goalRepository.findById(id).get();
            } else {
                status = HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<>(status);
            }
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnGoal, status);
    }

    // Get all the workouts for a goal
    @GetMapping("/{id}/workouts")
    public ResponseEntity<List<Workout>> getWorkoutsByGoal(
            @PathVariable Long id, Principal principal, Authentication authentication) {
        List<Workout> workoutsByGoal = new ArrayList<>();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            String ownerId = goalRepository.findById(id).get().getProfile().getId();
            if (securityService.isAdmin(authentication) || securityService.isOwner(ownerId, principal)) {
                status = HttpStatus.OK;
                workoutsByGoal = goalService.getWorkoutsByGoal(id);
            } else {
                status = HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<>(status);
            }
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(workoutsByGoal, status);
    }

    @PostMapping
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal) {
        Goal returnGoal = goalRepository.save(goal);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnGoal, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long id,
            @RequestBody Map<String, Object> updates, Principal principal, Authentication authentication) {
        Goal returnGoal = new Goal();
        HttpStatus status;
        if (goalRepository.existsById(id)) {
            String ownerId = goalRepository.findById(id).get().getProfile().getId();
            if (securityService.isAdmin(authentication) || securityService.isOwner(ownerId, principal)) {
                status = HttpStatus.OK;
                Goal toBePatchedGoal = goalRepository.findById(id).get();
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on exercise and set it to value v
                    Field field = ReflectionUtils.findField(Goal.class, k);
                    if (v instanceof ArrayList) {
                        ((ArrayList<?>) v).forEach((item) -> {
                            if (item instanceof LinkedHashMap) {
                                ((LinkedHashMap) item).forEach((itemKey, itemValue) -> {
                                    goalService.updateList(k, id, itemValue.toString());
                                });
                            }
                        });
                    } else {
                        field.setAccessible(true);
                        ReflectionUtils.setField(field, toBePatchedGoal, v);
                    }
                });
                goalRepository.save(toBePatchedGoal);
                returnGoal = toBePatchedGoal;
            } else {
                status = HttpStatus.FORBIDDEN;
                return new ResponseEntity<>(status);
            }
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnGoal, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable Long id, Authentication authentication) {
        HttpStatus status;
        if (securityService.isAdmin(authentication)) {
            if (goalRepository.existsById(id)) {
                status = HttpStatus.OK;
                goalRepository.deleteById(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(status);
    }
}
