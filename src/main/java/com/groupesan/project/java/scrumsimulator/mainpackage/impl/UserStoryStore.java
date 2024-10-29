package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class UserStoryStore {

    private static UserStoryStore userStoryStore;

    /**
     * returns the shared instance of the UserStoryStore which contains all user stories in the
     * system.
     *
     * @return
     */
    public static UserStoryStore getInstance() {
        if (userStoryStore == null) {
            userStoryStore = new UserStoryStore();
        }
        return userStoryStore;
    }

    private List<UserStory> userStories;

    private UserStoryStore() {
        userStories = new ArrayList<>();
    }

    public void addUserStory(UserStory userStory) {
        userStories.add(userStory);
    }

    public List<UserStory> getUserStories() {
        System.out.println(userStories);
        return new ArrayList<>(userStories);
    }

    public void removeUserStory(UserStory userStory) {
        userStories.remove(userStory);
    }

    public UserStory getUserStoryByName(String selectedUserStory) {
        for (UserStory userStory : userStories) {
            if (userStory.getName().equals(selectedUserStory)) {
                return userStory;
            }
        }
        return null;
    }

    public void clearUserStories() {
        userStories.clear();
    }
}
