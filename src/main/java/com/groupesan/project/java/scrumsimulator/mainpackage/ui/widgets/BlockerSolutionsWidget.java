package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintBlockerSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerSolutionsStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerSolutionForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockerSolutionsWidget extends JPanel implements BaseComponent {
    private JLabel id, name, desc;
    private SprintBlockerSolution blockerSolution;
    private JButton deleteButton;


    public BlockerSolutionsWidget(SprintBlockerSolution blockerSolution) {
        this.blockerSolution = blockerSolution;
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
        id = new JLabel(blockerSolution.getId().toString());
        id.setPreferredSize(new Dimension(50, 30));
        add(id, gbc);

        // Name/Title column
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        name = new JLabel(blockerSolution.getName());
        name.setPreferredSize(new Dimension(100, 30));
        add(name, gbc);

        // Description column with text truncation
        gbc.gridx = 3;
        gbc.weightx = 0.5;
        desc = new JLabel("<html>" + truncateText(blockerSolution.getDescription(), 40) + "</html>");
        desc.setPreferredSize(new Dimension(200, 30));
        add(desc, gbc);

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

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            BlockerSolutionsStore.getInstance().removeBlockerSolution(blockerSolution);
            Container parent = BlockerSolutionsWidget.this.getParent();
            parent.remove(BlockerSolutionsWidget.this);
            parent.revalidate();
            parent.repaint();
        });

        gbc.gridx = 4;
        gbc.weightx = 0.1;
        add(deleteButton, gbc);


        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void updateDisplay() {
        id.setText(blockerSolution.getId().toString());
        // status.setText(blockerSolution.getStatus());
        name.setText(blockerSolution.getName());
        desc.setText("<html>" + truncateText(blockerSolution.getDescription(), 40) + "</html>");
        revalidate();
        repaint();
    }
}
