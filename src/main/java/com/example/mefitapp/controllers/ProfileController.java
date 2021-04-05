package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Goal;
import com.example.mefitapp.models.Profile;
import com.example.mefitapp.models.Program;
import com.example.mefitapp.models.Workout;
import com.example.mefitapp.repositories.ProfileRepository;
import com.example.mefitapp.services.ProfileService;
import com.example.mefitapp.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SecurityService securityService;

    @GetMapping()
    public ResponseEntity<List<Profile>> getAllProfiles(Authentication authentication) {
        if (securityService.isAdmin(authentication)) {
            List<Profile> profiles = profileRepository.findAll();
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(profiles, status);
        } else {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(
            @PathVariable String id, Principal principal, Authentication authentication) {
        Profile returnProfile = new Profile();
        HttpStatus status;
        if (securityService.isOwner(id, principal) || securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                returnProfile = profileRepository.findById(id).get();
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(returnProfile, status);
    }

    // Get all the workouts for a profile
    @GetMapping("/{id}/workouts")
    public ResponseEntity<List<Workout>> getWorkoutsByProfile(
            @PathVariable String id, Principal principal, Authentication authentication) {
        List<Workout> workoutsByProfile = new ArrayList<>();
        HttpStatus status;
        if (securityService.isOwner(id, principal) || securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                workoutsByProfile = profileService.getWorkoutsByProfile(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(workoutsByProfile, status);
    }

    // Get all the programs for a profile
    @GetMapping("/{id}/programs")
    public ResponseEntity<List<Program>> getProgramsByProfile(
            @PathVariable String id, Principal principal, Authentication authentication) {
        List<Program> programsByProfile = new ArrayList<>();
        HttpStatus status;
        if (securityService.isOwner(id, principal) || securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                programsByProfile = profileService.getProgramsByProfile(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(programsByProfile, status);
    }

    // Get all the goals for a profile
    @GetMapping("/{id}/goals")
    public ResponseEntity<List<Goal>> getGoalsByProfile(
            @PathVariable String id, Principal principal, Authentication authentication) {
        List<Goal> goalsByProfile = new ArrayList<>();
        HttpStatus status;
        if (securityService.isOwner(id, principal) || securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                goalsByProfile = profileService.getGoalsByProfile(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(goalsByProfile, status);
    }

    @PostMapping
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        Profile returnProfile = profileRepository.save(profile);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnProfile, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable String id, @RequestBody Map<String, Object> updates,
            Principal principal, Authentication authentication) {
        Profile returnProfile = new Profile();
        HttpStatus status;
        if (securityService.isOwner(id, principal) || securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                Profile toBePatchedProfile = profileRepository.findById(id).get();
                // Map key is field name, v is value
                updates.forEach((k, v) -> {
                    // use reflection to get field k on exercise and set it to value v
                    Field field = ReflectionUtils.findField(Profile.class, k);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, toBePatchedProfile, v);
                });
                profileRepository.save(toBePatchedProfile);
                returnProfile = toBePatchedProfile;
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            return new ResponseEntity<>(status);
        }
        return new ResponseEntity<>(returnProfile, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable String id, Authentication authentication) {
        HttpStatus status;
        if (securityService.isAdmin(authentication)) {
            if (profileRepository.existsById(id)) {
                status = HttpStatus.OK;
                profileRepository.deleteById(id);
            } else {
                status = HttpStatus.NOT_FOUND;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(status);
    }
}
