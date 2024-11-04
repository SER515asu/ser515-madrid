package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.security.SecureRandom;

import javax.swing.*;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditBlockerSolutionForm extends JFrame implements BaseComponent {

    private SprintBlockerSolution blockerSolution;
    private JTextField nameField = new JTextField();
    private JTextArea descArea = new JTextArea();
    private JSlider minProbabilitySlider = new JSlider(0, 100, 20);
    private JSlider maxProbabilitySlider = new JSlider(0, 100, 80);
    private JLabel minProbabilityLabel = new JLabel("20%");
    private JLabel maxProbabilityLabel = new JLabel("80%");
    private JCheckBox randomizeProbabilityCheckBox = new JCheckBox("Randomize Probability");
    SecureRandom secRandom = new SecureRandom();

    public EditBlockerSolutionForm(SprintBlockerSolution blockerSolution) {
        this.blockerSolution = blockerSolution;
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Edit Solution" + blockerSolution.getId().toString());
        setSize(500, 500);

        nameField.setText(blockerSolution.getName());
        descArea.setText(blockerSolution.getDescription());
        minProbabilitySlider.setValue(blockerSolution.getMinProbability());
        maxProbabilitySlider.setValue(blockerSolution.getMaxProbability());
        minProbabilityLabel.setText(blockerSolution.getMinProbability() + "%");
        maxProbabilityLabel.setText(blockerSolution.getMaxProbability() + "%");

        JPanel myJpanel = new JPanel(new GridBagLayout());
        myJpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        myJpanel.add(new JLabel("Name:"), new CustomConstraints(0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(nameField, new CustomConstraints(1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(new JLabel("Description:"), new CustomConstraints(0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(new JScrollPane(descArea), new CustomConstraints(1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        myJpanel.add(new JLabel("Min Probability (%):"), new CustomConstraints(0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        minProbabilitySlider.setMajorTickSpacing(20);
        minProbabilitySlider.setMinorTickSpacing(5);
        minProbabilitySlider.setPaintTicks(true);
        minProbabilitySlider.setPaintLabels(true);
        minProbabilitySlider.addChangeListener(e -> {
            minProbabilityLabel.setText(minProbabilitySlider.getValue() + "%");
            validateProbability();
        });
        myJpanel.add(minProbabilitySlider, new CustomConstraints(1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        myJpanel.add(minProbabilityLabel, new CustomConstraints(1, 3, GridBagConstraints.WEST, GridBagConstraints.NONE));

        myJpanel.add(new JLabel("Max Probability (%):"), new CustomConstraints(0, 4, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        maxProbabilitySlider.setMajorTickSpacing(20);
        maxProbabilitySlider.setMinorTickSpacing(5);
        maxProbabilitySlider.setPaintTicks(true);
        maxProbabilitySlider.setPaintLabels(true);
        maxProbabilitySlider.addChangeListener(e -> {
            maxProbabilityLabel.setText(maxProbabilitySlider.getValue() + "%");
            validateProbability();
        });
        myJpanel.add(maxProbabilitySlider, new CustomConstraints(1, 4, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        myJpanel.add(maxProbabilityLabel, new CustomConstraints(1, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));

        randomizeProbabilityCheckBox.addActionListener(e -> probabilityRangeSelection());
        myJpanel.add(randomizeProbabilityCheckBox, new CustomConstraints(0, 6, GridBagConstraints.WEST, GridBagConstraints.NONE));


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (validateProbability()) {
                setBlockerSolution();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "min Probability should be less than Max Probability", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        myJpanel.add(cancelButton, new CustomConstraints(0, 7, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(submitButton, new CustomConstraints(1, 7, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }

    private void setBlockerSolution() {
        int minProbability;
        blockerSolution.setName(nameField.getText());
        blockerSolution.setDescription(descArea.getText());
        if (randomizeProbabilityCheckBox.isSelected()) {
            minProbability= generateRandomProbability(0, 90);
            blockerSolution.setBlockerSolutionMinProbability(minProbability);
            blockerSolution.setBlockerSolutionMaxProbability(generateRandomProbability(minProbability + 1, 100));
        } else {
            blockerSolution.setBlockerSolutionMinProbability(minProbabilitySlider.getValue());
            blockerSolution.setBlockerSolutionMaxProbability(maxProbabilitySlider.getValue());
        }

    }

    private boolean validateProbability() {
        int minProbability;
        int maxProbability;
        minProbability = minProbabilitySlider.getValue();
        maxProbability = maxProbabilitySlider.getValue();
        return minProbability < maxProbability;
    }

    private int generateRandomProbability(int min, int max) {
        return min + secRandom.nextInt(max - min + 1);
    }

    private void probabilityRangeSelection() {
        boolean isRandomizeSelected = randomizeProbabilityCheckBox.isSelected();
        minProbabilitySlider.setEnabled(!isRandomizeSelected);
        maxProbabilitySlider.setEnabled(!isRandomizeSelected);
        minProbabilityLabel.setEnabled(!isRandomizeSelected);
        maxProbabilityLabel.setEnabled(!isRandomizeSelected);
    }
}