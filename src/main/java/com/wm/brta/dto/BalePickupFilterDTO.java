package com.wm.brta.dto;


import java.sql.Date;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Supplier;

public class BalePickupFilterDTO {

	private Customer buyCustomer;
	private IncidentType incidentType;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	private Supplier supplier;
	private String state;

	private Date startDate;
	private Date endDate;

	public IncidentType getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}

	public Customer getBuyCustomer() {
		return buyCustomer;
	}

	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "BalePickupFilterDTO [buyCustomer=" + buyCustomer
				+ ", supplier=" + supplier + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}
