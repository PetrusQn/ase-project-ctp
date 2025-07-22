package ase.project.ctt.web.view.components;

import ase.project.ctt.application.NewTrainingSessionObserver;
import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.checkerframework.checker.units.qual.N;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateTrainingSessionDialog extends Dialog {

    private final TrainingSessionService client;
    private final List<NewTrainingSessionObserver> observers;

    private ase.project.ctt.web.view.components.DatePicker datePicker;
    private ase.project.ctt.web.view.components.NumberField durationField;
    private ase.project.ctt.web.view.components.NumberField distanceField;
    private ase.project.ctt.web.view.components.NumberField avgPowerField;
    private ase.project.ctt.web.view.components.NumberField avgHrField;
    private ase.project.ctt.web.view.components.NumberField avgCadenceField;
    private TypeSelector typeSelector;
    private NoteField noteField;
    private NameField nameField;

    public CreateTrainingSessionDialog(TrainingSessionService client) {
        this.client = client;
        this.observers = new ArrayList<>();
        this.setHeaderTitle("New training session");
        this.initInputFields();
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(createForm());
        this.add(dialogLayout);
        this.getFooter().add(createSaveButton());
        this.getFooter().add(createCancelButton());
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

    public void addObserver(NewTrainingSessionObserver observer) {
        this.observers.add(observer);
    }

    public void updateGridView() {
        for (NewTrainingSessionObserver observer : this.observers) {
            observer.update();
        }
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            if (this.onFormSubmit()) {
                this.updateGridView();
                this.close();
                this.resetAllInputFields();
            }
        });
        return saveButton;
    }

    private boolean onFormSubmit() {
        if(areRequiredFieldsSet()) {
            client.createSession(TrainingSessionMapper.toDto(TrainingSession.create(
                    datePicker.getValue(),
                    new Duration(this.durationField.getValue()),
                    new Distance(this.distanceField.getValue()),
                    this.getTrainingType(this.typeSelector),
                    this.calcTrainingStatus(),
                    new AvgPower(this.avgPowerField.getValue().intValue()),
                    new AvgHeartRate(this.avgHrField.getValue().intValue()),
                    new AvgCadence(this.avgCadenceField.getValue().intValue()),
                    this.noteField.getValue(), this.nameField.getValue())
            ));
            return true;
        } else {
            Notification.show("Name is required. Please enter any");
            return false;
        }
    }

    private boolean areRequiredFieldsSet() {
        return !this.nameField.isEmpty();
    }

    private void resetAllInputFields() {
        this.datePicker.resetValue();
        this.nameField.resetValue();
        this.noteField.resetValue();
        this.typeSelector.resetValue();
        this.durationField.resetValue();
        this.distanceField.resetValue();
        this.avgCadenceField.resetValue();
        this.avgHrField.resetValue();
        this.avgPowerField.resetValue();
    }

    private TrainingType getTrainingType(TypeSelector trainingType) {
        return switch (trainingType.getValue()) {
            case "recovery" -> TrainingType.RECOVERY;
            case "intensity" -> TrainingType.INTENSITY;
            default -> TrainingType.BASE;
        };
    }

    private TrainingStatus calcTrainingStatus() {
        if(LocalDate.now().isBefore(datePicker.getValue())) {
            return TrainingStatus.PLANNED;
        } else {
            return TrainingStatus.COMPLETED;
        }
    }

    private Button createCancelButton() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> {
            this.close();
            this.resetAllInputFields();
        });
        return cancelButton;
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
}
