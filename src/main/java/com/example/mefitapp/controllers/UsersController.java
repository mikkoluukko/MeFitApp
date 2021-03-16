package com.example.mefitapp.controllers;

import com.example.mefitapp.models.Users;
import com.example.mefitapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping()
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(users, status);
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user) {
        Users returnUser = usersRepository.save(user);
        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(returnUser, status);
    }
}
