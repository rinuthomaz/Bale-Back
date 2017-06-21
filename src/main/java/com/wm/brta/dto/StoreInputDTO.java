package com.wm.brta.dto;

import com.wm.brta.domain.Customer;

public class StoreInputDTO {

	private Customer buyCustomer;
	private String state;
	
	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "StoreInputDTO [buyCustomer=" + buyCustomer + ", state=" + state
				+ "]";
	}
	
	
	
	
}
