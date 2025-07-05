package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AvgCadence {

    private int avgCadence;

    protected AvgCadence() {
        // for jpa
    }

    public AvgCadence(int avgCadence) {
        if (avgCadence < 0) {
            throw new IllegalArgumentException("The average cadence cannot be negative");
        }
        this.avgCadence = avgCadence;
    }

    public boolean isZero() {
        return this.avgCadence == 0;
    }

    public int getAvgCadence() {
        return this.avgCadence;
    }

    @Override
    public String toString() {
        return this.avgCadence + " rpm";
    }
}
