package com.example.mefitapp.services;

import com.example.mefitapp.models.Exercise;
import com.example.mefitapp.models.ExerciseSet;
import com.example.mefitapp.repositories.ExerciseRepository;
import com.example.mefitapp.repositories.ExerciseSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseSetRepository exerciseSetRepository;

    public List<ExerciseSet> getExerciseSetsByExercise(Long id) {
        Exercise returnExercise = exerciseRepository.findById(id).get();
        return exerciseSetRepository.findAllByExercise(returnExercise);
    }
}
