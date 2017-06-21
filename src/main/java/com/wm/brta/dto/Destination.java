package com.wm.brta.dto;

import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Destination {
	
	private Integer destinationId;
	private String name;
	
	private HashSet<PickupDetails> pickupDetails;
	
	public Integer getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public HashSet<PickupDetails> getPickupDetails() {
		return pickupDetails;
	}
	public void setPickupDetails(HashSet<PickupDetails> pickupDetails) {
		this.pickupDetails = pickupDetails;
	}
	@Override
	public String toString() {
		return "Destination [destinationId=" + destinationId + ", name=" + name
				+ ", pickupDetails=" + pickupDetails + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationId == null) ? 0 : destinationId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((pickupDetails == null) ? 0 : pickupDetails.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Destination other = (Destination) obj;
		if (destinationId == null) {
			if (other.destinationId != null)
				return false;
		} else if (!destinationId.equals(other.destinationId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pickupDetails == null) {
			if (other.pickupDetails != null)
				return false;
		} else if (!pickupDetails.equals(other.pickupDetails))
			return false;
		return true;
	}
	
	
	
	

}
