package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AvgPower {

    private int avgPower;

    protected AvgPower() {
        // for jpa
    }

    public AvgPower(int avgPower) {
        if (avgPower < 0) {
            throw new IllegalArgumentException("The average power cannot be negative");
        }
        this.avgPower = avgPower;
    }

    public boolean isZero() {
        return this.avgPower == 0;
    }

    public int getAvgPower() {
        return this.avgPower;
    }

    @Override
    public String toString() {
        return this.avgPower + " watts";
    }
}
