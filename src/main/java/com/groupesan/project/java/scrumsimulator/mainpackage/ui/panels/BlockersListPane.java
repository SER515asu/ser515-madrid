package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Blocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BlockerWidget;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockersListPane extends JFrame implements BaseComponent {
    private List<BlockerWidget> widgets = new ArrayList<>();
    private JPanel headerPanel;
    private JPanel blockersPanel;

    public BlockersListPane() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Blocker List");
        setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        blockersPanel = new JPanel();
        blockersPanel.setLayout(new BoxLayout(blockersPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(blockersPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadBlockers();

        JButton newBlockerButton = new JButton("New Blocker");
        newBlockerButton.addActionListener(e -> {
            NewBlockerForm form = new NewBlockerForm();
            form.setVisible(true);

            form.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    Blocker blocker = form.getBlockerObject();
                    BlockerStore.getInstance().addBlocker(blocker);
                    addBlockerWidget(new BlockerWidget(blocker));
                }
            });
        });
        mainPanel.add(newBlockerButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.1;

        panel.add(createHeaderLabel("ID"), gbc);
        panel.add(createHeaderLabel("Status"), gbc);
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

    private void loadBlockers() {
        for (Blocker blocker : BlockerStore.getInstance().getBlockers()) {
            addBlockerWidget(new BlockerWidget(blocker));
        }
    }

    private void addBlockerWidget(BlockerWidget widget) {
        widgets.add(widget);
        blockersPanel.add(widget);
        blockersPanel.revalidate();
        blockersPanel.repaint();
    }
}