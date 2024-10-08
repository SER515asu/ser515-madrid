package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class SprintFactory {
    private static SprintFactory sprintFactory;

    public static SprintFactory getSprintFactory() {
        if (sprintFactory == null) {
            sprintFactory = new SprintFactory();
        }

        return sprintFactory;
    }

    private int numSprints;

    private SprintFactory() {
        numSprints = 0;
    }

    public Sprint createNewSprint(String name, String description, int length, int storyPoints) {
        Sprint newSprint = new Sprint(name, description, length, ++numSprints, storyPoints);
        return newSprint;
    }

    public Sprint createNewSprint(String name, String description, int length) {
        Sprint newSprint = new Sprint(name, description, length, ++numSprints);
        return newSprint;
    }

    public static void resetSprintFactory() {
        sprintFactory = new SprintFactory();
    }

}
