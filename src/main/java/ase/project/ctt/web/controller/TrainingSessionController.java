package ase.project.ctt.web.controller;

import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.common.Constants;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.SessionId;
import ase.project.ctt.infrastructure.repository.TrainingSessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constants.API_VERSIONPATH)
public class TrainingSessionController {

    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionController(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    @GetMapping
    public ResponseEntity<List<TrainingSessionDto>> getAllTrainingSessions() {
        return ResponseEntity.ok(trainingSessionRepository.findAll().stream().map(TrainingSessionMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionDto> getTrainingSessionById(@PathVariable UUID id) {
        return trainingSessionRepository.findById(SessionId.of(id))
                .map(TrainingSessionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrainingSessionDto> createTrainingSession(@RequestBody TrainingSessionDto requestDto) {
        TrainingSession newSession = TrainingSessionMapper.fromDto(requestDto);
        trainingSessionRepository.save(newSession);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/type/{type}")
//    public List<TrainingSession> getTrainingSessionsByType(@PathVariable String type) {
//        return trainingSessionRepository.findByTrainingType(TrainingType.valueOf(type.toUpperCase()));
//    }
//
//    @GetMapping("/range")
//    public List<TrainingSession> getByDateRange(@RequestParam String start, @RequestParam String end) {
//        return trainingSessionRepository.findByDateBetween(
//                LocalDate.parse(start), LocalDate.parse(end));
//    }
}
