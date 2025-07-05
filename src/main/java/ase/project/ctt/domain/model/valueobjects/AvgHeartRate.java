package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class AvgHeartRate {

    private int avgHr;

    protected AvgHeartRate() {
        // for jpa
    }

    public AvgHeartRate(int avgHr) {
        if (avgHr < 0) {
            throw new IllegalArgumentException("The average heart rate cannot be negative");
        }
        this.avgHr = avgHr;
    }

    public boolean isZero() {
        return this.avgHr == 0;
    }

    public int getAvgHr() {
        return this.avgHr;
    }

    @Override
    public String toString() {
        return this.avgHr + " bpm";
    }
}
