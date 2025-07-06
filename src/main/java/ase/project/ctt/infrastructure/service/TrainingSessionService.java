package ase.project.ctt.infrastructure.service;

import ase.project.ctt.application.client.TrainingSessionClient;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.application.exception.NoTrainingSessionsFoundException;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.common.Constants;
import ase.project.ctt.domain.model.TrainingSession;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingSessionService implements TrainingSessionClient {

    private static final String BASE_URL = Constants.BASE_URL + Constants.API_VERSIONPATH + Constants.TRAINING_SESSION_SUFFIX;

    private final RestTemplate restTemplate;

    public TrainingSessionService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<TrainingSessionDto> getAllSessions() {
        List<TrainingSessionDto> trainingSessionDtos = new ArrayList<>();
        TrainingSession[] sessions = restTemplate.getForObject(BASE_URL, TrainingSession[].class);
        if(sessions != null) {

            for(TrainingSession session : sessions) {
                trainingSessionDtos.add(TrainingSessionMapper.toDto(session));
            }

            return trainingSessionDtos;
        }
        throw new NoTrainingSessionsFoundException("Seems like there are no training sessions persisted yet");
    }

    @Override
    public TrainingSessionDto createSession(TrainingSessionDto newSessionDto) {
        HttpEntity<TrainingSessionDto> request = new HttpEntity<>(newSessionDto);
        return restTemplate.postForObject(BASE_URL, request, TrainingSessionDto.class);
    }
}
