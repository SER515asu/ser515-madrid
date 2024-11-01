package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BlockerWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BlockersListPane extends JFrame implements BaseComponent {
    private List<BlockerWidget> widgets = new ArrayList<>();
    private JPanel headerPanel;
    private JPanel blockersPanel;
    private SimulationStateManager simStateManager;

    public BlockersListPane() {
        this.init();
    }

    public void setSimulationStateManager(SimulationStateManager simStateManager) {
        this.simStateManager = simStateManager;
        // Update existing widgets with the state manager
        for (BlockerWidget widget : widgets) {
            widget.setStateManager(simStateManager);
        }
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton newBlockerButton = new JButton("New Blocker");
        newBlockerButton.addActionListener(e -> {
            if (simStateManager != null && simStateManager.isRunning()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cannot create new blockers while the simulation is running",
                    "Operation Not Allowed: Simulation Running",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            NewBlockerForm form = new NewBlockerForm();
            form.setVisible(true);

            form.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    SprintBlocker blocker = form.getBlockerObject();
                    if (blocker != null) {
                        BlockerStore.getInstance().addBlocker(blocker);
                        addBlockerWidget(new BlockerWidget(blocker, simStateManager));
                    }
                }
            });
        });

        buttonPanel.add(newBlockerButton);

        JButton blockerSolutionsButton = new JButton("Blocker Solutions");
        blockerSolutionsButton.addActionListener(e -> {
            if (simStateManager != null && simStateManager.isRunning()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cannot modify blocker solutions while the simulation is running",
                    "Operation Not Allowed: Simulation Running",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            BlockerSolutionsListPane blockerSolutionsPane = new BlockerSolutionsListPane();
            blockerSolutionsPane.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    refreshBlockersList();
                }
            });
            blockerSolutionsPane.setVisible(true);
        });
        buttonPanel.add(blockerSolutionsButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.2;

        panel.add(createHeaderLabel("ID"), gbc);
        panel.add(createHeaderLabel("Status"), gbc);
        gbc.weightx = 0.4;
        panel.add(createHeaderLabel("Name"), gbc);
        gbc.weightx = 0.5;
        panel.add(createHeaderLabel("Description"), gbc);
        gbc.weightx = 0.6;
        panel.add(createHeaderLabel("Probability Range (%)"), gbc);

        return panel;
    }

    private JLabel createHeaderLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        return label;
    }

    private void loadBlockers() {
        for (SprintBlocker blocker : BlockerStore.getInstance().getBlockers()) {
            addBlockerWidget(new BlockerWidget(blocker, simStateManager));
        }
    }

    private void addBlockerWidget(BlockerWidget widget) {
        widgets.add(widget);
        blockersPanel.add(widget);
        blockersPanel.revalidate();
        blockersPanel.repaint();
    }

    private void refreshBlockersList() {

        blockersPanel.removeAll();
        widgets.clear();
        
        loadBlockers();
        
        blockersPanel.revalidate();
        blockersPanel.repaint();
    }
}