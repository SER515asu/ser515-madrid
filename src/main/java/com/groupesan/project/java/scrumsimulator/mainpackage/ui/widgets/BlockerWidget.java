package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Blocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockerWidget extends JPanel implements BaseComponent {
    private JLabel id, status, name, desc;
    private Blocker blocker;
    private JButton deleteButton;

    public BlockerWidget(Blocker blocker) {
        this.blocker = blocker;
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

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
            BlockerStore.getInstance().removeBlocker(blocker);
            Container parent = BlockerWidget.this.getParent();
            parent.remove(BlockerWidget.this);
            parent.revalidate();
            parent.repaint();
        });

        gbc.gridx = 4;
        gbc.weightx = 0.1;
        this.add(deleteButton, gbc);

        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private String truncateText(String text, int maxLength) {
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private void updateDisplay() {
        id.setText(blocker.getId().toString());
        status.setText(blocker.getStatus());
        name.setText(blocker.getName());
        desc.setText("<html>" + truncateText(blocker.getDescription(), 40) + "</html>");
        revalidate();
        repaint();
    }
}