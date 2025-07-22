package ase.project.ctt.web.view.components.input;

import java.time.LocalDate;

public class DatePicker extends com.vaadin.flow.component.datepicker.DatePicker {
    public DatePicker() {
        this.setRequired(true);
        this.setValue(LocalDate.now());
    }

    public void resetValue() {
        this.setValue(LocalDate.now());
    }
}
