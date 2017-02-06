package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.hslu.infomac.notenverwaltung.embeddables.SchuelerFachPK;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "SchuelerFach")
@Table(name = "schueler_fach")
public class SchuelerFach implements Serializable {

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SchuelerFachPK id;

	@ManyToOne
	@JoinColumn(name = "schueler_id", nullable = false, insertable = false, updatable = false)
	private Schueler schueler;

	@ManyToOne
	@JoinColumn(name = "fach_id", nullable = false, insertable = false, updatable = false)
	private Fach fach;

	@Column(name = "fach_notendurchschnitt", nullable = false)
	private Double fachNotendurchschnitt = 0.0;

	@OneToMany(mappedBy = "schuelerFach", cascade = { CascadeType.ALL })
	private Set<Klausur> klausurenListe = new HashSet<Klausur>();

	public SchuelerFachPK getId() {
		return id;
	}

	public void setId(SchuelerFachPK id) {
		this.id = id;
	}

	public Schueler getSchueler() {
		return schueler;
	}

	public void setSchueler(Schueler schueler) {
		this.schueler = schueler;
	}

	public Fach getFach() {
		return fach;
	}

	public void setFach(Fach fach) {
		this.fach = fach;
	}

	public Double getFachNotendurchschnitt() {
		return fachNotendurchschnitt;
	}

	public void setFachNotendurchschnitt(Double fachNotendurchschnitt) {
		this.fachNotendurchschnitt = fachNotendurchschnitt;
	}

	public Set<Klausur> getKlausurenListe() {
		return klausurenListe;
	}

	public void setKlausurenListe(Set<Klausur> klausurenListe) {
		this.klausurenListe = klausurenListe;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getId().getFachId() + getId().getSchuelerId();
		return (int) result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SchuelerFach other = (SchuelerFach) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

}
