package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.security.SecureRandom;

public class SprintFactory {
    private static SprintFactory sprintFactory;
    private static final SecureRandom secureRandom = new SecureRandom();

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

        double errorChance = secureRandom.nextDouble();

        if (errorChance <= 0.10) { // 10% chance of error set for the scope of this project.
            throw new Exception("Error occurred while updating the sprint. Please try again!");
        }

        sprint.setName(name);
        sprint.setDescription(description);
        sprint.setLength(length);
    }

    // Error probability set to 100% for testing:
    // public void updateSprint(Sprint sprint, String name, String description, int length) throws Exception {
    // throw new Exception("Error occurred while updating the sprint. Please try again!");
    // }

    public static void resetSprintFactory() {
        sprintFactory = new SprintFactory();
    }
}
