package com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils;


import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Spike;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.TaskStatus;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.CreateNewSpikeForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.SpikeActionPane;

public class SpikeHandler {

    private boolean spikeOngoing=true;
    private TaskStatus status;

    public SpikeHandler(){
        startSpikeProcess();

    }
    private void startSpikeProcess() {
        while (spikeOngoing) {
            Spike spike = createSpike();
            if (spike == null) {
                System.out.println("Spike creation canceled.");
                spikeOngoing = false;
                break;
            }
            handleSpike(spike);
        }
    }
    private Spike createSpike() {
        CreateNewSpikeForm createNewSpikeForm = new CreateNewSpikeForm();
        createNewSpikeForm.setModal(true);
        createNewSpikeForm.setVisible(true);

        Spike spike = createNewSpikeForm.getSpike();
        createNewSpikeForm.dispose();

        return spike;
    }
    private void handleSpike(Spike spike) {
        int spikeVal = spike.getSpikeValue();

        System.out.println("Spike created with values: " +
                "Upper Bound = " + spike.getUpperBound() +
                ", Lower Bound = " + spike.getLowerBound() +
                ", Spike Value = " + spike.getSpikeValue() +
                ", Calculated Upper Bound = " + spike.getCalculatedUpperBound());

        if (spikeVal <= spike.getUpperBound()) {
            System.out.println("Spike Resolved");
            status = TaskStatus.RESOLVED;
            spikeOngoing = false;
        } else if (spikeVal > spike.getUpperBound() && spikeVal <= spike.getCalculatedUpperBound()) {
            handleSpikeAction();
        }
    }

    private void handleSpikeAction() {
        SpikeActionPane spikeActionPane = new SpikeActionPane();
        spikeActionPane.setModal(true);
        spikeActionPane.setVisible(true);

        String action = spikeActionPane.getAction();
        spikeActionPane.dispose();


        if ("reallocate".equals(action)) {
            System.out.println("Reallocate resources");
        } else if ("conclude".equals(action)) {
            System.out.println("Conclude Spike");
            status = TaskStatus.UNRESOLVED;
            spikeOngoing = false;
        }
    }

    public TaskStatus getSpikeStatus(){
        return status;
    }
}
