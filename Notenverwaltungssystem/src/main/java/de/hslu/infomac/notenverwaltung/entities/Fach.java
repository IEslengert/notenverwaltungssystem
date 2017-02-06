package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Igor Eslengert
 */
@Entity(name = "Fach")
@Table(name = "fach", uniqueConstraints = @UniqueConstraint(columnNames = { "klasse_id", "name" }))
// @SequenceGenerator(name = "fach_sequence", sequenceName = "fach_id_seq",
// allocationSize = 1)
public class Fach implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "fach_sequence")
	@GenericGenerator(name = "fach_sequence", strategy = "increment")
	@GeneratedValue(generator = "fach_sequence")
	@Column(name = "fach_id")
	private Long fachId;

	@Column(name = "name", length = 250, nullable = false)
	private String name;

	@Column(name = "unterrichtssprache", length = 50)
	private String unterrichtssprache = "Deutsch";

	@ManyToOne
	@JoinColumn(name = "lehrer_id")
	private Lehrer lehrer;

	@ManyToOne
	@JoinColumn(name = "klasse_id")
	private Klasse klasse;

	// @OneToMany(mappedBy = "fach")
	// private Set<Klausur> klausurenListe = new HashSet<Klausur>();

	@OneToMany(mappedBy = "fach", cascade = { CascadeType.ALL })
	private Set<SchuelerFach> schuelerFachListe = new HashSet<SchuelerFach>();

	public Long getFachId() {
		return fachId;
	}

	public void setFachId(Long fachId) {
		this.fachId = fachId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnterrichtssprache() {
		return unterrichtssprache;
	}

	public void setUnterrichtssprache(String unterrichtssprache) {
		this.unterrichtssprache = unterrichtssprache;
	}

	public Lehrer getLehrer() {
		return lehrer;
	}

	public void setLehrer(Lehrer lehrer) {
		this.lehrer = lehrer;
	}

	public Klasse getKlasse() {
		return klasse;
	}

	public void setKlasse(Klasse klasse) {
		this.klasse = klasse;
	}

	public Set<SchuelerFach> getSchuelerFachListe() {
		return schuelerFachListe;
	}

	public void setSchuelerFachListe(Set<SchuelerFach> schuelerFachListe) {
		this.schuelerFachListe = schuelerFachListe;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getFachId();
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
		final Fach other = (Fach) obj;
		if (getFachId() != other.getFachId()) {
			return false;
		}
		return true;
	}
}
