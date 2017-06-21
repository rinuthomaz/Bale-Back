package com.wm.brta.dto;

import java.util.HashSet;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;

public class AssignmentFilterDTO {
	
	private Customer buyCustomer;
	private User user;
	private Supplier supplier;
	private Customer destination;
	private String state;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	private Integer frequency;
	private HashSet<Integer> dayList;
	private HashSet<Integer> monthWeekList;

	
	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Customer getDestination() {
		return destination;
	}
	public void setDestination(Customer destination) {
		this.destination = destination;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public HashSet<Integer> getDayList() {
		return dayList;
	}
	public void setDayList(HashSet<Integer> dayList) {
		this.dayList = dayList;
	}
	public HashSet<Integer> getMonthWeekList() {
		return monthWeekList;
	}
	public void setMonthWeekList(HashSet<Integer> monthWeekList) {
		this.monthWeekList = monthWeekList;
	}
	@Override
	public String toString() {
		return "AssignmentFilterDTO [buyCustomer=" + buyCustomer + ", user="
				+ user + ", supplier=" + supplier + ", destination="
				+ destination + ", frequency=" + frequency + ", dayList="
				+ dayList + ", monthWeekList=" + monthWeekList + "]";
	}
}
