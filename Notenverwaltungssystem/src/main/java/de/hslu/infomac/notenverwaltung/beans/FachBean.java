package de.hslu.infomac.notenverwaltung.beans;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.hslu.infomac.notenverwaltung.embeddables.SchuelerFachPK;
import de.hslu.infomac.notenverwaltung.entities.Benutzer;
import de.hslu.infomac.notenverwaltung.entities.Fach;
import de.hslu.infomac.notenverwaltung.entities.Klausur;
import de.hslu.infomac.notenverwaltung.entities.Lehrer;
import de.hslu.infomac.notenverwaltung.entities.Schueler;
import de.hslu.infomac.notenverwaltung.entities.SchuelerFach;
import de.hslu.infomac.notenverwaltung.enumerations.Rolle;
import de.hslu.infomac.notenverwaltung.helper.HibernateUtil;
import de.hslu.infomac.notenverwaltung.helper.JsfUtil;

/**
 * @author Igor Eslengert
 *
 */
@ManagedBean(name = "fachBean")
@SessionScoped
public class FachBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////////////////////////////
	////////// Bean Attribute //////////
	///////////////////////////////////

	private Fach fach;

	private List<Fach> faecherListe;

	private SchuelerFach schuelerFach;

	private List<SchuelerFach> schuelerFachListe;

	private boolean wirdEditiert;

	private Klausur klausur;

	private List<Klausur> klausurListe;

	@ManagedProperty(value = "#{klasseBean}")
	private KlasseBean klasseBean;

	@ManagedProperty(value = "#{personBean}")
	private PersonBean personBean;

	//////////////////////////////////////
	//// Getter/Setter der Attribute ////
	////////////////////////////////////

	public Fach getFach() {
		return fach;
	}

	public void setFach(Fach fach) {
		this.fach = fach;
	}

	public List<Klausur> getKlausurListe() {
		return klausurListe;
	}

	public void setKlausurListe(List<Klausur> klausurListe) {
		this.klausurListe = klausurListe;
	}

	public Klausur getKlausur() {
		return klausur;
	}

	public void setKlausur(Klausur klausur) {
		this.klausur = klausur;
	}

	public List<Fach> getFaecherListe() {
		return faecherListe;
	}

	public void setFaecherListe(List<Fach> faecherListe) {
		this.faecherListe = faecherListe;
	}

	public SchuelerFach getSchuelerFach() {
		return schuelerFach;
	}

	public void setSchuelerFach(SchuelerFach schuelerFach) {
		this.schuelerFach = schuelerFach;
	}

	public List<SchuelerFach> getSchuelerFachListe() {
		return schuelerFachListe;
	}

	public void setSchuelerFachListe(List<SchuelerFach> schuelerFachListe) {
		this.schuelerFachListe = schuelerFachListe;
	}

	public boolean isWirdEditiert() {
		return wirdEditiert;
	}

	public void setWirdEditiert(boolean wirdEditiert) {
		this.wirdEditiert = wirdEditiert;
	}

	public PersonBean getPersonBean() {
		return personBean;
	}

	public void setPersonBean(PersonBean personBean) {
		this.personBean = personBean;
	}

	public KlasseBean getKlasseBean() {
		return klasseBean;
	}

	public void setKlasseBean(KlasseBean klasseBean) {
		this.klasseBean = klasseBean;
	}

	///////////////////////////////////
	//////// CRUD Operationen ////////
	/////////////////////////////////

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

	public Fach getFachById(long fachId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Fach fach = (Fach) session.get(Fach.class, fachId);

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return fach;
	}

	@SuppressWarnings("unchecked")
	public List<Fach> getFaecherByKlasse(long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Fach.class);
		criteria.add(Restrictions.eq("klasse.klasseId", klasseId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Fach> faecher = criteria.list();

		session.getTransaction().commit();
		return faecher;
	}

	@SuppressWarnings("unchecked")
	public List<Fach> getFaecherByLehrer(long lehrerId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Fach.class);
		criteria.add(Restrictions.eq("lehrer.personId", lehrerId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Fach> faecher = criteria.list();
		session.getTransaction().commit();
		return faecher;
	}

	@SuppressWarnings("unchecked")
	public List<Fach> getFaecherByLehrerAndKlasse(long lehrerId, long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Fach.class);
		criteria.add(Restrictions.eq("lehrer.personId", lehrerId));
		criteria.add(Restrictions.eq("klasse.klasseId", klasseId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Fach> faecher = criteria.list();

		session.getTransaction().commit();
		return faecher;
	}

	public Fach getFachByKlasseAndName(long klasseId, String name) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Fach.class);
		criteria.add(Restrictions.eq("klasse.klasseId", klasseId));
		criteria.add(Restrictions.eq("name", name));

		Fach fach = (Fach) criteria.uniqueResult();

		session.getTransaction().commit();
		return fach;
	}

	public void createFach() {
		Fach nameKlasseUnique = getFachByKlasseAndName(fach.getKlasse().getKlasseId(), fach.getName());

		if (nameKlasseUnique == null) {
			create(fach);
			List<Schueler> schuelerListe = personBean.getSchuelerByKlasse(fach.getKlasse().getKlasseId());
			// Schüler von der Klasse dem Fach zuordnen
			assignSchuelerToFach(schuelerListe, fach);
			fach = new Fach();
			fach.setKlasse(klasseBean.getKlasse());
			JsfUtil.addSuccessMessage("createEditFach:create", "Das Fach wurde erfolgreich hinzugefügt.");
		} else if (nameKlasseUnique != null) {
			JsfUtil.addErrorMessage("createEditFach:name",
					"Die Klasse hat bereits ein Fach mit dem Namen " + fach.getName() + " .");
		}
	}

	public void editFach() {
		edit(fach);
		JsfUtil.addSuccessMessage("createEditFach:edit", "Das Fach wurde erfolgreich aktualisiert.");
	}

	public void deleteFach() {
		long fachId = fach.getFachId();
		faecherListe.remove(fach);
		delete(fach);
		JsfUtil.addSuccessMessage(null, "Das Fach mit der Id " + fachId + " wurde erfolgreich gelöscht.");
	}

	public String prepareCreateFach() {
		fach = new Fach();
		fach.setKlasse(klasseBean.getKlasse());
		wirdEditiert = false;
		personBean.setLehrerListe(personBean.getAllLehrer());
		return "createEditFach.xhtml?faces-redirect=true";
	}

	public String prepareListFaecher(long klasseId) {
		this.faecherListe = getFaecherByKlasse(klasseId);
		klasseBean.setKlasse(klasseBean.getKlasseById(klasseId));
		return "faecherAnzeigen.xhtml?faces-redirect=true";
	}

	public String prepareListFaecherVonLehrer(long lehrerId) {
		this.faecherListe = getFaecherByLehrer(lehrerId);
		return "meineFaecher.xhtml?faces-redirect=true";
	}

	public void assignSchuelerToFach(List<Schueler> schuelerListe, Fach fach) {
		for (Schueler schueler : schuelerListe) {
			SchuelerFach schuelerFach = new SchuelerFach();
			schuelerFach.setSchueler(schueler);
			schuelerFach.setFach(fach);
			schuelerFach.setId(new SchuelerFachPK(schueler.getPersonId(), fach.getFachId()));
			create(schuelerFach);
		}
	}

	public String prepareEditFach(long fachId) {
		fach = getFachById(fachId);
		wirdEditiert = true;
		personBean.setLehrerListe(personBean.getAllLehrer());
		return "createEditFach.xhtml?faces-redirect=true";
	}

	public void deleteSchuelerVomFach(List<Schueler> schuelerListe, Fach fach) {
		for (Schueler schueler : schuelerListe) {
			SchuelerFach schuelerFach = getSchuelerFachBySchuelerAndFach(schueler.getPersonId(), fach.getFachId());
			delete(schuelerFach);
		}
	}

	@SuppressWarnings("unchecked")
	public List<SchuelerFach> getSchuelerFaecherByFach(long fachId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerFach.class);
		criteria.add(Restrictions.eq("fach.fachId", fachId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SchuelerFach> schuelerFaecherListe = criteria.list();

		session.getTransaction().commit();
		return schuelerFaecherListe;
	}

	public SchuelerFach getSchuelerFachBySchuelerAndFach(long schuelerId, long fachId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		SchuelerFach schuelerFach = (SchuelerFach) session.get(SchuelerFach.class,
				new SchuelerFachPK(schuelerId, fachId));

		session.getTransaction().commit();

		// HibernateUtil.getSessionFactory().close();

		return schuelerFach;
	}

	public String prepareCreateKlausur(long lehrerId, long klasseId) {
		faecherListe = getFaecherByLehrerAndKlasse(lehrerId, klasseId);
		klausurListe = new ArrayList<Klausur>();
		return "klausurErfassen.xhtml?faces-redirect=true";
	}

	public void prepareKlausurFormular() {
		klausurListe = new ArrayList<Klausur>();
		List<SchuelerFach> schuelerFacherListe = getSchuelerFaecherByFach(fach.getFachId());
		if (schuelerFacherListe != null && !schuelerFacherListe.isEmpty()) {
			for (SchuelerFach schuelerFach : schuelerFacherListe) {
				Klausur klausur = new Klausur();
				klausur.setSchuelerFach(schuelerFach);
				klausurListe.add(klausur);
			}
		} else {
			JsfUtil.addErrorMessage(null, "Keine Schüler für das Fach vorhanden.");
		}
	}

	@SuppressWarnings("unchecked")
	public List<Klausur> getKlausurenBySchuelerAndFach(long schuelerId, long fachId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(Klausur.class);
		Criteria schuelerFachCriteria = criteria.createCriteria("schuelerFach");
		schuelerFachCriteria.add(Restrictions.eq("id.fachId", fachId));
		schuelerFachCriteria.add(Restrictions.eq("id.schuelerId", schuelerId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Klausur> klausurenListe = criteria.list();

		session.getTransaction().commit();
		return klausurenListe;
	}

	@SuppressWarnings("unchecked")
	public List<SchuelerFach> getSchuelerFaecherBySchuelerAndKlasse(long schuelerId, long klasseId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerFach.class);
		criteria.add(Restrictions.eq("id.schuelerId", schuelerId));
		Criteria klasseCriteria = criteria.createCriteria("fach");
		klasseCriteria.add(Restrictions.eq("klasse.klasseId", klasseId));

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SchuelerFach> schuelerFachListe = criteria.list();

		session.getTransaction().commit();
		return schuelerFachListe;
	}

	@SuppressWarnings("unchecked")
	public List<SchuelerFach> getSchuelerFaecherBySchuelerAndKlasseAndLehrer(long schuelerId, long klasseId,
			long lehrerId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria criteria = session.createCriteria(SchuelerFach.class);
		criteria.add(Restrictions.eq("id.schuelerId", schuelerId));
		Criteria klasseCriteria = criteria.createCriteria("fach");
		klasseCriteria.add(Restrictions.eq("klasse.klasseId", klasseId));
		klasseCriteria.add(Restrictions.eq("lehrer.personId", lehrerId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<SchuelerFach> schuelerFachListe = criteria.list();

		session.getTransaction().commit();
		return schuelerFachListe;
	}

	public void fachNotendurchschnittBerechnenForSchuelerAndFach(long schuelerId, long fachId) {
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.DOWN);
		List<Klausur> klausurenListe = getKlausurenBySchuelerAndFach(schuelerId, fachId);
		double notenSummiert = 0;
		double anzahlNoten = 0;
		for (Klausur klausur : klausurenListe) {
			double note = klausur.getNote();
			double gewichtung = klausur.getGewichtung();
			//System.out.println(note * gewichtung);
			notenSummiert = notenSummiert + note * gewichtung;
			anzahlNoten = anzahlNoten + gewichtung;
		}
		SchuelerFach schuelerFach = getSchuelerFachBySchuelerAndFach(schuelerId, fachId);
		double fachNotendurchschnitt = schuelerFach.getFachNotendurchschnitt();
		try {
			fachNotendurchschnitt = df.parse(df.format(notenSummiert / anzahlNoten)).doubleValue();
		} catch (ParseException e) {
			JsfUtil.addErrorMessage(null, e.getMessage());
		}

		schuelerFach.setFachNotendurchschnitt(fachNotendurchschnitt);
		edit(schuelerFach);
	}

	public void createKlausur() {
		for (Klausur klausur : klausurListe) {
			create(klausur);
			SchuelerFachPK schuelerFachPK = klausur.getSchuelerFach().getId();
			fachNotendurchschnittBerechnenForSchuelerAndFach(schuelerFachPK.getSchuelerId(),
					schuelerFachPK.getFachId());
		}
		JsfUtil.addSuccessMessage("klausurErfassenForm:erfassen", "Die Klausur wurde erfolgreich erfasst.");
		klausurListe = new ArrayList<Klausur>();
	}

	public String schuelerAusKlausurListeEntfernen(long schuelerId) {
		int position = 0;
		int i = 0;
		for (Klausur klausur : klausurListe) {
			if (klausur.getSchuelerFach().getSchueler().getPersonId() == schuelerId) {
				position = i;
			} else {
				klausur.setNote(null);
				klausur.setGewichtung(1.0);
				klausur.setPunkte(null);
				klausur.setMaxPunkte(null);
			}
			i++;
		}
		klausurListe.remove(position);
		JsfUtil.addSuccessMessage(null, "Schüler wurde aus der Klausur entfern.");
		return "klausurErfassen.xhtml?faces-redirect=true";
	}

	public void notenLaden() {
		schuelerFachListe = null;
		schuelerFachListe = getSchuelerFaecherBySchuelerAndKlasse(
				klasseBean.getSchuelerKlasse().getId().getSchuelerId(),
				klasseBean.getSchuelerKlasse().getId().getKlasseId());
		if (schuelerFachListe != null && !schuelerFachListe.isEmpty()) {
			for (SchuelerFach schuelerFach : schuelerFachListe) {
				List<Klausur> klausurListe = getKlausurenBySchuelerAndFach(schuelerFach.getId().getSchuelerId(),
						schuelerFach.getId().getFachId());
				schuelerFach.setKlausurenListe(new HashSet<Klausur>(klausurListe));
			}
		} else {
			JsfUtil.addErrorMessage(null, "Keine Fächer in der Klasse vorhanden.");
		}
	}

	public void notenLadenAlsLehrer(long lehrerId) {
		schuelerFachListe = null;
		Lehrer klassenLehrer = klasseBean.getKlasse().getKlassenLehrer();
		boolean istKlassenLehrer = false;
		if (klassenLehrer != null &&klassenLehrer.getPersonId()!= null) {
			istKlassenLehrer = (klassenLehrer.getPersonId() == lehrerId);
		}
		boolean istSchulleiter = ((Benutzer) personBean.getPersonById(lehrerId)).hasRolle(Rolle.Schulleiter);
		if (istKlassenLehrer || istSchulleiter) {
			schuelerFachListe = getSchuelerFaecherBySchuelerAndKlasse(
					klasseBean.getSchuelerKlasse().getId().getSchuelerId(),
					klasseBean.getSchuelerKlasse().getId().getKlasseId());
		} else {
			schuelerFachListe = getSchuelerFaecherBySchuelerAndKlasseAndLehrer(
					klasseBean.getSchuelerKlasse().getId().getSchuelerId(),
					klasseBean.getSchuelerKlasse().getId().getKlasseId(), lehrerId);
		}
		if (schuelerFachListe != null && !schuelerFachListe.isEmpty()) {
			for (SchuelerFach schuelerFach : schuelerFachListe) {
				List<Klausur> klausurListe = getKlausurenBySchuelerAndFach(schuelerFach.getId().getSchuelerId(),
						schuelerFach.getId().getFachId());
				schuelerFach.setKlausurenListe(new HashSet<Klausur>(klausurListe));
			}
		} else {
			if (istKlassenLehrer) {
				JsfUtil.addErrorMessage(null, "Keine Fächer in der Klasse vorhanden.");
			} else {
				JsfUtil.addErrorMessage(null,
						"Keine Fächer in der Klasse vorhanden oder Sie haben keine Fächer in der ausgewählten Klasse.");
			}
		}
	}

	public String prepareShowNotenAlsSchueler(long schuelerId) {
		prepareShowNoten(schuelerId);
		return "notenansichtAlsSchueler.xhtml?faces-redirect=true";
	}

	public String prepareShowNotenVonSchueler(long schuelerId) {
		prepareShowNoten(schuelerId);
		return "notenansichtVonSchueler.xhtml?faces-redirect=true";
	}

	private void prepareShowNoten(long schuelerId) {
		klasseBean.setSchuelerKlassenListe(klasseBean.getSchuelerKlasseBySchueler(schuelerId));
		klasseBean.setSchuelerKlasse(null);
		schuelerFachListe = null;
	}

	public void prepareShowKlausuren(SchuelerFach schuelerFach) {
		klausurListe = new ArrayList<Klausur>();
		klausurListe.addAll(schuelerFach.getKlausurenListe());
	}
}
