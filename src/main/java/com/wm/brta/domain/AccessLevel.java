package com.wm.brta.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="accesslevel")
public class AccessLevel {
	
	
	private long accessLevelId;
	private String accessLevelDescription;
	private boolean isEnabled;
	private Date  createDate;
	private String updatedBy;
	private Date updatedDate;

	
	@Id
	@Column(name="accessid", unique=true,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getAccessLevelId() {
		return accessLevelId;
	}
	
	public void setAccessLevelId(long accessLevelId) {
		this.accessLevelId = accessLevelId;
	}
	
	@Column(name="accesslvldesc", nullable=false)
	public String getAccessLevelDescription() {
		return accessLevelDescription;
	}
	public void setAccessLevelDescription(String accessLevelDescription) {
		this.accessLevelDescription = accessLevelDescription;
	}
	
	@Column(name="enabled", unique=true,nullable=false)
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	@Column(name="createdate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="updatedby")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name="updatedat")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	@Override
	public String toString() {
		return "AccessLevel [accessLevelId=" + accessLevelId
				+ ", accessLevelDescription=" + accessLevelDescription
				+ ", isEnabled=" + isEnabled + ", createDate=" + createDate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ "]";
	}

	
	

}
