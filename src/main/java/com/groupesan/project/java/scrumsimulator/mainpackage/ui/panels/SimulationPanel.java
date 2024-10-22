package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.List;

public class SimulationPanel extends JPanel implements BaseComponent {

    private SimulationStateManager simulationStateManager;
    private JButton startSimulationButton;
    private JButton stopSimulationButton;
    private List<Sprint> sprintList;

    /** Simulation Panel Initialization. */
    public SimulationPanel() {
        this.simulationStateManager = new SimulationStateManager();
        this.sprintList = SprintStore.getInstance().getSprints();
        this.init();
    }

    public void setRole(String role) {
        startSimulationButton.setEnabled(!"Product Owner".equals(role));
    }

    @Override
    public void init() {
        startSimulationButton = new JButton("Start Simulation");
        stopSimulationButton = new JButton("Stop Simulation");

        stopSimulationButton.setVisible(false);

        startSimulationButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simulationStateManager.startSimulation();
                        updateButtonVisibility();
                    }
                });

        stopSimulationButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simulationStateManager.stopSimulation();
                        JOptionPane.showMessageDialog(null, "Simulation stopped!");
                        updateButtonVisibility();
                    }
                });

        add(startSimulationButton);
        add(stopSimulationButton);
    }

    public void updateButtonVisibility() {
        // Show/hide buttons based on the simulation state
        if (simulationStateManager.isRunning()) {
            stopSimulationButton.setVisible(true);
            startSimulationButton.setVisible(false);
        } else {
            stopSimulationButton.setVisible(false);
            startSimulationButton.setVisible(true);
        }
    }
}
