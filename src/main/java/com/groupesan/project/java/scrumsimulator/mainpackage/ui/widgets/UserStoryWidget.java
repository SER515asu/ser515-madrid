package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditUserStoryForm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserStoryWidget extends JPanel implements BaseComponent {
    private JLabel id, points, bv, name, desc;
    private UserStory userStory;

    public UserStoryWidget(UserStory userStory) {
        this.userStory = userStory;
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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Pass the existing user story to the form for editing
                EditUserStoryForm form = new EditUserStoryForm(userStory);
                form.setVisible(true);
            }
        });

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }
}