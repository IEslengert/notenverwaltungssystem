package de.hslu.infomac.notenverwaltung.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import de.hslu.infomac.notenverwaltung.entities.Benutzer;
import de.hslu.infomac.notenverwaltung.entities.Fach;
import de.hslu.infomac.notenverwaltung.entities.Klasse;
import de.hslu.infomac.notenverwaltung.entities.Lehrer;
import de.hslu.infomac.notenverwaltung.entities.Person;
import de.hslu.infomac.notenverwaltung.entities.Schueler;
import de.hslu.infomac.notenverwaltung.entities.SchuelerKlasse;
import de.hslu.infomac.notenverwaltung.entities.Sorgeberechtigter;
import de.hslu.infomac.notenverwaltung.enumerations.Anrede;
import de.hslu.infomac.notenverwaltung.enumerations.Rolle;
import de.hslu.infomac.notenverwaltung.enumerations.Status;
import de.hslu.infomac.notenverwaltung.exceptions.CouldNotHashPasswordException;
import de.hslu.infomac.notenverwaltung.helper.HibernateUtil;
import de.hslu.infomac.notenverwaltung.helper.JsfUtil;
import de.hslu.infomac.notenverwaltung.helper.Util;

/**
 * @author Igor Eslengert
 */
@ManagedBean(name = "personBean", eager = true)
@SessionScoped
public class PersonBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////////////////////////////
	////////// Bean Attribute //////////
	///////////////////////////////////

	private Schueler schueler;

	private List<Schueler> schuelerListe;

	private Lehrer lehrer;

	private List<Lehrer> lehrerListe;

	private Sorgeberechtigter sorgeberechtigter;

	private boolean wirdEditiert;

	private boolean gefaehrdeteSchuelerAnzeige;

	private boolean lehrerEingeloggt;

	@ManagedProperty(value = "#{mailBean}")
	private MailBean mailBean;

	//////////////////////////////////////
	//// Getter/Setter der Attribute ////
	////////////////////////////////////

	public boolean isSchulleiter() {
		return lehrer.hasRolle(Rolle.Schulleiter);
	}

	public void setSchulleiter(boolean istSchulleiter) {
		if (istSchulleiter) {
			lehrer.getRollen().add(Rolle.Schulleiter);
		}
	}

	public Schueler getSchueler() {
		return schueler;
	}

	public void setSchueler(Schueler schueler) {
		this.schueler = schueler;
	}

	public boolean isGefaehrdeteSchuelerAnzeige() {
		return gefaehrdeteSchuelerAnzeige;
	}

	public void setGefaehrdeteSchuelerAnzeige(boolean gefaehrdeteSchuelerAnzeige) {
		this.gefaehrdeteSchuelerAnzeige = gefaehrdeteSchuelerAnzeige;
	}

	public List<Schueler> getSchuelerListe() {
		return schuelerListe;
	}

	public void setSchuelerListe(List<Schueler> schuelerListe) {
		this.schuelerListe = schuelerListe;
	}

	public Lehrer getLehrer() {
		return lehrer;
	}

	public void setLehrer(Lehrer lehrer) {
		this.lehrer = lehrer;
	}

	public List<Lehrer> getLehrerListe() {
		return lehrerListe;
	}

	public void setLehrerListe(List<Lehrer> lehrerListe) {
		this.lehrerListe = lehrerListe;
	}

	public boolean isWirdEditiert() {
		return wirdEditiert;
	}

	public boolean isLehrerEingeloggt() {
		return lehrerEingeloggt;
	}

	public void setLehrerEingeloggt(boolean lehrerEingeloggt) {
		this.lehrerEingeloggt = lehrerEingeloggt;
	}

	public void setWirdEditiert(boolean wirdEditiert) {
		this.wirdEditiert = wirdEditiert;
	}

	public Sorgeberechtigter getSorgeberechtigter() {
		return sorgeberechtigter;
	}

	public void setSorgeberechtigter(Sorgeberechtigter sorgeberechtigter) {
		this.sorgeberechtigter = sorgeberechtigter;
	}

	public MailBean getMailBean() {
		return mailBean;
	}

	public void setMailBean(MailBean mailBean) {
		this.mailBean = mailBean;
	}

	///////////////////////////////////
	//////// CRUD Operationen ////////
	/////////////////////////////////

	public Benutzer getBenutzerByLoginName(String loginName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Benutzer.class);
		criteria.add(Restrictions.eq("loginName", loginName));

		Benutzer benutzer = (Benutzer) criteria.uniqueResult();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return benutzer;

	}

	public Person getPersonByEmail(String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Person.class);
		criteria.add(Restrictions.eq("email", email));

		Person person = (Person) criteria.uniqueResult();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return person;

	}

	public Lehrer getLehrerByPersonalNr(String personalNr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Lehrer.class);
		criteria.add(Restrictions.eq("personalNr", personalNr));

		Lehrer lehrer = (Lehrer) criteria.uniqueResult();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return lehrer;

	}

	public Person getPersonById(long personId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person person = (Person) session.get(Person.class, personId);

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return person;

	}

	@SuppressWarnings("unchecked")
	public List<Schueler> getAllSchueler() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Schueler.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Schueler> schuelerliste = criteria.list();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();
		return schuelerliste;

	}

	@SuppressWarnings("unchecked")
	public List<Lehrer> getAllLehrer() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Lehrer.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Lehrer> lehrerliste = criteria.list();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();
		return lehrerliste;

	}

	public void create(Object object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.persist(object);

		session.getTransaction().commit();

	}

	public void delete(Object object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.delete(object);

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

	}

	public void edit(Object object) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		session.merge(object);

		session.getTransaction().commit();
	}

	///////////////////////////////////
	//////// Weitere Methoden ////////
	/////////////////////////////////

	@SuppressWarnings("unchecked")
	public List<Lehrer> getNewLehrerAndLehrerWithoutKlasse() {

		List<Lehrer> alleLehrerListe = getAllLehrer();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Lehrer> lehrerListeMitKlasse = new ArrayList<Lehrer>();
		Criteria criteria = session.createCriteria(Lehrer.class);
		Criteria klasseCriteria = criteria.createCriteria("klassenListe");
		klasseCriteria.add(Restrictions.eq("istAktiv", true));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		lehrerListeMitKlasse.addAll(criteria.list());
		for (Lehrer lehrer : lehrerListeMitKlasse) {
			alleLehrerListe.remove(lehrer);
		}

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return alleLehrerListe;
	}

	@SuppressWarnings("unchecked")
	public List<Schueler> getNewSchuelerAndSchuelerWithoutKlasse() {

		// Schüler mit der letzten Einschreibung spätestens vor zwei Jahren
		int letzteEinschreibungVorJahren = 2;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -letzteEinschreibungVorJahren);
		Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

		List<Schueler> schuelerListe = getNewSchuelerAndSchuelerByDate(timestamp);

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Schueler> schuelerListeMitKlasse = new ArrayList<Schueler>();
		Criteria criteria = session.createCriteria(Schueler.class);
		criteria.add(Restrictions.ge("letzteEinschreibung", timestamp));
		Criteria klasseCriteria = criteria.createCriteria("schuelerKlassenListe");
		klasseCriteria.add(Restrictions.eq("status", Status.Eingeschrieben));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		schuelerListeMitKlasse.addAll(criteria.list());
		for (Schueler schueler : schuelerListeMitKlasse) {
			schuelerListe.remove(schueler);
		}

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return schuelerListe;
	}

	@SuppressWarnings("unchecked")
	public List<Schueler> getNewSchuelerAndSchuelerByDate(Timestamp timestamp) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Schueler.class);
		criteria.add(Restrictions.or(Restrictions.ge("letzteEinschreibung", timestamp), Restrictions
				.and(Restrictions.isEmpty("schuelerKlassenListe"), Restrictions.isNull("letzteEinschreibung"))));
		List<Schueler> schuelerListe = criteria.list();

		session.getTransaction().commit();

		return schuelerListe;
	}

	// TODO: Mail an Schueler entkommentieren
	public void createSchueler() throws CouldNotHashPasswordException {
		Date date = new Date();
		if (schueler.getGeburtstag().before(date) && sorgeberechtigter.getGeburtstag().before(date)) {
			Benutzer schuelerLoginName = getBenutzerByLoginName(schueler.getLoginName());
			Person schuelerEmail = getPersonByEmail(schueler.getEmail());
			Person sorgeberechtigterEmail = getPersonByEmail(sorgeberechtigter.getEmail());
			if (schuelerLoginName == null && schuelerEmail == null && sorgeberechtigterEmail == null
					&& !(schueler.getEmail().equals(sorgeberechtigter.getEmail()))) {
				// generiertes passwort zufügen
				String generatedPasswort = Util.getRandomString(20);
				schueler.setPasswort(Util.hash(generatedPasswort));
				// rolle "schüler" zufügen
				schueler.getRollen().add(Rolle.Schueler);
				// ersterlogin auf true
				schueler.setErsterLogin(true);
				// schüler erstellen
				create(schueler);
				sorgeberechtigter.setSchueler(schueler);
				create(sorgeberechtigter);

				nachrichtAnBenutzerSenden(schueler, generatedPasswort, true);

				JsfUtil.addSuccessMessage("createEditSchueler:create", "Der Schüler wurde erfolgreich angelegt.");
				schueler = new Schueler();
				sorgeberechtigter = new Sorgeberechtigter();
			} else if (schuelerLoginName != null) {
				JsfUtil.addErrorMessage("createEditSchueler:loginName", "Der gewählte Login-Name existiert bereits.");
			} else if (schuelerEmail != null) {
				JsfUtil.addErrorMessage("createEditSchueler:email",
						"Die gewählte Schüler-Email-Adresse existiert bereits.");
			} else if (sorgeberechtigterEmail != null) {
				JsfUtil.addErrorMessage("createEditSchueler:emailSB",
						"Die gewählte Sorgeberechtigter-Email-Adresse existiert bereits.");
			} else if (schueler.getEmail().equals(sorgeberechtigter.getEmail())) {
				JsfUtil.addErrorMessage("createEditSchueler:emailSB",
						"Die SB-Email Adresse und die Schueler Email Adresse dürfen nicht gleich sein.");
				JsfUtil.addErrorMessage("createEditSchueler:email",
						"Die SB-Email Adresse und die Schueler Email Adresse dürfen nicht gleich sein.");

			}
		} else if (schueler.getGeburtstag().after(date)) {
			JsfUtil.addErrorMessage("createEditLehrer:geburtstag",
					"Das Geburtsdatum vom Schüler darf nicht in der Zukuft sein.");
		} else if (sorgeberechtigter.getGeburtstag().after(date)) {
			JsfUtil.addErrorMessage("createEditLehrer:geburtstagSB",
					"Das Geburtsdatum vom Sorgeberechtigten darf nicht in der Zukuft sein.");
		}

	}

	private void nachrichtAnBenutzerSenden(Benutzer benutzer, String passwort, boolean neuerAccount) {
		String inhalt = "";
		String betreff = "";
		List<String> empfaenger = new ArrayList<String>();
		empfaenger.add(benutzer.getEmail());
		if (neuerAccount) {
			inhalt = "für Sie wurde ein Account im Notenverwaltungssystem angelegt.<br/><br/>"
					+ "Ihre Zugansdaten lauten wie folgt: <br/> Benutzername: " + benutzer.getLoginName() + "<br/>"
					+ "Passwort: " + passwort
					+ "<br/><br/> Das zuvor genannte Passwort wurde zufällig generiert, daher "
					+ "werden Sie beim ersten Login aufgefordert das Passwort zu ändern.";
			betreff = "Ihre Zugangsdaten zum Notenverwaltungssystem.";
		} else if (!neuerAccount) {
			inhalt = "Ihr Passwort im Notenverwaltungssystem wurde zurückgesetzt.<br/><br/>"
					+ "Das neue Passwort von Ihrem Account (" + benutzer.getLoginName()
					+ ") lauten wie folgt: <br/> Passwort: " + passwort
					+ "<br/><br/> Das zuvor genannte Passwort wurde zufällig generiert, daher "
					+ "werden Sie beim nächsten Login aufgefordert das Passwort zu ändern.";
			betreff = "Ihre Passwort im Notenverwaltungssystem wurde zurückgesetzt.";
		}
		String empfaengerName = "";
		if (benutzer instanceof Lehrer) {
			Lehrer lehrer = (Lehrer) benutzer;
			empfaengerName = mailBean.empaengerFormatieren(lehrer.getTitel(), lehrer.getVorname(),
					lehrer.getNachname());
		} else {
			empfaengerName = mailBean.empaengerFormatieren("", benutzer.getVorname(), benutzer.getNachname());
		}
		mailBean.sendMail(betreff, inhalt, empfaenger, null, benutzer.getAnrede(), empfaengerName,
				"mic311.notenverwaltungssystem@gmail.com", "Notenverwaltungssystem");
	}

	// TODO: Mail an Lehrer entkommentieren
	public void createLehrer() throws CouldNotHashPasswordException {
		Date date = new Date();
		if (lehrer.getGeburtstag().before(date)) {
			Benutzer lehrerLoginName = getBenutzerByLoginName(lehrer.getLoginName());
			Person lehrerEmail = getPersonByEmail(lehrer.getEmail());
			Lehrer lehrerPersonalNr = getLehrerByPersonalNr(lehrer.getPersonalNr());
			if (lehrerLoginName == null && lehrerEmail == null && lehrerPersonalNr == null) {
				String generatedPasswort = Util.getRandomString(20);
				lehrer.setPasswort(Util.hash(generatedPasswort));
				// rolle "schüler" zufügen
				lehrer.getRollen().add(Rolle.Lehrer);
				// ersterlogin auf true
				lehrer.setErsterLogin(true);
				// schüler erstellen
				create(lehrer);

				nachrichtAnBenutzerSenden(lehrer, generatedPasswort, true);

				lehrer = new Lehrer();
				JsfUtil.addSuccessMessage("createEditLehrer:create", "Der Lehrer wurde erfolgreich angelegt.");
			} else if (lehrerLoginName != null) {
				JsfUtil.addErrorMessage("createEditLehrer:loginName", "Der gewählte Login-Name existiert bereits.");
			} else if (lehrerEmail != null) {
				JsfUtil.addErrorMessage("createEditLehrer:email", "Die gewählte Email-Adresse existiert bereits.");
			} else if (lehrerPersonalNr != null) {
				JsfUtil.addErrorMessage("createEditLehrer:personalNr", "Die gewählte PersonalNr existiert bereits.");
			}
		} else {
			JsfUtil.addErrorMessage("createEditLehrer:geburtstag", "Das Geburtsdatum darf nicht in der Zukuft sein.");
		}
	}

	public void editSchueler() {
		Person schuelerEmail = getPersonByEmail(schueler.getEmail());
		Person sorgeberechtigterEmail = getPersonByEmail(sorgeberechtigter.getEmail());

		if (schuelerEmail == null || schuelerEmail.getPersonId() == schueler.getPersonId()) {
			if (sorgeberechtigterEmail == null
					|| sorgeberechtigterEmail.getPersonId() == sorgeberechtigter.getPersonId()) {
				schueler.setSorgeberechtigter(sorgeberechtigter);
				edit(schueler);
				JsfUtil.addSuccessMessage("createEditSchueler:edit", "Der Schüler wurde erfolgreich aktualisiert.");
			} else {
				JsfUtil.addErrorMessage("createEditSchueler:emailSB",
						"Die gewählte Sorgeberechtigter-Email-Adresse existiert bereits.");
			}
		} else {
			JsfUtil.addErrorMessage("createEditSchueler:email",
					"Die gewählte Schüler-Email-Adresse existiert bereits.");
		}
	}

	public void editLehrer() {
		Person lehrerEmail = getPersonByEmail(lehrer.getEmail());
		if (lehrerEmail == null || lehrerEmail.getPersonId() == lehrer.getPersonId()) {
			edit(lehrer);
			HttpSession session = Util.getSession();
			Benutzer benutzer = (Benutzer) session.getAttribute("user");
			if (benutzer.getPersonId() == lehrer.getPersonId()) {
				session.setAttribute("user", lehrer);
			}
			JsfUtil.addSuccessMessage("createEditLehrer:edit", "Der Lehrer wurde erfolgreich aktualisiert.");
		} else {
			JsfUtil.addErrorMessage("createEditLehrer:email", "Die gewählte Email-Adresse existiert bereits.");
		}
	}

	public void deleteSchueler() {
		long schuelerId = schueler.getPersonId();
		schuelerListe.remove(schueler);
		delete(schueler);
		JsfUtil.addSuccessMessage(null, "Der Schüler mit der Id " + schuelerId + " wurde erfolgreich gelöscht.");
	}

	public void deleteLehrer() {
		KlasseBean klasseBean = new KlasseBean();
		FachBean fachBean = new FachBean();
		List<Klasse> klassenVomLehrer = klasseBean.getKlassenByLehrer(lehrer.getPersonId());
		for (Klasse klasse : klassenVomLehrer) {
			klasse.setKlassenLehrer(null);
			edit(klasse);
		}
		List<Fach> faecherVomLehrer = fachBean.getFaecherByLehrer(lehrer.getPersonId());
		for (Fach fach : faecherVomLehrer) {
			fach.setLehrer(null);
			edit(fach);
		}

		long lehrerId = lehrer.getPersonId();
		lehrerListe.remove(lehrer);
		delete(lehrer);
		JsfUtil.addSuccessMessage(null, "Der Lehrer mit der Id " + lehrerId + " wurde erfolgreich gelöscht.");
	}

	public void neuesPasswortGenerierenLehrer() throws CouldNotHashPasswordException {
		String generatedPasswort = Util.getRandomString(20);
		lehrer.setPasswort(Util.hash(generatedPasswort));
		lehrer.setErsterLogin(true);
		edit(lehrer);
		nachrichtAnBenutzerSenden(lehrer, generatedPasswort, false);
		JsfUtil.addSuccessMessage(null, "Ein neues Passwort wurde generiert und an den Lehrer mit der Id "
				+ lehrer.getPersonId() + " gesendet.");
	}

	public void neuesPasswortGenerierenSchueler() throws CouldNotHashPasswordException {
		String generatedPasswort = Util.getRandomString(20);
		schueler.setPasswort(Util.hash(generatedPasswort));
		schueler.setErsterLogin(true);
		edit(schueler);
		nachrichtAnBenutzerSenden(schueler, generatedPasswort, false);
		JsfUtil.addSuccessMessage(null, "Ein neues Passwort wurde generiert und an den Schüler mit der Id "
				+ schueler.getPersonId() + " gesendet.");
	}

	public String prepareEditSchueler(long schuelerId) {
		schueler = (Schueler) getPersonById(schuelerId);
		sorgeberechtigter = schueler.getSorgeberechtigter();
		wirdEditiert = true;
		return "createEditSchueler.xhtml?faces-redirect=true";
	}

	public String prepareEditLehrer(long lehrerId) {
		lehrer = (Lehrer) getPersonById(lehrerId);
		wirdEditiert = true;
		return "createEditLehrer.xhtml?faces-redirect=true";
	}

	public String prepareCreateSchueler() {
		schueler = new Schueler();
		sorgeberechtigter = new Sorgeberechtigter();
		wirdEditiert = false;
		return "createEditSchueler.xhtml?faces-redirect=true";
	}

	public String prepareCreateLehrer() {
		lehrer = new Lehrer();
		wirdEditiert = false;
		return "createEditLehrer.xhtml?faces-redirect=true";
	}

	public String prepareListSchueler() {
		this.schuelerListe = getAllSchueler();
		return "schuelerAnzeigen.xhtml?faces-redirect=true";
	}

	public String prepareListLehrer() {
		this.lehrerListe = getAllLehrer();
		return "lehrerAnzeigen.xhtml?faces-redirect=true";
	}

	public void prepareShowSchueler(long schuelerId) {
		schueler = (Schueler) getPersonById(schuelerId);
		sorgeberechtigter = schueler.getSorgeberechtigter();
	}

	public void prepareShowLehrer(long lehrerId) {
		lehrer = (Lehrer) getPersonById(lehrerId);

	}

	public StreamedContent getProfilBild() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String benutzerIdString = context.getExternalContext().getRequestParameterMap().get("benutzerId");
			if (benutzerIdString != null && !benutzerIdString.isEmpty()) {
				Benutzer benutzer = (Benutzer) getPersonById(new Long(benutzerIdString));
				if (benutzer != null) {
					byte[] profilBild = benutzer.getProfilBild();
					if (profilBild != null) {
						return new DefaultStreamedContent(new ByteArrayInputStream(profilBild));
					} else {
						return null;
					}
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	public StreamedContent getSchuelerProfilBild() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {

			byte[] profilBild = schueler.getProfilBild();
			if (profilBild != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(profilBild));
			} else {
				return null;
			}
		}
	}

	public StreamedContent getLehrerProfilBild() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {

			byte[] profilBild = lehrer.getProfilBild();
			if (profilBild != null) {
				return new DefaultStreamedContent(new ByteArrayInputStream(profilBild));
			} else {
				return null;
			}
		}
	}

	public void schuelerProfilBildHochladen(FileUploadEvent event) {
		schueler.setProfilBild(event.getFile().getContents());
	}

	public void lehrerProfilBildHochladen(FileUploadEvent event) {
		lehrer.setProfilBild(event.getFile().getContents());
	}

	public Anrede[] getAnredeValues() {
		return Anrede.values();

	}

	public String prepareListSchuelerByKlasse(long klasseId) {
		this.schuelerListe = getSchuelerByKlasse(klasseId);
		gefaehrdeteSchuelerAnzeige = false;
		return "schuelerVonKlasseAnzeigen.xhtml?faces-redirect=true";
	}

	public String prepareListGefaehrdeteSchuelerByKlasse(long klasseId) {
		this.schuelerListe = getGefaehrdeteSchuelerByKlasse(klasseId);
		gefaehrdeteSchuelerAnzeige = true;
		return "schuelerVonKlasseAnzeigen.xhtml?faces-redirect=true";
	}

	@SuppressWarnings("unchecked")
	public List<Schueler> getGefaehrdeteSchuelerByKlasse(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerKlasse.class);
		criteria.setProjection(Projections.property("schueler"));
		criteria.add(Restrictions.eq("id.klasseId", klasseId));
		criteria.add(Restrictions.eq("istVersetzungsgefaehrdet", true));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Schueler> schuelerListe = criteria.list();

		session.getTransaction().commit();
		return schuelerListe;
	}

	@SuppressWarnings("unchecked")
	public List<Schueler> getSchuelerByKlasse(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerKlasse.class);
		criteria.setProjection(Projections.property("schueler"));
		criteria.add(Restrictions.eq("id.klasseId", klasseId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Schueler> schuelerListe = criteria.list();

		session.getTransaction().commit();
		return schuelerListe;
	}
}
