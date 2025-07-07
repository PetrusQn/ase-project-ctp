package ase.project.ctt.application.mapper;

import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;

public class TrainingSessionMapper {

    public static TrainingSession fromDto(TrainingSessionDto dto) {
        return TrainingSession.create(
                dto.date(),
                new Duration(dto.durationInMinutes()),
                new Distance(dto.distanceInKm()),
                TrainingType.valueOf(dto.trainingType().toUpperCase()),
                TrainingStatus.valueOf(dto.trainingStatus().toUpperCase()),
                new AvgPower(dto.avgPower()),
                new AvgHeartRate(dto.avgHeartRate()),
                new AvgCadence(dto.avgCadence()),
                dto.notes()
        );
    }

    public static TrainingSessionDto toDto(TrainingSession trainingSession) {
        return new TrainingSessionDto(
                trainingSession.getDate(),
                trainingSession.getDuration().getDuration(),
                trainingSession.getDistance().getKilometers(),
                trainingSession.getTrainingType().name(),
                trainingSession.getTrainingStatus().name(),
                trainingSession.getAvgPower().getAvgPower(),
                trainingSession.getAvgHr().getAvgHr(),
                trainingSession.getAvgCadence().getAvgCadence(),
                trainingSession.getNotes()
        );
    }
}
