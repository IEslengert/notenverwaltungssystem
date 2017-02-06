package de.hslu.infomac.notenverwaltung.embeddables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Igor Eslengert
 *
 */
@Embeddable
public class SchuelerKlassePK implements Serializable {

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Column(name = "schueler_id")
	private Long schuelerId;

	@Column(name = "klasse_id")
	private Long klasseId;

	public SchuelerKlassePK() {
	}

	public SchuelerKlassePK(long schuelerId, long klasseId) {
		this.schuelerId = schuelerId;
		this.klasseId = klasseId;
	}

	public Long getSchuelerId() {
		return schuelerId;
	}

	public void setSchuelerId(Long schuelerId) {
		this.schuelerId = schuelerId;
	}

	public Long getKlasseId() {
		return klasseId;
	}

	public void setKlasseId(Long klasseId) {
		this.klasseId = klasseId;
	}

}
