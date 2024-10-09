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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
        setSize(600, 300);
        setLocationRelativeTo(null);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        // Create initial sprints using SprintFactory only if the store is empty
        if (SprintStore.getInstance().getSprints().isEmpty()) {
            Sprint aSprint = SprintFactory.getSprintFactory().createNewSprint("foo", "bar", 2);
            Sprint aSprint2 = SprintFactory.getSprintFactory().createNewSprint("foo2", "bar2", 4);
            SprintStore.getInstance().addSprint(aSprint);
            SprintStore.getInstance().addSprint(aSprint2);
        }

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
                                    public void windowClosed(
                                            java.awt.event.WindowEvent windowEvent) {
                                        Sprint newSprint = form.getSprintObject();
                                        if (newSprint != null) {
                                            // The SprintFactory should handle both creation and storage. This is to avoid multiple entries in the SprintStore or the sprint list.
                                            SprintFactory.getSprintFactory().createNewSprint(
                                                newSprint.getName(), 
                                                newSprint.getDescription(), 
                                                newSprint.getLength()
                                            );
                                            refreshSprintList();
                                        }
                                    }
                                });
                    }
                });

        JButton sprintVariablesButton = new JButton("Create Sprint Cycle");
        sprintVariablesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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

    // New function to refresh the sprint list as soon as a new sprint is added.
    public static void refreshSprintList() {
        widgets.clear();
        subPanel.removeAll();

        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            widgets.add(new SprintWidget(sprint));
        }

        int i = 0;
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
    }
    public void addSprints(int numberOfSprints, int lowerBound, int upperBound) {
        for (int i = 1; i <= numberOfSprints; i++) {
            int sprintDuration = secureRandom.nextInt((upperBound - lowerBound) + 1) + lowerBound;

            String name = "Sprint " + i;
            String description = "";
            Sprint newSprint = SprintFactory.getSprintFactory().createNewSprint(name, description, sprintDuration);
            SprintStore.getInstance().addSprint(newSprint);
        }
        refreshSprintList();
    }

}