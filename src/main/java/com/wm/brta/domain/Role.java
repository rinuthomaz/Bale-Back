package com.wm.brta.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {
	
	private int roleId;
	private String roleDescription;
	private boolean isEnabled;
	private Date  createDate;
	private String updatedBy;
	private Date updatedDate;
	
	
	
	@Id
	@Column(name="roleid", unique=true,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Column(name="roledesc", nullable=false)
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
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
		return "Role [roleId=" + roleId + ", roleDescription="
				+ roleDescription + ", isEnabled=" + isEnabled
				+ ", createDate=" + createDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}


}
