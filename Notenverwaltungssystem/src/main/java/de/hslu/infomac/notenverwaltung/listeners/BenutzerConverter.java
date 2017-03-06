package de.hslu.infomac.notenverwaltung.listeners;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import de.hslu.infomac.notenverwaltung.beans.PersonBean;

@ManagedBean(name = "benutzerConverter")
@RequestScoped
@FacesConverter("benutzerConverter")
public class BenutzerConverter implements Converter {

	@ManagedProperty(value = "#{personBean}")
	private PersonBean personBean;
	
	public PersonBean getPersonBean() {
		return personBean;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}

	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		if (value != null && value.trim().length() > 0) {
			try {
				return personBean.getPersonById(Long.valueOf(value));
			} catch (NumberFormatException e) {
				throw new ConverterException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Kein Valider Benutzer."));
			}
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		if (object != null) {
			return String.valueOf(object);
		} else {
			return null;
		}
	}
}
