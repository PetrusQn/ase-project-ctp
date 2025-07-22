package ase.project.ctt.web.view.components.input;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class AddTrainingSessionButton extends Button {

    public AddTrainingSessionButton(Dialog dialogToOpen) {
        this.setIcon(new Icon(VaadinIcon.PLUS));
        this.setText("Add");
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addClickListener(clickEvent -> dialogToOpen.open());
    }
}
