package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Distance {

    private double kilometers;

    protected Distance() {
        // for jpa
    }

    public Distance(double kilometers) {
        if (kilometers < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        this.kilometers = kilometers;
    }

    public double getKilometers() {
        return this.kilometers;
    }

    public Distance add(Distance otherDistance) {
        return new Distance(this.kilometers + otherDistance.getKilometers());
    }

    @Override
    public String toString() {
        return this.kilometers + " km";
    }
}
