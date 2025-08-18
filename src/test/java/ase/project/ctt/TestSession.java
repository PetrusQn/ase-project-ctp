package ase.project.ctt;

import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;

import java.time.LocalDate;

public class TestSession {
    private static final TrainingSession testSession = TrainingSession.create(LocalDate.now(), new Duration(90.0),
            new Distance(50.0), TrainingType.BASE, TrainingStatus.COMPLETED, new AvgPower(220),
            new AvgHeartRate(139), new AvgCadence(90), "90 min just at LT1", "Foundation work (test)");

    private static final TrainingSessionDto testSessionDto = new TrainingSessionDto(
            testSession.getId(),
            testSession.getDate(),
            testSession.getDuration().get(),
            testSession.getDistance().getKilometers(),
            testSession.getTrainingType().toString(),
            testSession.getTrainingStatus().toString(),
            testSession.getAvgPower().getAvgPower(),
            testSession.getAvgHr().getAvgHr(),
            testSession.getAvgCadence().getAvgCadence(),
            testSession.getNotes(),
            testSession.getName()
    );

    public static TrainingSession getSession() {
        return testSession;
    }

    public static TrainingSessionDto getSessionDto() {
        return testSessionDto;
    }
}
