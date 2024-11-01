package com.groupesan.project.java.scrumsimulator.mainpackage.impl;

public class Spike {
    private int upperBound;
    private int lowerBound;
    private int spikeValue;

    private int calculatedUpperBound;

    public Spike() {}

    public Spike(int upperBound, int lowerBound, int spikeValue, int calculatedUpperBound) {
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.spikeValue = spikeValue;
        this.calculatedUpperBound = calculatedUpperBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getSpikeValue() {
        return spikeValue;
    }

    public void setSpikeValue(int spikeValue) {
        this.spikeValue = spikeValue;
    }
    public int getCalculatedUpperBound() {
        return calculatedUpperBound;
    }

    public void setCalculatedUpperBound(int calculatedUpperBound) {
        this.calculatedUpperBound = calculatedUpperBound;
    }

}
