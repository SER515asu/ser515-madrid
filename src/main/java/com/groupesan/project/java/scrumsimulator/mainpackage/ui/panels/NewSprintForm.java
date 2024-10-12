package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.SprintBacklogManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

public class NewSprintForm extends JFrame implements BaseComponent {
    JTextField nameField = new JTextField();
    JTextArea descArea = new JTextArea();
    SpinnerNumberModel sprintDaysSpinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    SpinnerNumberModel sprintPointsSpinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    JSpinner sprintDays = new JSpinner( sprintDaysSpinnerNumberModel);
    JSpinner sprintStoryPoints = new JSpinner(sprintPointsSpinnerNumberModel);


    DefaultListModel<String> listModel;
    List<UserStory> sprintBacklog = null;
    JList<String> usList;
    private boolean isSubmitted = false;

    public NewSprintForm() {
        this.init();
    }

    public void init() {
        setTitle("New Sprint");
        setSize(400, 300);

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

        JLabel pointsLabel = new JLabel("Length (Days):");
        myJpanel.add(
                pointsLabel,
                new CustomConstraints(
                        0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                sprintDays,
                new CustomConstraints(
                        1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.WEST));

        JLabel storyPointsLabel = new JLabel("Story Points:");
        myJpanel.add(
                storyPointsLabel,
                new CustomConstraints(
                        0, 3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                sprintStoryPoints,
                new CustomConstraints(
                        1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.WEST));

        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(e -> {
            isSubmitted = false;
            dispose();
        });

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Field validation
                        String name = nameField.getText().trim();
                        String description = descArea.getText().trim();
                        Integer length = (Integer) sprintDays.getValue();
                        if (name.isEmpty() || description.isEmpty() || length <= 0) {
                            // Display error message
                            JOptionPane.showMessageDialog(
                                    NewSprintForm.this,
                                    "All fields are required and must be valid.",
                                    "Validation Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        isSubmitted = true;
                        Sprint newSprint = getSprintObject();
                        if (newSprint != null) {
                            dispose();
                        }

                    }
                });

        JButton generateSBButton = new JButton("Generate Sprint Backlog");
        generateSBButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText().trim();
                        String description = descArea.getText().trim();
                        Integer length = (Integer) sprintDays.getValue();
                        Integer targetStoryPoints = (Integer) sprintStoryPoints.getValue();

                        if (name.isEmpty() || description.isEmpty() || length <= 0 || targetStoryPoints <= 0) {
                            JOptionPane.showMessageDialog(myJpanel, "Please fill all required fields", "Error", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        List<UserStory> availableUserStories = UserStoryStore.getInstance().getUserStories();
                        availableUserStories.sort((us1, us2) -> Integer.compare((int) us2.getBusinessValue(), (int) us1.getBusinessValue()));

                        SprintBacklogManager sprintBacklogManager = new SprintBacklogManager();
                        sprintBacklog = sprintBacklogManager.generateSprintBacklog(availableUserStories, targetStoryPoints);
                        if (sprintBacklog.isEmpty()) {
                            JOptionPane.showMessageDialog(myJpanel, "No stories could be added to the sprint backlog. All stories exceed point limits","Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (UserStory userStory : sprintBacklog) {
                            listModel.addElement(userStory.toString());
                        }
                        listModel = new DefaultListModel<>();
                        usList = new JList<>(listModel);
                        usList.revalidate();
                        usList.repaint();

                        isSubmitted = true;
                        Sprint newSprint = getSprintObject();
                        if (newSprint != null) {
//                            SprintStore.getInstance().addSprint(newSprint);
                            SprintListPane.refreshSprintList();
                            dispose();
                        }
                    }
                });



        listModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            listModel.addElement(String.valueOf(userStory));
        }

        usList = new JList<>(listModel);
        usList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        usList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UserStory) {
                    setText(((UserStory) value).toString());
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(usList);
        scrollPane.setPreferredSize(new Dimension(300, 100));

        JLabel userStoriesLabel = new JLabel("User Stories:");
        myJpanel.add(
                userStoriesLabel,
                new CustomConstraints(
                        0, 4, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                usList,
                new CustomConstraints(
                        1, 4, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.NONE));

        myJpanel.add(
                cancelButton,
                new CustomConstraints(0, 5, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(
                submitButton,
                new CustomConstraints(1, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));


        myJpanel.add(
                generateSBButton,
                new CustomConstraints(2, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));


        add(myJpanel);
    }
    public Sprint getSprintObject() {
        if (!isSubmitted) {
            return null;
        }
        String name = nameField.getText();
        String description = descArea.getText();
        Integer length = (Integer) sprintDays.getValue();
        SprintFactory sprintFactory = SprintFactory.getSprintFactory();

        Sprint mySprint = sprintFactory.createNewSprint(name, description, length);

        if (sprintBacklog != null && !sprintBacklog.isEmpty()) {
            for (UserStory userStory : sprintBacklog) {
                mySprint.addUserStory(userStory);
            }
        } else {
            int[] selectedIdx = usList.getSelectedIndices();
            for (int idx : selectedIdx) {
                String stringIdentifier = listModel.getElementAt(idx);
                for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
                    if (stringIdentifier.equals(userStory.toString())) {
                        mySprint.addUserStory(userStory);
                        break;
                    }
                }
            }
        }
        sprintBacklog = null;
        return mySprint;
    }
}