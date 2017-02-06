package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "Klausur")
@Table(name = "klausur")
// @SequenceGenerator(name = "klausur_sequence", sequenceName =
// "klausur_id_seq", allocationSize = 1)
public class Klausur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "klausur_sequence", strategy = "increment")
	@GeneratedValue(generator = "klausur_sequence")
	@Column(name = "klausur_id")
	private Long klausurId;

	@Column(name = "note", nullable = false)
	private Double note;

	@Column(name = "art_leistung", length = 150)
	private String artLeistung;

	@Column(name = "punkte")
	private Double punkte;

	@Column(name = "max_punkte")
	private Double maxPunkte;

	@Column(name = "bemerkung")
	private String bemerkung;

	@Column(name = "gewichtung", nullable = false)
	private Double gewichtung = 1.0;

	// @ManyToOne
	// @JoinColumn(name = "fach_id", nullable = false)
	// private Fach fach;
	//
	// @ManyToOne
	// @JoinColumn(name = "schueler_id", nullable = false)
	// private Schueler schueler;

	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "fach_id", referencedColumnName = "fach_id", nullable = false),
			@JoinColumn(name = "schueler_id", referencedColumnName = "schueler_id", nullable = false) })
	private SchuelerFach schuelerFach;

	public Long getKlausurId() {
		return klausurId;
	}

	public void setKlausurId(Long klausurId) {
		this.klausurId = klausurId;
	}

	public Double getNote() {
		return note;
	}

	public void setNote(Double note) {
		this.note = note;
	}

	public String getArtLeistung() {
		return artLeistung;
	}

	public void setArtLeistung(String artLeistung) {
		this.artLeistung = artLeistung;
	}

	public Double getPunkte() {
		return punkte;
	}

	public void setPunkte(Double punkte) {
		this.punkte = punkte;
	}

	public Double getMaxPunkte() {
		return maxPunkte;
	}

	public void setMaxPunkte(Double maxPunkte) {
		this.maxPunkte = maxPunkte;
	}

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public Double getGewichtung() {
		return gewichtung;
	}

	public void setGewichtung(Double gewichtung) {
		this.gewichtung = gewichtung;
	}

	public SchuelerFach getSchuelerFach() {
		return schuelerFach;
	}

	public void setSchuelerFach(SchuelerFach schuelerFach) {
		this.schuelerFach = schuelerFach;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + (getKlausurId() == null ? 0 : getKlausurId());
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
		final Klausur other = (Klausur) obj;
		if (getKlausurId() != other.getKlausurId()) {
			return false;
		}
		return true;
	}

}
