package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumObject;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;

import java.util.ArrayList;
import java.util.List;

public class SprintBlocker extends ScrumObject {
    private BlockerIdentifier id;
    private String name;
    private String description;
    private String status;
    private List<UserStory> userStories = new ArrayList<>();

    public SprintBlocker(String name, String description, String status, List<UserStory> userStories) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.userStories = userStories;
    }

    protected void register() {
        this.id = new BlockerIdentifier(ScrumIdentifierStoreSingleton.get().getNextId());
    }

    public ScrumIdentifier getId() {
        if (!isRegistered()) {
            throw new IllegalStateException("This Blocker has not been registered and does not have an ID yet!");
        }
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (isRegistered()) {
            return this.getId().toString() + " - " + name;
        }
        return "(unregistered) - " + getName();
    }

    public List<UserStory> getUserStories() {
        if (userStories == null) {
            userStories = new ArrayList<>();
        }
        return userStories;
    }

    public void addUserStory(UserStory userStory) {
        if (!userStories.contains(userStory)) {
            userStories.add(userStory);
        }
    }

    public void removeUserStory(UserStory userStory) {
        userStories.remove(userStory);
    }
}