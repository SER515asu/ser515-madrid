package com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils;

import com.groupesan.project.java.scrumsimulator.mainpackage.ui.dialogs.simulation.SimulationWizard;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.WizardHandler;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.Simulation;

public class WizardManager {
    private static WizardManager instance = null;
    private SimulationWizard currentSimulationWizard = null;

    private WizardManager() {}

    public static WizardManager get() {
        if (instance == null) {
            instance = new WizardManager();
        }
        return instance;
    }

    public void showSimulationWizard(WizardHandler<Simulation> handler) {
        if (currentSimulationWizard == null || !currentSimulationWizard.isDisplayable()) {
            currentSimulationWizard = new SimulationWizard(handler);
            currentSimulationWizard.setVisible(true);
        } else {
            currentSimulationWizard.toFront();
        }
    }
}