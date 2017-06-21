package com.wm.brta.dto;

public class Commodity
{
	private Integer commodityId;
	private String name;
	private Integer balesPicked;
	private Integer balesRemaining;
	
	
	public Integer getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String string) {
		this.name = string;
	}
	public Integer getBalesPicked() {
		return balesPicked;
	}
	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}
	public Integer getBalesRemaining() {
		return balesRemaining;
	}
	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}
	@Override
	public String toString() {
		return "Commodity [commodityId=" + commodityId + ", name=" + name
				+ ", balesPicked=" + balesPicked + ", balesRemaining="
				+ balesRemaining + "]";
	}
	
	
	
	
	

}
