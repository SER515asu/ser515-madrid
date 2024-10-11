package com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.User;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;

import java.util.ArrayList;
import java.util.List;

public class SprintBacklogManager {
    public List<UserStory> generateSprintBacklog(List<UserStory> userStoryList, int totalStoryPoints){
        List<UserStory> sprintBacklog = new ArrayList<>();
        backtrack(userStoryList, sprintBacklog, new ArrayList<>(), 0, totalStoryPoints, 0);
        return sprintBacklog;
//        userStoryList.sort((a,b)->Double.compare(b.getBusinessValue(),a.getBusinessValue()));
//
//        for(UserStory userStory: userStoryList){
//            int storyPoints = (int)userStory.getPointValue();
//
//            if(storyPoints<=totalStoryPoints){
//                sprintBacklog.add(userStory);
//                totalStoryPoints -= storyPoints;
//            }
//
//        }

//        return sprintBacklog;

    }
    private void backtrack(List<UserStory> stories, List<UserStory> sprintBacklog, List<UserStory> currentSelection,
                           int start, int remainingPoints, int currentValue) {

        // If current selection is better, update the sprint backlog
        if (calculateBusinessValue(currentSelection) > calculateBusinessValue(sprintBacklog)) {
            sprintBacklog.clear();
            sprintBacklog.addAll(currentSelection);
        }

        // Iterate over available stories starting from 'start'
        for (int i = start; i < stories.size(); i++) {
            UserStory story = stories.get(i);
            int storyPoints = (int) story.getPointValue();

            // Proceed if adding this story doesn't exceed remaining points
            if (storyPoints <= remainingPoints) {
                currentSelection.add(story); // Add story to current selection
                backtrack(stories, sprintBacklog, currentSelection, i + 1, remainingPoints - storyPoints,
                        currentValue + (int) story.getBusinessValue());
                currentSelection.remove(currentSelection.size() - 1); // Backtrack (remove last added)
            }
        }
    }

    /**
     * Helper function to calculate the total business value of a list of user stories.
     */
    private int calculateBusinessValue(List<UserStory> userStories) {
        return userStories.stream().mapToInt(us -> (int) us.getBusinessValue()).sum();
    }
}
