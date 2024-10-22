package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumObject;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;


public class SprintBlockerSolution extends ScrumObject {
    private BlockerSolutionIdentifier id;
    private String name;
    private String description;

    public SprintBlockerSolution(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected void register() {
        this.id = new BlockerSolutionIdentifier(ScrumIdentifierStoreSingleton.get().getNextId());
    }

    public ScrumIdentifier getId() {
        if (!isRegistered()) {
            throw new IllegalStateException("This Blocker Solution has not been registered and does not have an ID yet!");
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

    @Override
    public String toString() {
        if (isRegistered()) {
            return this.getId().toString() + " - " + name;
        }
        return "(unregistered) - " + getName();
    }
}
