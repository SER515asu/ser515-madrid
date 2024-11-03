package com.groupesan.project.java.scrumsimulator.mainpackage.state;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationStateTest {

    private SimulationStateManager simulationStateManager;

    @BeforeEach
    public void setUp() {
        simulationStateManager = new SimulationStateManager();
    }

    @AfterEach
    public void tearDown() {
        simulationStateManager = null;
    }

    @Test
    public void testInitialState() {
        assertFalse(simulationStateManager.isRunning());
    }

    @Test
    public void testStartSimulation() {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Running in headless mode; skipping UI setup.");
        } else {
            simulationStateManager.startSimulation();
            assertTrue(simulationStateManager.isRunning());
        }
    }

    @Test
    public void testStopSimulation() {
        simulationStateManager.stopSimulation();
        assertFalse(simulationStateManager.isRunning());
    }

    @Test
    void testResetAllBlockersStatusOnTechnicalIssue() {
        List<UserStory> userStories = new ArrayList<>();
        SprintBlocker blocker1 = new SprintBlocker("Block1", "Blocker1", "In progress", userStories, 0, 30);
        SprintBlocker blocker2 = new SprintBlocker("Block2", "Blocker2", "Resolved", userStories, 5, 45);

        List<SprintBlocker> blockersList = BlockerStore.getInstance().getBlockers();
        blockersList.add(blocker1);
        blockersList.add(blocker2);
        simulationStateManager.resetAllBlockersStatus();

        for (SprintBlocker blocker : BlockerStore.getInstance().getBlockers()) {
                assertEquals("Open", blocker.getStatus());
        }
    }
}
