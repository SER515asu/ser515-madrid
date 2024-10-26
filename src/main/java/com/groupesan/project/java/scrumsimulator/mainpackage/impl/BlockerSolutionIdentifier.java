package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumIdentifier;

public class BlockerSolutionIdentifier extends ScrumIdentifier {
    public BlockerSolutionIdentifier(int value) {
        super(value);
    }

    @Override
    public String toString() {
        return "Solution #" + this.id;
    }
}