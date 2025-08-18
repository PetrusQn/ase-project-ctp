package ase.project.ctt;

import ase.project.ctt.application.exception.CreateTrainingSessionException;
import ase.project.ctt.application.exception.DeleteTrainingSessionException;
import ase.project.ctt.application.exception.UpdateTrainingSessionFailedException;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CyclingTrainingToolApplicationTests {
	private static final TrainingSessionService trainingSessionService  = new TrainingSessionService();

	@Test
	void contextLoads() {}

	@Test
	void createSession_throws_exception() {
		assertThrows(CreateTrainingSessionException.class, () -> trainingSessionService.createSession(null));
	}

	@Test
	void deleteSession_throws_exception() {
		assertThrows(DeleteTrainingSessionException.class, () -> trainingSessionService.deleteSession(null));
	}

	@Test
	void updateSession_throws_exception() {
		assertThrows(UpdateTrainingSessionFailedException.class, () -> trainingSessionService.updateSession(null));
	}

	@Test
	void mapper_toDto_successful() {
		assertEquals(TestSession.getSession().getName(), TrainingSessionMapper.toDto(TestSession.getSession()).name());
	}

	@Test
	void mapper_toTrainingSession_successful() {
		assertEquals(TestSession.getSessionDto().name(), TrainingSessionMapper.toTrainingSession(TestSession.getSessionDto()).getName());
	}

	@Test
	void trainingSession_updateName_successful() {
		TrainingSession session = TestSession.getSession();
		session.updateName("updated name");
		assertEquals("updated name", session.getName());
	}

	@Test
	void createSession_successful() {
		TrainingSession session = TrainingSession.create(LocalDate.now(), new Duration(120.0),
				new Distance(60.75), TrainingType.BASE, TrainingStatus.PLANNED, new AvgPower(190),
				new AvgHeartRate(128), new AvgCadence(88), "120 min easy", "Test session");

		assertEquals(60.75, session.getDistance().getKilometers());
		assertEquals(TrainingType.BASE, session.getTrainingType());
		assertEquals("Test session", session.getName());
	}

	@Test
	void avgCadence_isZero() {
		assertFalse(TestSession.getSession().getAvgCadence().isZero());
	}

	@Test
	void sessionId_equals() {
        assertNotEquals(TestSession.getSession().getId(), SessionId.newId());
	}
}
