package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*import com.wm.brta.data.domain.Pickupsview;*/
/*import com.wm.brta.data.dto.BalePickupFilterDTO;
import com.wm.brta.data.dto.BalePickupUpdateDTO;*/


import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.AssignmentFilterDTO;
import com.wm.brta.dto.BalePickupAssignmentDTO;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.BalePickupUpdateDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.UserDTO;

public interface BalePickupService {
	
	public HashSet<Destination>getAllDestinations(ActivityListInputPayload activityListPayload);
	public List<PickupDetails> getAllDestinationsNew(ActivityListInputPayload activityListPayload);

	public PickupDetailsWithMasterSets getPickupDetails(PickupDetails pickup);
	public void addBalePickupAssignments(BalePickupAssignmentDTO assignmentDTO, UserDTO user);
	public Set<BalePickupAssignment> getPickupAssignments(AssignmentFilterDTO assignmentFilter);
	
	public List<Pickupsview> getPickUps(BalePickupFilterDTO balePickupFilter);
	public Set<PendingReport> getAllPendingPickups(PendingStoreReportDTO pendingStoreReportDTO);

	public List<String> getBalePickupImages(Integer balePickupId);
	

	public void updateBalePickupAssignmentstoAssignActivity(
			List<BalePickupAssignment> assignments, UserDTO user);
	public HashSet<Destination> getAllPickupsForOnCall(OnCallInputPayload inputPayLoad);
	
	public List<PickupDetails> getAllPickupsForOnCallNew(OnCallInputPayload inputPayLoad);

	
	public Set<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite);
	public String assignNewDriver(BalePickupAssignment assignment) throws Exception;
	
	
	public PickupDetails createPickupDetailsObject(PickupDetailsFromExcel pickupDetailsFromExcel);
	public String removeNewDriver(BalePickupAssignment assignment);

	public String getBalePickupImagesFromName(String imageName);

}
