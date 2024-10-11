package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.SprintBacklogManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditSprintForm extends JFrame implements BaseComponent {

    private Sprint sprint;
    JTextField nameField = new JTextField();
    JTextArea descArea = new JTextArea();
    SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    SpinnerNumberModel sprintPointsSpinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);

    JSpinner sprintDays = new JSpinner(spinnerNumberModel);
    JSpinner sprintStoryPoints = new JSpinner(sprintPointsSpinnerNumberModel);

    List<UserStory> sprintBacklog = null;
    DefaultListModel<String> listModel;
    DefaultListModel<String> sprintListModel;
    JList<String> usList;
    JList<String> sprintList;

    public EditSprintForm(Sprint sprint, SprintDetailsPane sprintDetailsPane) {
        this.sprint = sprint;
        this.init();
    }

    @Override
    public void init() {
        setTitle("Edit Sprint");
        setSize(800, 600);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        BorderLayout myBorderLayout = new BorderLayout();
        setLayout(myBorderLayout);

        nameField = new JTextField(sprint.getName());
        descArea = new JTextArea(sprint.getDescription());
        sprintDays = new JSpinner(new SpinnerNumberModel(5, 1, 999999, 1));

        JLabel nameLabel = new JLabel("Name:");
        myJpanel.add(nameLabel, new CustomConstraints(0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(nameField, new CustomConstraints(1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JLabel descLabel = new JLabel("Description:");
        myJpanel.add(descLabel, new CustomConstraints(0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(new JScrollPane(descArea), new CustomConstraints(1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        JLabel pointsLabel = new JLabel("Length (Days):");
        myJpanel.add(pointsLabel, new CustomConstraints(0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(sprintDays, new CustomConstraints(1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.WEST));

        JLabel storyPointsLabel = new JLabel("Story Points:");
        myJpanel.add(storyPointsLabel, new CustomConstraints(0, 3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(sprintStoryPoints, new CustomConstraints(1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.WEST));

        listModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            listModel.addElement(userStory.toString());
        }

        usList = new JList<>(listModel);
        usList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane allStoriesScrollPane = new JScrollPane(usList);
        allStoriesScrollPane.setPreferredSize(new Dimension(300, 400));

        // Sprint User Stories List
        sprintListModel = new DefaultListModel<>();
        for (UserStory userStory : sprint.getUserStories()) {
            sprintListModel.addElement(userStory.toString());
        }

        sprintList = new JList<>(sprintListModel);
        sprintList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sprintStoriesScrollPane = new JScrollPane(sprintList);
        sprintStoriesScrollPane.setPreferredSize(new Dimension(300, 400));


        JLabel allStoriesLabel = new JLabel("Product Backlog:");
        myJpanel.add(allStoriesLabel, new CustomConstraints(0, 4, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(allStoriesScrollPane, new CustomConstraints(0, 5, GridBagConstraints.WEST, GridBagConstraints.BOTH));

        JLabel sprintStoriesLabel = new JLabel("Sprint Backlog:");
        myJpanel.add(sprintStoriesLabel, new CustomConstraints(1, 4, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(sprintStoriesScrollPane, new CustomConstraints(1, 5, GridBagConstraints.EAST, GridBagConstraints.BOTH));


        JButton removeButton = new JButton("Remove from Sprint");
        removeButton.addActionListener(e -> {
            String selectedValue = sprintList.getSelectedValue();
            if (selectedValue != null) {
                sprintListModel.removeElement(selectedValue);
                UserStory userStory = UserStoryStore.getInstance().getUserStories().stream()
                        .filter(us -> us.toString().equals(selectedValue))
                        .findFirst().orElse(null);
                if (userStory != null) {
                    sprint.removeUserStory(userStory);
                }
            }
        });

//        myJpanel.add(removeButton, new CustomConstraints(1, 6, GridBagConstraints.EAST, GridBagConstraints.NONE));
        JButton generateSBButton = new JButton("Generate Sprint Backlog");
        generateSBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<UserStory> userStoryList = UserStoryStore.getInstance().getUserStories();
                if (userStoryList.isEmpty()) {
                    JOptionPane.showMessageDialog(myJpanel, "No user stories available.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                SprintBacklogManager sprintBacklogManager = new SprintBacklogManager();
                sprintBacklog = sprintBacklogManager.generateSprintBacklog(userStoryList, (int) sprintStoryPoints.getValue());
                if (sprintBacklog.isEmpty()) {
                    JOptionPane.showMessageDialog(myJpanel, "No stories could be added to the sprint backlog. All stories exceed point limits", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }



                for (UserStory userStory : sprintBacklog) {
                    if (!sprint.getUserStories().contains(userStory)) {
                        sprint.addUserStory(userStory);
                    }
                    if (!sprintListModel.contains(userStory.toString())) {
                        sprintListModel.addElement(userStory.toString());
                    }
                }

            }
        });
//        myJpanel.add(generateSBButton, new CustomConstraints(0, 6, GridBagConstraints.WEST, GridBagConstraints.NONE));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Cancel and Submit Buttons
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String description = descArea.getText();
                Integer length = (Integer) sprintDays.getValue();
                sprint.getUserStories().clear();
                usList.getSelectedValuesList().forEach(us -> {
                    UserStoryStore.getInstance().getUserStories().stream()
                            .filter(userStory -> userStory.toString().equals(us))
                            .findFirst()
                            .ifPresent(sprint::addUserStory);
                });

                SprintFactory.getSprintFactory().updateSprint(sprint, name, description, length);
                dispose();
                SprintListPane.refreshSprintList();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        });

//        myJpanel.add(cancelButton, new CustomConstraints(0, 6, GridBagConstraints.EAST, GridBagConstraints.NONE));
//        myJpanel.add(submitButton, new CustomConstraints(1, 6, GridBagConstraints.WEST, GridBagConstraints.NONE));
        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(generateSBButton);
        buttonPanel.add(removeButton);


        myJpanel.add(buttonPanel, new CustomConstraints(0, 7, GridBagConstraints.CENTER, GridBagConstraints.NONE, 2, 1));
        add(myJpanel);
    }

    public Sprint getSprintObject() {
        String name = nameField.getText();
        String description = descArea.getText();
        Integer length = (Integer) sprintDays.getValue();

        SprintFactory sprintFactory = SprintFactory.getSprintFactory();
        Sprint mySprint = sprintFactory.createNewSprint(name, description, length);

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

        SprintStore.getInstance().addSprint(mySprint);
        System.out.println(mySprint);
        return mySprint;
    }
}