package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class SimulationPanel extends JPanel implements BaseComponent {

    private static final SimulationStateManager simulationStateManager  = new SimulationStateManager();
    private static final JButton startSimulationButton  = new JButton("Start Simulation");;
    private static final JButton stopSimulationButton = new JButton("Stop Simulation");

    /** Simulation Panel Initialization. */
    public SimulationPanel() {
        this.init();
    }

    public void setRole(String role) {
        startSimulationButton.setEnabled(!"Product Owner".equals(role));
    }

    @Override
    public void init() {

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

    public static void updateButtonVisibility() {
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
