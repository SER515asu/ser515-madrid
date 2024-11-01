package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerSolutionsStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerSolutionForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockerSolutionsWidget extends JPanel implements BaseComponent {
    private JLabel id, name, desc, probabilityRange;
    private SprintBlockerSolution blockerSolution;
    private JButton deleteButton;
    private JButton blockerDropdownButton;
    private JPopupMenu blockerPopupMenu;

    public BlockerSolutionsWidget(SprintBlockerSolution blockerSolution) {
        this.blockerSolution = blockerSolution;
        this.init();
        populateBlockers();
    }

    public void init() {
        removeAll();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Main content panel (top row)
        JPanel mainContent = new JPanel(new GridBagLayout());
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.fill = GridBagConstraints.HORIZONTAL;
        mainGbc.insets = new Insets(5, 5, 5, 5);

        // ID column
        mainGbc.weightx = 0.1;
        mainGbc.gridx = 0;
        id = new JLabel(blockerSolution.getId().toString());
        id.setPreferredSize(new Dimension(50, 30));
        mainContent.add(id, mainGbc);

        // Name/Title column
        mainGbc.gridx = 1;
        mainGbc.weightx = 0.2;
        name = new JLabel(blockerSolution.getName());
        name.setPreferredSize(new Dimension(100, 30));
        mainContent.add(name, mainGbc);

        // Description column with text truncation
        mainGbc.gridx = 2;
        mainGbc.weightx = 0.5;
        desc = new JLabel("<html>" + truncateText(blockerSolution.getDescription(), 40) + "</html>");
        desc.setPreferredSize(new Dimension(200, 30));
        mainContent.add(desc, mainGbc);

        // Delete button
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (blockerSolution.getBlocker() != null) {
                blockerSolution.getBlocker().setSolution(null);
            }

            BlockerSolutionsStore.getInstance().removeBlockerSolution(blockerSolution);
            Container parent = BlockerSolutionsWidget.this.getParent();
            parent.remove(BlockerSolutionsWidget.this);
            parent.revalidate();
            parent.repaint();
        });

        mainGbc.gridx = 4;
        mainGbc.weightx = 0.3;
        mainContent.add(deleteButton, mainGbc);

        mainGbc.gridx = 3;
        mainGbc.weightx = 0.3;
        probabilityRange = new JLabel(getProbabilityRangeText());
        probabilityRange.setPreferredSize(new Dimension(100, 30));
        mainContent.add(probabilityRange, mainGbc);

        // Add main content panel to the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        add(mainContent, gbc);

        // Add blocker selection panel below (second row)
        JPanel blockerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blockerDropdownButton = new JButton("Select Blocker");
        if (blockerSolution.getBlocker() != null) {
            blockerDropdownButton.setText("Blocker: " + blockerSolution.getBlocker().getName());
        }
        blockerDropdownButton.addActionListener(e -> showBlockerPopupMenu());
        blockerPanel.add(blockerDropdownButton);

        gbc.gridy = 1;
        add(blockerPanel, gbc);


        // Add click listener for editing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                EditBlockerSolutionForm form = new EditBlockerSolutionForm(blockerSolution);
                form.setVisible(true);
                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateDisplay();
                    }
                });
            }
        });

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private void showBlockerPopupMenu() {
        if (blockerPopupMenu != null) {
            blockerPopupMenu.removeAll();
        } else {
            blockerPopupMenu = new JPopupMenu();
        }
    
        JMenuItem noneItem = new JMenuItem("None");
        noneItem.addActionListener(e -> {
            if (blockerSolution.getBlocker() != null) {
                blockerSolution.getBlocker().setSolution(null);
            }
            blockerSolution.setBlocker(null);
            blockerDropdownButton.setText("Select Blocker");
        });
        blockerPopupMenu.add(noneItem);
        blockerPopupMenu.addSeparator();
    
        BlockerStore blockerStore = BlockerStore.getInstance();
        for (SprintBlocker blocker : blockerStore.getBlockers()) {
            JMenuItem menuItem = new JMenuItem(blocker.getName());
            menuItem.addActionListener(e -> {
                if (checkBlockerSelection(blocker)) {
                    if (blockerSolution.getBlocker() != null) {
                        blockerSolution.getBlocker().setSolution(null);
                    }
                    blockerSolution.setBlocker(blocker);
                    blocker.setSolution(blockerSolution);
                    blockerDropdownButton.setText("Blocker: " + blocker.getName());
                } else {
                    String errorMessage = !blockerStore.getBlockers().contains(blocker) ?
                        "This blocker no longer exists." :
                        "This blocker is already associated with another solution.";
                    
                    JOptionPane.showMessageDialog(BlockerSolutionsWidget.this,
                        errorMessage,
                        "Blocker Selection Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            blockerPopupMenu.add(menuItem);
        }
    
        blockerPopupMenu.show(blockerDropdownButton, 0, blockerDropdownButton.getHeight());
    }

    private String getProbabilityRangeText() {
        return blockerSolution.getBlockerSolutionMinProbability() + "% - " + blockerSolution.getBlockerSolutionMaxProbability() + "%";
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void updateDisplay() {
        id.setText(blockerSolution.getId().toString());
        // status.setText(blockerSolution.getStatus());
        name.setText(blockerSolution.getName());
        desc.setText("<html>" + truncateText(blockerSolution.getDescription(), 40) + "</html>");
        SprintBlocker blocker = blockerSolution.getBlocker();
        probabilityRange.setText(getProbabilityRangeText());
        if (blocker != null && !BlockerStore.getInstance().getBlockers().contains(blocker)) {
            blockerSolution.setBlocker(null);
            blockerDropdownButton.setText("Select Blocker");
        } else if (blocker != null) {
            blockerDropdownButton.setText("Blocker: " + blocker.getName());
        } else {
            blockerDropdownButton.setText("Select Blocker");
        }

        revalidate();
        repaint();
    }

    private void populateBlockers() {
        if (blockerPopupMenu != null) {
            blockerPopupMenu.removeAll();
        }
    }

    private boolean checkBlockerSelection(SprintBlocker blocker) {
        if (!BlockerStore.getInstance().getBlockers().contains(blocker)) {
            return false;
        }
        
        BlockerSolutionsStore blockerSolutionsStore = BlockerSolutionsStore.getInstance();
        for (SprintBlockerSolution existingSolution : blockerSolutionsStore.getAllSolutions()) {
            if (existingSolution != this.blockerSolution && existingSolution.getBlocker() == blocker) {
                return false;
            }
        }
        return true;
    }
}