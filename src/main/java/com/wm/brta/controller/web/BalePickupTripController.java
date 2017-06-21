package com.wm.brta.controller.web;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupExcelDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.BaleRouteTripService;


@RestController
public class BalePickupTripController {

	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	BalePickupService balePickupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickupTripController.class);


	
	
	@RequestMapping(value = "/tripMgmt/add/tripdetails", method = RequestMethod.POST)
	public OutputResponse addTripDetailsForWeb(
			@RequestBody PickupDetails pickupDetails) {
		OutputResponse response = new OutputResponse();
		try{

		String status = baleRouteTripService.addTripDetails(pickupDetails);

		response.setMessage(status);
		}catch(Exception e){
			LOGGER.error("Error:BalePickupTripController:addTripDetails=====>" +e);
		}

		return response;
	}

	
	
	@RequestMapping(value = "/tripMgmt/update/completetrip", method = RequestMethod.POST)
	public OutputResponse completeTripForWeb(
			@RequestBody CompleteTripInputPayLoad completeTripPayLoad) {
		OutputResponse response = new OutputResponse();
		try{
		baleRouteTripService.completeTrip(completeTripPayLoad);
		}catch(Exception e){
			LOGGER.error("Error:BalePickupTripController:completeTrip=====>" +e);
		}

		return response;
	}
	

	

	@RequestMapping(value = "/common/getAll/pickUps", method = RequestMethod.POST)
	@ResponseBody
	public List<Pickupsview> getAllPickUps(
			@RequestBody BalePickupFilterDTO balePickupFilter) {

		List<Pickupsview> assignments = new ArrayList<Pickupsview>();
		try{

		assignments = balePickupService.getPickUps(balePickupFilter);
		}catch(Exception e){
			LOGGER.error("Error:BalePickupTripController:getPickUps=====>" +e);
		}
		return assignments;
	}

	@RequestMapping(value = "/tripMgmt/pickUp/images", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getPickupImages(@RequestBody Integer tripId) {
		List<String> images = balePickupService.getBalePickupImages(tripId);
		return images;
	}

	@RequestMapping(value = "/tripMgmt/upload/pickUpsFromExcel", method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse uploadBalePickUpsFromExcel(
			@RequestBody PickupExcelDTO pickupExcelDTO) {

		
		OutputResponse response = new OutputResponse();

		List<PickupDetailsFromExcel> pickupDetailsFromExcelList = pickupExcelDTO
				.getPicupExcelList();
				
		 Integer count = 0;
		

		for (PickupDetailsFromExcel obj : pickupDetailsFromExcelList) {
			
			PickupDetailsFromExcel pickupDetailsFromExcel = obj;

			PickupDetails pickupDetails = balePickupService
					.createPickupDetailsObject(pickupDetailsFromExcel);
		 
			if(pickupDetails != null){
				 String status = baleRouteTripService.addTripDetails(pickupDetails);
				 
				 CompleteTripInputPayLoad completeTripPayLoad=new CompleteTripInputPayLoad();
				 completeTripPayLoad.setUserId(pickupDetails.getUserId());
				 completeTripPayLoad.setDate(pickupDetails.getPickupDate());
				 
				 List<PickupDetails> pickupDetailslist=new ArrayList<PickupDetails>();
				  pickupDetailslist.add(pickupDetails);
				 
				  completeTripPayLoad.setCompletePickups(pickupDetailslist);
				  
					baleRouteTripService.completeTrip(completeTripPayLoad);
				 count=count+1;
					//response.setMessage(status);
			}else{
			}		
		}

		String message = count + " trip record uploaded ";
		response.setMessage(message);
		return response;
	}
}
