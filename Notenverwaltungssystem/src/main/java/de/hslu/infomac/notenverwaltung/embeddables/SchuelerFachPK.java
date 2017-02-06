package de.hslu.infomac.notenverwaltung.embeddables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Igor Eslengert
 *
 */
@Embeddable
public class SchuelerFachPK implements Serializable {

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Column(name = "schueler_id")
	private Long schuelerId;

	@Column(name = "fach_id")
	private Long fachId;

	public SchuelerFachPK() {
	}

	public SchuelerFachPK(long schuelerId, long fachId) {
		this.schuelerId = schuelerId;
		this.fachId = fachId;
	}

	public Long getSchuelerId() {
		return schuelerId;
	}

	public void setSchuelerId(Long schuelerId) {
		this.schuelerId = schuelerId;
	}

	public Long getFachId() {
		return fachId;
	}

	public void setFachId(Long fachId) {
		this.fachId = fachId;
	}
}
