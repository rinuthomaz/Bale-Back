package com.wm.brta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PickupDetailsFromExcel {
	
	private String storeName;	
	private String pickupDate;
	private Integer balesPicked;
	private Integer balesRemaining;
	private String commodity;
	private Integer bol;
	
	@JsonProperty("STORE_NAME")
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	@JsonProperty("PICKUP_DATE")
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	
	@JsonProperty("BALES_PICKED")
	public Integer getBalesPicked() {
		return balesPicked;
	}
	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}
	
	@JsonProperty("BALES_REMAINING")
	public Integer getBalesRemaining() {
		return balesRemaining;
	}
	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}
	
	@JsonProperty("COMMODITY")
	public String getCommodity() {
		return commodity;
	}
	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}
	
	@JsonProperty("BOL")
	public Integer getBol() {
		return bol;
	}
	public void setBol(Integer bol) {
		this.bol = bol;
	}
	@Override
	public String toString() {
		return "PickupDetailsFromExcel [storeName=" + storeName
				+ ", pickupDate=" + pickupDate + ", balesPicked=" + balesPicked
				+ ", balesRemaining=" + balesRemaining + ", commodity="
				+ commodity + ", bol=" + bol + "]";
	}
	
	
	
	
	
	
}