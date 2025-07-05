package ase.project.ctt.infrastructure.repository;

import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.SessionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, SessionId> {

    List<TrainingSession> findByDateBetween(LocalDate start, LocalDate end);

    List<TrainingSession> findByTrainingType(TrainingType type);

    // more query-methods to come
}
