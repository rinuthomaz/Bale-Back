package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;

import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PickupDetails;

public interface BaleRouteTripService {
	
	public String addTripDetails(PickupDetails pickupDetails);

	public void completeTrip(CompleteTripInputPayLoad completeTripPayLoad);

	public HashSet<Destination> getSavedOnlyTrips(
			ActivityListInputPayload inputPayload); 
	
	public List<PickupDetails> getSavedOnlyTripsNew(
			ActivityListInputPayload inputPayload); 

}
