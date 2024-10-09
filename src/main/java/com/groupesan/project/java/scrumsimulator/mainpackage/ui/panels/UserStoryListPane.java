package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.UserStoryWidget;

public class UserStoryListPane extends JFrame implements BaseComponent {
    private List<UserStoryWidget> widgets = new ArrayList<>();
    private JPanel headerPanel;
    private JPanel storiesPanel;
    private String currentRole;

    public UserStoryListPane(String currentRole) {
        this.currentRole = currentRole;
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("User Story list");
        setSize(800, 600);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    
        // Create header panel
        headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    
        // Create panel for user stories
        storiesPanel = new JPanel();
        storiesPanel.setLayout(new BoxLayout(storiesPanel, BoxLayout.Y_AXIS)); // Vertical stacking
        JScrollPane scrollPane = new JScrollPane(storiesPanel); // Make scrollable if there are many items
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Load existing user stories
        loadUserStories();
    
        // Add "New User Story" button
        JButton newSprintButton = new JButton("New User Story");
        System.out.println("Current role :" + currentRole);
        if (currentRole != null && currentRole.equalsIgnoreCase("Scrum Master")) {
            newSprintButton.setEnabled(true);
        } else {
            newSprintButton.setEnabled(false);
        }
        newSprintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewUserStoryForm form = new NewUserStoryForm();
                form.setVisible(true);
    
                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        UserStory userStory = form.getUserStoryObject();
                        UserStoryStore.getInstance().addUserStory(userStory);
                        addUserStoryWidget(new UserStoryWidget(userStory, null, currentRole));
                    }
                });
            }
        });
        mainPanel.add(newSprintButton, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;

        panel.add(createHeaderLabel("ID"), gbc);
        panel.add(createHeaderLabel("Points"), gbc);
        panel.add(createHeaderLabel("Business Value"), gbc);
        gbc.weightx = 0.2;
        panel.add(createHeaderLabel("Name"), gbc);
        gbc.weightx = 0.5;
        panel.add(createHeaderLabel("Description"), gbc);

        return panel;
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return label;
    }

    private void loadUserStories() {
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            addUserStoryWidget(new UserStoryWidget(userStory, null, currentRole));
        }
    }

    private void addUserStoryWidget(UserStoryWidget widget) {
        widgets.add(widget);
        storiesPanel.add(widget);
        storiesPanel.revalidate();
        storiesPanel.repaint();
    }
    
    // Issue50: Added the refreshUserStories method to refresh the user stories, as the form is submitted.
    public void refreshUserStories() {
    storiesPanel.removeAll();
    loadUserStories();
    storiesPanel.revalidate();
    storiesPanel.repaint();
}
}