package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationButtonStateInterface;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;

public class SimulationPanel extends JPanel implements BaseComponent, SimulationButtonStateInterface {
    private final SimulationStateManager simulationStateManager;
    private final JButton startSimulationButton;
    private final JButton stopSimulationButton;

    public SimulationPanel(SimulationStateManager simulationStateManager) {
        this.simulationStateManager = simulationStateManager;
        this.startSimulationButton = new JButton("Start Simulation");
        this.stopSimulationButton = new JButton("Stop Simulation");
        simulationStateManager.setButtonStateListener(this);
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
                    }
                });

        stopSimulationButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        simulationStateManager.stopSimulation();
                        JOptionPane.showMessageDialog(null, "Simulation stopped!");
                    }
                });

        add(startSimulationButton);
        add(stopSimulationButton);
    }

    @Override
    public void updateButtonVisibility(boolean isRunning) {
        stopSimulationButton.setVisible(isRunning);
        startSimulationButton.setVisible(!isRunning);
    }
}