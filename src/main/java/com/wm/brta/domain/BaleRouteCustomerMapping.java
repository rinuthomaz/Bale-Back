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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="[baleroutecustomermapping]")
public class BaleRouteCustomerMapping {
	
	
	private Customer buyCustomer;
	
	private Customer sellCustomer;
	private int baleRouteCustomermappingId;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buycustomerid",  nullable = false)
	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sellcustomerid",  nullable = false)
	public Customer getSellCustomer() {
		return sellCustomer;
	}
	public void setSellCustomer(Customer sellCustomer) {
		this.sellCustomer = sellCustomer;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "baleroutecustomermappingid", unique = true, nullable = false)
	public int getBaleRouteCustomermappingId() {
		return baleRouteCustomermappingId;
	}
	public void setBaleRouteCustomermappingId(int baleRouteCustomermappingId) {
		this.baleRouteCustomermappingId = baleRouteCustomermappingId;
	}
	@Column(name = "createdate", unique = true, nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "updatedat", unique = true, nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name = "updatedby", unique = true, nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	}
