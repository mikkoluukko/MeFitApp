package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Goal;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ProgramRepository;
import com.example.mefitapp.services.ProgramService;
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
@RequestMapping("/api/v1/programs")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProgramService programService;

    @GetMapping()
    public ResponseEntity<List<Program>> getAllPrograms() {
        List<Program> programs = programRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(programs, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Program> getProgram(@PathVariable Long id) {
        Program returnProgram = new Program();
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnProgram = programRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnProgram, status);
    }

    // Get all the workouts for a program
    @GetMapping("/{id}/workouts")
    public ResponseEntity<List<Workout>> getWorkoutsByProgram(@PathVariable Long id) {
        List<Workout> workoutsByProgram = new ArrayList<>();
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            workoutsByProgram = programService.getWorkoutsByProgram(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(workoutsByProgram, status);
    }

    // Get all the profiles for a program
    @GetMapping("/{id}/profiles")
    public ResponseEntity<List<Profile>> getProfilesByProgram(@PathVariable Long id) {
        List<Profile> profilesByProgram = new ArrayList<>();
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            profilesByProgram = programService.getProfilesByProgram(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(profilesByProgram, status);
    }

    // Get all the goals for a program
    @GetMapping("/{id}/goals")
    public ResponseEntity<List<Goal>> getGoalsByProgram(@PathVariable Long id) {
        List<Goal> goalsByProgram = new ArrayList<>();
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            goalsByProgram = programService.getGoalsByProgram(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(goalsByProgram, status);
    }

    @PostMapping
    public ResponseEntity<Program> addProgram(@RequestBody Program program) {
        Program returnProgram = programRepository.save(program);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnProgram, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Program returnProgram = new Program();
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            Program toBePatchedProgram = programRepository.findById(id).get();
            // Map key is field name, v is value
            updates.forEach((k, v) -> {
                // use reflection to get field k on exercise and set it to value v
                Field field = ReflectionUtils.findField(Program.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, toBePatchedProgram, v);
            });
            programRepository.save(toBePatchedProgram);
            returnProgram = toBePatchedProgram;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnProgram, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProgram(@PathVariable Long id) {
        HttpStatus status;
        if (programRepository.existsById(id)) {
            status = HttpStatus.OK;
            programRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }


}
