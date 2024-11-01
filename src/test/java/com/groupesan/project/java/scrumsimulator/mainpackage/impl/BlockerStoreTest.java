package com.groupesan.project.java.scrumsimulator.mainpackage.impl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
public class BlockerStoreTest {
    private BlockerStore blockerStore;
    @BeforeEach
    void setUp() {
        blockerStore = BlockerStore.getInstance();
        blockerStore.getBlockers().clear();
    }
    @Test
    void testGetInstance() {
        BlockerStore instance1 = BlockerStore.getInstance();
        BlockerStore instance2 = BlockerStore.getInstance();
        assertNotNull(instance1);
        assertSame(instance1, instance2, "getInstance should always return the same instance");
    }
    @Test
    void testAddBlocker() {
        int initialSize = blockerStore.getBlockers().size();
        SprintBlocker newBlocker = new SprintBlocker("Test Blocker", "Test Description", "Open", null, 50, 60);
        blockerStore.addBlocker(newBlocker);
        List<SprintBlocker> blockers = blockerStore.getBlockers();
        assertEquals(initialSize + 1, blockers.size(), "Blocker list size should increase by 1");
        assertTrue(blockers.contains(newBlocker), "Added blocker should be in the list");
    }
    @Test
    void testGetBlockers() {
        List<SprintBlocker> blockers = blockerStore.getBlockers();
        // Verify that the returned list is a copy
        int initialSize = blockers.size();
        blockers.add(new SprintBlocker("Test Blocker", "Test Description", "Open", null, 80, 95));
        assertEquals(initialSize, blockerStore.getBlockers().size(), "Original list in BlockerStore should not be modified");
    }
    @Test
    void testRemoveBlocker() {
        List<SprintBlocker> initialBlockers = blockerStore.getBlockers();
        assertFalse(initialBlockers.isEmpty(), "There should be initial blockers");
        SprintBlocker blockerToRemove = initialBlockers.get(0);
        blockerStore.removeBlocker(blockerToRemove);
        List<SprintBlocker> updatedBlockers = blockerStore.getBlockers();
        assertFalse(updatedBlockers.contains(blockerToRemove), "Removed blocker should not be in the list");
        assertEquals(initialBlockers.size() - 1, updatedBlockers.size(), "Blocker list size should decrease by 1");
    }
    @Test
    void testClearBlockers() {
        blockerStore.clearBlockers();
        assertTrue(blockerStore.getBlockers().isEmpty(), "Blocker list should be empty after clearing");
    }
    @Test
    void testAddInitialBlockers() {
        blockerStore.clearBlockers();
        blockerStore.addInitialBlockers();
        List<SprintBlocker> blockers = blockerStore.getBlockers();
        assertEquals(5, blockers.size(), "Blocker list should have 5 initial blockers");
    }
}