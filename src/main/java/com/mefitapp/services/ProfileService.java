package com.mefitapp.services;

import com.mefitapp.models.Profile;
import com.mefitapp.repositories.GoalRepository;
import com.mefitapp.repositories.ProfileRepository;
import com.mefitapp.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GoalRepository goalRepository;

    public List<Goal> getGoalsByProfile(String id) {
        Profile returnProfile = profileRepository.findById(id).get();
        return goalRepository.findAllByProfile(returnProfile);
    }

    public void updateList(Object listName, String profileId, String itemId) {
        if ("goals".equals(listName.toString())) {
            updateGoals(profileId, Long.valueOf(itemId));
        }
    }

    public void updateGoals(String profileId, Long goalId) {
        Profile toBePatchedProfile = profileRepository.findById(profileId).get();
        Goal toBeAddedGoal = goalRepository.findById(goalId).get();
        toBePatchedProfile.getGoals().add(toBeAddedGoal);
        profileRepository.save(toBePatchedProfile);
    }
}
