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

    public Blocker createNewBlocker(String name, String description, UserStory userStory) {
        Blocker newBlocker = new Blocker(name, description, userStory);
        return newBlocker;
    }
}