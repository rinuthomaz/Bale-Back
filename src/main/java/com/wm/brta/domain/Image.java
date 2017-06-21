package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[File]")
public class Image
{
	private Integer imageUId;
	private Integer tripId;
	private Date createDate;
	private boolean enabled;
	private Date updatedAt;
	private String updatedBy;
	private String path;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fileid", unique = true, nullable = false)
	public Integer getImageUId() {
		return imageUId;
	}
	
	public void setImageUId(Integer imageUId) {
		this.imageUId = imageUId;
	}
	@Column(name = "tripid", nullable = false)
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	@Column(name = "createdate",nullable = false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "enabled",  nullable = false)
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name = "updatedat",nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name = "updatedby",nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name = "path", nullable = false)
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
