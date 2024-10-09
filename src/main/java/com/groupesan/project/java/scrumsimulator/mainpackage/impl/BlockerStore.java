package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class BlockerStore {
    private static BlockerStore blockerStore;

    public static BlockerStore getInstance() {
        if (blockerStore == null) {
            blockerStore = new BlockerStore();
        }
        return blockerStore;
    }

    private List<Blocker> blockers;

    private BlockerStore() {
        blockers = new ArrayList<>();
        addInitialBlockers();
    }

    private void addInitialBlockers() {
        BlockerFactory factory = BlockerFactory.getInstance();
        
        addBlocker(factory.createNewBlocker("Role Implementation", "The roles set by the user don't work right now.", "Open"));
        addBlocker(factory.createNewBlocker("Missing Design Specs", "UI team hasn't provided final design specifications.", "In Progress"));
        addBlocker(factory.createNewBlocker("Simulation Window issue", "Simulation window should not pop up on first boot.", "Open"));
        addBlocker(factory.createNewBlocker("Edits reflect instantly", "Any edits made in the sprint variables should update in the list instantaneously.", "Resolved"));
        addBlocker(factory.createNewBlocker("Role heirarchy", "Need to implement role heirarchies.", "In Progress"));
    }

    public void addBlocker(Blocker blocker) {
        blocker.doRegister();  // Ensure the blocker is registered
        blockers.add(blocker);
    }

    public List<Blocker> getBlockers() {
        return new ArrayList<>(blockers);
    }

    public void removeBlocker(Blocker blocker) {
        blockers.remove(blocker);
    }
}