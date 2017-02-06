package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "Konferenz")
@Table(name = "konferenz")
public class Konferenz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "konferenz_sequence", strategy = "increment")
	@GeneratedValue(generator = "konferenz_sequence")
	@Column(name = "konferenz_id")
	private Long konferenzId;

	@Column(name = "datum", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date datum;

	@Column(name = "raum", length = 50, nullable = false)
	private String raum;

	@Column(name = "von_uhrzeit", length = 20, nullable = false)
	private String vonUhrzeit;

	@Column(name = "bis_uhrzeit", length = 20, nullable = false)
	private String bisUhrzeit;

	@Column(name = "nachricht", nullable = false)
	private String nachricht;

	@ManyToOne
	@JoinColumn(name = "klasse_id", nullable = false)
	private Klasse klasse;

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public String getRaum() {
		return raum;
	}

	public void setRaum(String raum) {
		this.raum = raum;
	}

	public String getVonUhrzeit() {
		return vonUhrzeit;
	}

	public void setVonUhrzeit(String vonUhrzeit) {
		this.vonUhrzeit = vonUhrzeit;
	}

	public String getBisUhrzeit() {
		return bisUhrzeit;
	}

	public void setBisUhrzeit(String bisUhrzeit) {
		this.bisUhrzeit = bisUhrzeit;
	}

	public String getNachricht() {
		return nachricht;
	}

	public void setNachricht(String nachricht) {
		this.nachricht = nachricht;
	}

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public Long getKonferenzId() {
		return konferenzId;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getKonferenzId();
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
		final Konferenz other = (Konferenz) obj;
		if (getKonferenzId() != other.getKonferenzId()) {
			return false;
		}
		return true;
	}

}
