package com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;

import java.util.ArrayList;
import java.util.List;

public class SprintBacklogManager {
    public List<UserStory> generateSprintBacklog(List<UserStory> userStoryList, int totalStoryPoints){
        List<UserStory> sprintBacklog = new ArrayList<>();
        userStoryList.sort((a,b)->Double.compare(b.getBusinessValue(),a.getBusinessValue()));
        for(UserStory userStory: userStoryList){
            int storyPoints = (int)userStory.getPointValue();

            if(storyPoints<=totalStoryPoints){
                sprintBacklog.add(userStory);
                totalStoryPoints -= storyPoints;
            }

        }
        return sprintBacklog;

    }
}