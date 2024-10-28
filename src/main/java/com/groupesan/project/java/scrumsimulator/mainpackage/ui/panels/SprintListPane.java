package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SprintWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SprintListPane extends JFrame implements BaseComponent {
    private static List<SprintWidget> widgets = new ArrayList<>();
    private static JPanel subPanel = new JPanel(new GridBagLayout());
    private static JScrollPane scrollPane = new JScrollPane();

    SecureRandom secureRandom = new SecureRandom();

    public SprintListPane() {
        this.init();
    }
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sprints list");
        setSize(800, 500);
        setLocationRelativeTo(null);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        refreshSprintList();

        GridBagConstraints scrollPaneConstraint = new CustomConstraints(
                0, 0, GridBagConstraints.NORTH, 1.0, 1.0, GridBagConstraints.BOTH);
        scrollPaneConstraint.gridwidth = 2;
        myJpanel.add(
                scrollPane,
                scrollPaneConstraint
                );

        JButton newSprintButton = new JButton("New Sprint");
        newSprintButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        NewSprintForm form = new NewSprintForm();
                        form.setVisible(true);

                        form.addWindowListener(
                                new java.awt.event.WindowAdapter() {
                                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                                        refreshSprintList();
                                    }
                                });
                    }
                });

        JButton sprintVariablesButton = new JButton("Create Sprint Cycle");
        sprintVariablesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SprintStore.getInstance().clearSprintList();
                        refreshSprintList();
                        SprintVariablePane sprintVariablePane = new SprintVariablePane(SprintListPane.this);
                        sprintVariablePane.setVisible(true);
                    }
                });
        myJpanel.add(
                newSprintButton,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 0.5, 0.2, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                sprintVariablesButton,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 0.5, 0.2, GridBagConstraints.HORIZONTAL));


        add(myJpanel);
    }

    private static JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridBagLayout());

        Font boldFont = new Font("Arial", Font.BOLD, 12);

        JLabel idHeader = new JLabel("ID");
        idHeader.setFont(boldFont);
        idHeader.setPreferredSize(new Dimension(50, 20));

        JLabel nameHeader = new JLabel("Name");
        nameHeader.setFont(boldFont);
        nameHeader.setPreferredSize(new Dimension(100, 20));

        JLabel descHeader = new JLabel("Description");
        descHeader.setFont(boldFont);
        descHeader.setPreferredSize(new Dimension(60, 20));

        JLabel lenHeader = new JLabel("Length");
        lenHeader.setFont(boldFont);
        lenHeader.setPreferredSize(new Dimension(50, 20));

        JLabel remainingHeader = new JLabel("Days Remaining");
        remainingHeader.setFont(boldFont);
        remainingHeader.setPreferredSize(new Dimension(100, 20));

        JLabel storyPointsHeader = new JLabel("Total Story points");
        storyPointsHeader.setFont(boldFont);
        storyPointsHeader.setPreferredSize(new Dimension(100, 20));

        JLabel numUserStoriesHeader = new JLabel("User Stories");
        numUserStoriesHeader.setFont(boldFont);
        numUserStoriesHeader.setPreferredSize(new Dimension(100, 20));

        // Use the same layout constraints as in SprintWidget
        headerPanel.add(idHeader, new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(nameHeader, new CustomConstraints(1, 0, GridBagConstraints.WEST, 0.2, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(descHeader, new CustomConstraints(2, 0, GridBagConstraints.WEST, 0.4, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(lenHeader, new CustomConstraints(3, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(remainingHeader, new CustomConstraints(4, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(storyPointsHeader, new CustomConstraints(5, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        headerPanel.add(numUserStoriesHeader, new CustomConstraints(6, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));

        return headerPanel;
    }


    // New function to refresh the sprint list as soon as a new sprint is added.
    public static void refreshSprintList() {
        widgets.clear();
        subPanel.removeAll();

        subPanel.add(createHeaderPanel(), new CustomConstraints(0, 0, GridBagConstraints.WEST, 1.0, 0.1, GridBagConstraints.HORIZONTAL));
        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            widgets.add(new SprintWidget(sprint));
        }

        int i = 1;
        for (SprintWidget widget : widgets) {
            subPanel.add(
                    widget,
                    new CustomConstraints(
                            0,
                            i++,
                            GridBagConstraints.WEST,
                            1.0,
                            0.1,
                            GridBagConstraints.HORIZONTAL));
        }

        subPanel.revalidate();
        subPanel.repaint();
        scrollPane.setViewportView(subPanel);
    }
    public void addSprints(int numberOfSprints, int lowerBound, int upperBound, int storyPoints) {
        for (int i = 1; i <= numberOfSprints; i++) {
            int sprintDuration = secureRandom.nextInt((upperBound - lowerBound) + 1) + lowerBound;

            String name = "Sprint " + i;
            String description = "";
            Sprint newSprint = SprintFactory.getSprintFactory().createNewSprint(name, description, sprintDuration, storyPoints);
            SprintStore.getInstance().addSprint(newSprint);
        }
        refreshSprintList();
    }

}