package ase.project.ctt.application.dto;


import java.time.LocalDate;

public record TrainingSessionDto (
        LocalDate date,
        double durationInMinutes,
        double distanceInKm,
        String trainingType,
        String trainingStatus,
        int avgPower,
        int avgHeartRate,
        int avgCadence,
        String notes,
        String name
) { }
