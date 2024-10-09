package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Blocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerFactory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;

public class NewBlockerForm extends JFrame implements BaseComponent {

    private JTextField nameField = new JTextField();
    private JTextArea descArea = new JTextArea();
    private JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Open", "In Progress", "Resolved"});

    public NewBlockerForm() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("New Blocker");
        setSize(500, 400);

        JPanel myJpanel = new JPanel(new GridBagLayout());
        myJpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        myJpanel.add(new JLabel("Title:"), new CustomConstraints(0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(nameField, new CustomConstraints(1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(new JLabel("Description:"), new CustomConstraints(0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(new JScrollPane(descArea), new CustomConstraints(1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        myJpanel.add(new JLabel("Status:"), new CustomConstraints(0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(statusCombo, new CustomConstraints(1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            dispose();
        });

        myJpanel.add(cancelButton, new CustomConstraints(0, 3, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(submitButton, new CustomConstraints(1, 3, GridBagConstraints.WEST, GridBagConstraints.NONE));

        add(myJpanel);
    }

    public Blocker getBlockerObject() {
        String title = nameField.getText();
        String description = descArea.getText();
        String status = (String) statusCombo.getSelectedItem();

        BlockerFactory blockerFactory = BlockerFactory.getInstance();
        Blocker blocker = blockerFactory.createNewBlocker(title, description, status);
        blocker.doRegister();

        System.out.println(blocker);
        return blocker;
    }
}