package ase.project.ctt.infrastructure.service;

import ase.project.ctt.application.client.TrainingSessionClient;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.application.exception.NoTrainingSessionsFoundException;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.common.Constants;
import ase.project.ctt.domain.model.valueobjects.SessionId;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TrainingSessionService implements TrainingSessionClient {

    private static final String BASE_URL = Constants.BASE_URL + Constants.API_VERSIONPATH + Constants.TRAINING_SESSION_SUFFIX;

    private final RestTemplate restTemplate;

    public TrainingSessionService() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<TrainingSessionDto> getAllSessions() {
        TrainingSessionDto[] trainingSessionDtos = restTemplate.getForObject(BASE_URL, TrainingSessionDto[].class);
        if (trainingSessionDtos != null) {
            return Arrays.stream(trainingSessionDtos).toList();
        }
        throw new NoTrainingSessionsFoundException("Seems like there are no training sessions persisted yet");
    }

    @Override
    public TrainingSessionDto createSession(TrainingSessionDto newSessionDto) {
        HttpEntity<TrainingSessionDto> request = new HttpEntity<>(newSessionDto);
        return restTemplate.postForObject(BASE_URL, request, TrainingSessionDto.class);
    }

    @Override
    public void deleteSession(TrainingSessionDto sessionDtoToDelete) {
        HttpEntity<TrainingSessionDto> request = new HttpEntity<>(sessionDtoToDelete);
        restTemplate.delete(BASE_URL + "/" + TrainingSessionMapper.toTrainingSession(sessionDtoToDelete).getId());
    }

    @Override
    public boolean updateSession(TrainingSessionDto sessionToUpdate) {
        try {
            restTemplate.put(BASE_URL + "/" + sessionToUpdate.id(), sessionToUpdate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
