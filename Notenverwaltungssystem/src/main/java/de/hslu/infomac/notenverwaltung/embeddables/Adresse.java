package de.hslu.infomac.notenverwaltung.embeddables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Igor Eslengert
 *
 */
@Embeddable
public class Adresse implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "strasse", length = 250, nullable = false)
	private String strasse;

	@Column(name = "hausnummer", length = 50, nullable = false)
	private String hausnummer;

	@Column(name = "ort", length = 250, nullable = false)
	private String ort;

	@Column(name = "plz", length = 150, nullable = false)
	private String plz;

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}
}
