package de.hslu.infomac.notenverwaltung.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Igor Eslengert
 *
 */
@Entity(name = "Sorgeberechtigter")
@Table(name = "sorgeberechtigter")
@PrimaryKeyJoinColumn(name = "person_id")
public class Sorgeberechtigter extends Person {
	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "schueler_id", unique = true, nullable = false)
	private Schueler schueler;

	@Column(name = "verwandtschaftsgrad", length = 50,  nullable = false)
	private String verwandtschaftsgrad;

	public Schueler getSchueler() {
		return schueler;
	}

	public void setSchueler(Schueler schueler) {
		this.schueler = schueler;
	}

	public String getVerwandtschaftsgrad() {
		return verwandtschaftsgrad;
	}

	public void setVerwandtschaftsgrad(String verwandtschaftsgrad) {
		this.verwandtschaftsgrad = verwandtschaftsgrad;
	}

}
