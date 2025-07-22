package ase.project.ctt.web.view.components.input;

import ase.project.ctt.domain.model.enums.TrainingType;
import com.vaadin.flow.component.select.Select;

public class TypeSelector extends Select<String> {
    public TypeSelector() {
        this.setLabel("Training type");
        this.setRequiredIndicatorVisible(true);
        this.setItems(
                TrainingType.RECOVERY.name().toLowerCase(),
                TrainingType.BASE.name().toLowerCase(),
                TrainingType.INTENSITY.name().toLowerCase());
        this.setValue("base");
    }

    public void resetValue() {
        this.setValue("base");
    }
}
