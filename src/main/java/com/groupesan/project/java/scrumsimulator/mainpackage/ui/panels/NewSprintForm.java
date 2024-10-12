package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NewSprintForm extends JFrame implements BaseComponent {
    JTextField nameField = new JTextField();
    JTextArea descArea = new JTextArea();
    SpinnerNumberModel sprintDaysSpinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    SpinnerNumberModel sprintPointsSpinnerNumberModel = new SpinnerNumberModel(5, 1, 999999, 1);
    JSpinner sprintDays = new JSpinner(sprintDaysSpinnerNumberModel);
    JSpinner sprintStoryPoints = new JSpinner(sprintPointsSpinnerNumberModel);

    List<UserStory> sprintBacklog = new ArrayList<>();
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

        JButton selectUserStoriesButton = new JButton("Select User Stories");

        selectUserStoriesButton.addActionListener(e -> showUserStorySelectionPopup());

        myJpanel.add(
                cancelButton,
                new CustomConstraints(0, 5, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(
                submitButton,
                new CustomConstraints(1, 5, GridBagConstraints.WEST, GridBagConstraints.NONE));
        myJpanel.add(
                selectUserStoriesButton,
                new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }

    private void showUserStorySelectionPopup() {
        JDialog userStoryDialog = new JDialog(this, "Select User Stories", true);
        userStoryDialog.setSize(300, 300);
        userStoryDialog.setLayout(new BorderLayout());

        List<UserStory> availableUserStories = getAvailableUserStories();

        DefaultListModel<UserStory> userStoryListModel = new DefaultListModel<>();
        for (UserStory userStory : availableUserStories) {
            userStoryListModel.addElement(userStory);
        }

        JList<UserStory> userStoryJList = new JList<>(userStoryListModel);
        userStoryJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        userStoryJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component1 = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UserStory) {
                    setText(((UserStory) value).getName() + " (" + ((UserStory) value).getPointValue() + " SP)");
                }
                return component1;
            }
        });

        JScrollPane scrollPane = new JScrollPane(userStoryJList);
        userStoryDialog.add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            List<UserStory> selectedUserStories = userStoryJList.getSelectedValuesList();
            if (selectedUserStories != null) {
                sprintBacklog.clear();
                sprintBacklog.addAll(selectedUserStories);
            }
            userStoryDialog.dispose();
        });

        userStoryDialog.add(okButton, BorderLayout.SOUTH);
        userStoryDialog.setLocationRelativeTo(this); // Center the popup on the main form
        userStoryDialog.setVisible(true); // Show the popup
    }

    private List<UserStory> getAvailableUserStories() {
        List<UserStory> allStories = UserStoryStore.getInstance().getUserStories();
        List<Sprint> sprints = SprintStore.getInstance().getSprints();
        List<UserStory> unavailableStories = new ArrayList<>();

        for (Sprint sprint : sprints) {
            unavailableStories.addAll(sprint.getUserStories());
        }

        List<UserStory> availableStories = new ArrayList<>(allStories);
        availableStories.removeAll(unavailableStories);

        return availableStories;
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

        if (!sprintBacklog.isEmpty()) {
            for (UserStory userStory : sprintBacklog) {
                mySprint.addUserStory(userStory);
            }
        }
        return mySprint;
    }
}