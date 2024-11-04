package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Spike;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.Random;
public class CreateNewSpikeForm extends JDialog implements BaseComponent {
    private JTextArea createSpikeDisplay;
    private JTextField upperBoundField;
    private JTextField lowerBoundField;
    SecureRandom random = new SecureRandom();

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
        JButton submitButton = new JButton("Conduct Spike");
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
        Random random = new Random();
        int approvalNumber = random.nextInt(100) + 1;

        try {
            if (lowerBoundField.getText().isEmpty() || upperBoundField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter values for both bounds.", "Input Error", JOptionPane.ERROR_MESSAGE);
                lowerBoundField.setText("");
                upperBoundField.setText("");
                return null;
            }
        
            int lowerBound;
            int upperBound;
        
            try {
                lowerBound = Integer.parseInt(lowerBoundField.getText());
                upperBound = Integer.parseInt(upperBoundField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
                lowerBoundField.setText("");
                upperBoundField.setText("");
                return null;
            }
        
            if (lowerBound > upperBound) {
                JOptionPane.showMessageDialog(this, "Lower bound cannot be greater than upper bound.", "Input Error", JOptionPane.ERROR_MESSAGE);
                lowerBoundField.setText("");
                upperBoundField.setText("");
                return null;
            }

            if (approvalNumber < 25) {
            JOptionPane.showMessageDialog(this, "Management could not approve the required effort story points. Please try again later.", "Approval Error", JOptionPane.ERROR_MESSAGE);
            return null;
            }

            int diff = upperBound - lowerBound;
            int calculatedUpperBound = upperBound + diff;
            
            int spikeValue =  random.nextInt(calculatedUpperBound - lowerBound + 1) + lowerBound;

            return new Spike(upperBound, lowerBound, spikeValue, calculatedUpperBound);


        } catch (NumberFormatException e) {
            return null;
        }
    }
    public Spike getSpike() {

        return spike;
    }
}