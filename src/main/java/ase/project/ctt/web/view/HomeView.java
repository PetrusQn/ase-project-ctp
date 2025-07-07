package ase.project.ctt.web.view;

import ase.project.ctt.application.client.TrainingSessionClient;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import ase.project.ctt.web.view.components.AddTrainingSessionButton;
import ase.project.ctt.web.view.components.CreateTrainingSessionDialog;
import ase.project.ctt.web.view.components.TrainingSessionGrid;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Route("")
public class HomeView extends VerticalLayout {

    private final TrainingSessionService client;

    public HomeView(TrainingSessionService client) {
        this.client = client;
        this.initializeGrid();
        Dialog createTrainingSessionDialog = new CreateTrainingSessionDialog(this.client);
        this.add(new AddTrainingSessionButton(createTrainingSessionDialog));
    }

    private void initializeGrid() {
        try {
            List<TrainingSessionDto> sessions = client.getAllSessions();
            this.add(new TrainingSessionGrid(sessions));
        } catch (Exception e) {
            Notification.show("An error occurred while fetching the training sessions");
        }
    }
}
