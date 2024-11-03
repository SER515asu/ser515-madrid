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

    private List<SprintBlocker> blockers;

    private BlockerStore() {
        blockers = new ArrayList<>();
        addInitialBlockers();
    }

    void addInitialBlockers() {
        BlockerFactory factory = BlockerFactory.getInstance();
        
        addBlocker(factory.createNewBlocker("Role Implementation", "The roles set by the user don't work right now.", "Open", 40, 45));
        addBlocker(factory.createNewBlocker("Missing Design Specs", "UI team hasn't provided final design specifications.", "Open", 10, 40));
        addBlocker(factory.createNewBlocker("Simulation Window issue", "Simulation window should not pop up on first boot.", "Open", 60, 75));
        addBlocker(factory.createNewBlocker("Edits reflect instantly", "Any edits made in the sprint variables should update in the list instantaneously.", "Open", 30, 45));
        addBlocker(factory.createNewBlocker("Role heirarchy", "Need to implement role heirarchies.", "Open", 20, 30));
    }

    public void addBlocker(SprintBlocker blocker) {
        blocker.doRegister();  // Ensure the blocker is registered
        blockers.add(blocker);
    }

    public List<SprintBlocker> getBlockers() {
        return new ArrayList<>(blockers);
    }

    public void removeBlocker(SprintBlocker blocker) {
        blockers.remove(blocker);
    }

    public void clearBlockers() {
        blockers.clear();
    }

    public SprintBlocker[] getAllBlockers() {
        return blockers.toArray(new SprintBlocker[0]);
    }
}