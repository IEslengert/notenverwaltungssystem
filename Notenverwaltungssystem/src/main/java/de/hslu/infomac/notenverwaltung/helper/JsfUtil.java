package de.hslu.infomac.notenverwaltung.helper;

import java.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Igor Eslengert
 */
public class JsfUtil {

	public static void addErrorMessage(final Exception ex, final String defaultMsg, final String uiComponent) {
		final String msg = ex.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			addErrorMessage(uiComponent, msg);
		} else {
			addErrorMessage(uiComponent, defaultMsg);
		}
	}

	public static void addErrorMessage(final String msg) {
		final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addErrorMessage(final String uiComponent, final String msg) {
		final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
		FacesContext.getCurrentInstance().addMessage(uiComponent, facesMsg);
	}

	public static void addErrorMessages(final List<String> messages) {
		for (final String message : messages) {
			addErrorMessage(message);
		}
	}

	public static void addSuccessMessage(final String msg) {
		final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static void addSuccessMessage(final String uiComponent, final String msg) {
		final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
		FacesContext.getCurrentInstance().addMessage(uiComponent, facesMsg);
	}

	public static void addWarningMessage(final String msg) {
		final FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, null);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	public static String getMsg(final ResourceBundle bundle, final String msgKey, final String paramValue) {
		final String msgValue = bundle.getString(msgKey);
		final MessageFormat messageFormat = new MessageFormat(msgValue);
		final Object[] args = { paramValue };
		return messageFormat.format(args);
	}

}
