package ase.project.ctt.application.client;

import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.domain.model.valueobjects.SessionId;

import java.util.List;

public interface TrainingSessionClient {
    List<TrainingSessionDto> getAllSessions();
    TrainingSessionDto createSession(TrainingSessionDto sessionToCreate);
    void deleteSession(TrainingSessionDto sessionToDelete);
    boolean updateSession(TrainingSessionDto sessionToUpdate);
}
