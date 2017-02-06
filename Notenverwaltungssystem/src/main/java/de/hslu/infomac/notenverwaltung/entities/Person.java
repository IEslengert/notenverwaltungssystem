package de.hslu.infomac.notenverwaltung.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import de.hslu.infomac.notenverwaltung.embeddables.Adresse;
import de.hslu.infomac.notenverwaltung.enumerations.Anrede;

/**
 * @author Igor Eslengert
 */
@Entity(name = "Person")
@Table(name = "person")
// @SequenceGenerator(name = "person_sequence", sequenceName = "person_id_seq",
// allocationSize = 1)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
	// "person_sequence")
	@GenericGenerator(name = "person_seq", strategy = "increment")
	@GeneratedValue(generator = "person_seq")
	@Column(name = "person_id")
	private Long personId;

	@Column(name = "vorname", length = 250, nullable = false)
	private String vorname;

	@Column(name = "nachname", length = 250, nullable = false)
	private String nachname;

	@Column(name = "geburtstag", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date geburtstag;

	@Column(name = "email", length = 250, nullable = false, unique = true)
	private String email;

	@Column(name = "telefonnummer", length = 250)
	private String telefonnummer;

	@Enumerated(EnumType.STRING)
	@Column(name = "anrede", length = 50, nullable = false)
	private Anrede anrede;

	private Adresse adresse = new Adresse();

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Date getGeburtstag() {
		return geburtstag;
	}

	public void setGeburtstag(Date geburtstag) {
		this.geburtstag = geburtstag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public Anrede getAnrede() {
		return anrede;
	}

	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	@Override
	public int hashCode() {
		final long prime = 31;
		long result = 1;
		result = prime * result + getPersonId();
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
		final Person other = (Person) obj;
		if (getPersonId() != other.getPersonId()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getPersonId().toString();
	}
}
