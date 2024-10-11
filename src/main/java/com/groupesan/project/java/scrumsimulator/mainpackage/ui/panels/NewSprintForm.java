package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.SprintBacklogManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    JSpinner sprintDays = new JSpinner( sprintDaysSpinnerNumberModel);
    JSpinner sprintStoryPoints = new JSpinner(sprintPointsSpinnerNumberModel);

    DefaultListModel<String> listModel;
    List<UserStory> sprintBacklog = null;
    JList<String> usList;

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
                        String name = nameField.getText().trim();
                        String description = descArea.getText().trim();
                        Integer length = (Integer) sprintDays.getValue();
                        Integer storyPoints = (Integer) sprintStoryPoints.getValue();
                        int[] selectedIdx = usList.getSelectedIndices();
                        if(name.isEmpty() || description.isEmpty() || length <= 0 || storyPoints <= 0 || selectedIdx.length == 0) {
                            JOptionPane.showMessageDialog(myJpanel, "Please fill all required Fields", "Error", JOptionPane.INFORMATION_MESSAGE);
                            return;

                        }
                        JOptionPane.showMessageDialog(
                                myJpanel, "Sprint created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
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

                        sprintBacklog = findOptimalStories(availableUserStories, targetStoryPoints); ;
//                        sprintBacklog = sprintBacklogManager.generateSprintBacklog(userStoryList, (int)sprintStoryPoints.getValue());
                        if (sprintBacklog.isEmpty()) {
                            JOptionPane.showMessageDialog(myJpanel, "No stories could be added to the sprint backlog. All stories exceed point limits","Error", JOptionPane.ERROR_MESSAGE);
                            return ;
                        }

                        listModel.clear(); // Clear the old list
                        for (UserStory userStory : sprintBacklog) {
                            listModel.addElement(userStory.toString()); // Add selected user stories
                        }

                        usList.setModel(listModel);
                        usList.repaint();
                        usList.revalidate();
//                        JOptionPane.showMessageDialog(
//                                myJpanel, "Sprint backlog generated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                });


        listModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            listModel.addElement(userStory.toString());
        }

        usList = new JList<>(listModel);
        usList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
    /**
     * Backtracking function to find the optimal combination of user stories that add up to the target story points.
     * This maximizes the business value.
     */
    private List<UserStory> findOptimalStories(List<UserStory> availableUserStories, int targetStoryPoints) {
        List<UserStory> result = new ArrayList<>();
        List<UserStory> temp = new ArrayList<>();
        backtrack(availableUserStories, result, temp, 0, targetStoryPoints, 0, 0);
        return result;
    }

    /**
     * Helper function for the backtracking process.
     */
    private void backtrack(List<UserStory> stories, List<UserStory> result, List<UserStory> temp, int start, int targetPoints, int currentPoints, int currentValue) {
        if (currentPoints == targetPoints) {
            // If the current selection has the exact number of points, check if it has a higher value
            if (calculateBusinessValue(temp) > calculateBusinessValue(result)) {
                result.clear();
                result.addAll(temp);
            }
            return;
        }

        // Try adding more stories to the current selection
        for (int i = start; i < stories.size(); i++) {
            UserStory story = stories.get(i);
            int storyPoints = (int) story.getBusinessValue();

            if (currentPoints + storyPoints <= targetPoints) {
                // Add the story and continue exploring
                temp.add(story);
                backtrack(stories, result, temp, i + 1, targetPoints, currentPoints + storyPoints, (int) (currentValue + story.getBusinessValue()));
                temp.remove(temp.size() - 1); // Remove the story to backtrack
            }
        }
    }

    /**
     * Helper function to calculate total business value of a list of user stories.
     */
    private int calculateBusinessValue(List<UserStory> userStories) {
        int totalValue = 0;
        for (UserStory us : userStories) {
            totalValue += us.getBusinessValue();
        }
        return totalValue;
    }

    public Sprint getSprintObject() {
        String name = nameField.getText();
        String description = descArea.getText();
        Integer length = (Integer) sprintDays.getValue();
        SprintFactory sprintFactory = SprintFactory.getSprintFactory();

        Sprint mySprint = sprintFactory.createNewSprint(name, description, length);

        // Check if sprintBacklog is populated (i.e., the user clicked "Generate Sprint Backlog")
        if (sprintBacklog != null && !sprintBacklog.isEmpty()) {
            // Add the stories from the generated sprint backlog
            for (UserStory userStory : sprintBacklog) {
                mySprint.addUserStory(userStory);
            }
        } else {
            // If no backlog was generated, fall back to manually selected stories
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

//        SprintStore.getInstance().addSprint(mySprint);
//
//        System.out.println(mySprint);

        return mySprint;
    }
}
