package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class SprintStoreTest {

    private SprintStore sprintStore;

    @BeforeEach
    public void setUp() {
        // Get the instance of SprintStore and clear any existing sprints before each test
        sprintStore = SprintStore.getInstance();
        sprintStore.clearSprintList();
    }

    @Test
    public void testGetInstance() {
        SprintStore instance1 = SprintStore.getInstance();
        SprintStore instance2 = SprintStore.getInstance();
        assertNotNull(instance1, "SprintStore instance should not be null");
        assertSame(instance1, instance2, "getInstance should always return the same singleton instance");
    }

    @Test
    public void testAddSprint() {
        // Create a new Sprint object to add
        Sprint newSprint = new Sprint("Sprint 1", "Description 1", 10);
        int initialSize = sprintStore.getSprints().size();

        // Add the Sprint to the SprintStore
        sprintStore.addSprint(newSprint);

        // Get the updated list of sprints
        List<Sprint> sprints = sprintStore.getSprints();

        // Verify that the SprintStore contains the newly added sprint
        assertEquals(initialSize + 1, sprints.size(), "Sprint list size should increase by 1 after adding a sprint");
        assertTrue(sprints.contains(newSprint), "Added sprint should be in the SprintStore");
    }

    @Test
    public void testClearSprintList() {
        // Add some sprints to the SprintStore
        sprintStore.addSprint(new Sprint("Sprint 1", "Description 1", 10));
        sprintStore.addSprint(new Sprint("Sprint 2", "Description 2", 15));

        // Ensure that there are sprints in the store before clearing
        assertFalse(sprintStore.getSprints().isEmpty(), "Sprint list should not be empty before clearing");

        // Clear the sprint list
        sprintStore.clearSprintList();

        // Verify that the sprint list is empty after clearing
        assertTrue(sprintStore.getSprints().isEmpty(), "Sprint list should be empty after clearing");
    }

    @Test
    public void testGetSprintsReturnsCopy() {
        // Add a sprint to the SprintStore
        Sprint newSprint = new Sprint("Sprint 1", "Description 1", 10);
        sprintStore.addSprint(newSprint);

        // Get the list of sprints and modify it locally
        List<Sprint> sprints = sprintStore.getSprints();
        int initialSize = sprints.size();

        // Modify the local copy of the sprint list
        sprints.add(new Sprint("Sprint 3", "Description 3", 20));

        // Verify that the original SprintStore list remains unchanged
        assertEquals(initialSize, sprintStore.getSprints().size(), "Original sprint list in SprintStore should not be modified by external changes");
    }

    @Test
    public void testAddMultipleSprints() {
        // Create and add multiple sprints
        Sprint sprint1 = new Sprint("Sprint 1", "Description 1", 10);
        Sprint sprint2 = new Sprint("Sprint 2", "Description 2", 15);

        sprintStore.addSprint(sprint1);
        sprintStore.addSprint(sprint2);

        // Get the list of sprints
        List<Sprint> sprints = sprintStore.getSprints();

        // Verify that all the sprints have been added correctly
        assertTrue(sprints.contains(sprint1), "Sprint 1 should be in the SprintStore");
        assertTrue(sprints.contains(sprint2), "Sprint 2 should be in the SprintStore");
        assertEquals(2, sprints.size(), "Sprint list size should be 2 after adding two sprints");
    }

    @Test
    public void testSprintListIsInitiallyEmpty() {
        // Clear the sprint list to ensure it's empty
        sprintStore.clearSprintList();

        // Verify that the sprint list is empty on initialization
        assertTrue(sprintStore.getSprints().isEmpty(), "Sprint list should be empty when no sprints are added");
    }
}