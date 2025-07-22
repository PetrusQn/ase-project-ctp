package ase.project.ctt.web.view;

import ase.project.ctt.application.NewTrainingSessionObserver;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import ase.project.ctt.web.view.components.input.AddTrainingSessionButton;
import ase.project.ctt.web.view.components.dialog.TrainingSessionDialog;
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
        TrainingSessionDialog trainingSessionDialog = new TrainingSessionDialog(this.client);
        this.add(new AddTrainingSessionButton(trainingSessionDialog));
        trainingSessionDialog.addObserver(this);
    }

    @Override
    public void update() {
        this.trainingSessionGrid.updateContent(client.getAllSessions());
    }
}
