package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Display Sprint details when clicked on a sprint.
 * */
public class SprintDetailsPane extends JFrame{
    private Sprint sprint;

    public SprintDetailsPane(Sprint sprint) {
        this.sprint = sprint;
        setTitle("Sprint Details");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        setVisible(true);
    }

    public void init() {
        JPanel myJpanel = new JPanel(new GridBagLayout());

        JLabel sprintNameLabel = new JLabel("Sprint Name: ");
        myJpanel.add(sprintNameLabel,
                new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.1, 0.1, GridBagConstraints.HORIZONTAL));

        JLabel sprintNameValue = new JLabel(sprint.getName());
        myJpanel.add(sprintNameValue,
                new CustomConstraints(1, 0, GridBagConstraints.WEST, 0.1, 0.1, GridBagConstraints.HORIZONTAL));

        JLabel sprintDescLabel = new JLabel("Description: ");
        myJpanel.add(sprintDescLabel,
                new CustomConstraints(0, 1, GridBagConstraints.WEST, 0.1, 0.1, GridBagConstraints.HORIZONTAL));

        JLabel sprintDescValue = new JLabel(sprint.getDescription());
        myJpanel.add(sprintDescValue,
                new CustomConstraints(1, 1, GridBagConstraints.WEST, 0.1, 0.1, GridBagConstraints.HORIZONTAL));

        JLabel userStoriesLabel = new JLabel("User Stories:");
        myJpanel.add(userStoriesLabel,
                new CustomConstraints(0, 2, GridBagConstraints.WEST, 0.1, 0.1, GridBagConstraints.HORIZONTAL));

        //Display all user stories in the sprint
        List<UserStory> userStories = sprint.getUserStories();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (UserStory story : userStories) {
            listModel.addElement(story.getName());
        }
        JList<String> userStoriesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(userStoriesList);

        myJpanel.add(
                scrollPane,
                new CustomConstraints(1, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.BOTH)
        );
        //Allow developer to cancel saving added changes
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                }
        );
        //Allow developer to save changes of sprint
        JButton saveSprintButton = new JButton("Save");
        saveSprintButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                }
        );
        myJpanel.add(
                cancelButton,
                new CustomConstraints(0, 4, GridBagConstraints.EAST, GridBagConstraints.NONE)
        );
        myJpanel.add(
                saveSprintButton,
                new CustomConstraints(1, 4, GridBagConstraints.WEST, GridBagConstraints.NONE)
        );

        add(myJpanel);
    }
}
