package com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;

import java.util.ArrayList;
import java.util.List;

public class SprintBacklogManager {
    public List<UserStory> generateSprintBacklog(List<UserStory> userStoryList, int totalStoryPoints){
        List<UserStory> sprintBacklog = new ArrayList<>();
        selectUserStories(userStoryList, sprintBacklog, new ArrayList<>(), 0, totalStoryPoints, 0);
        return sprintBacklog;
    }

    /**
     * Select User stories with highest business value which are less than user story point in the form.
     * */
    private void selectUserStories(List<UserStory> stories, List<UserStory> sprintBacklog, List<UserStory> currentSelection,
                           int start, int remainingPoints, int currentValue) {

        if (currentSelection.stream().mapToInt(us -> (int) us.getBusinessValue()).sum() >
                sprintBacklog.stream().mapToInt(us -> (int) us.getBusinessValue()).sum()) {
            sprintBacklog.clear();
            sprintBacklog.addAll(currentSelection); // Update sprint backlog with current selection of user story
        }

        for (int i = start; i < stories.size(); i++) {
            UserStory story = stories.get(i);
            int storyPoints = (int) story.getPointValue();

            if (storyPoints <= remainingPoints) {
                currentSelection.add(story); // Add story to current selection
                selectUserStories(stories, sprintBacklog, currentSelection, i + 1, remainingPoints - storyPoints,
                        currentValue + (int) story.getBusinessValue());
                currentSelection.remove(currentSelection.size() - 1);
            }
        }
    }
}
