/**
 * 
 */
package de.hslu.infomac.notenverwaltung.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "Schueler")
@Table(name = "schueler")
// @AttributeOverrides({ @AttributeOverride(name = "person_id", column =
// @Column(name = "schueler_id")) })
@PrimaryKeyJoinColumn(name = "schueler_id")
public class Schueler extends Benutzer {
	private static final long serialVersionUID = 1L;

	@Column(name = "besonderheit")
	private String besonderheit;

	@Column(name = "letzte_einschreibung")
	private Timestamp letzteEinschreibung;

	@OneToOne(mappedBy = "schueler", cascade = { CascadeType.ALL })
	private Sorgeberechtigter sorgeberechtigter;

	@OneToMany(mappedBy = "schueler", cascade = { CascadeType.ALL })
	// @OneToMany(mappedBy = "schueler", fetch = FetchType.LAZY, cascade =
	// CascadeType.REMOVE)
	private Set<SchuelerKlasse> schuelerKlassenListe = new HashSet<SchuelerKlasse>();

	@OneToMany(mappedBy = "schueler", cascade = { CascadeType.ALL })
	// @OneToMany(mappedBy = "schueler", fetch = FetchType.LAZY, cascade =
	// CascadeType.REMOVE)
	private Set<SchuelerFach> schuelerFaecherListe = new HashSet<SchuelerFach>();

	// @OneToMany(mappedBy = "schueler")
	// private Set<Klausur> klausurListe = new HashSet<Klausur>();

	public String getBesonderheit() {
		return besonderheit;
	}

	public void setBesonderheit(String besonderheit) {
		this.besonderheit = besonderheit;
	}

	public Timestamp getLetzteEinschreibung() {
		return letzteEinschreibung;
	}

	public void setLetzteEinschreibung(Timestamp letzteEinschreibung) {
		this.letzteEinschreibung = letzteEinschreibung;
	}

	public Sorgeberechtigter getSorgeberechtigter() {
		return sorgeberechtigter;
	}

	public void setSorgeberechtigter(Sorgeberechtigter sorgeberechtigter) {
		this.sorgeberechtigter = sorgeberechtigter;
	}

	public Set<SchuelerKlasse> getSchuelerKlassenListe() {
		return schuelerKlassenListe;
	}

	public void setSchuelerKlassenListe(Set<SchuelerKlasse> schuelerKlassenListe) {
		this.schuelerKlassenListe = schuelerKlassenListe;
	}

	public Set<SchuelerFach> getSchuelerFaecherListe() {
		return schuelerFaecherListe;
	}

	public void setSchuelerFaecherListe(Set<SchuelerFach> schuelerFaecherListe) {
		this.schuelerFaecherListe = schuelerFaecherListe;
	}

	// public Set<Klausur> getKlausurListe() {
	// return klausurListe;
	// }
	//
	// public void setKlausurListe(Set<Klausur> klausurListe) {
	// this.klausurListe = klausurListe;
	// }

}
