package de.hslu.infomac.notenverwaltung.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;

import de.hslu.infomac.notenverwaltung.entities.Benutzer;
import de.hslu.infomac.notenverwaltung.entities.Schueler;
import de.hslu.infomac.notenverwaltung.exceptions.CouldNotHashPasswordException;
import de.hslu.infomac.notenverwaltung.helper.JsfUtil;
import de.hslu.infomac.notenverwaltung.helper.Util;

/**
 * @author Igor Eslengert
 */
@ManagedBean(name = "authentifizierungBean")
@SessionScoped
public class AuthentifizierungBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////////////////////////////
	////////// Bean Attribute //////////
	///////////////////////////////////

	private String loginName;

	private String passwort;

	private String neuesPasswort;

	@ManagedProperty(value = "#{personBean}")
	private PersonBean personBean;

	//////////////////////////////////////
	//// Getter/Setter der Attribute ////
	////////////////////////////////////

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getNeuesPasswort() {
		return neuesPasswort;
	}

	public void setNeuesPasswort(String neuesPasswort) {
		this.neuesPasswort = neuesPasswort;
	}

	public PersonBean getPersonBean() {
		return personBean;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}

	///////////////////////////////////
	//////// Weitere Methoden ////////
	/////////////////////////////////

	public String updatePasswort() {
		HttpSession session = Util.getSession();
		Benutzer benutzer = (Benutzer) session.getAttribute("user");
		try {
			if (benutzer.getPasswort().equals(Util.hash(passwort))) {
				if (!passwort.equals(neuesPasswort)) {
					benutzer.setPasswort(Util.hash(neuesPasswort));
					benutzer.setErsterLogin(false);
					personBean.edit(benutzer);
					session.setAttribute("user", benutzer);
					passwort = null;
					neuesPasswort = null;
					return "eingeloggterBenutzerProfil.xhtml?faces-redirect=true";
				} else {
					JsfUtil.addErrorMessage("passwortAendernForm:passwortAendern",
							"Das neue Passwort und das alte Passwort dürfen nicht gleich sein.");
				}
			} else {
				JsfUtil.addErrorMessage("passwortAendernForm:passwortAendern", "Das aktuelle Passwort ist falsch.");
			}
		} catch (CouldNotHashPasswordException e) {
			JsfUtil.addErrorMessage("passwortAendernForm:passwortAendern", "Ihr passwort konnte nicht gehasht werden.");
			e.printStackTrace();
		}
		passwort = null;
		neuesPasswort = null;
		return "";
	}

	public String login() {

		Benutzer benutzer = personBean.getBenutzerByLoginName(loginName);

		if (benutzer != null && benutzer.getPersonId() != null) {
			try {
				if (benutzer.getPasswort().equals(Util.hash(passwort))) {
					HttpSession session = Util.getSession();
					session.setAttribute("user", benutzer);
					// session.setMaxInactiveInterval(1800);

					if (benutzer instanceof Schueler) {
						personBean.setLehrerEingeloggt(false);
					} else {
						personBean.setLehrerEingeloggt(true);
					}

					passwort = null;
					if (benutzer.isErsterLogin()) {

						return "passwortAendern.xhtml?faces-redirect=true";
					} else {
						if (benutzer instanceof Schueler) {
							personBean.setSorgeberechtigter(((Schueler) benutzer).getSorgeberechtigter());
						}
						return "eingeloggterBenutzerProfil.xhtml?faces-redirect=true";
					}
				}
			} catch (CouldNotHashPasswordException e) {
				JsfUtil.addErrorMessage("loginForm:login", "Ihr passwort konnte nicht gehasht werden.");
				e.printStackTrace();
			}
		}
		passwort = null;
		JsfUtil.addErrorMessage("loginForm:login", "Falscher Benutzername oder Passwort.");
		return "";
	}

	public String logout() {
		invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	private void invalidateSession() {
		final HttpSession session = Util.getSession();
		if (session != null) {
			session.invalidate();
		}
	}

}
