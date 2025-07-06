package ase.project.ctt.application.client;

import ase.project.ctt.application.dto.TrainingSessionDto;

import java.util.List;

public interface TrainingSessionClient {
    List<TrainingSessionDto> getAllSessions();
    TrainingSessionDto createSession(TrainingSessionDto dto);
}
