package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class BlockerSolutionsFactory {
    private static BlockerSolutionsFactory blockerSolutionsFactory;

    public static BlockerSolutionsFactory getInstance() {
        if (blockerSolutionsFactory == null) {
            blockerSolutionsFactory = new BlockerSolutionsFactory();
        }
        return blockerSolutionsFactory;
    }

    private BlockerSolutionsFactory() {}

    public SprintBlockerSolution createNewBlockerSolution(String title, String description) {
        SprintBlockerSolution newBlockerSolution = new SprintBlockerSolution(title, description);
        return newBlockerSolution;
    }
}