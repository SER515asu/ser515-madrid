package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Spike;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpikeActionPane extends JDialog implements BaseComponent {
    String action;
    public SpikeActionPane(){
        this.init();
    }
    public void  init(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Spike Action");
        setSize(400, 300);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton reallocate_resources_button = new JButton("Reallocate Resources");
        reallocate_resources_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "reallocate";
                dispose();
            }
        });

        JButton concludeSpikeButton = new JButton("Conclude Spike");
        concludeSpikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action = "conclude";

              dispose();
            }
        });

        panel.add(
                reallocate_resources_button,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        panel.add(
                concludeSpikeButton,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
        add(panel);
    }
    public String getAction(){
        return action;
    }


}
