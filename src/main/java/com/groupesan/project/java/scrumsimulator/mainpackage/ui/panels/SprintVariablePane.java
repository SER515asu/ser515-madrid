package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SprintVariablePane extends JFrame implements BaseComponent {
    private JTextArea simulationIdDisplay;
    private SprintListPane sprintListPane;

    public SprintVariablePane(SprintListPane sprintListPane){
        this.sprintListPane = sprintListPane;
        this.init();
    }


    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sprint Variables");
        setSize(400, 300);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        simulationIdDisplay = new JTextArea(2, 20);
        simulationIdDisplay.setEditable(false);
        JLabel hyphen = new JLabel(" - ");

        JTextField sprintDurationField1 = new JTextField(10);
        JTextField sprintDurationField2 = new JTextField(10);
        JTextField numberOfSprintsField = new JTextField(20);

        JLabel nameLabel = new JLabel("Range for Each Sprint Duration(in Days):");
        JLabel sprintsLabel = new JLabel("Number of Sprints:");

        panel.add(
                nameLabel,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                sprintDurationField1,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                hyphen,
                new CustomConstraints(
                        2, 0, GridBagConstraints.CENTER, 1.0, 1.0, GridBagConstraints.NONE));
        panel.add(
                sprintDurationField2,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        panel.add(
                sprintsLabel,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                numberOfSprintsField,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton submitButton = new JButton("Set");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numberOfSprints = Integer.parseInt(numberOfSprintsField.getText());

                    int lowerBound = Integer.parseInt(sprintDurationField1.getText());
                    int upperBound = Integer.parseInt(sprintDurationField2.getText());

                    if(lowerBound==0 || upperBound==0){
                        sprintDurationField1.setText("");
                        sprintDurationField2.setText("");

                        JOptionPane.showMessageDialog(panel, "Minimum Sprint Duration can't be set to 0.", "Error", JOptionPane.ERROR_MESSAGE);

                        return;
                    }

                    if(lowerBound>upperBound){
                        sprintDurationField1.setText("");
                        sprintDurationField2.setText("");

                        JOptionPane.showMessageDialog(panel, "Lower bound cannot be greater than upper bound. Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);

                        return;
                    }

                    if(lowerBound<0 || upperBound<0 || numberOfSprints<0){
                        JOptionPane.showMessageDialog(panel, "Cannot accept negative values. Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);

                        return;
                    }

                    if(numberOfSprints==0){
                        JOptionPane.showMessageDialog(panel, "Number Of Sprints cannot be zero. Please enter valid values.", "Error", JOptionPane.ERROR_MESSAGE);

                        return;
                    }

                    SprintStore.getInstance().clearSprintList();
                    SprintFactory.resetSprintFactory();

                    sprintListPane.addSprints(numberOfSprints, lowerBound, upperBound);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        panel.add(
                submitButton,
                new CustomConstraints(
                        0, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                simulationIdDisplay,
                new CustomConstraints(
                        1, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        add(panel);

    }
}
