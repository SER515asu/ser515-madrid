package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JButton;

import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SimulationPanelTest {
    private SimulationPanel simulationPanel;
    private SimulationStateManager mockSimulationStateManager;

    @BeforeEach
    public void setUp() {
        mockSimulationStateManager = new SimulationStateManager();
        simulationPanel = new SimulationPanel(mockSimulationStateManager);
    }

    @Test
    public void testStartStopButtonsHiddenForProductOwner() {
        simulationPanel.setRole("Product Owner");
        simulationPanel.repaint();

        JButton startSimulationButton = (JButton) simulationPanel.getComponent(0);

        assertFalse(startSimulationButton.isEnabled(), "Start button should be disabled for Product Owner role");
    }

    @Test
    public void testStartStopButtonsVisibleForOtherRoles() {
        simulationPanel.setRole("Scrum Master");

        JButton startSimulationButton = (JButton) simulationPanel.getComponent(0);
        JButton stopSimulationButton = (JButton) simulationPanel.getComponent(1);

        assertTrue(startSimulationButton.isEnabled(), "Start button should be enabled for non-Product Owner role");
        assertTrue(stopSimulationButton.isEnabled(), "Stop button should be enabled for non-Product Owner role");
    }
}
