package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

class ProbabilityUtilsTest {
    private ProbabilityRange range;

    @BeforeEach
    void setUp() {
        range = new ProbabilityRange() {
            @Override
            public int getMinProbability() {
                return 10;
            }

            @Override
            public int getMaxProbability() {
                return 90;
            }
        };
    }

    @RepeatedTest(100)
    void testGenerateRandomProbability_WithinRange() {
        int generatedProbability = ProbabilityUtils.generateRandomProbability(range);

        assertTrue(
                generatedProbability >= range.getMinProbability() && generatedProbability <= range.getMaxProbability(),
                "Generated probability is out of range"
        );
    }

}
