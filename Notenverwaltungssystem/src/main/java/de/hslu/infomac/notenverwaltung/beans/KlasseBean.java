package de.hslu.infomac.notenverwaltung.beans;

import java.io.Serializable;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DualListModel;

import de.hslu.infomac.notenverwaltung.embeddables.SchuelerKlassePK;
import de.hslu.infomac.notenverwaltung.entities.Fach;
import de.hslu.infomac.notenverwaltung.entities.Klasse;
import de.hslu.infomac.notenverwaltung.entities.Konferenz;
import de.hslu.infomac.notenverwaltung.entities.Lehrer;
import de.hslu.infomac.notenverwaltung.entities.Schueler;
import de.hslu.infomac.notenverwaltung.entities.SchuelerFach;
import de.hslu.infomac.notenverwaltung.entities.SchuelerKlasse;
import de.hslu.infomac.notenverwaltung.enumerations.Status;
import de.hslu.infomac.notenverwaltung.helper.HibernateUtil;
import de.hslu.infomac.notenverwaltung.helper.JsfUtil;

/**
 * @author Igor Eslengert
 */
@ManagedBean(name = "klasseBean")
@SessionScoped
public class KlasseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////////////////////////////
	////////// Bean Attribute //////////
	///////////////////////////////////

	private Klasse klasse;

	private List<Klasse> klassenListe;

	private SchuelerKlasse schuelerKlasse;

	private List<SchuelerKlasse> schuelerKlassenListe;

	private Konferenz konferenz = new Konferenz();

	private List<Konferenz> konferenzenListe;

	private boolean wirdEditiert;

	private DualListModel<Schueler> dualSchuelerListe;

	@ManagedProperty(value = "#{personBean}")
	private PersonBean personBean;

	@ManagedProperty(value = "#{mailBean}")
	private MailBean mailBean;

	//////////////////////////////////////
	//// Getter/Setter der Attribute ////
	////////////////////////////////////

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public List<Klasse> getKlassenListe() {
		return klassenListe;
	}

	public void setKlassenListe(List<Klasse> klassenListe) {
		this.klassenListe = klassenListe;
	}

	public SchuelerKlasse getSchuelerKlasse() {
		return schuelerKlasse;
	}

	public MailBean getMailBean() {
		return mailBean;
	}

	public void setMailBean(MailBean mailBean) {
		this.mailBean = mailBean;
	}

	public void setSchuelerKlasse(SchuelerKlasse schuelerKlasse) {
		this.schuelerKlasse = schuelerKlasse;
	}

	public List<SchuelerKlasse> getSchuelerKlassenListe() {
		return schuelerKlassenListe;
	}

	public void setSchuelerKlassenListe(List<SchuelerKlasse> schuelerKlassenListe) {
		this.schuelerKlassenListe = schuelerKlassenListe;
	}

	public Konferenz getKonferenz() {
		return konferenz;
	}

	public void setKonferenz(Konferenz konferenz) {
		this.konferenz = konferenz;
	}

	public List<Konferenz> getKonferenzenListe() {
		return konferenzenListe;
	}

	public void setKonferenzenListe(List<Konferenz> konferenzenListe) {
		this.konferenzenListe = konferenzenListe;
	}

	public PersonBean getPersonBean() {
		return personBean;
	}

	public DualListModel<Schueler> getDualSchuelerListe() {
		return dualSchuelerListe;
	}

	public void setDualSchuelerListe(DualListModel<Schueler> dualSchuelerListe) {
		this.dualSchuelerListe = dualSchuelerListe;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}

	public boolean isWirdEditiert() {
		return wirdEditiert;
	}

	public void setWirdEditiert(boolean wirdEditiert) {
		this.wirdEditiert = wirdEditiert;
	}

	///////////////////////////////////
	//////// CRUD Operationen ////////
	/////////////////////////////////

	public Klasse getKlasseById(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Klasse klasse = (Klasse) session.get(Klasse.class, klasseId);

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return klasse;

	}

	@SuppressWarnings("unchecked")
	public List<Klasse> getKlassenByLehrer(long lehrerId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Klasse.class);
		criteria.add(Restrictions.eq("klassenLehrer.personId", lehrerId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Klasse> klassen = criteria.list();

		session.getTransaction().commit();
		return klassen;
	}

	@SuppressWarnings("unchecked")
	public List<Klasse> getAllKlassen() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Klasse.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Klasse> klassenListe = criteria.list();

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();
		return klassenListe;

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

	public Klasse getKlasseByLehrerAndJahr(long lehrerId, int jahr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Klasse.class);
		criteria.add(Restrictions.eq("klassenLehrer.personId", lehrerId));
		criteria.add(Restrictions.eq("jahr", jahr));

		Klasse klasse = (Klasse) criteria.uniqueResult();

		session.getTransaction().commit();
		return klasse;
	}

	public Klasse getKlasseByJahrgangsstufeAndJahr(String jahrgangsstufe, int jahr) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Klasse.class);
		criteria.add(Restrictions.eq("jahrgangsstufe", jahrgangsstufe));
		criteria.add(Restrictions.eq("jahr", jahr));

		Klasse klasse = (Klasse) criteria.uniqueResult();

		session.getTransaction().commit();
		return klasse;
	}

	public void createKlasse() {
		int nowYear = Calendar.getInstance().get(Calendar.YEAR);
		int futureYear = nowYear+20;
		if (klasse.getJahr() >= nowYear -1 && klasse.getJahr() <= futureYear) {
			Lehrer klassenLehrer = klasse.getKlassenLehrer();
			Klasse jahrLehrerUnique = null;
			if (klassenLehrer != null) {
				jahrLehrerUnique = getKlasseByLehrerAndJahr(klassenLehrer.getPersonId(), klasse.getJahr());
			}
			Klasse jahrJahrgangstufeUnique = getKlasseByJahrgangsstufeAndJahr(klasse.getJahrgangsstufe(),
					klasse.getJahr());

			if (jahrLehrerUnique == null && jahrJahrgangstufeUnique == null) {
				create(klasse);

				JsfUtil.addSuccessMessage("createEditKlasse:create", "Die Klasse wurde erfolgreich angelegt.");
				personBean.getLehrerListe().remove(klasse.getKlassenLehrer());
				klasse = new Klasse();
			} else if (jahrLehrerUnique != null) {
				JsfUtil.addErrorMessage("createEditKlasse:jahr",
						"Der ausgewählte Lehrer hat bereits eine Klasse im Jahr " + klasse.getJahr() + " .");
				JsfUtil.addErrorMessage("createEditKlasse:klassenLehrer",
						"Der ausgewählte Lehrer hat bereits eine Klasse im Jahr " + klasse.getJahr() + " .");
			} else if (jahrJahrgangstufeUnique != null) {
				JsfUtil.addErrorMessage("createEditKlasse:jahr", "Die Jahrgangsstufe " + klasse.getJahrgangsstufe()
						+ " ist schon im Jahr " + klasse.getJahr() + " vorhanden.");
				JsfUtil.addErrorMessage("createEditKlasse:jahrgangsstufe", "Die Jahrgangsstufe "
						+ klasse.getJahrgangsstufe() + " ist schon im Jahr " + klasse.getJahr() + " vorhanden.");
			}
		} else if (klasse.getJahr() < nowYear) {
			JsfUtil.addErrorMessage("createEditKlasse:jahr",
					"Das Jahr der Klasse darf nicht zu weit in der Vergangenheit liegen.");
		} else if (klasse.getJahr() > futureYear) {
			JsfUtil.addErrorMessage("createEditKlasse:jahr",
					"Das Jahr der Klasse darf nicht zu weit in der Zukuft liegen.");
		}
	}

	public void editKlasse() {
		Lehrer neuerKlassenLehrer = klasse.getKlassenLehrer();
		Lehrer alterKlassenLehrer = getKlasseById(klasse.getKlasseId()).getKlassenLehrer();
		Klasse jahrLehrerUnique = null;
		if (neuerKlassenLehrer != null
				&& (alterKlassenLehrer == null || !alterKlassenLehrer.equals(neuerKlassenLehrer))) {
			jahrLehrerUnique = getKlasseByLehrerAndJahr(neuerKlassenLehrer.getPersonId(), klasse.getJahr());
		}

		if (jahrLehrerUnique == null) {
			edit(klasse);
			JsfUtil.addSuccessMessage("createEditKlasse:edit", "Die Klasse wurde erfolgreich aktualisiert.");
		} else if (jahrLehrerUnique != null) {
			JsfUtil.addErrorMessage("createEditKlasse:jahr",
					"Der ausgewählte Lehrer hat bereits eine Klasse im Jahr " + klasse.getJahr() + " .");
			JsfUtil.addErrorMessage("createEditKlasse:klassenLehrer",
					"Der ausgewählte Lehrer hat bereits eine Klasse im Jahr " + klasse.getJahr() + " .");
		}
	}

	public void deleteKlasse() {
		long klassenId = klasse.getKlasseId();
		klassenListe.remove(klasse);
		delete(klasse);
		JsfUtil.addSuccessMessage(null, "Die Klasse mit der Id " + klassenId + " wurde erfolgreich gelöscht.");
		// return prepareList();
	}

	public String prepareEdit(long klasseId) {
		klasse = getKlasseById(klasseId);
		wirdEditiert = true;
		List<Lehrer> lehrerListe = personBean.getNewLehrerAndLehrerWithoutKlasse();
		if (klasse.getKlassenLehrer() != null) {
			lehrerListe.add(klasse.getKlassenLehrer());
		}
		personBean.setLehrerListe(lehrerListe);
		return "createEditKlasse.xhtml?faces-redirect=true";
	}

	public String prepareCreate() {
		klasse = new Klasse();
		wirdEditiert = false;
		personBean.setLehrerListe(personBean.getNewLehrerAndLehrerWithoutKlasse());
		return "createEditKlasse.xhtml?faces-redirect=true";
	}

	public String prepareShow(long klasseId) {
		klasse = getKlasseById(klasseId);
		konferenzenListe = getKonferenzenByKlasse(klasseId);
		return "klasseAnzeigen.xhtml?faces-redirect=true";
	}

	public String prepareListKlassenVonLehrer(long lehrerId) {
		this.klassenListe = getKlassenByLehrer(lehrerId);
		return "meineKlassenAnzeigen.xhtml?faces-redirect=true";
	}

	public String prepareList() {
		this.klassenListe = getAllKlassen();
		return "klassenAnzeigen.xhtml?faces-redirect=true";
	}

	@SuppressWarnings("unchecked")
	public List<Konferenz> getKonferenzenByKlasse(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Konferenz.class);
		criteria.add(Restrictions.eq("klasse.klasseId", klasseId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Konferenz> konferenz = criteria.list();

		session.getTransaction().commit();
		return konferenz;
	}

	@SuppressWarnings("unchecked")
	public List<SchuelerKlasse> getSchuelerKlasseByKlasse(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerKlasse.class);
		criteria.add(Restrictions.eq("id.klasseId", klasseId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SchuelerKlasse> schuelerKlasse = criteria.list();

		session.getTransaction().commit();
		return schuelerKlasse;
	}

	public long getKlassenGroesseByKlasse() {
		if (klasse != null && klasse.getKlasseId() != null) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(SchuelerKlasse.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("id.klasseId", klasse.getKlasseId()));
			long klassenGroesse = (long) criteria.uniqueResult();

			session.getTransaction().commit();
			return klassenGroesse;
		} else {
			return 0;
		}
	}

	public long getAzahlGefaehrdeteSchuelerByKlasse() {
		if (klasse != null && klasse.getKlasseId() != null) {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(SchuelerKlasse.class);
			criteria.setProjection(Projections.rowCount());
			criteria.add(Restrictions.eq("id.klasseId", klasse.getKlasseId()));
			criteria.add(Restrictions.eq("istVersetzungsgefaehrdet", true));
			long azahlGefaehrdeteSchueler = (long) criteria.uniqueResult();
			session.getTransaction().commit();
			return azahlGefaehrdeteSchueler;
		} else {
			return 0;
		}
	}

	public String prepareAssignSchuelerToKlasse(long klasseId) {
		klasse = getKlasseById(klasseId);
		dualSchuelerListe = new DualListModel<Schueler>(personBean.getNewSchuelerAndSchuelerWithoutKlasse(),
				personBean.getSchuelerByKlasse(klasse.getKlasseId()));
		return "schuelerKlasseVerwalten.xhtml?faces-redirect=true";
	}

	public SchuelerKlasse getSchuelerKlasseBySchuelerAndKlasse(long schuelerId, long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		SchuelerKlasse schuelerKlasse = (SchuelerKlasse) session.get(SchuelerKlasse.class,
				new SchuelerKlassePK(schuelerId, klasseId));

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return schuelerKlasse;
	}

	public void assignSchuelerToKlasse() {
		List<Schueler> schuelerSollenInKlasse = new ArrayList<Schueler>();
		schuelerSollenInKlasse.addAll(dualSchuelerListe.getTarget());
		if (schuelerSollenInKlasse.size() <= klasse.getMaxKlassenGroesse()) {

			List<Schueler> schuelerZurKlasseZuordnen = new ArrayList<Schueler>();
			List<Schueler> schuelerVonKlasseLöschen = new ArrayList<Schueler>();

			List<Schueler> schuelerInKlasse = personBean.getSchuelerByKlasse(klasse.getKlasseId());

			// Neue Schüler in der schuelerSollenInKlasse lassen, alte jedoch
			// löschen und nicht vorhandene in schuelerInKlasse in die Liste
			// schuelerVonKlasseLöschen packen
			for (Schueler schueler : schuelerInKlasse) {
				if (schuelerSollenInKlasse.contains(schueler)) {
					schuelerSollenInKlasse.remove(schueler);
				} else {
					schuelerVonKlasseLöschen.add(schueler);
				}
			}
			schuelerZurKlasseZuordnen.addAll(schuelerSollenInKlasse);

			createSchuelerKlasse(schuelerZurKlasseZuordnen);

			deleteAssignedSchuelerKlasse(schuelerVonKlasseLöschen);

			// Neue schüler den Fächern zuordnen und entfernte Schüler aus den
			// Fächern entfernen
			FachBean fachBean = new FachBean();
			List<Fach> klassenFaecher = fachBean.getFaecherByKlasse(klasse.getKlasseId());
			for (Fach fach : klassenFaecher) {
				fachBean.assignSchuelerToFach(schuelerZurKlasseZuordnen, fach);
				fachBean.deleteSchuelerVomFach(schuelerVonKlasseLöschen, fach);
			}

			JsfUtil.addSuccessMessage("schuelerZuordnung:submit", "Zuordnung wurde erfolgreich durchgeführt.");
		} else {
			JsfUtil.addErrorMessage("schuelerZuordnung:submit",
					"Es sind " + schuelerSollenInKlasse.size() + " Schüler dieser Klasse zugeordnet. Diese Klasse "
							+ klasse.getKlasseId() + ": " + klasse.getJahrgangsstufe() + " darf maximal "
							+ klasse.getMaxKlassenGroesse() + " Schüler aufnehmen. ");
		}

	}

	private void createSchuelerKlasse(List<Schueler> schuelerZurKlasseZuordnen) {

		for (Schueler schueler : schuelerZurKlasseZuordnen) {
			SchuelerKlasse schuelerKlasse = new SchuelerKlasse();
			schuelerKlasse.setSchueler(schueler);
			schuelerKlasse.setKlasse(klasse);
			schuelerKlasse.setId(new SchuelerKlassePK(schueler.getPersonId(), klasse.getKlasseId()));
			schuelerKlasse.setStatus(Status.Eingeschrieben);

			create(schuelerKlasse);

			Date date = new Date();
			schueler.setLetzteEinschreibung(new Timestamp(date.getTime()));
			edit(schueler);
		}

	}

	private void deleteAssignedSchuelerKlasse(List<Schueler> schuelerVonKlasseLöschen) {
		for (Schueler schueler : schuelerVonKlasseLöschen) {
			SchuelerKlasse schuelerKlasse = getSchuelerKlasseBySchuelerAndKlasse(schueler.getPersonId(),
					klasse.getKlasseId());
			delete(schuelerKlasse);
		}
	}

	// TODO: Mail an Schueler entkommentieren
	public void klassenkonferenzEinberufen() {

		Date date = new Date();
		if (konferenz.getDatum().after(date)) {
			konferenz.setKlasse(klasse);
			create(konferenz);
			konferenzDatenAnSchuelerUndSBSenden(personBean.getSchuelerByKlasse(klasse.getKlasseId()));
			JsfUtil.addSuccessMessage("klassenkonferenzForm:confirm", "Klassenkonferenz wurde einberufen.");
			konferenzenListe.add(konferenz);
			konferenz = new Konferenz();
		} else {
			JsfUtil.addErrorMessage("klassenkonferenzForm:datum",
					"Das Datum der Konferenz darf nicht in der Vergangenheit sein.");
		}
	}

	private void konferenzDatenAnSchuelerUndSBSenden(List<Schueler> schuelerListe) {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		List<String> empfaenger = new ArrayList<String>();
		Lehrer klassenLehrer = klasse.getKlassenLehrer();
		for (Schueler schueler : schuelerListe) {
			empfaenger.add(schueler.getEmail());
			empfaenger.add(schueler.getSorgeberechtigter().getEmail());
		}

		String inhalt = konferenz.getNachricht() + "<br/><br/>Konferenzinfos:<br/>Datum: "
				+ df.format(konferenz.getDatum()) + "<br/>Von: " + konferenz.getVonUhrzeit() + "<br/>Bis: "
				+ konferenz.getBisUhrzeit() + "<br/>Raum: " + konferenz.getRaum();
		String betreff = "Einladung zur Klassenkonferenz";

		mailBean.sendMail(betreff, inhalt, empfaenger, null, null, null, klassenLehrer.getEmail(),
				mailBean.empaengerFormatieren(klassenLehrer.getTitel(), klassenLehrer.getVorname(),
						klassenLehrer.getNachname()));
	}

	// TODO: Sende Mail entkommentieren
	public void klasseAbschliessen() {
		List<SchuelerKlasse> schuelerKlasseListe = getSchuelerKlasseByKlasse(klasse.getKlasseId());
		if (schuelerKlasseListe != null && !schuelerKlasseListe.isEmpty()) {
			for (SchuelerKlasse schuelerKlasse : schuelerKlasseListe) {
				boolean hatKlasseBestanden = versetzungsbedingungenPrüfenForSchuelerByKlass(
						schuelerKlasse.getId().getSchuelerId(), schuelerKlasse.getId().getKlasseId());

				double notendurchschnitt = schuelerNotendurchschnittBerechnen(schuelerKlasse.getId().getSchuelerId(),
						schuelerKlasse.getId().getKlasseId());

				if (notendurchschnitt == 0.0) {
					hatKlasseBestanden = false;
				}

				schuelerKlasse.setIstVersetzungsgefaehrdet(!hatKlasseBestanden);
				schuelerKlasse.setNotendurchschnitt(notendurchschnitt);
				String bemerkung = null;
				if (hatKlasseBestanden) {
					schuelerKlasse.setStatus(Status.Bestanden);
				} else {
					bemerkung = "Sie haben leider die Klasse " + klasse.getJahrgangsstufe() + " im Jahr "
							+ klasse.getJahr() + " nicht bestanden.";

					mailBean.sendMail(
							"Die Klasse " + klasse.getJahrgangsstufe() + " im Jahr " + klasse.getJahr()
									+ " nicht bestanden.",
							bemerkung, Arrays.asList(schuelerKlasse.getSchueler().getEmail()),
							Arrays.asList(schuelerKlasse.getSchueler().getSorgeberechtigter().getEmail()),
							schuelerKlasse.getSchueler().getAnrede(),
							schuelerKlasse.getSchueler().getVorname() + " "
									+ schuelerKlasse.getSchueler().getNachname(),
							"mic311.notenverwaltungssystem@gmail.com", "Notenverwaltungssystem");

					schuelerKlasse.setStatus(Status.NichtBestanden);
				}
				schuelerKlasse.setBemerkung(bemerkung);
				edit(schuelerKlasse);
			}
			klasse.setIstAktiv(false);
			edit(klasse);
			JsfUtil.addSuccessMessage(null, "Die Klasse wurde erfolgreich abgeschlossen.");
		} else {
			JsfUtil.addErrorMessage(null, "Die Klasse hat keine Schüler und kann deshalb nicht abgeschlossen werden.");
		}
	}

	// TODO: Sende Mail entkommentieren
	public void versetzungsbedingungenPrüfen() {
		List<SchuelerKlasse> schuelerKlasseListe = getSchuelerKlasseByKlasse(klasse.getKlasseId());
		if (schuelerKlasseListe != null && !schuelerKlasseListe.isEmpty()) {
			for (SchuelerKlasse schuelerKlasse : schuelerKlasseListe) {
				boolean versetzungMoeglich = versetzungsbedingungenPrüfenForSchuelerByKlass(
						schuelerKlasse.getId().getSchuelerId(), schuelerKlasse.getId().getKlasseId());

				double notendurchschnitt = schuelerNotendurchschnittBerechnen(schuelerKlasse.getId().getSchuelerId(),
						schuelerKlasse.getId().getKlasseId());

				schuelerKlasse.setIstVersetzungsgefaehrdet(!versetzungMoeglich);
				schuelerKlasse.setNotendurchschnitt(notendurchschnitt);
				String bemerkung = null;
				if (!versetzungMoeglich) {
					bemerkung = "Sie sind laut der letzte Versetzungsprüfung versetzungsgefährdet.";

					mailBean.sendMail(
							"In Klasse " + klasse.getJahrgangsstufe() + " im Jahr " + klasse.getJahr()
									+ " versetzungsgefährdet.",
							bemerkung, Arrays.asList(schuelerKlasse.getSchueler().getEmail()),
							Arrays.asList(schuelerKlasse.getSchueler().getSorgeberechtigter().getEmail()),
							schuelerKlasse.getSchueler().getAnrede(),
							schuelerKlasse.getSchueler().getVorname() + " "
									+ schuelerKlasse.getSchueler().getNachname(),
							"mic311.notenverwaltungssystem@gmail.com", "Notenverwaltungssystem");

				}
				schuelerKlasse.setBemerkung(bemerkung);
				edit(schuelerKlasse);
			}
			JsfUtil.addSuccessMessage(null,
					"Die Versetzungsbedingungen für alle Schüler dieser Klasse wurden überprüft.");
		} else {
			JsfUtil.addErrorMessage(null, "Die Klasse hat keine Schüler und kann deshalb nicht geprüft werden.");
		}
	}

	private boolean versetzungsbedingungenPrüfenForSchuelerByKlass(long schuelerId, long klasseId) {
		FachBean fachBean = new FachBean();
		List<SchuelerFach> schuelerFachListe = fachBean.getSchuelerFaecherBySchuelerAndKlasse(schuelerId, klasseId);
		int anzahlUngenuegend = 0;
		int anzahlMangelhaft = 0;
		int anzahlBesserAlsBefriedigend = 0;
		boolean versetzungMoeglich = false;
		for (SchuelerFach schuelerFach : schuelerFachListe) {
			double fachNotendurchschnitt = schuelerFach.getFachNotendurchschnitt();
			if (fachNotendurchschnitt > 0) {
				if (fachNotendurchschnitt >= 5.5) {
					anzahlUngenuegend = anzahlUngenuegend + 1;
				} else if (fachNotendurchschnitt >= 4.5) {
					anzahlMangelhaft = anzahlMangelhaft + 1;
				} else if (fachNotendurchschnitt < 2.5) {
					anzahlBesserAlsBefriedigend = anzahlBesserAlsBefriedigend + 1;
				}
			}
		}
		if (anzahlUngenuegend == 0) {
			if (anzahlMangelhaft <= 2) {
				versetzungMoeglich = true;
			} else if ((anzahlMangelhaft - 2) <= anzahlBesserAlsBefriedigend) {
				versetzungMoeglich = true;
			}
		}
		return versetzungMoeglich;
	}

	private double schuelerNotendurchschnittBerechnen(long schuelerId, long klasseId) {
		FachBean fachBean = new FachBean();
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.DOWN);
		List<SchuelerFach> schuelerFachListe = fachBean.getSchuelerFaecherBySchuelerAndKlasse(schuelerId, klasseId);
		double notenSummiert = 0;
		double anzahlNoten = 0;
		for (SchuelerFach schuelerFach : schuelerFachListe) {
			double fachNotendurchschnitt = schuelerFach.getFachNotendurchschnitt();
			if (fachNotendurchschnitt > 0) {
				notenSummiert = notenSummiert + fachNotendurchschnitt;
				anzahlNoten = anzahlNoten + 1;
			}
		}
		double notendurchschnitt = 0;
		try {
			if (anzahlNoten > 0) {
				notendurchschnitt = df.parse(df.format(notenSummiert / anzahlNoten)).doubleValue();
			}
		} catch (ParseException e) {
			JsfUtil.addErrorMessage(null, e.getMessage());
		}
		return notendurchschnitt;
	}

	@SuppressWarnings("unchecked")
	public List<SchuelerKlasse> getSchuelerKlasseBySchueler(long schuelerId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerKlasse.class);
		criteria.add(Restrictions.eq("id.schuelerId", schuelerId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SchuelerKlasse> schuelerKlasseListe = criteria.list();

		session.getTransaction().commit();
		return schuelerKlasseListe;
	}
}
