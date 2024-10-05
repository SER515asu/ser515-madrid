package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.util.ArrayList;
import java.util.List;

public class BlockerStore {

    private static BlockerStore blockerStore;

    /**
     * returns the shared instance of the BlockerStore which contains all blockers in the
     * system.
     *
     * @return
     */
    public static BlockerStore getInstance() {
        if (blockerStore == null) {
            blockerStore = new BlockerStore();
        }
        return blockerStore;
    }

    private List<Blocker> blockers;

    private BlockerStore() {
        blockers = new ArrayList<>();
    }

    public void addBlocker(Blocker blocker) {
        blockers.add(blocker);
    }

    public List<Blocker> getBlockers() {
        return new ArrayList<>(blockers);
    }

    public void removeBlocker(Blocker blocker) {
        blockers.remove(blocker);
    }
}