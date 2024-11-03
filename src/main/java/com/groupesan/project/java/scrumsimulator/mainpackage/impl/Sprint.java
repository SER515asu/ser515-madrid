package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class Sprint {
    private ArrayList<UserStory> userStories = new ArrayList<>();
    private String name;

    private String description;

    private int length;

    private int remainingDays;

    private int id;

    private int storyPoints;

    public Sprint(String name, String description, int length, int id, int storyPoints) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.remainingDays = length;
        this.id = id;
        this.storyPoints = storyPoints;
    }

    public Sprint(String name, String description, int length, int id) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.remainingDays = length;
        this.id = id;
    }

    public Sprint(String name, String desc, int length) {
        this.name = name;
        this.description = desc;
        this.length = length;
        this.userStories = new ArrayList<>();
    }

    public void addUserStory(UserStory us) {
        if (!userStories.contains(us) && us.isAvailableForSprint()) {
            userStories.add(us);
            us.setAssignedSprint(this);
            updateStoryPoints();
        }
    }

    public  void removeAllUserStories(){
        userStories.clear();
    }

    public void removeUserStory(UserStory userStory) {
        if (userStories.remove(userStory)) {
            userStory.setAssignedSprint(null);
            updateStoryPoints();
        }
    }

    public List<UserStory> getUserStories() {
        return new ArrayList<>(userStories);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDaysRemaining() {
        return remainingDays;
    }

    public void setDaysRemaining(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public void decrementRemainingDays() {
        if (remainingDays > 0) remainingDays--;
    }

    public int getId() {
        return id;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public int setStoryPoints(int storyPoints) {
        return this.storyPoints = storyPoints;
    }

    private void updateStoryPoints() {
        int totalPoints = (int) userStories.stream().mapToDouble(UserStory::getPointValue).sum();
        if (totalPoints > storyPoints) {
            storyPoints = totalPoints;
        }
    }

    public String toString() {
        String header = "Sprint " + this.id + ": " + this.name + "\n";
        StringBuilder USes = new StringBuilder();

        for (UserStory us : userStories) {
            USes.append(us.toString()).append("\n");
        }
        return header + USes;
    }
}
