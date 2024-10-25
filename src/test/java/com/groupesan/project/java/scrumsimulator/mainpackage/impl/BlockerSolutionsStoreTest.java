package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class BlockerSolutionsStoreTest {
    private BlockerSolutionsStore blockerSolutionsStore;

    @BeforeEach
    void setUp() {
        blockerSolutionsStore = BlockerSolutionsStore.getInstance();
        blockerSolutionsStore.getBlockerSolutions().clear();
    }

    @Test
    void testGetInstance() {
        BlockerSolutionsStore instance1 = BlockerSolutionsStore.getInstance();
        BlockerSolutionsStore instance2 = BlockerSolutionsStore.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2, "getInstance should always return the same instance");
    }

    @Test
    void testAddBlockerSolution() {
        int initialSize = blockerSolutionsStore.getBlockerSolutions().size();
        SprintBlockerSolution newSolution = new SprintBlockerSolution("Test Solution", "Test Description");
        blockerSolutionsStore.addBlockerSolution(newSolution);
        List<SprintBlockerSolution> solutions = blockerSolutionsStore.getBlockerSolutions();
        assertEquals(initialSize + 1, solutions.size(), "Solution list size should increase by 1");
        assertTrue(solutions.contains(newSolution), "Added solution should be in the list");
    }

    @Test
    void testGetBlockerSolutions() {
        List<SprintBlockerSolution> solutions = blockerSolutionsStore.getBlockerSolutions();
        // Verify that the returned list is a copy
        int initialSize = solutions.size();
        solutions.add(new SprintBlockerSolution("Test Solution", "Test Description"));
        assertEquals(initialSize, blockerSolutionsStore.getBlockerSolutions().size(), 
            "Original list in BlockerSolutionsStore should not be modified");
    }

    @Test
    void testRemoveBlockerSolution() {
        List<SprintBlockerSolution> initialSolutions = blockerSolutionsStore.getBlockerSolutions();
        assertFalse(initialSolutions.isEmpty(), "There should be initial solutions");
        SprintBlockerSolution solutionToRemove = initialSolutions.get(0);
        blockerSolutionsStore.removeBlockerSolution(solutionToRemove);
        List<SprintBlockerSolution> updatedSolutions = blockerSolutionsStore.getBlockerSolutions();
        assertFalse(updatedSolutions.contains(solutionToRemove), "Removed solution should not be in the list");
        assertEquals(initialSolutions.size() - 1, updatedSolutions.size(), "Solution list size should decrease by 1");
    }

    @Test
    void testClearBlockerSolutions() {
        blockerSolutionsStore.clearBlockerSolutions();
        assertTrue(blockerSolutionsStore.getBlockerSolutions().isEmpty(), "Solution list should be empty after clearing");
    }

    @Test
    void testAddInitialBlockerSolutions() {
        blockerSolutionsStore.clearBlockerSolutions();
        blockerSolutionsStore.addInitialBlockerSolutions();
        List<SprintBlockerSolution> solutions = blockerSolutionsStore.getBlockerSolutions();
        assertEquals(5, solutions.size(), "Solution list should have 5 initial solutions");
    }

    @Test
    void testGetAllSolutions() {
        blockerSolutionsStore.clearBlockerSolutions();
        SprintBlockerSolution solution1 = new SprintBlockerSolution("Solution 1", "Description 1");
        SprintBlockerSolution solution2 = new SprintBlockerSolution("Solution 2", "Description 2");
        blockerSolutionsStore.addBlockerSolution(solution1);
        blockerSolutionsStore.addBlockerSolution(solution2);

        SprintBlockerSolution[] solutionsArray = blockerSolutionsStore.getAllSolutions();
        assertEquals(2, solutionsArray.length, "Array length should be 2");
        assertTrue(List.of(solutionsArray).contains(solution1), "Array should contain solution1");
        assertTrue(List.of(solutionsArray).contains(solution2), "Array should contain solution2");
    }
}