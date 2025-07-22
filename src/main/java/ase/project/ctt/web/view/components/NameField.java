package ase.project.ctt.web.view.components;

import com.vaadin.flow.component.textfield.TextField;

public class NameField extends TextField {
    public NameField() {
        this.setLabel("Name");
        this.setMaxLength(32);
        this.setRequired(true);
        this.setPlaceholder("easy zone 2 ride");
    }

    public void resetValue() {
        this.setValue("");
    }
}
