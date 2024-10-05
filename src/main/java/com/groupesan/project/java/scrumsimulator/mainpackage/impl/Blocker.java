package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumObject;

public class Blocker extends ScrumObject{

    private BlockerIdentifier id;
    private String name;
    private String description;
    private UserStory userStory;

    public Blocker(BlockerIdentifier id, String name, UserStory userStory) {
        this.id = id;
        this.name = name;
        this.description = "";
        this.userStory = userStory;
        register();
    }

    public Blocker(String name2, String description2, UserStory userStory2) {
        this.name = name2;
        this.description = description2;
        this.userStory = userStory2;
        register();
    }

    @Override
    protected void register() {
        this.id = new BlockerIdentifier(ScrumIdentifierStoreSingleton.get().getNextId());
    }

    @Override
    public ScrumIdentifier getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Blocker [id=" + id + ", name=" + name + ", description=" + description + ", userStory=" + userStory + "]";
    }
    
    // getters and setters for description and userStory
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserStory getUserStory() {
        return userStory;
    }

    public void setUserStory(UserStory userStory) {
        this.userStory = userStory;
    }
    
}
