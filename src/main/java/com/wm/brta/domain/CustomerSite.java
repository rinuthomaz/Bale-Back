package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="[customersite]")
public class CustomerSite {
	
	
	
	private Integer customerSiteId;
	private Customer customer;
	private Location location;
	private String siteName;
	private SalesTerritory salesTerritory;
	private Integer customerServiceRepId;
	private Integer salesRepId;
	private String sysUserName;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	private boolean hasFrequency;
	private float avgBaleWeight;
	private Date balePickupStartDate;
	private List<BalePickupMaterialConfiguration> balePickupMaterialConfiguration;
	private List<BalePickupSupplierSiteConfiguration> BalePickupSupplierSiteConfiguration;
	private List<String>configuredMaterials;
	private List<String>configuredSupplierSites;
	
	private String alternativeSearchReference;
	
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy="customerSite")
	@JsonBackReference(value="materialConfigurations")
	@JsonIgnore
	public List<BalePickupMaterialConfiguration> getBalePickupMaterialConfiguration() {
		return balePickupMaterialConfiguration;
	}
	public void setBalePickupMaterialConfiguration(
			List<BalePickupMaterialConfiguration> balePickupMaterialConfiguration) {
		this.balePickupMaterialConfiguration = balePickupMaterialConfiguration;
	}
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,mappedBy="customerSite")
	@JsonBackReference(value="supplierSiteConfigurations")
	@JsonIgnore
	public List<BalePickupSupplierSiteConfiguration> getBalePickupSupplierSiteConfiguration() {
		return BalePickupSupplierSiteConfiguration;
	}
	public void setBalePickupSupplierSiteConfiguration(
			List<BalePickupSupplierSiteConfiguration> balePickupSupplierSiteConfiguration) {
		BalePickupSupplierSiteConfiguration = balePickupSupplierSiteConfiguration;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customersiteid", unique = true, nullable = false)
	public Integer getCustomerSiteId() {
		return customerSiteId;
	}
	public void setCustomerSiteId(Integer customerSiteId) {
		this.customerSiteId = customerSiteId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customerid",nullable=false)
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="locationid",nullable=false)
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Column(name="sitename",nullable=false)
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="salesterritoryid",nullable=false)
	public SalesTerritory getSalesTerritory() {
		return salesTerritory;
	}
	public void setSalesTerritory(SalesTerritory salesTerritory) {
		this.salesTerritory = salesTerritory;
	}
	@Column(name="customerservicerepid",nullable=false)
	public Integer getCustomerServiceRepId() {
		return customerServiceRepId;
	}
	public void setCustomerServiceRepId(Integer customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}
	@Column(name="salesrepid",nullable=false)
	public Integer getSalesRepId() {
		return salesRepId;
	}
	public void setSalesRepId(Integer salesRepId) {
		this.salesRepId = salesRepId;
	}
	@Column(name="sysusername")
	public String getSysUserName() {
		return sysUserName;
	}
	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}
	@Column(name="createdate",nullable=false)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="updatedat",nullable=false)
	public Date getUpdatedAt() {
		
		return updatedAt;
	}
	
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="updatedby",nullable=false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name="hasfrequency")
	public boolean isHasFrequency() {
		return hasFrequency;
	}
	public void setHasFrequency(boolean hasFrequency) {
		this.hasFrequency = hasFrequency;
	}
	@Column(name="balepickupstartdate")
	public Date getBalePickupStartDate() {
		return balePickupStartDate;
	}
	public void setBalePickupStartDate(Date balePickupStartDate) {
		this.balePickupStartDate = balePickupStartDate;
	}
	@Column(name="avgbaleweight")
	public float getAvgBaleWeight() {
		return avgBaleWeight;
	}
	public void setAvgBaleWeight(float avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}
	@Transient
	public List<String> getConfiguredSupplierSites() {
		return configuredSupplierSites;
	}
	public void setConfiguredSupplierSites(List<String> configuredSupplierSites) {
		this.configuredSupplierSites = configuredSupplierSites;
	}
	@Transient
	public List<String> getConfiguredMaterials() {
		return configuredMaterials;
	}
	public void setConfiguredMaterials(List<String> configuredMaterials) {
		this.configuredMaterials = configuredMaterials;
	}
	
	@Column(name="alternativesearchreference")
	public String getAlternativeSearchReference() {
		return alternativeSearchReference;
	}
	public void setAlternativeSearchReference(String alternativeSearchReference) {
		this.alternativeSearchReference = alternativeSearchReference;
	}
	
	
	
	
	
	
	
	
	

}
