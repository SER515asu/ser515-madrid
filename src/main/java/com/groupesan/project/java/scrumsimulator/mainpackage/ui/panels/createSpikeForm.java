package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class createSpikeForm  extends JFrame implements BaseComponent {
    private JTextArea createSpikeDisplay;
    private JTextField upperBoundField;
    private JTextField lowerBoundField;
    public createSpikeForm(){
        init();
    }

    public void init(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Create Spike");
        setSize(400, 300);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        createSpikeDisplay = new JTextArea(2, 20);
        createSpikeDisplay.setEditable(false);
        JLabel hyphen = new JLabel(" - ");


        upperBoundField = new JTextField(10);
        lowerBoundField = new JTextField(10);

        JLabel nameLabel = new JLabel("Range for Spike (in Story Points):");

        panel.add(
                nameLabel,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                lowerBoundField,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                hyphen,
                new CustomConstraints(
                        2, 0, GridBagConstraints.CENTER, 1.0, 1.0, GridBagConstraints.NONE));
        panel.add(
                upperBoundField,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton submitButton = new JButton("Set");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int randomeValue = getValue();
                if ( randomeValue!= -1) {
                    dispose();
                }
            }

         });
        panel.add(
                submitButton,
                new CustomConstraints(
                        0, 3, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                createSpikeDisplay,
                new CustomConstraints(
                        1, 3, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        add(panel);
    }

    public int getValue(){
        try {
            int lowerBound = Integer.parseInt(lowerBoundField.getText());
            int upperBound = Integer.parseInt(upperBoundField.getText());

            if (lowerBound > upperBound) {
                throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
            }

            Random random = new Random();
            return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid integers for bounds.");
            return -1;
        }
    }
}
