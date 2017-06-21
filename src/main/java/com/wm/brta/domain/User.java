package com.wm.brta.domain;

import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="[user]")
public class User {
	

	private long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String mobilePhone;
	private String otherPhone;
	private boolean isExternalUserFlag;
	private boolean isEnabled;
	private Date  createDate;
	private String updatedBy;
	private Date updatedDate;
	private Supplier supplier;
	
	
	private String statusFilter;


	private int supplierFilter;
	private int roleId;
	
	public User(){
		
	}
	
	public User(long userId, String firstName, String lastName, String emailId,
			String mobilePhone, boolean isEnabled, Date createDate,
			String updatedBy, Date updatedDate,Supplier supplier) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobilePhone = mobilePhone;
		this.isEnabled = isEnabled;
		this.createDate = createDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.supplier=supplier;
	}
	@Transient
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	@Transient
	public String getStatusFilter() {
		return statusFilter;
	}
	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}
	@Transient
	public int getSupplierFilter() {
		return supplierFilter;
	}
	public void setSupplierFilter(int supplierFilter) {
		this.supplierFilter = supplierFilter;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@Column(name = "firstname",  nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name = "middlename")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Column(name = "lastname",  nullable = false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name = "email",  nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Column(name = "mobilephone",  nullable = false)
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Column(name = "phone")
	public String getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	@Column(name = "isexternal",  nullable = false)
	public boolean isExternalUserFlag() {
		return isExternalUserFlag;
	}
	public void setExternalUserFlag(boolean isExternalUserFlag) {
		this.isExternalUserFlag = isExternalUserFlag;
	}
	@Column(name = "enabled",  nullable = false)
	public boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
  @Column(name = "createdate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "updatedby")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name = "updatedat")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", mobilePhone=" + mobilePhone
				+ ", otherPhone=" + otherPhone + ", isExternalUserFlag="
				+ isExternalUserFlag + ", isEnabled=" + isEnabled
				+ ", createDate=" + createDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate +"]";
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid",  nullable = false)
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
