package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

import java.security.SecureRandom;

public class ProbabilityUtils {
    private static final SecureRandom random = new SecureRandom();

    public static int generateRandomProbability(ProbabilityRange range) {
        return random.nextInt(range.getMaxProbability() - range.getMinProbability() + 1) + range.getMinProbability();
    }

    public static boolean checkTheSuccessScenario(double probability) {
        int actualScenario = random.nextInt(100) + 1;
        return probability >= actualScenario;
    }
}
