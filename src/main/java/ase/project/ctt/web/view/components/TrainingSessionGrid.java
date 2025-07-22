package ase.project.ctt.web.view.components;

import ase.project.ctt.application.dto.TrainingSessionDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.renderer.LocalDateRenderer;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class TrainingSessionGrid extends Grid<TrainingSessionDto> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public TrainingSessionGrid(List<TrainingSessionDto> trainingSessions) {
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
        System.out.println("Double Click on: " + clickedSessionDto.notes());
    }
}
