package ase.project.ctt.web.view.components;

import ase.project.ctt.application.mapper.TrainingSessionMapper;
import ase.project.ctt.domain.model.TrainingSession;
import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
import ase.project.ctt.domain.model.valueobjects.*;
import ase.project.ctt.infrastructure.service.TrainingSessionService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.time.LocalDate;


public class CreateTrainingSessionDialog extends Dialog {

    private TrainingSessionService client;

    private DatePicker datePicker;
    private NumberField durationField;
    private NumberField distanceField;
    private Select<String> typeSelector;
    private NumberField avgPowerField;
    private NumberField avgHrField;
    private NumberField avgCadenceField;
    private TextArea noteField;

    public CreateTrainingSessionDialog(TrainingSessionService client) {
        this.client = client;
        this.setHeaderTitle("New training session");
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(createCreationForm());
        this.add(dialogLayout);
        this.getFooter().add(createSaveButton());
        this.getFooter().add(createCancelButton());
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(event -> {
            if (this.onFormSubmit()) {
                this.close();
                this.resetAllInputFields();
            }
        });
        return saveButton;
    }

    private boolean onFormSubmit() {
        if(areRequiredFieldsSet()) {
            client.createSession(TrainingSessionMapper.toDto(TrainingSession.create(datePicker.getValue(),
                    new Duration(this.durationField.getValue()),
                    new Distance(this.distanceField.getValue()),
                    this.getTrainingType(this.typeSelector),
                    this.calcTrainingStatus(),
                    new AvgPower(this.avgPowerField.getValue().intValue()),
                    new AvgHeartRate(this.avgHrField.getValue().intValue()),
                    new AvgCadence(this.avgCadenceField.getValue().intValue()),
                    this.noteField.getValue())));
            return true;
        } else {
            Notification.show("Please fill all the necessary boxes");
            return false;
        }
    }

    private boolean areRequiredFieldsSet() {
        return !this.noteField.isEmpty();
    }

    private void resetAllInputFields() {
        this.datePicker.setValue(LocalDate.now());
        this.durationField.setValue(0.0);
        this.distanceField.setValue(0.0);
        this.typeSelector.setValue("base");
        this.avgPowerField.setValue(0.0);
        this.avgHrField.setValue(0.0);
        this.avgCadenceField.setValue(0.0);
        this.noteField.setValue("");
    }

    private TrainingType getTrainingType(Select<String> trainingType) {
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

    private FormLayout createCreationForm() {
        this.datePicker = new DatePicker("Execution date");
        this.datePicker.setRequired(true);
        this.datePicker.setValue(LocalDate.now());

        this.durationField = createNumberField("Duration", "mins");
        this.distanceField = createNumberField("Distance", "km");

        this.typeSelector = new Select<>();
        this.typeSelector.setLabel("Training type");
        this.typeSelector.setRequiredIndicatorVisible(true);
        this.typeSelector.setItems(TrainingType.RECOVERY.name().toLowerCase(), TrainingType.BASE.name().toLowerCase(), TrainingType.INTENSITY.name().toLowerCase());

        this.avgPowerField = createNumberField("Average power", "w");
        this.avgHrField = createNumberField("Average heart rate", "bpm");
        this.avgCadenceField = createNumberField("Average cadence", "rpm");

        this.noteField = createNoteField();

        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.setExpandFields(true);

        FormLayout.FormRow firstRow = new FormLayout.FormRow();
        firstRow.add(datePicker, 1);

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

    private NumberField createNumberField(String label, String suffixText) {
        NumberField field = new NumberField();
        field.setLabel(label);
        Div suffix = new Div();
        suffix.setText(suffixText);
        field.setSuffixComponent(suffix);
        field.setValue(0.0);
        return field;
    }

    private TextArea createNoteField() {
        TextArea noteField = new TextArea();
        noteField.setLabel("Notes");
        noteField.setMaxLength(100);
        noteField.setRequired(true);
        noteField.setValueChangeMode(ValueChangeMode.EAGER);
        noteField.setPlaceholder("Easy Zone 2 ride");
        noteField.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/100");
        });
        return noteField;
    }
}
