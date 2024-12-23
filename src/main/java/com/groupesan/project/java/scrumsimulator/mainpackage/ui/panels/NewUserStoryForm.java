package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NewUserStoryForm extends JFrame implements BaseComponent {

    Double[] pointsList = {1.0, 2.0, 3.0, 5.0, 8.0, 11.0, 19.0, 30.0, 49.0};

    Double[] businessValueList = {1.0, 2.0, 3.0, 5.0, 8.0, 11.0, 19.0, 30.0, 49.0};

    public NewUserStoryForm() {
            this.init();
    }

    JTextField nameField = new JTextField();
    JTextArea descArea = new JTextArea();
    JComboBox<Double> pointsCombo = new JComboBox<>(pointsList);
    JComboBox<Double> businessValueCombo = new JComboBox<>(businessValueList);
    JButton submitButton;

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("New User Story");
        setSize(500, 400);

        

        pointsCombo = new JComboBox<>(pointsList);
        businessValueCombo = new JComboBox<>(businessValueList);


        nameField = new JTextField();
        descArea = new JTextArea();
        pointsCombo = new JComboBox<>(pointsList);
        businessValueCombo = new JComboBox<>(businessValueList);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        BorderLayout myBorderLayout = new BorderLayout();

        setLayout(myBorderLayout);

        JLabel nameLabel = new JLabel("Name:");
        myJpanel.add(
                nameLabel,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                nameField,
                new CustomConstraints(
                        1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JLabel descLabel = new JLabel("Description:");
        myJpanel.add(
                descLabel,
                new CustomConstraints(
                        0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                new JScrollPane(descArea),
                new CustomConstraints(
                        1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        JLabel pointsLabel = new JLabel("Points:");
        myJpanel.add(
                pointsLabel,
                new CustomConstraints(
                        0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                pointsCombo,
                new CustomConstraints(
                        1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JLabel businessValueLabel = new JLabel("Business Value:");
        myJpanel.add(
                businessValueLabel,
                new CustomConstraints(
                        0, 3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                businessValueCombo,
                new CustomConstraints(
                        1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty() || descArea.getText().trim().isEmpty()
                    || pointsCombo.getSelectedItem() == null || businessValueCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(
                        NewUserStoryForm.this,
                        "All fields are required.",
                        "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            UserStory userStory = getUserStoryObject();
            if (userStory != null) {
                userStory.doRegister();
                dispose();
            }
            
        });

        myJpanel.add(cancelButton, new CustomConstraints(0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(submitButton, new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public UserStory getUserStoryObject() {
        String name = nameField.getText();
        String description = descArea.getText();
        Double points = (Double) pointsCombo.getSelectedItem();
        Double businessValue = (Double) businessValueCombo.getSelectedItem();

        if (name.isEmpty() || description.isEmpty() || points == null || businessValue == null) {
            return null;
        }

        UserStoryFactory userStoryFactory = UserStoryFactory.getInstance();
        UserStory userStory = userStoryFactory.createNewUserStory(name, description, points, businessValue);
        userStory.doRegister();
        return userStory;
    }
}
