package ase.project.ctt.web.view;

import ase.project.ctt.application.NewTrainingSessionObserver;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import ase.project.ctt.web.view.components.AddTrainingSessionButton;
import ase.project.ctt.web.view.components.CreateTrainingSessionDialog;
import ase.project.ctt.web.view.components.TrainingSessionGrid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route("")
public class HomeView extends VerticalLayout implements NewTrainingSessionObserver {

    private final TrainingSessionService client;
    private final TrainingSessionGrid trainingSessionGrid;

    public HomeView(TrainingSessionService client) {
        this.client = client;
        this.trainingSessionGrid = new TrainingSessionGrid(client.getAllSessions());
        this.add(trainingSessionGrid);
        CreateTrainingSessionDialog createTrainingSessionDialog = new CreateTrainingSessionDialog(this.client);
        this.add(new AddTrainingSessionButton(createTrainingSessionDialog));
        createTrainingSessionDialog.addObserver(this);
    }

    @Override
    public void update() {
        this.trainingSessionGrid.updateContent(client.getAllSessions());
    }
}
