package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;

import javax.persistence.*;

import de.hslu.infomac.notenverwaltung.embeddables.SchuelerKlassePK;
import de.hslu.infomac.notenverwaltung.enumerations.Status;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "SchuelerKlasse")
@Table(name = "schueler_klasse")
public class SchuelerKlasse implements Serializable {

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SchuelerKlassePK id;

	@ManyToOne
	@JoinColumn(name = "schueler_id", nullable = false, insertable = false, updatable = false)
	private Schueler schueler;

	@ManyToOne
	@JoinColumn(name = "klasse_id", nullable = false, insertable = false, updatable = false)
	private Klasse klasse;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "bemerkung", length = 250)
	private String bemerkung;

	@Column(name = "ist_versetzungsgefaehrdet", nullable = false)
	private Boolean istVersetzungsgefaehrdet = false;

	@Column(name = "notendurchschnitt", nullable = false)
	private Double notendurchschnitt = 0.0;

	public SchuelerKlassePK getId() {
		return id;
	}

	public void setId(SchuelerKlassePK id) {
		this.id = id;
	}

	public Schueler getSchueler() {
		return schueler;
	}

	public void setSchueler(Schueler schueler) {
		this.schueler = schueler;
	}

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public String getStatus() {
		if (status.equals(Status.NichtBestanden)) {
			return "Nicht Bestanden";
		} else if (status.equals(Status.Bestanden)) {
			return "Bestanden";
		} else {
			return "Eingeschrieben";
		}

	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public Boolean getIstVersetzungsgefaehrdet() {
		return istVersetzungsgefaehrdet;
	}

	public void setIstVersetzungsgefaehrdet(Boolean istVersetzungsgefaehrdet) {
		this.istVersetzungsgefaehrdet = istVersetzungsgefaehrdet;
	}

	public Double getNotendurchschnitt() {
		return notendurchschnitt;
	}

	public void setNotendurchschnitt(Double notendurchschnitt) {
		this.notendurchschnitt = notendurchschnitt;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getId().getKlasseId() + getId().getSchuelerId();
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
		final SchuelerKlasse other = (SchuelerKlasse) obj;
		if (getId() != other.getId()) {
			return false;
		}
		return true;
	}

}
