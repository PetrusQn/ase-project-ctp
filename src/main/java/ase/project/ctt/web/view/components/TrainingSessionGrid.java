package ase.project.ctt.web.view.components;

import ase.project.ctt.application.TrainingSessionObserver;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import ase.project.ctt.web.view.components.dialog.EditTrainingSessionDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class TrainingSessionGrid extends Grid<TrainingSessionDto> implements TrainingSessionObserver {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final TrainingSessionService client;

    public TrainingSessionGrid(TrainingSessionService client) {
        this.client = client;
        List<TrainingSessionDto> trainingSessions = this.client.getAllSessions();
        if(trainingSessions != null) {
            this.addColumns();
            this.setItems(trainingSessions);
        } else {
            throw new IllegalArgumentException("There are no training sessions to display");
        }
        this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        this.setWidth("50%");
        this.setUserSelect();
        this.addItemDoubleClickListener(event -> onDoubleClick(event.getItem()));
    }

    private void addColumns() {
        this.addColumn(TrainingSessionDto -> TrainingSessionDto.date().format(formatter)).setHeader("Date");
        this.addColumn(TrainingSessionDto -> TrainingSessionDto.trainingType().toLowerCase()).setHeader("Type");
        this.addColumn(TrainingSessionDto::name).setHeader("Name");
    }

    public void updateContent(List<TrainingSessionDto> updatedContent) {
        this.setItems(updatedContent);
    }

    private void setUserSelect() {
        this.getElement().getStyle().set("-webkit-user-select", "none")
                .set("-moz-user-select", "none")
                .set("user-select", "none");
    }

    private void onDoubleClick(TrainingSessionDto clickedSessionDto) {
        EditTrainingSessionDialog editTrainingSessionDialog = new EditTrainingSessionDialog(client, clickedSessionDto);
        editTrainingSessionDialog.open();
        editTrainingSessionDialog.addObserver(this);
    }

    @Override
    public void update() {
        this.updateContent(client.getAllSessions());
    }
}
