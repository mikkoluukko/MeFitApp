package com.example.mefitapp.repositories;

import com.example.mefitapp.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
