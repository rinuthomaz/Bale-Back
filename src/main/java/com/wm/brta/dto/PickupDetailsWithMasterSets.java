package com.wm.brta.dto;

import java.util.HashSet;

import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Material;

public class PickupDetailsWithMasterSets {
	
	
	PickupDetails pickupDetails;
	HashSet<Material> materials;
	HashSet<IncidentType>incidentTypes;
	
	
	
	public PickupDetails getPickupDetails() {
		return pickupDetails;
	}
	public void setPickupDetails(PickupDetails pickupDetails) {
		this.pickupDetails = pickupDetails;
	}
	public HashSet<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(HashSet<Material> materials) {
		this.materials = materials;
	}
	public HashSet<IncidentType> getIncidentTypes() {
		return incidentTypes;
	}
	public void setIncidentTypes(HashSet<IncidentType> incidentTypes) {
		this.incidentTypes = incidentTypes;
	}
	
	
	
	
	
	
	
	

}
