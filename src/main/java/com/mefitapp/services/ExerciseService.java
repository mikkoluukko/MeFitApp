package com.mefitapp.services;

import com.mefitapp.models.Exercise;
import com.mefitapp.models.ExerciseSet;
import com.mefitapp.repositories.ExerciseRepository;
import com.mefitapp.repositories.ExerciseSetRepository;
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
