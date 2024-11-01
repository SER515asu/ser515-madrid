package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class BlockerSolutionsStore {
    private static BlockerSolutionsStore blockerSolutionsStore;

    public static BlockerSolutionsStore getInstance() {
        if (blockerSolutionsStore == null) {
            blockerSolutionsStore = new BlockerSolutionsStore();
        }
        return blockerSolutionsStore;
    }

    private List<SprintBlockerSolution> blockerSolutions;

    private BlockerSolutionsStore() {
        blockerSolutions = new ArrayList<>();
        addInitialBlockerSolutions();
    }

    void addInitialBlockerSolutions() {
        BlockerSolutionsFactory factory = BlockerSolutionsFactory.getInstance();
        
        addBlockerSolution(factory.createNewBlockerSolution("Solution Uno", "Desc Uno: The user should be able to save the sprint variables.", 30, 50));
        addBlockerSolution(factory.createNewBlockerSolution("Solution Dos", "Desc Dos: The user should be able to save the sprint variables.", 10, 20));
        addBlockerSolution(factory.createNewBlockerSolution("Solution Tres", "Desc Tres: The user should be able to save the sprint variables.", 90, 100));
        addBlockerSolution(factory.createNewBlockerSolution("Solution Quatro", "Desc Quatro: The user should be able to save the sprint variables.", 60, 85));
        addBlockerSolution(factory.createNewBlockerSolution("Solution Sinko", "Desc Sinko: The user should be able to save the sprint variables.", 10, 70));
    }

    public void addBlockerSolution(SprintBlockerSolution blockerSolution) {
        blockerSolution.doRegister();
        blockerSolutions.add(blockerSolution);
    }

    public List<SprintBlockerSolution> getBlockerSolutions() {
        return new ArrayList<>(blockerSolutions);
    }

    public void removeBlockerSolution(SprintBlockerSolution blockerSolution) {
        blockerSolutions.remove(blockerSolution);
    }

    public void clearBlockerSolutions() {
        blockerSolutions.clear();
    }

    public SprintBlockerSolution[] getAllSolutions() {
        return blockerSolutions.toArray(new SprintBlockerSolution[0]);
    }
}