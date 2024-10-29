package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.SprintDetailsPane;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SprintWidget extends JPanel implements BaseComponent {

    JLabel id;
    JLabel name;
    JLabel desc;
    JLabel len;
    JLabel remaining;
    JLabel storyPoints;

    JLabel numUserStories;

    public SprintWidget(Sprint sprint) {
        id = new JLabel(Integer.toString(sprint.getId()));
        id.setPreferredSize(new Dimension(50, 20));
        name = new JLabel(sprint.getName());
        name.setPreferredSize(new Dimension(100, 20));
        desc = new JLabel(sprint.getDescription());
        desc.setPreferredSize(new Dimension(60, 20));
        len = new JLabel(Integer.toString(sprint.getLength()));
        len.setPreferredSize(new Dimension(50, 20));
        remaining = new JLabel(Integer.toString(sprint.getDaysRemaining()));
        remaining.setPreferredSize(new Dimension(100, 20));
        storyPoints = new JLabel(Integer.toString(sprint.getStoryPoints()));
        storyPoints.setPreferredSize(new Dimension(100, 20));
        numUserStories = new JLabel(Integer.toString(sprint.getUserStories().size()));
        numUserStories.setPreferredSize(new Dimension(100, 20));
        this.init();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SprintDetailsPane detailsPane = new SprintDetailsPane(sprint);
                detailsPane.setVisible(true);
            }
        });
    }

    public void init() {

        GridBagLayout myGridBagLayout = new GridBagLayout();

        setLayout(myGridBagLayout);

        add(
                id,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                name,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 0.2, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                desc,
                new CustomConstraints(
                        2, 0, GridBagConstraints.WEST, 0.4, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                len,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                remaining,
                new CustomConstraints(
                        4, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                storyPoints,
                new CustomConstraints(
                        5, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(
                numUserStories,
                new CustomConstraints(
                        6, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
    }
}
