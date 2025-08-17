package ase.project.ctt.web.view.components.dialog;

import ase.project.ctt.application.NewTrainingSessionObserver;
import ase.project.ctt.application.dto.TrainingSessionDto;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
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
    private final TrainingSessionService client;

    private DatePicker datePicker;
    private NumberField durationField;
    private NumberField distanceField;
    private NumberField avgPowerField;
    private NumberField avgHrField;
    private NumberField avgCadenceField;
    private TypeSelector typeSelector;
    private NoteField noteField;
    private NameField nameField;

    public EditTrainingSessionDialog(TrainingSessionService client, TrainingSessionDto clickedSession) {
        this.observers = new ArrayList<>();
        this.client = client;
        this.clickedSession = clickedSession;
        this.setHeaderTitle("Edit: " + clickedSession.name());
        this.initInputFields();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(createForm());
        this.add(dialogLayout);
        this.getFooter().add(createCancelButton());
        this.getFooter().add(createSaveButton());
        this.getFooter().add(createDeleteButton());
    }

    private Button createDeleteButton() {
        Button deleteButton = new Button("Delete");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(event -> {
            if(this.deleteSession()) {
                this.updateGridView();
                this.close();
            }
        });
        return deleteButton;
    }

    private boolean deleteSession() {
        return this.client.deleteSession(clickedSession);
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
        return this.client.updateSession(createDtoFromInput());
    }

    private TrainingSessionDto createDtoFromInput() {
        return new TrainingSessionDto(
                clickedSession.id(),
                datePicker.getValue(),
                durationField.getValue(),
                distanceField.getValue(),
                getTrainingType().toString(),
                calcTrainingStatus().toString(),
                avgPowerField.getValue().intValue(),
                avgHrField.getValue().intValue(),
                avgCadenceField.getValue().intValue(),
                noteField.getValue(),
                nameField.getValue()
        );
    }

    private void initInputFields() {
        this.datePicker = new DatePicker();
        datePicker.setValue(clickedSession.date());
        this.durationField = new NumberField("Duration", "min");
        durationField.setValue(clickedSession.durationInMinutes());
        this.distanceField = new NumberField("Distance", "km");
        distanceField.setValue(clickedSession.distanceInKm());
        this.avgPowerField = new NumberField("Average power", "w");
        avgPowerField.setValue((double)clickedSession.avgPower());
        this.avgHrField = new NumberField("Average heart rate", "bpm");
        avgHrField.setValue((double)clickedSession.avgHeartRate());
        this.avgCadenceField = new NumberField("Average cadence", "rpm");
        avgCadenceField.setValue((double)clickedSession.avgCadence());
        this.typeSelector = new TypeSelector();
        typeSelector.setValue(clickedSession.trainingType());
        this.noteField = new NoteField();
        noteField.setValue(clickedSession.notes());
        this.nameField = new NameField();
        nameField.setValue(clickedSession.name());
    }

    private FormLayout createForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setExpandFields(true);

        FormLayout.FormRow firstRow = new FormLayout.FormRow();
        firstRow.add(nameField, datePicker);

        FormLayout.FormRow secondRow = new FormLayout.FormRow();
        secondRow.add(datePicker);

        FormLayout.FormRow thirdRow = new FormLayout.FormRow();
        thirdRow.add(durationField, distanceField);

        FormLayout.FormRow fourthRow = new FormLayout.FormRow();
        fourthRow.add(typeSelector);

        FormLayout.FormRow fifthRow = new FormLayout.FormRow();
        fifthRow.add(avgPowerField, avgHrField, avgCadenceField);

        FormLayout.FormRow sixthRow = new FormLayout.FormRow();
        sixthRow.add(noteField, 3);

        formLayout.add(firstRow, secondRow, thirdRow, fourthRow, fifthRow, sixthRow);

        return formLayout;
    }

    private Button createCancelButton() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.getStyle().set("margin-right", "auto");
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
