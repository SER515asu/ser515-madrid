package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public interface BlockerUpdateListener {
    void onBlockerStatusChanged(SprintBlocker blocker, String newStatus);
}
