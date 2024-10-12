package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditUserStoryForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.UserStoryListPane;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserStoryWidget extends JPanel implements BaseComponent {
    private JLabel id, points, bv, name, desc;
    private UserStory userStory;
    private JButton deleteButton;
    private UserStoryListPane parentPane;
    private String currentRole;

    public UserStoryWidget(UserStory userStory, UserStoryListPane parentPane, String currentRole) {
        this.userStory = userStory;
        this.parentPane = parentPane; // Issue50: Updated the constructor to accept a parentPane for refreshing the user stories list
        this.currentRole = currentRole;
        this.init();
    }

    public void init() {
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ID column
        gbc.weightx = 0.1;
        gbc.gridx = 0;
        id = new JLabel(userStory.getId().toString());
        id.setPreferredSize(new Dimension(50, 30));
        add(id, gbc);

        // Points column
        gbc.gridx = 1;
        points = new JLabel(Double.toString(userStory.getPointValue()));
        points.setPreferredSize(new Dimension(50, 30));
        add(points, gbc);

        // Business Value column
        gbc.gridx = 2;
        bv = new JLabel(Double.toString(userStory.getBusinessValue()));
        bv.setPreferredSize(new Dimension(50, 30));
        add(bv, gbc);

        // Name column
        gbc.gridx = 3;
        gbc.weightx = 0.2;
        name = new JLabel(userStory.getName());
        name.setPreferredSize(new Dimension(100, 30));
        add(name, gbc);

        // Description column with text truncation
        gbc.gridx = 4;
        gbc.weightx = 0.5;
        desc = new JLabel("<html>" + truncateText(userStory.getDescription(), 40) + "</html>");
        desc.setPreferredSize(new Dimension(200, 30));
        add(desc, gbc);

        addMouseListener(new MouseAdapter() { // mouseClicked event listener is for editing user story
            @Override
            public void mouseClicked(MouseEvent e) {
                EditUserStoryForm form = new EditUserStoryForm(userStory, parentPane);
                form.setVisible(true);
                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateWidgetContent();
                        if (parentPane != null) {
                            parentPane.refreshUserStories(); // Issue50: Updated the event handler to refresh the user stories list as the form is submitted.
                        }
                    }
                });
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserStoryStore.getInstance().removeUserStory(userStory);
                Container parent = UserStoryWidget.this.getParent();
                parent.remove(UserStoryWidget.this);
                parent.revalidate();
                parent.repaint();
                if (parentPane != null) {
                    parentPane.refreshUserStories(); // Issue50: Updated the delete button event handler to refresh the user stories list
                }
            }
        });

        if (currentRole != null && currentRole.equalsIgnoreCase("Scrum Master")) {
            deleteButton.setEnabled(true);
        } else {
            deleteButton.setEnabled(false);
        }

        this.add(deleteButton);

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    // Issue50: Added the updateWidgetContent method to update the user story widget content
    private void updateWidgetContent() {
        id.setText(userStory.getId().toString());
        points.setText(Double.toString(userStory.getPointValue()));
        bv.setText(Double.toString(userStory.getBusinessValue()));
        name.setText(userStory.getName());
        desc.setText("<html>" + truncateText(userStory.getDescription(), 40) + "</html>");
        revalidate();
        repaint();
    }
}