package be.fp.distriWebApp.core.model.dto;
// Generated by Hibernate Tools 4.3.1.Final

import java.util.Date;


public class RefLovsDto implements java.io.Serializable {


	private String domCode;
	private RefLovsDomainsDto refLovsDomains;

	private String code;
	private String labFr;
	private String longLabFr;
	private Integer sequenceNr;
	private Date creationDate;
	private String creationUser;
	private Date modificationDate;

	private String modificationUser;

	public String getDomCode() {
		return domCode;
	}

	public void setDomCode(String domCode) {
		this.domCode = domCode;
	}

	public RefLovsDomainsDto getRefLovsDomains() {
		return refLovsDomains;
	}

	public void setRefLovsDomains(RefLovsDomainsDto refLovsDomains) {
		this.refLovsDomains = refLovsDomains;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabFr() {
		return labFr;
	}

	public void setLabFr(String labFr) {
		this.labFr = labFr;
	}

	public String getLongLabFr() {
		return longLabFr;
	}

	public void setLongLabFr(String longLabFr) {
		this.longLabFr = longLabFr;
	}

	public Integer getSequenceNr() {
		return sequenceNr;
	}

	public void setSequenceNr(Integer sequenceNr) {
		this.sequenceNr = sequenceNr;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getModificationUser() {
		return modificationUser;
	}

	public void setModificationUser(String modificationUser) {
		this.modificationUser = modificationUser;
	}


}
