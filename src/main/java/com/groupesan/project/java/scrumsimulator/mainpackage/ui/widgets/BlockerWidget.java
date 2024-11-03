package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.Simulation;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerSolutionsStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class BlockerWidget extends JPanel implements BaseComponent {
    private JLabel id, status, name, desc, solutionLabel, probabilityRange;
    private SprintBlocker blocker;
    private JButton deleteButton;
    private JButton userStoryDropdownButton;
    private JPopupMenu userStoryPopupMenu;
    private SimulationStateManager simStateManager;


    public BlockerWidget(SprintBlocker blocker, SimulationStateManager simStateManager) {
        this.blocker = blocker;
        this.simStateManager = simStateManager;
        this.init();
        populateUserStories();
        restoreSelectedUserStory();
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
        id = new JLabel(blocker.getId().toString());
        id.setPreferredSize(new Dimension(50, 30));
        add(id, gbc);

        // Status column
        gbc.gridx = 1;
        status = new JLabel(blocker.getStatus());
        status.setPreferredSize(new Dimension(80, 30));
        add(status, gbc);

        // Name column
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        name = new JLabel(blocker.getName());
        name.setPreferredSize(new Dimension(100, 30));
        add(name, gbc);

        // Description column with text truncation
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        desc = new JLabel("<html>" + truncateText(blocker.getDescription(), 40) + "</html>");
        desc.setPreferredSize(new Dimension(200, 30));
        add(desc, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.3;
        probabilityRange = new JLabel(getProbabilityRangeText());
        probabilityRange.setPreferredSize(new Dimension(100, 30));
        add(probabilityRange, gbc);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (simStateManager != null && simStateManager.isRunning()) {
                    JOptionPane.showMessageDialog(
                        BlockerWidget.this,
                        "Cannot edit blocker information while the simulation is running",
                        "Operation Not Allowed: Simulation Running",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                EditBlockerForm form = new EditBlockerForm(blocker);
                form.setVisible(true);
                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateDisplay();
                    }
                });
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (simStateManager != null && simStateManager.isRunning()) {
                JOptionPane.showMessageDialog(
                    BlockerWidget.this,
                    "Cannot delete blocker while the simulation is running",
                    "Operation Not Allowed: Simulation Running",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (blocker.getSolution() != null) {
                blocker.getSolution().setBlocker(null);
            }

            BlockerStore.getInstance().removeBlocker(blocker);
            Container parent = BlockerWidget.this.getParent();
            parent.remove(BlockerWidget.this);
            parent.revalidate();
            parent.repaint();
        });

        gbc.gridx = 5;
        gbc.weightx = 0.1;
        add(deleteButton, gbc);

        userStoryDropdownButton = new JButton("Edit User Stories");
        userStoryDropdownButton.addActionListener(e -> {
            if (simStateManager != null && simStateManager.isRunning()) {
                JOptionPane.showMessageDialog(
                    BlockerWidget.this,
                    "Cannot edit user stories while the simulation is running",
                    "Operation Not Allowed: Simulation Running",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            showUserStoryPopupMenu();
        });
        gbc.gridx = 5;
        gbc.weightx = 0.3;
        add(userStoryDropdownButton, gbc);

        JPanel solutionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        solutionLabel = new JLabel(getSolutionText());
        solutionPanel.add(solutionLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        add(solutionPanel, gbc);


        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private void showUserStoryPopupMenu() {
        if (userStoryPopupMenu != null) {
            userStoryPopupMenu.removeAll();
        } else {
            userStoryPopupMenu = new JPopupMenu();
        }

        ButtonGroup buttonGroup = new ButtonGroup();
        
        JRadioButtonMenuItem noneItem = new JRadioButtonMenuItem("None");
        noneItem.setSelected(blocker.getUserStories().isEmpty());
        noneItem.addActionListener(e -> clearUserStoryAssignments());
        buttonGroup.add(noneItem);
        userStoryPopupMenu.add(noneItem);
        
        userStoryPopupMenu.addSeparator();

        UserStoryStore userStoryStore = UserStoryStore.getInstance();
        for (UserStory userStory : userStoryStore.getUserStories()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(userStory.getName());
            
            boolean isAssignedToOther = isUserStoryAssignedToOtherBlocker(userStory);
            
            menuItem.setEnabled(!isAssignedToOther);
            
            menuItem.setSelected(blocker.getUserStories().contains(userStory));
            
            menuItem.addActionListener(e -> updateSelectedUserStories(menuItem, userStory));
            buttonGroup.add(menuItem);
            userStoryPopupMenu.add(menuItem);
        }

        userStoryPopupMenu.show(userStoryDropdownButton, 0, userStoryDropdownButton.getHeight());
    }
    
    private boolean isUserStoryAssignedToOtherBlocker(UserStory userStory) {
        for (SprintBlocker otherBlocker : BlockerStore.getInstance().getAllBlockers()) {
            if (otherBlocker != this.blocker && otherBlocker.getUserStories().contains(userStory)) {
                return true;
            }
        }
        return false;
    }

    private void clearUserStoryAssignments() {
        for (UserStory userStory : blocker.getUserStories().toArray(new UserStory[0])) {
            userStory.removeBlocker(blocker);
            blocker.removeUserStory(userStory);
        }
    }

    private void updateSelectedUserStories(JRadioButtonMenuItem menuItem, UserStory userStory) {
        if (menuItem.isSelected()) {
            clearUserStoryAssignments();
            
            blocker.addUserStory(userStory);
            userStory.addBlocker(blocker);
        }
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void updateDisplay() {
        id.setText(blocker.getId().toString());
        status.setText(blocker.getStatus());
        name.setText(blocker.getName());
        desc.setText("<html>" + truncateText(blocker.getDescription(), 40) + "</html>");
        probabilityRange.setText(getProbabilityRangeText());
        SprintBlockerSolution solution = blocker.getSolution();
        if (solution != null && !Arrays.asList(BlockerSolutionsStore.getInstance().getAllSolutions()).contains(solution)) {
            blocker.setSolution(null);
            solutionLabel.setText("No solution assigned");
        } else {
            solutionLabel.setText(getSolutionText());
        }
        
        revalidate();
        repaint();
    }

    private String getProbabilityRangeText() {
        return blocker.getMinProbability() + "% - " + blocker.getMaxProbability() + "%";
    }

    public void setStateManager(SimulationStateManager simStateManager) {
        this.simStateManager = simStateManager;
    }

    public void populateUserStories() {
        if (userStoryPopupMenu != null) {
            userStoryPopupMenu.removeAll();
        }
    }

    public void restoreSelectedUserStory() {
        if (userStoryPopupMenu != null) {
            for (Component component : userStoryPopupMenu.getComponents()) {
                if (component instanceof JRadioButtonMenuItem) {
                    JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) component;
                    if (!menuItem.getText().equals("None")) {
                        UserStory userStory = UserStoryStore.getInstance().getUserStoryByName(menuItem.getText());
                        menuItem.setSelected(userStory != null && blocker.getUserStories().contains(userStory));
                        menuItem.setEnabled(!isUserStoryAssignedToOtherBlocker(userStory));
                    }
                }
            }
        }
    }

    private String getSolutionText() {
        SprintBlockerSolution solution = blocker.getSolution();
        if (solution != null && Arrays.asList(BlockerSolutionsStore.getInstance().getAllSolutions()).contains(solution)) {
            return "Solution: " + solution.getName();
        }
        return "No solution assigned";
    }
    public SprintBlocker getBlocker() {
        return blocker;
    }
    public void updateStatus(String newStatus) {
        blocker.setStatus(newStatus);
        status.setText(newStatus);
    }
}
