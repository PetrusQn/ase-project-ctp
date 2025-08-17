package ase.project.ctt.web.controller;

import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.common.Constants;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
import ase.project.ctt.infrastructure.repository.TrainingSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(Constants.API_VERSIONPATH + Constants.TRAINING_SESSION_SUFFIX)
public class TrainingSessionController {

    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionController(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    @GetMapping
    public ResponseEntity<List<TrainingSessionDto>> getAllTrainingSessions() {
        return ResponseEntity.ok(trainingSessionRepository.findAll().stream().map(TrainingSessionMapper::toDto).toList());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<TrainingSessionDto> getTrainingSessionById(@PathVariable UUID id) {
//        return trainingSessionRepository.findById(id)
//                .map(TrainingSessionMapper::toDto)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    @PostMapping
    public ResponseEntity<TrainingSessionDto> createTrainingSession(@RequestBody TrainingSessionDto requestDto) {
        TrainingSession newSession = TrainingSessionMapper.toTrainingSession(requestDto);
        trainingSessionRepository.save(newSession);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable String id) {
        TrainingSession session = findSessionById(id);

        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        trainingSessionRepository.delete(session);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTrainingSession(@PathVariable String id, @RequestBody TrainingSessionDto requestDto) {
        TrainingSession session = findSessionById(id);

        if (session == null) {
            return ResponseEntity.notFound().build();
        }
        session.updateDate(requestDto.date());
        session.updateDuration(new Duration(requestDto.durationInMinutes()));
        session.updateDistance(new Distance(requestDto.distanceInKm()));
        session.updateTrainingType(TrainingType.valueOf(requestDto.trainingType().toUpperCase()));
        session.updateTrainingStatus(TrainingStatus.valueOf(requestDto.trainingStatus().toUpperCase()));
        session.updateAvgPower(new AvgPower(requestDto.avgPower()));
        session.updateAvgHr(new AvgHeartRate(requestDto.avgHeartRate()));
        session.updateAvgCadence(new AvgCadence(requestDto.avgCadence()));
        session.updateNotes(requestDto.notes());
        session.updateName(requestDto.name());

        trainingSessionRepository.save(session);
        return ResponseEntity.noContent().build();
    }

    private TrainingSession findSessionById(String id) {
        Optional<TrainingSession> possibleSession = trainingSessionRepository.findById(SessionId.of(UUID.fromString(id)));
        return possibleSession.orElse(null);
    }
}
