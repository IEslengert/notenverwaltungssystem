package de.hslu.infomac.notenverwaltung.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "Lehrer")
@Table(name = "lehrer")
// @AttributeOverrides({ @AttributeOverride(name = "person_id", column =
// @Column(name = "lehrer_id")) })
@PrimaryKeyJoinColumn(name = "lehrer_id")
public class Lehrer extends Benutzer {
	private static final long serialVersionUID = 1L;

	@Column(name = "personal_nr", length = 250, nullable = false, unique = true)
	private String personalNr;

	@Column(name = "titel", length = 50)
	private String titel;

	@OneToMany(mappedBy = "klassenLehrer", cascade = { CascadeType.MERGE })
	private Set<Klasse> klassenListe = new HashSet<Klasse>();

	@OneToMany(mappedBy = "lehrer")
	private Set<Fach> faecherListe = new HashSet<Fach>();

	public String getPersonalNr() {
		return personalNr;
	}

	public void setPersonalNr(String personalNr) {
		this.personalNr = personalNr;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public Set<Klasse> getKlassenListe() {
		return klassenListe;
	}

	public void setKlassenListe(Set<Klasse> klassenListe) {
		this.klassenListe = klassenListe;
	}

	public Set<Fach> getFaecherListe() {
		return faecherListe;
	}

	public void setFaecherListe(Set<Fach> faecherListe) {
		this.faecherListe = faecherListe;
	}

}
