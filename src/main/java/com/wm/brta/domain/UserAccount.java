package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Table(name="[useraccount]")
@Entity
public class UserAccount {
	
	
	private long accountId;
	private User user;
	private Role role;
	//private AccessLevel access;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	private boolean isEnabled;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "acctid", unique = true, nullable = false)
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid",  nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleid",  nullable = false)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "accessid",  nullable = false)
//	public AccessLevel getAccess() {
//		return access;
//	}
//	public void setAccess(AccessLevel access) {
//		this.access = access;
//	}
	

	@Column(name = "createdate")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "updatedat")
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name = "updatedby",  nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name = "enabled",  nullable = false)
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	
	
	
	

}
