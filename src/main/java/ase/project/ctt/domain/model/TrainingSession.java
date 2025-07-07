package ase.project.ctt.domain.model;

import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "training_sessions")
public class TrainingSession {

     @EmbeddedId
    private SessionId id;

    private LocalDate date;

    @Embedded
    private Duration duration;

    @Embedded
    private Distance distance;

    @Enumerated(EnumType.STRING)
    private TrainingType trainingType;

    @Enumerated(EnumType.STRING)
    private TrainingStatus trainingStatus;

    @Embedded
    private AvgPower avgPower;

    @Embedded
    private AvgHeartRate avgHr;

    @Embedded
    private AvgCadence avgCadence;

    private String notes;

    protected TrainingSession() {
        // for jpa
    }

    private TrainingSession(SessionId id,
                            LocalDate date,
                            Duration duration,
                            Distance distance,
                            TrainingType trainingType,
                            TrainingStatus trainingStatus,
                            AvgPower avgPower,
                            AvgHeartRate avgHr,
                            AvgCadence avgCadence,
                            String notes) {
        this.id = id;
        this.date = date;
        this.duration = duration;
        this.distance = distance;
        this.trainingType = trainingType;
        this.trainingStatus = trainingStatus;
        this.avgPower = avgPower;
        this.avgHr = avgHr;
        this.avgCadence = avgCadence;
        this.notes = notes;
    }

    public static TrainingSession create(
                         LocalDate date,
                         Duration duration,
                         Distance distance,
                         TrainingType trainingType,
                         TrainingStatus trainingStatus,
                         AvgPower avgPower,
                         AvgHeartRate avgHr,
                         AvgCadence avgCadence,
                         String notes) {
        return new TrainingSession(
                SessionId.newId(),
                date,
                duration,
                distance,
                trainingType,
                trainingStatus,
                avgPower,
                avgHr,
                avgCadence,
                notes);
    }

    public SessionId getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public Distance getDistance() {
        return this.distance;
    }

    public TrainingType getTrainingType() {
        return this.trainingType;
    }

    public TrainingStatus getTrainingStatus() {
        return this.trainingStatus;
    }

    public AvgPower getAvgPower() {
        return this.avgPower;
    }

    public AvgHeartRate getAvgHr() {
        return this.avgHr;
    }

    public AvgCadence getAvgCadence() {
        return this.avgCadence;
    }

    public String getNotes() {
        return this.notes;
    }

    public void updateDuration(Duration newDuration) {
        this.duration = newDuration;
    }

    public void updateDistance(Distance newDistance) {
        this.distance = newDistance;
    }

    public void updateTrainingType(TrainingType newTrainingType) {
        this.trainingType = newTrainingType;
    }

    public void updateTrainingStatus(TrainingStatus newTrainingStatus) {
        this.trainingStatus = newTrainingStatus;
    }

    public void updateAvgPower(AvgPower newAvgPower) {
        this.avgPower = newAvgPower;
    }

    public void updateAvgHr(AvgHeartRate newAvgHr) {
        this.avgHr = newAvgHr;
    }

    public void updateAvgCadence(AvgCadence newAvgCadence) {
        this.avgCadence = newAvgCadence;
    }

    public void updateNotes(String newNotes) {
        this.notes = newNotes;
    }
}
