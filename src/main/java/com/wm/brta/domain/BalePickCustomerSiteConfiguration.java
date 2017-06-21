package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="[balepickupcustomersiteconfiguration]")
public class BalePickCustomerSiteConfiguration 
{
	
	private Integer balePickUpCustomerSiteConfigurationId;
	private CustomerSite buyCustomerSite;
	private CustomerSite sellCustomerSite;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	private boolean enabled;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="balepickupcustomersiteconfigurationid",nullable = false,unique=true)
	public Integer getBalePickUpCustomerSiteConfigurationId() {
		return balePickUpCustomerSiteConfigurationId;
	}
	public void setBalePickUpCustomerSiteConfigurationId(
			Integer balePickUpCustomerSiteConfigurationId) {
		this.balePickUpCustomerSiteConfigurationId = balePickUpCustomerSiteConfigurationId;
	}
	
	@OneToOne(fetch=FetchType.EAGER)
	
	@JoinColumn(name="buycustomersiteid",nullable = false)
	public CustomerSite getBuyCustomerSite() {
		return buyCustomerSite;
	}
	public void setBuyCustomerSite(CustomerSite buyCustomerSite) {
		this.buyCustomerSite = buyCustomerSite;
	}
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sellcustomersiteid",nullable = false)
	public CustomerSite getSellCustomerSite() {
		return sellCustomerSite;
	}
	public void setSellCustomerSite(CustomerSite sellCustomerSite) {
		this.sellCustomerSite = sellCustomerSite;
	}
	
	@Column(name="createdate",nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="updatedat",nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="updatedby",nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name="enabled",nullable = false)
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "BalePickCustomerSiteConfiguration [balePickUpCustomerSiteConfigurationId="
				+ balePickUpCustomerSiteConfigurationId
				+ ", buyCustomerSite="
				+ buyCustomerSite
				+ ", sellCustomerSite="
				+ sellCustomerSite
				+ ", createdDate="
				+ createdDate
				+ ", updatedAt="
				+ updatedAt
				+ ", updatedBy=" + updatedBy + ", enabled=" + enabled + "]";
	}
	
	
	

}
