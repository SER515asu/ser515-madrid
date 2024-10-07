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

    public Blocker createNewBlocker(String title, String description, String status) {
        Blocker newBlocker = new Blocker(title, description, status, null);
        return newBlocker;
    }
}