package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;

public class NewBlockerForm extends JFrame implements BaseComponent {

    private JTextField nameField = new JTextField();
    private JTextArea descArea = new JTextArea();
    private JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Open", "In Progress", "Resolved"});
    private JSlider minProbabilitySlider = new JSlider(0, 100, 20);
    private JSlider maxProbabilitySlider = new JSlider(0, 100, 80);
    private JLabel minProbabilityLabel = new JLabel("20%");
    private JLabel maxProbabilityLabel = new JLabel("80%");
    private boolean isFormSubmitted = false;

    public NewBlockerForm() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("New Blocker");
        setSize(500, 400);

        JPanel myJpanel = new JPanel(new GridBagLayout());
        myJpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        myJpanel.add(new JLabel("Title:"), new CustomConstraints(0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(nameField, new CustomConstraints(1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(new JLabel("Description:"), new CustomConstraints(0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(new JScrollPane(descArea), new CustomConstraints(1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        myJpanel.add(new JLabel("Status:"), new CustomConstraints(0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(statusCombo, new CustomConstraints(1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(new JLabel("Min Probability (%):"), new CustomConstraints(0, 3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        minProbabilitySlider.setMajorTickSpacing(20);
        minProbabilitySlider.setMinorTickSpacing(5);
        minProbabilitySlider.setPaintTicks(true);
        minProbabilitySlider.setPaintLabels(true);
        minProbabilitySlider.addChangeListener(e -> minProbabilityLabel.setText(minProbabilitySlider.getValue() + "%"));
        myJpanel.add(minProbabilitySlider, new CustomConstraints(1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        myJpanel.add(minProbabilityLabel, new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));

        myJpanel.add(new JLabel("Max Probability (%):"), new CustomConstraints(0, 5, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        maxProbabilitySlider.setMajorTickSpacing(20);
        maxProbabilitySlider.setMinorTickSpacing(5);
        maxProbabilitySlider.setPaintTicks(true);
        maxProbabilitySlider.setPaintLabels(true);
        maxProbabilitySlider.addChangeListener(e -> maxProbabilityLabel.setText(maxProbabilitySlider.getValue() + "%"));
        myJpanel.add(maxProbabilitySlider, new CustomConstraints(1, 5, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        myJpanel.add(maxProbabilityLabel, new CustomConstraints(1, 6, GridBagConstraints.WEST, GridBagConstraints.NONE));


        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {
            isFormSubmitted = false;
            dispose();
        });


        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (validateForm()) {
                isFormSubmitted = true;
                SprintBlocker blocker = getBlockerObject();
                if (blocker != null) {
                    dispose();
                }
            }

        });

        myJpanel.add(cancelButton, new CustomConstraints(0, 7, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(submitButton, new CustomConstraints(1, 7, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }


    public boolean validateForm() {
        String title = nameField.getText();
        String description = descArea.getText();
        String status = (String) statusCombo.getSelectedItem();

        if (title.isEmpty() || description.isEmpty() || status.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public SprintBlocker getBlockerObject() {
        if(!isFormSubmitted) {
            return null;
        }
        String title = nameField.getText();
        String description = descArea.getText();
        String status = (String) statusCombo.getSelectedItem();
        int minProbability = minProbabilitySlider.getValue();
        int maxProbability = maxProbabilitySlider.getValue();

        BlockerFactory blockerFactory = BlockerFactory.getInstance();
        SprintBlocker blocker = blockerFactory.createNewBlocker(title, description, status, minProbability, maxProbability);
        blocker.doRegister();

        System.out.println(blocker);
        return blocker;
    }
}