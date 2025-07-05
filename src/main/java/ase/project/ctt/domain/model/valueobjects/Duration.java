package ase.project.ctt.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Duration {

    private double minutes;

    protected Duration() {
        // for jpa
    }

    public Duration(double minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException("Duration has to be greater than zero");
        }
        this.minutes = minutes;
    }

    public double getDuration() {
        return this.minutes;
    }

    public Duration add(Duration otherDuration) {
        return new Duration(this.minutes + otherDuration.getMinutes());
    }

    public int getHours() {
        return (int)this.minutes / 60;
    }

    public int getMinutes() {
        return (int)this.minutes % 60;
    }

    @Override
    public String toString() {
        return this.getHours() + "h " + this.getMinutes() + "mins";
    }
}
