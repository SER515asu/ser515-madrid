package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BlockerSolutionsWidget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
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
    public void testProbabilityRangeDisplay() throws NoSuchFieldException, IllegalAccessException {
        // Test if the probability range label displays correctly in a widget
        SprintBlockerSolution blockerSolution = new SprintBlockerSolution("Test Blocker solution", "description", 10, 30);
        blockerSolution.doRegister();

        BlockerSolutionsWidget widget = new BlockerSolutionsWidget(blockerSolution);
        Field probabilityRangeField = BlockerSolutionsWidget.class.getDeclaredField("probabilityRange");
        probabilityRangeField.setAccessible(true);

        JLabel probabilityRangeLabel = (JLabel) probabilityRangeField.get(widget);
        assertEquals("10% - 30%", probabilityRangeLabel.getText(), "Probability range should display correctly");
    }

    @Test
    void testAddBlockerSolution() {
        int initialSize = blockerSolutionsStore.getBlockerSolutions().size();
        SprintBlockerSolution newSolution = new SprintBlockerSolution("Test Solution", "Test Description", 10, 30);
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
        solutions.add(new SprintBlockerSolution("Test Solution", "Test Description", 10, 30));
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
        SprintBlockerSolution solution1 = new SprintBlockerSolution("Solution 1", "Description 1", 10, 20);
        SprintBlockerSolution solution2 = new SprintBlockerSolution("Solution 2", "Description 2", 10, 20);
        blockerSolutionsStore.addBlockerSolution(solution1);
        blockerSolutionsStore.addBlockerSolution(solution2);

        SprintBlockerSolution[] solutionsArray = blockerSolutionsStore.getAllSolutions();
        assertEquals(2, solutionsArray.length, "Array length should be 2");
        assertTrue(List.of(solutionsArray).contains(solution1), "Array should contain solution1");
        assertTrue(List.of(solutionsArray).contains(solution2), "Array should contain solution2");
    }
}