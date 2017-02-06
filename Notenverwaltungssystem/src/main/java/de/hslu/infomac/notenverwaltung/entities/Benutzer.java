package de.hslu.infomac.notenverwaltung.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import de.hslu.infomac.notenverwaltung.enumerations.Rolle;

/**
 * @author Igor Eslengert
 */
@Entity(name = "Benutzer")
@Table(name = "benutzer")
// @AttributeOverrides({ @AttributeOverride(name = "personId", column =
// @Column(name = "benutzer_id")) })
@PrimaryKeyJoinColumn(name = "benutzer_id")
public abstract class Benutzer extends Person {
	private static final long serialVersionUID = 1L;

	@Column(name = "religion", length = 100)
	private String religion;

	@Column(name = "login_name", length = 250, nullable = false, unique = true)
	private String loginName;

	@Column(name = "passwort", length = 250, nullable = false)
	private String passwort;

	@Lob
	@Column(name = "profil_bild", columnDefinition = "longblob")
	private byte[] profilBild;

	@Column(name = "erster_login", nullable = false)
	private Boolean ersterLogin = true;

	@ElementCollection(targetClass = Rolle.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "rolle", joinColumns = { @JoinColumn(name = "benutzer_id") })
	@Column(name = "rollen_name")
	private Set<Rolle> rollen = new HashSet<Rolle>();

	public boolean hasRolle(Rolle rolle) {
		return rollen.contains(rolle);
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public byte[] getProfilBild() {
		return profilBild;
	}

	public void setProfilBild(byte[] profilBild) {
		this.profilBild = profilBild;
	}

	public Boolean isErsterLogin() {
		return ersterLogin;
	}

	public void setErsterLogin(Boolean ersterLogin) {
		this.ersterLogin = ersterLogin;
	}

	public Set<Rolle> getRollen() {
		return rollen;
	}

	public void setRollen(Set<Rolle> rollen) {
		this.rollen = rollen;
	}
}
