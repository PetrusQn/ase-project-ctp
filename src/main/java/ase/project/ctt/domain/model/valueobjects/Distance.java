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

    public boolean isGreaterThan(Distance otherDistance) {
        return this.kilometers > otherDistance.getKilometers();
    }

    public boolean isZero() {
        return this.kilometers == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Distance distance)) return false;
        return Double.compare(distance.getKilometers(), this.kilometers) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.kilometers);
    }

    @Override
    public String toString() {
        return this.kilometers + " km";
    }
}
