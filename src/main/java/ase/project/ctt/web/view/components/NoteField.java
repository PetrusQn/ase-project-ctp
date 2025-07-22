package ase.project.ctt.web.view.components;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;

public class NoteField extends TextArea {
    public NoteField() {
        this.setLabel("Notes");
        this.setMaxLength(100);
        this.setRequired(true);
        this.setValueChangeMode(ValueChangeMode.EAGER);
        this.setPlaceholder("Some description or hints");
        this.addValueChangeListener(e -> e.getSource()
                .setHelperText(e.getValue().length() + "/100"));
    }

    public void resetValue() {
        this.setValue("");
    }
}
