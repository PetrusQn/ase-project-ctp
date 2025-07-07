package ase.project.ctt.web.view.components;

import ase.project.ctt.domain.model.enums.TrainingStatus;
import ase.project.ctt.domain.model.enums.TrainingType;
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

    public CreateTrainingSessionDialog() {
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
           // To be implemented
        });
        return saveButton;
    }

    private Button createCancelButton() {
        return new Button("Cancel", e -> this.close());
    }

    private FormLayout createCreationForm() {
        DatePicker datePicker = new DatePicker("Execution date");

        NumberField durationField = createNumberField("Duration", "mins");
        NumberField distanceField = createNumberField("Distance", "km");

        Select<String> typeSelector = new Select<>();
        typeSelector.setLabel("Training type");
        typeSelector.setItems(TrainingType.RECOVERY.name().toLowerCase(), TrainingType.BASE.name().toLowerCase(), TrainingType.INTENSITY.name().toLowerCase());

        Select<String> statusSelector = new Select<>();
        statusSelector.setLabel("Training status");
        statusSelector.setItems(TrainingStatus.PLANNED.name().toLowerCase(), TrainingStatus.COMPLETED.name().toLowerCase());

        NumberField avgPowerField = createNumberField("Average power", "w");
        NumberField avgHrField = createNumberField("Average heart rate", "bpm");
        NumberField avgCadence = createNumberField("Average cadence", "rpm");

        TextArea noteField = createNoteField();

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
        fourthRow.add(avgPowerField, avgHrField, avgCadence);

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
