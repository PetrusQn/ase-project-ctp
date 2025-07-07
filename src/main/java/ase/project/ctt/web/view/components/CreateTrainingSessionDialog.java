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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;


public class CreateTrainingSessionDialog extends Dialog {

    private TrainingSessionService client;

    private DatePicker datePicker;
    private NumberField durationField;
    private NumberField distanceField;
    private Select<String> typeSelector;
    private Select<String> statusSelector;
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
            client.createSession(TrainingSessionMapper.toDto(TrainingSession.create(datePicker.getValue(),
                    new Duration(durationField.getValue()),
                    new Distance(distanceField.getValue()),
                    this.getTrainingType(typeSelector),
                    this.getTrainingStatus(statusSelector),
                    new AvgPower(avgPowerField.getValue().intValue()),
                    new AvgHeartRate(avgHrField.getValue().intValue()),
                    new AvgCadence(avgCadenceField.getValue().intValue()),
                    noteField.getValue())));
            this.close();
        });
        return saveButton;
    }

    private TrainingType getTrainingType(Select<String> trainingType) {
        return switch (trainingType.getValue()) {
            case "recovery" -> TrainingType.RECOVERY;
            case "base" -> TrainingType.BASE;
            case "intensity" -> TrainingType.INTENSITY;
            default -> TrainingType.UNDEFINED;
        };
    }

    private TrainingStatus getTrainingStatus(Select<String> trainingStatus) {
        return switch (trainingStatus.getValue()) {
          case "planned" -> TrainingStatus.PLANNED;
          case "completed" -> TrainingStatus.COMPLETED;
          default -> TrainingStatus.UNDEFINED;
        };
    }

    private Button createCancelButton() {
        return new Button("Cancel", e -> this.close());
    }

    private FormLayout createCreationForm() {
        this.datePicker = new DatePicker("Execution date");

        this.durationField = createNumberField("Duration", "mins");
        this.distanceField = createNumberField("Distance", "km");

        this.typeSelector = new Select<>();
        typeSelector.setLabel("Training type");
        typeSelector.setItems(TrainingType.RECOVERY.name().toLowerCase(), TrainingType.BASE.name().toLowerCase(), TrainingType.INTENSITY.name().toLowerCase());

        this.statusSelector = new Select<>();
        statusSelector.setLabel("Training status");
        statusSelector.setItems(TrainingStatus.PLANNED.name().toLowerCase(), TrainingStatus.COMPLETED.name().toLowerCase());

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
        thirdRow.add(typeSelector, statusSelector);

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
        return field;
    }

    private TextArea createNoteField() {
        TextArea noteField = new TextArea();
        noteField.setLabel("Notes");
        noteField.setMaxLength(100);
        noteField.setValueChangeMode(ValueChangeMode.EAGER);
        noteField.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/100");
        });
        return noteField;
    }
}
