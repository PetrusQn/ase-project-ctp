package ase.project.ctt.web.view.components.dialog;

import ase.project.ctt.application.NewTrainingSessionObserver;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import ase.project.ctt.web.view.components.input.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EditTrainingSessionDialog extends Dialog {
    private final List<NewTrainingSessionObserver> observers;
    private final TrainingSessionDto clickedSession;

    private DatePicker datePicker;
    private NumberField durationField;
    private NumberField distanceField;
    private NumberField avgPowerField;
    private NumberField avgHrField;
    private NumberField avgCadenceField;
    private TypeSelector typeSelector;
    private NoteField noteField;
    private NameField nameField;

    public EditTrainingSessionDialog(TrainingSessionDto clickedSession) {
        this.observers = new ArrayList<>();
        this.clickedSession = clickedSession;
        this.setHeaderTitle("Edit: " + clickedSession.name());
        this.initInputFields();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(createForm());
        this.getFooter().add(createSaveButton());
        this.getFooter().add(createCancelButton());
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            if (this.onFormSubmit()) {
                this.updateGridView();
                this.close();
            }
        });
        return saveButton;
    }

    private boolean onFormSubmit() {
        return false;
    }

    private void initInputFields() {
        this.datePicker = new DatePicker();
        this.durationField = new NumberField("Duration", "min");
        this.distanceField = new NumberField("Distance", "km");
        this.avgPowerField = new NumberField("Average power", "w");
        this.avgHrField = new NumberField("Average heart rate", "bpm");
        this.avgCadenceField = new NumberField("Average cadence", "rpm");
        this.typeSelector = new TypeSelector();
        this.noteField = new NoteField();
        this.nameField = new NameField();
    }

    private FormLayout createForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setExpandFields(true);

        FormLayout.FormRow firstRow = new FormLayout.FormRow();
        firstRow.add(nameField, datePicker);

        FormLayout.FormRow secondRow = new FormLayout.FormRow();
        secondRow.add(durationField, distanceField);

        FormLayout.FormRow thirdRow = new FormLayout.FormRow();
        thirdRow.add(typeSelector);

        FormLayout.FormRow fourthRow = new FormLayout.FormRow();
        fourthRow.add(avgPowerField, avgHrField, avgCadenceField);

        FormLayout.FormRow fifthRow = new FormLayout.FormRow();
        fifthRow.add(noteField, 3);

        formLayout.add(firstRow, secondRow, thirdRow, fourthRow, fifthRow);

        return formLayout;
    }

    private Button createCancelButton() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> {
            this.close();
        });
        return cancelButton;
    }

    private TrainingStatus calcTrainingStatus() {
        if (LocalDate.now().isBefore(datePicker.getValue())) {
            return TrainingStatus.PLANNED;
        } else {
            return TrainingStatus.COMPLETED;
        }
    }

    private TrainingType getTrainingType() {
        return switch (this.typeSelector.getValue()) {
            case "recovery" -> TrainingType.RECOVERY;
            case "intensity" -> TrainingType.INTENSITY;
            default -> TrainingType.BASE;
        };
    }

    public void addObserver(NewTrainingSessionObserver observer) {
        this.observers.add(observer);
    }

    public void updateGridView() {
        for (NewTrainingSessionObserver observer : this.observers) {
            observer.update();
        }
    }
}
