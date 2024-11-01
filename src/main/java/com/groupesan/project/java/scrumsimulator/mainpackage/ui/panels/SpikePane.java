package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.*;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.SpikeHandler;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class SpikePane extends JFrame implements BaseComponent {
    private static JPanel subPanel = new JPanel(new GridBagLayout());
    private static JScrollPane scrollPane = new JScrollPane();

    public SpikePane() {
        this.init();
    }

    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Spike Panel");
        setSize(1200, 400);
        setLocationRelativeTo(null);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(myGridbagLayout);

        refreshUserStoryList();

        GridBagConstraints scrollPaneConstraint = new CustomConstraints(
                0, 0, GridBagConstraints.NORTH, 1.0, 1.0, GridBagConstraints.BOTH);
        mainPanel.add(scrollPane, scrollPaneConstraint);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshUserStoryList());

        mainPanel.add(
                refreshButton,
                new CustomConstraints(
                        0, 1, GridBagConstraints.EAST, 0.1, 0.1, GridBagConstraints.NONE));

        add(mainPanel);
    }

    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;
        gbc.gridx = 0;

        JLabel userStoryIdHeader = createHeaderLabel("US #ID");
        JLabel nameHeader = createHeaderLabel("Name");
        JLabel descHeader = createHeaderLabel("Description");
        JLabel sprintIdHeader = createHeaderLabel("Sprint #ID");
        JLabel storyPointsHeader = createHeaderLabel("UserStory Points");
        JLabel statusHeader = createHeaderLabel("Spike Status");
        JLabel actionHeader = createHeaderLabel("Action");

        gbc.gridx = 0;
        headerPanel.add(userStoryIdHeader, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.2;
        headerPanel.add(nameHeader, gbc);
        gbc.gridx = 2;
        gbc.weightx = 0.4;
        headerPanel.add(descHeader, gbc);
        gbc.gridx = 3;
        gbc.weightx = 0.1;
        headerPanel.add(sprintIdHeader, gbc);
        gbc.gridx = 4;
        gbc.weightx = 0.2;
        headerPanel.add(storyPointsHeader, gbc);
        gbc.gridx = 5;
        gbc.weightx = 0.3;
        headerPanel.add(statusHeader, gbc);
        gbc.gridx = 6;
        gbc.weightx = 0.2;
        headerPanel.add(actionHeader, gbc);

        return headerPanel;
    }

    public static void refreshUserStoryList() {
        subPanel.removeAll();

        List<Sprint> sprints = SprintStore.getInstance().getSprints();

        boolean hasUserStories = sprints.stream()
                .anyMatch(sprint -> !sprint.getUserStories().isEmpty());

        if (!hasUserStories) {
            JLabel emptyLabel = new JLabel("No user stories in any of the sprints' backlogs");
            emptyLabel.setHorizontalAlignment(JLabel.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 14));
            emptyLabel.setForeground(new Color(128, 128, 128));

            subPanel.add(emptyLabel,
                    new CustomConstraints(0, 0, GridBagConstraints.CENTER, 1.0, 1.0, GridBagConstraints.BOTH));
        } else {
            JPanel headerPanel = createHeaderPanel();
            subPanel.add(headerPanel,
                    new CustomConstraints(0, 0, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

            java.util.Set<String> displayedStoryIds = new java.util.HashSet<>();

            int row = 1;
            for (Sprint sprint : sprints) {
                for (UserStory story : sprint.getUserStories()) {
                    if (!displayedStoryIds.add(story.getId().toString())) {
                        continue;
                    }

                    JPanel storyPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.fill = GridBagConstraints.HORIZONTAL;
                    gbc.gridy = 0;

                    JLabel idLabel = new JLabel(story.getId().toString());
                    JLabel nameLabel = new JLabel(story.getName());
                    JLabel descLabel = new JLabel(story.getDescription());
                    JLabel sprintIdLabel = new JLabel(String.valueOf(sprint.getId()));
                    JLabel pointsLabel = new JLabel(String.valueOf((int) story.getPointValue()));
                    
                    final JLabel statusLabel = new JLabel(story.getStatus() != null ? story.getStatus() : "");
                    statusLabel.setHorizontalAlignment(JLabel.CENTER);
    
                    gbc.gridx = 0;
                    gbc.weightx = 0.1;
                    storyPanel.add(idLabel, gbc);
                    gbc.gridx = 1;
                    gbc.weightx = 0.2;
                    storyPanel.add(nameLabel, gbc);
                    gbc.gridx = 2;
                    gbc.weightx = 0.4;
                    storyPanel.add(descLabel, gbc);
                    gbc.gridx = 3;
                    gbc.weightx = 0.3;
                    storyPanel.add(sprintIdLabel, gbc);
                    gbc.gridx = 4;
                    gbc.weightx = 0.2;
                    storyPanel.add(pointsLabel, gbc);
                    gbc.gridx = 5;
                    gbc.weightx = 0.2;
                    storyPanel.add(statusLabel, gbc);
    
                    JButton createSpikeButton = new JButton("Create Spike");
                    createSpikeButton.addActionListener(e -> {
//                        CreateNewSpikeForm createNewSpikeForm = new CreateNewSpikeForm();
//                        createNewSpikeForm.setModal(true);
//                        createNewSpikeForm.setVisible(true);
//                        Spike spike = createNewSpikeForm.getSpike();
//                        if (spike != null) {
//                            story.setStatus("Unresolved");
//                            statusLabel.setText("Unresolved");
//                            System.out.println("Spike created and status updated for story: " + story.getId());
//
//                            statusLabel.revalidate();
//                            statusLabel.repaint();
//
//                            System.out.println("Spike created with values: " +
//                                    "Upper Bound = " + spike.getUpperBound() +
//                                    ", Lower Bound = " + spike.getLowerBound() +
//                                    ", Spike Value = " + spike.getSpikeValue() +
//                                    ", Calculated Upper Bound = " + spike.getCalculatedUpperBound() +
//                                    ", Status = " + story.getStatus());
//                        }
                        SpikeHandler spikeHandler = new SpikeHandler();
                        TaskStatus spikeStatus = spikeHandler.getSpikeStatus();
                        spikeHandler=null;

                        String userStoryStatus= spikeStatus.toString();
                        if(userStoryStatus!=null && (userStoryStatus.equals("RESOLVED")|| userStoryStatus.equals("UNRESOLVED"))){
                            createSpikeButton.setEnabled(false);

                        }
                        else{
                            createSpikeButton.setEnabled(true);
                        }
                        story.setStatus(userStoryStatus.toString());
                        statusLabel.setText(userStoryStatus.toString());
                        statusLabel.revalidate();
                        statusLabel.repaint();
                    });
    
                    gbc.gridx = 6;
                    gbc.weightx = 0.2;
                    storyPanel.add(createSpikeButton, gbc);
    
                    storyPanel.setPreferredSize(new Dimension(storyPanel.getPreferredSize().width, 25));
    
                    subPanel.add(storyPanel,
                            new CustomConstraints(0, row++, GridBagConstraints.WEST, 1.0, 0.0,
                                    GridBagConstraints.HORIZONTAL));
                }
            }
        }
    
        subPanel.revalidate();
        subPanel.repaint();
        scrollPane.setViewportView(subPanel);
    }

    private static JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return label;
    }
}