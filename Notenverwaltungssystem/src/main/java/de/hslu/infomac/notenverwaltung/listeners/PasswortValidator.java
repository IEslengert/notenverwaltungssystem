package de.hslu.infomac.notenverwaltung.listeners;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwortValidator")
public class PasswortValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String passwort = value.toString();

		UIInput uiInputConfirmPasswort = (UIInput) component.getAttributes().get("confirmPasswort");
		String confirmPasswort = uiInputConfirmPasswort.getSubmittedValue().toString();

		if (passwort == null || passwort.isEmpty() || confirmPasswort == null || confirmPasswort.isEmpty()) {
			return;
		}

		if (!passwort.equals(confirmPasswort)) {
			String msg = "Das neue Passwort und das bestätigte Passwort stimmen nicht überein.";
			uiInputConfirmPasswort.setValid(false);
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}

	}
}
