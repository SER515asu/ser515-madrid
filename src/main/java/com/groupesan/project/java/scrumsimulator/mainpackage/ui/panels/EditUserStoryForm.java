package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditUserStoryForm extends JFrame implements BaseComponent {
        private UserStoryListPane parentPane;
        Double[] pointsList = { 1.0, 2.0, 3.0, 5.0, 8.0, 11.0, 19.0, 30.0, 49.0 };

        Double[] businessValueList = { 1.0, 2.0, 3.0, 5.0, 8.0, 11.0, 19.0, 30.0, 49.0 };

        public EditUserStoryForm(UserStory userStory, UserStoryListPane parentPane) {
                this.userStory = userStory;
                this.parentPane = parentPane; // Issue50: Added parentPane to the constructor for implementing a callback function to refresh the user stories list
                this.init();
        }

        private UserStory userStory;

        private JTextField nameField = new JTextField();
        private JTextArea descArea = new JTextArea();
        private JComboBox<Double> pointsCombo = new JComboBox<>(pointsList);

        private JComboBox<Double> businessValueCombo = new JComboBox<>(businessValueList);

        public void init() {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setTitle("Edit User Story " + userStory.getId().toString());
                setSize(400, 300);

                nameField = new JTextField(userStory.getName());
                descArea = new JTextArea(userStory.getDescription());
                pointsCombo = new JComboBox<>(pointsList);
                pointsCombo.setSelectedItem(userStory.getPointValue());
                businessValueCombo.setSelectedItem(userStory.getBusinessValue());

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
                                                1, 0, GridBagConstraints.EAST, 1.0, 0.0,
                                                GridBagConstraints.HORIZONTAL));

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
                                                1, 2, GridBagConstraints.EAST, 1.0, 0.0,
                                                GridBagConstraints.HORIZONTAL));

                JLabel businessValueLabel = new JLabel("Business Value:");
                myJpanel.add(
                                businessValueLabel,
                                new CustomConstraints(
                                                0, 3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
                myJpanel.add(
                                businessValueCombo,
                                new CustomConstraints(
                                                1, 3, GridBagConstraints.EAST, 1.0, 0.0,
                                                GridBagConstraints.HORIZONTAL));

                JButton cancelButton = new JButton("Cancel");

                cancelButton.addActionListener(
                                new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                                dispose();
                                        }
                                });

                JButton submitButton = new JButton("Submit");

                submitButton.addActionListener(
                                new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                                String name = nameField.getText();
                                                String description = descArea.getText();
                                                Double points = (Double) pointsCombo.getSelectedItem();
                                                Double businessValue = (Double) businessValueCombo.getSelectedItem();

                                                userStory.setName(name);
                                                userStory.setDescription(description);
                                                userStory.setPointValue(points);
                                                userStory.setBusinessValue(businessValue);
                                                if (parentPane != null) {
                                                        parentPane.refreshUserStories(); // Issue50: Refresh the user stories list
                                                }
                                                dispose();
                                        }
                                });

                myJpanel.add(
                                cancelButton,
                                new CustomConstraints(0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE));
                myJpanel.add(
                                submitButton,
                                new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));

                add(myJpanel);
        }
}
