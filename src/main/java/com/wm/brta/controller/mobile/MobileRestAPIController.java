package com.wm.brta.controller.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.IncidentTypeService;
import com.wm.brta.service.LoginService;
import com.wm.brta.service.UserService;

@RestController
public class MobileRestAPIController {

	

	@Autowired
	private LoginService loginService;
	
	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	BalePickupService balePickupService;

	
	@RequestMapping(value = "/service/authenticate/driver", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> authenticateDriver(@RequestBody User user) {
		
		System.out.println("----in login ctrl=====");
		UserDTO userDTO = null;
		try {
			userDTO = loginService.authenticate(user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("----in login ctrl====="+userDTO);

		if (userDTO.isAuthenticationErrorFlag()) {
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.FORBIDDEN);
		} else
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service/pickups", method = RequestMethod.POST)
	@ResponseBody
	public HashSet<Destination> getAllDestinations(
			@RequestBody ActivityListInputPayload activitylistPayLoad) {

		HashSet<Destination> destinationsToReturn = null;

		destinationsToReturn = balePickupService
				.getAllDestinations(activitylistPayLoad);

		return destinationsToReturn;

	}
	
	@RequestMapping(value = "/service/pickupsNew", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getAllDestinationsNew(
			@RequestBody ActivityListInputPayload activitylistPayLoad) {

		System.out.println("====hello======");
		List<PickupDetails> destinationsToReturn = new ArrayList<PickupDetails>();

		destinationsToReturn = balePickupService
				.getAllDestinationsNew(activitylistPayLoad);

		System.out.println("====hello=1====="+destinationsToReturn.size());

		
		return destinationsToReturn;

	}

	@RequestMapping(value = "/service/pickupdetails", method = RequestMethod.POST)
	@ResponseBody
	public PickupDetailsWithMasterSets getPickupDetails(
			@RequestBody PickupDetails pickup) {

		PickupDetailsWithMasterSets pickupDetailsWithMasterSets = null;

		pickupDetailsWithMasterSets = balePickupService
				.getPickupDetails(pickup);

		return pickupDetailsWithMasterSets;

	}

	@RequestMapping(value = "/service/onCallPickups", method = RequestMethod.POST)
	@ResponseBody
	public HashSet<Destination> getOnCallPickups(
			@RequestBody OnCallInputPayload inputPayLoad) {
		HashSet<Destination> destinations = new HashSet<Destination>();

		destinations = balePickupService.getAllPickupsForOnCall(inputPayLoad);

		return destinations;

	}
	
	
	@RequestMapping(value = "/service/onCallPickupsNew", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getOnCallPickupsNew(
			@RequestBody OnCallInputPayload inputPayLoad) {
		List<PickupDetails> destinations = new ArrayList<PickupDetails>();

		destinations = balePickupService.getAllPickupsForOnCallNew(inputPayLoad);

		return destinations;

	}

	@RequestMapping(value = "service/add/tripdetails", method = RequestMethod.POST)
	public OutputResponse addTripDetails(
			@RequestBody PickupDetails pickupDetails) {
		OutputResponse response = new OutputResponse();

		String status = baleRouteTripService.addTripDetails(pickupDetails);

		response.setMessage(status);

		return response;
	}
	

	@RequestMapping("service/healthCheck")
	public String healthCheck() {
		return "ta da! brta app is working";
	}
	
	@RequestMapping(value = "service/update/completetrip", method = RequestMethod.POST)
	public OutputResponse completeTrip(
			@RequestBody CompleteTripInputPayLoad completeTripPayLoad) {
		OutputResponse response = new OutputResponse();
		baleRouteTripService.completeTrip(completeTripPayLoad);

		return response;
	}
	
	@RequestMapping(value = "service/get/savedonlytrips", method = RequestMethod.POST)
	public HashSet<Destination> getSavedOnlyTrips(
			@RequestBody ActivityListInputPayload inputPayload) {

		HashSet<Destination> pickupDetails = baleRouteTripService
				.getSavedOnlyTrips(inputPayload);

		return pickupDetails;
	}
	
	@RequestMapping(value = "service/get/savedonlytripsNew", method = RequestMethod.POST)
	public List<PickupDetails> getSavedOnlyTripsNew(
			@RequestBody ActivityListInputPayload inputPayload) {

		List<PickupDetails> pickupDetails = baleRouteTripService
				.getSavedOnlyTripsNew(inputPayload);

		return pickupDetails;
	}
	
	@RequestMapping(value = "service/pickUp/imageFromName", method = RequestMethod.POST)
	@ResponseBody
	public String getPickupImagesFromTripAndName(@RequestBody String imgData) {
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
        JsonObject json = (JsonObject) parser
                .parse(imgData);
        Gson gson = new Gson();
        String imageName =gson.toJson(json.get("imageName"));
        imageName = imageName.replace("\"", "");
        
        String images = balePickupService.getBalePickupImagesFromName(imageName.toString());
      
		return images;
	}


}