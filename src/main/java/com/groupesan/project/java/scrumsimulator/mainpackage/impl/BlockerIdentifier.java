package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;

public class BlockerIdentifier extends ScrumIdentifier {
    public BlockerIdentifier(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return "Blocker #" + this.id;
    }
}