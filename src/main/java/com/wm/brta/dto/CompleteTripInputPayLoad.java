package com.wm.brta.dto;

import java.util.List;

public class CompleteTripInputPayLoad 
{
	
	private Integer userId;
	private String date;
	
	
	private List<PickupDetails> completePickups;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<PickupDetails> getCompletePickups() {
		return completePickups;
	}
	public void setCompletePickups(List<PickupDetails> completePickups) {
		this.completePickups = completePickups;
	}
	
	
	
	

}
