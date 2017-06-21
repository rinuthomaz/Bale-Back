package com.wm.brta.dto;

import java.util.List;

public class ActivityListInputPayload {
	
	private Integer userId;
	private List<String> dates;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<String> getDates() {
		return dates;
	}
	public void setDates(List<String> dates) {
		this.dates = dates;
	}
	
	@Override
	public String toString() {
		return "ActivityListInputPayload [userId=" + userId + ", dates="
				+ dates + "]";
	}
}
