package ase.project.ctt.application.dto;


import ase.project.ctt.domain.model.valueobjects.SessionId;

import java.time.LocalDate;

public record TrainingSessionDto (
        SessionId id,
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
