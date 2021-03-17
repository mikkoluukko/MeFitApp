package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Profile;
import com.example.mefitapp.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping()
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(profiles, status);
    }

    @GetMapping("/{profile_id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long profile_id) {
        Profile returnProfile = new Profile();
        HttpStatus status;
        if (profileRepository.existsById(profile_id)) {
            status = HttpStatus.OK;
            returnProfile = profileRepository.findById(profile_id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnProfile, status);
    }

    @PostMapping
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        Profile returnProfile = profileRepository.save(profile);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnProfile, status);
    }

    @PutMapping("/{profile_id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long profile_id, @RequestBody Profile profile) {
        Profile returnProfile = new Profile();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!profile_id.equals(profile.getProfile_id())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnProfile,status);
        }
        returnProfile = profileRepository.save(profile);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnProfile, status);
    }

    @DeleteMapping("/{profile_id}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable Long profile_id) {
        HttpStatus status;
        if (profileRepository.existsById(profile_id)) {
            status = HttpStatus.OK;
            profileRepository.deleteById(profile_id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }


}
