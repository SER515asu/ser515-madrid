package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import javax.swing.JFrame;

import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;

public class BlockersPane extends JFrame implements BaseComponent{
    public BlockersPane() {
        this.init();
    }

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Blockers list");
        setSize(400, 300);
    }
}
