package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Spike;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
public class CreateNewSpikeForm extends JDialog implements BaseComponent {
    private JTextArea createSpikeDisplay;
    private JTextField upperBoundField;
    private JTextField lowerBoundField;

    private Spike spike;
    public CreateNewSpikeForm(){
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
                Spike newSpike = createSpike();
                if (newSpike != null) {
                    spike = newSpike;
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
    public Spike createSpike(){
        try {
            int lowerBound = Integer.parseInt(lowerBoundField.getText());
            int upperBound = Integer.parseInt(upperBoundField.getText());

            if (lowerBoundField.getText().isEmpty() || upperBoundField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter values for both bounds.", "Input Error", JOptionPane.ERROR_MESSAGE);
                throw new NumberFormatException("Please enter values for both bounds.");
            }
            if (lowerBound > upperBound) {
                JOptionPane.showMessageDialog(this, "Lower bound cannot be greater than upper bound.", "Input Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Lower bound cannot be greater than upper bound.");
            }
            int calculatedUpperBound = lowerBound + upperBound;
            Random random = new Random();
            int spikeValue =  random.nextInt(calculatedUpperBound - lowerBound + 1) + lowerBound;

            return new Spike(upperBound, lowerBound, spikeValue, calculatedUpperBound);


        } catch (NumberFormatException e) {
            System.out.println("excpetion has occured"+e.toString());
            return null;
        }
    }
    public Spike getSpike() {

        return spike;
    }}