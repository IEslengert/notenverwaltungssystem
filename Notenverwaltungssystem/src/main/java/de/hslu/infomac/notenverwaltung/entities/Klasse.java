package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Igor Eslengert
 */
@Entity(name = "Klasse")
@Table(name = "klasse", uniqueConstraints = { @UniqueConstraint(columnNames = { "jahr", "jahrgangsstufe" }),
		@UniqueConstraint(columnNames = { "jahr", "lehrer_id" }) })
// @SequenceGenerator(name = "klasse_sequence", sequenceName = "klasse_id_seq",
// allocationSize = 1)
public class Klasse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "klasse_sequence")
	@GenericGenerator(name = "klasse_sequence", strategy = "increment")
	@GeneratedValue(generator = "klasse_sequence")
	@Column(name = "klasse_id")
	private Long klasseId;

	@Column(name = "max_klassen_groesse", nullable = false)
	private Integer maxKlassenGroesse = 30;

	@Column(name = "jahrgangsstufe", length = 100, nullable = false)
	private String jahrgangsstufe;

	@Column(name = "jahr", nullable = false)
	private Integer jahr;

	@Column(name = "klassenraum", length = 50)
	private String klassenraum;

	@Column(name = "ist_aktiv", nullable = false)
	private Boolean istAktiv = true;

	@ManyToOne
	@JoinColumn(name = "lehrer_id")
	private Lehrer klassenLehrer;

	@OneToMany(mappedBy = "klasse", cascade = { CascadeType.ALL })
	private Set<SchuelerKlasse> schuelerKlasseListe = new HashSet<SchuelerKlasse>();

	@OneToMany(mappedBy = "klasse", cascade = { CascadeType.ALL })
	private Set<Fach> faecherListe = new HashSet<Fach>();

	// @OneToMany(mappedBy = "klasse")
	// private Set<Klausur> klausurenListe = new HashSet<Klausur>();

	@OneToMany(mappedBy = "klasse", cascade = { CascadeType.ALL })
	private Set<Konferenz> konferenzenListe = new HashSet<Konferenz>();

	public Long getKlasseId() {
		return klasseId;
	}

	public void setKlasseId(Long klasseId) {
		this.klasseId = klasseId;
	}

	public Integer getMaxKlassenGroesse() {
		return maxKlassenGroesse;
	}

	public void setMaxKlassenGroesse(Integer maxKlassenGroesse) {
		this.maxKlassenGroesse = maxKlassenGroesse;
	}

	public String getJahrgangsstufe() {
		return jahrgangsstufe;
	}

	public void setJahrgangsstufe(String jahrgangsstufe) {
		this.jahrgangsstufe = jahrgangsstufe;
	}

	public Integer getJahr() {
		return jahr;
	}

	public void setJahr(Integer jahr) {
		this.jahr = jahr;
	}

	public String getKlassenraum() {
		return klassenraum;
	}

	public void setKlassenraum(String klassenraum) {
		this.klassenraum = klassenraum;
	}

	public Boolean getIstAktiv() {
		return istAktiv;
	}

	public void setIstAktiv(Boolean istAktiv) {
		this.istAktiv = istAktiv;
	}

	public Lehrer getKlassenLehrer() {
		return klassenLehrer;
	}

	public void setKlassenLehrer(Lehrer klassenLehrer) {
		this.klassenLehrer = klassenLehrer;
	}

	public Set<SchuelerKlasse> getSchuelerKlasseListe() {
		return schuelerKlasseListe;
	}

	public void setSchuelerKlasseListe(Set<SchuelerKlasse> schuelerKlasseListe) {
		this.schuelerKlasseListe = schuelerKlasseListe;
	}

	public Set<Fach> getFaecherListe() {
		return faecherListe;
	}

	public void setFaecherListe(Set<Fach> faecherListe) {
		this.faecherListe = faecherListe;
	}

	public Set<Konferenz> getKonferenzenListe() {
		return konferenzenListe;
	}

	public void setKonferenzenListe(Set<Konferenz> konferenzenListe) {
		this.konferenzenListe = konferenzenListe;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getKlasseId();
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
		final Klasse other = (Klasse) obj;
		if (getKlasseId() != other.getKlasseId()) {
			return false;
		}
		return true;
	}
}
