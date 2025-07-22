package ase.project.ctt.web.view.components.input;

import com.vaadin.flow.component.html.Div;

public class NumberField extends com.vaadin.flow.component.textfield.NumberField {
    public NumberField(String labelText, String suffixText) {
        this.setLabel(labelText);
        Div suffix = new Div();
        suffix.setText(suffixText);
        this.setSuffixComponent(suffix);
        this.setValue(0.0);
    }

    public void resetValue() {
        this.setValue(0.0);
    }
}
