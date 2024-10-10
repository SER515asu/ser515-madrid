package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Blocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditBlockerForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BlockerWidget extends JPanel implements BaseComponent {
    private JLabel id, status, name, desc;
    private Blocker blocker;
    private JButton deleteButton;
    private JButton userStoryDropdownButton;
    private JPopupMenu userStoryPopupMenu;


    public BlockerWidget(Blocker blocker) {
        this.blocker = blocker;
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
        add(deleteButton, gbc);

        userStoryDropdownButton = new JButton("Edit User Stories");
        userStoryDropdownButton.addActionListener(e -> showUserStoryPopupMenu());
        gbc.gridx = 4;
        gbc.weightx = 0.3;
        add(userStoryDropdownButton, gbc);


        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }

    private void showUserStoryPopupMenu() {
        // Clear existing popup menu items if already populated
        if (userStoryPopupMenu != null) {
            userStoryPopupMenu.removeAll();
        } else {
            userStoryPopupMenu = new JPopupMenu();
        }

        // Allow to select multiple user stories for one blocker
        UserStoryStore userStoryStore = UserStoryStore.getInstance();
        for (UserStory userStory : userStoryStore.getUserStories()) {
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(userStory.getName());
            menuItem.setSelected(blocker.getUserStories().contains(userStory));
            menuItem.addActionListener(e -> updateSelectedUserStories(menuItem, userStory));
            userStoryPopupMenu.add(menuItem);
        }

        // Show the popup menu below the button
        userStoryPopupMenu.show(userStoryDropdownButton, 0, userStoryDropdownButton.getHeight());
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

    private void updateSelectedUserStories(JCheckBoxMenuItem menuItem, UserStory userStory) {
        if (menuItem.isSelected()) {
            blocker.addUserStory(userStory);
        } else {
            blocker.removeUserStory(userStory);
        }
    }

    public void populateUserStories() {
        if (userStoryPopupMenu != null) {
            userStoryPopupMenu.removeAll();
        }
    }

    public void restoreSelectedUserStory() {
        if (userStoryPopupMenu != null) {
            for (Component component : userStoryPopupMenu.getComponents()) {
                if (component instanceof JCheckBoxMenuItem) {
                    JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) component;
                    UserStory userStory = UserStoryStore.getInstance().getUserStoryByName(menuItem.getText());
                    menuItem.setSelected(userStory != null && blocker.getUserStories().contains(userStory));
                }
            }
        }
    }
}
