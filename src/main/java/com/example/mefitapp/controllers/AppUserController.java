package com.example.mefitapp.controllers;

import com.example.mefitapp.models.AppUser;
import com.example.mefitapp.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping()
    public ResponseEntity<List<AppUser>> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(users, status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long id) {
        AppUser returnUser = new AppUser();
        HttpStatus status;
        if (appUserRepository.existsById(id)) {
            status = HttpStatus.OK;
            returnUser = appUserRepository.findById(id).get();
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(returnUser, status);
    }

    @PostMapping
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user) {
        AppUser returnUser = appUserRepository.save(user);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnUser, status);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppUser> updateUser(@PathVariable Long id, @RequestBody AppUser appUser) {
        AppUser returnUser = new AppUser();
        HttpStatus status;
        /*
         We want to check if the request body matches what we see in the path variable.
         This is to ensure some level of security, making sure someone
         hasn't done some malicious stuff to our body.
        */
        if (!id.equals(appUser.getId())) {
            status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(returnUser,status);
        }
        returnUser = appUserRepository.save(appUser);
        status = HttpStatus.NO_CONTENT;
        return new ResponseEntity<>(returnUser, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        HttpStatus status;
        if (appUserRepository.existsById(id)) {
            status = HttpStatus.OK;
            appUserRepository.deleteById(id);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }


}
