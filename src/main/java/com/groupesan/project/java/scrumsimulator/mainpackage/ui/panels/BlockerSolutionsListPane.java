package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerSolutionsStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BlockerSolutionsWidget;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BlockerSolutionsListPane extends JFrame implements BaseComponent {
    private List<BlockerSolutionsWidget> widgets = new ArrayList<>();
    private JPanel headerPanel;
    private JPanel blockerSolutionsPanel;

    public BlockerSolutionsListPane() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Blocker Solutions List");
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        blockerSolutionsPanel = new JPanel();
        blockerSolutionsPanel.setLayout(new BoxLayout(blockerSolutionsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(blockerSolutionsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadBlockerSolutions();

        JButton newBlockerSolutionButton = new JButton("New Solution");
        newBlockerSolutionButton.addActionListener(e -> {
            NewBlockerSolutionForm form = new NewBlockerSolutionForm();
            form.setVisible(true);

            form.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    SprintBlockerSolution blockerSolution = form.getBlockerSolutionObject();
                    if (blockerSolution != null) {
                        BlockerSolutionsStore.getInstance().addBlockerSolution(blockerSolution);
                        addBlockerSolutionsWidget(new BlockerSolutionsWidget(blockerSolution));
                    }
                }
            });
        });
        mainPanel.add(newBlockerSolutionButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;

        panel.add(createHeaderLabel("ID"), gbc);
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

    private void loadBlockerSolutions() {
        for (SprintBlockerSolution blockerSolution : BlockerSolutionsStore.getInstance().getBlockerSolutions()) {
            addBlockerSolutionsWidget(new BlockerSolutionsWidget(blockerSolution));
        }
    }

    private void addBlockerSolutionsWidget(BlockerSolutionsWidget widget) {
        widgets.add(widget);
        blockerSolutionsPanel.add(widget);
        blockerSolutionsPanel.revalidate();
        blockerSolutionsPanel.repaint();
    }
}