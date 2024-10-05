package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.Random;

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

    public Sprint createNewSprint(String name, String description, int length) {
        Sprint newSprint = new Sprint(name, description, length, ++numSprints);
        return newSprint;
    }

    /**
     * Update the sprint variables.
     * If an error occurs, display the error message.
     * */
    public void updateSprint(Sprint sprint, String name, String description, int length) throws Exception {
        Random random = new Random();
        double errorChance = random.nextDouble();

        if (errorChance <= 0.001) {
            throw new Exception("Error occurred while updating the sprint. Please try again!");
        }

        sprint.setName(name);
        sprint.setDescription(description);
        sprint.setLength(length);
    }

}
