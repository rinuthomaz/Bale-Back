package com.wm.brta.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.IncidentType;

@JsonInclude(Include.NON_NULL)
public class PickupDetails {
	
	private Integer storeId;
	private Integer assignmentId;
	private String storeName;
	private String address;
	
	
	private String pickupDate;
	private Integer balesPicked;
	private Integer balesRemaining;
	private HashSet<Commodity> commodities;
	private IncidentType incidentType;
	private Integer BOL;
	private Destination destination;
	private String comments;
	private boolean incident;
	private Integer userId;
	
	private List<String> images;
	
	private Set<Image> tripImages;


	private boolean destinationDrop = true;
	
	
	
	
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	@Override
	public String toString() {
		return "PickupDetails [storeId=" + storeId + ", assignmentId="
				+ assignmentId + ", storeName=" + storeName + ", address="
				+ address + ", pickupDate=" + pickupDate + ", balesPicked="
				+ balesPicked + ", balesRemaining=" + balesRemaining
				+ ", commodities=" + commodities + ", incidentType="
				+ incidentType + ", BOL=" + BOL + ", destination="
				+ destination + ", comments=" + comments + ", incident="
				+ incident + ", userId=" + userId + ", destinationDrop="
				+ destinationDrop + "]";
	}
	public Integer getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(Integer assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	public Integer getBalesPicked(Integer integer) {
		return balesPicked;
	}
	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}
	public Integer getBalesRemaining(Integer integer) {
		return balesRemaining;
	}
	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}
	
	

	public Integer getBOL() {
		return BOL;
	}
	public void setBOL(Integer bOL) {
		BOL = bOL;
	}
	public Integer getBalesPicked() {
		return balesPicked;
	}
	public Integer getBalesRemaining() {
		return balesRemaining;
	}
	public HashSet<Commodity> getCommodities() {
		return commodities;
	}
	public void setCommodities(HashSet<Commodity> commodities) {
		this.commodities = commodities;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public IncidentType getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}
	public boolean isIncident() {
		return incident;
	}
	public void setIncident(boolean incident) {
		this.incident = incident;
	}
	public boolean isDestinationDrop() {
		return destinationDrop;
	}
	public void setDestinationDrop(boolean destinationDrop) {
		this.destinationDrop = destinationDrop;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
	public Set<Image> getTripImages() {
		return tripImages;
	}
	public void setTripImages(Set<Image> tripImages) {
		this.tripImages = tripImages;
	}
	
	
	
	
	
	
	

}
