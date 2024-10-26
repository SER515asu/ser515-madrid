package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class BlockerFactory {
    private static BlockerFactory blockerFactory;

    public static BlockerFactory getInstance() {
        if (blockerFactory == null) {
            blockerFactory = new BlockerFactory();
        }
        return blockerFactory;
    }

    private BlockerFactory() {}

    public SprintBlocker createNewBlocker(String title, String description, String status, int minProbability, int maxProbability) {
        SprintBlocker newBlocker = new SprintBlocker(title, description, status, null, minProbability, maxProbability);
        return newBlocker;
    }
}