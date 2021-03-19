package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ProgramRepository;
import com.example.mefitapp.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping
    public ResponseEntity<Program> addProgram(@RequestBody Program program) {
        Program returnProgram = programRepository.save(program);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnProgram, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Program> updateProgram(@PathVariable Long id, @RequestBody Program program) {
        Program returnProgram = new Program();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!id.equals(program.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnProgram, status);
        }
        returnProgram = programRepository.save(program);
        status = HttpStatus.NO_CONTENT;
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
