package ase.project.ctt.web.view.components;

import ase.project.ctt.application.dto.TrainingSessionDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

import java.util.List;

public class TrainingSessionGrid extends Grid<TrainingSessionDto> {

    public TrainingSessionGrid(List<TrainingSessionDto> trainingSessions) {
        if(trainingSessions != null) {
            this.addColumns();
            this.setItems(trainingSessions);
        } else {
            throw new IllegalArgumentException("There are no training sessions to display");
        }
        this.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        this.setWidth("50%");
    }

    private void addColumns() {
        this.addColumn(TrainingSessionDto::date).setHeader("Date");
        this.addColumn(TrainingSessionDto::trainingType).setHeader("Type");
        this.addColumn(TrainingSessionDto::notes).setHeader("Note");
    }
}
