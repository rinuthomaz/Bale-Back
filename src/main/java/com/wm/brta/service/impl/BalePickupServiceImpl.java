package com.wm.brta.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import net.iharder.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.BalePickupMaterialConfigurationDAO;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.CustomerSiteDAO;
import com.wm.brta.dao.IncidentTypeDao;
import com.wm.brta.dao.MasterDataDao;
/*import com.wm.brta.data.domain.Pickupsview;*/
/*import com.wm.brta.data.dto.BalePickupFilterDTO;
 import com.wm.brta.data.dto.BalePickupUpdateDTO;*/
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.AssignmentFilterDTO;
import com.wm.brta.dto.BalePickupAssignmentDTO;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.util.PickupInformation;

@Transactional
@Component("balePickupService")
public class BalePickupServiceImpl implements BalePickupService {

	@Autowired
	BalePickUpAssignmentDAO balePickupAssignment;

	@Autowired
	CustomerSiteDAO customerSiteDAO;

	@Autowired
	BalePickupMaterialConfigurationDAO balePickupMaterialConfigurationDAO;

	@Autowired
	IncidentTypeDao incidentType;

	@Autowired
	MasterDataDao masterDataDAO;

	@Autowired
	BalePickupTripDAO balePickupTripDAO;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickupServiceImpl.class);

	@Override
	public HashSet<Destination> getAllDestinations(
			ActivityListInputPayload activityListPayload) {

		List<PickupInformation> weekNumbersAndDays = new ArrayList<PickupInformation>();

		List<Integer> days = null;
		try {
			weekNumbersAndDays = getAllWeekNumbersAndDays(activityListPayload
					.getDates());
			// days = getAllDays(activityListPayload.getDates());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<BalePickupAssignment> assignmentsForUser = null;
		List<BalePickupAssignment> assignmentsOnCall = null;
		List<BalePickupAssignment> assignments = new ArrayList<BalePickupAssignment>();

		// System.out.println("Days-->"+days);
		boolean inputHasTodayDate = false;
		assignmentsForUser = balePickupAssignment
				.getAllAssignments(activityListPayload.getUserId());
		assignmentsOnCall = balePickupAssignment.getAllOnCallAssignments();
		for (String date : activityListPayload.getDates()) {
			System.out.println("Date coming->" + date);
			Date dateToday = new Date();
			SimpleDateFormat format = new SimpleDateFormat();
			format = new SimpleDateFormat("MM/dd/yyyy");
			String dateFormat = format.format(dateToday);
			if (dateFormat.equals(date)) {
				inputHasTodayDate = true;
			}

		}
		if (inputHasTodayDate)
			assignmentsForUser.addAll(assignmentsOnCall);
		// ignore assignments diverted to another user

		for (BalePickupAssignment assignmentForUser : assignmentsForUser) {
			if (assignmentForUser.getFrequency() != 3) {
				for (PickupInformation pickupInformation : weekNumbersAndDays) {

					if (assignmentForUser.getWeekNumber().equals(
							pickupInformation.getWeekNumber())
							&& assignmentForUser.getDay().equals(
									pickupInformation.getDayNumber())
							&& (assignmentForUser.getDivertedUser() == null || (assignmentForUser
									.getDivertedUser() != null && assignmentForUser
									.getDivertedUser().getUserId() == activityListPayload
									.getUserId()))) {

						assignmentForUser.setPickupDate(pickupInformation
								.getPickupDate());
						assignments.add(assignmentForUser);
					}
				}
			} else {
				//assignments.add(assignmentForUser);
			}
		}

		// get trips ,if any, for the assignments

		List<BalePickupTrip> trips = balePickupTripDAO.getTripsForAssignments(
				assignments, activityListPayload.getDates());

		HashSet<PickupDetails> pickupDetailsList = new HashSet<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {
			if (trips != null && trips.size() != 0) {
				for (BalePickupTrip trip : trips) {

					if (trip.getLoadTripId() != null
							&& trip.getBalePickupAssignment()
									.getBalePickupAssignmentId()
									.equals(assignment
											.getBalePickupAssignmentId())) // filter
																			// out
																			// those
																			// assignments
																			// which
																			// are
																			// completed
																			// in
																			// Trip
																			// table.
						continue loop1;
				}
			}
			PickupDetails pickUpDetails = new PickupDetails();

			
			pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
					.getCustomerSiteId());
			pickUpDetails.setAssignmentId(assignment
					.getBalePickupAssignmentId());
			pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
					.getSiteName());
			Destination destination = new Destination();
			if (assignment.getSellCustomerSite() != null)
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
			pickUpDetails.setDestination(destination);
			String address = assignment.getBuyCustomerSite().getLocation()
					.getHouseNumber()
					+ " "
					+

					assignment.getBuyCustomerSite().getLocation().getAddress3()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress4()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress5()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getPostCode();
			pickUpDetails.setAddress(address);
			if (assignment.getFrequency() != 3)
				pickUpDetails.setPickupDate(assignment.getPickupDate());
			else {
				Date dateToday = new Date();
				SimpleDateFormat format = new SimpleDateFormat();
				format = new SimpleDateFormat("MM/dd/yyyy");
				pickUpDetails.setPickupDate(format.format(dateToday));
			}

			pickupDetailsList.add(pickUpDetails);

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {
				if (assignment.getSellCustomerSite() != null) {
					Destination destination = new Destination();
					HashSet<PickupDetails> pickupsForDestination = new HashSet<PickupDetails>();
					destination.setName(assignment.getSellCustomerSite()
							.getSiteName());
					destination.setDestinationId(assignment
							.getSellCustomerSite().getCustomerSiteId());

					for (PickupDetails pickupDetails : pickupDetailsList) {

						if (pickupDetails.getDestination().getDestinationId() == destination
								.getDestinationId()) {
							pickupsForDestination.add(pickupDetails);
						}
					}
					destination.setPickupDetails(pickupsForDestination);
					if (destination.getPickupDetails().size() != 0)
						destinations.add(destination);

				}
			}

		}

		return destinations;
	}

	

	@Override
	public List<PickupDetails> getAllDestinationsNew(
			ActivityListInputPayload activityListPayload) {

		List<String> dates=activityListPayload.getDates();
		Integer userId=activityListPayload.getUserId();
		List<DriverPickups> allDriverPickups=new ArrayList<DriverPickups>();
		List<PickupDetails> pickupDetailsList= new ArrayList<PickupDetails>();
		
		for(String date : dates){
			try{
				Integer day = BaleUtils.getDay(date);
				Integer weekNumber = BaleUtils.getWeekNumber(date);
				List<DriverPickups> driverPickups=
						balePickupAssignment.getDriverPickupsByDate(userId,date,day,weekNumber);
				
				if(driverPickups !=null && driverPickups.size()>0){
					allDriverPickups.addAll(driverPickups);
				}
			}catch(Exception e){
				LOGGER.error("Error  : getAllDestinationsNew == > " +e);
			}
		}
		
		if(allDriverPickups.size()>0){
			for(DriverPickups driverPickup : allDriverPickups){
				PickupDetails pickupDetails=new PickupDetails();
				pickupDetails.setStoreId(driverPickup.getBuyCustomerSiteId());
				pickupDetails.setAssignmentId(driverPickup.getBalePickupAssignmentId());
				pickupDetails.setStoreName(driverPickup.getBuyCustomerSiteName());
				
				String address = driverPickup.getBuyCustomerHouseNumber()
						+ " "
						+ driverPickup.getBuyCustomerAddress3()
						+ " "
						+ driverPickup.getBuyCustomerAddress4()
						+ " "
						+ driverPickup.getBuyCustomerAddress5()
						+ " "
						+ driverPickup.getBuyCustomerPostCode();
				
				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(driverPickup.getPickupDate());
				
				Destination destination=new Destination();
				destination.setDestinationId(driverPickup.getSellCustomerSiteId());
				
				pickupDetails.setDestination(destination);
				//pickupDetails.setIncident(driverPickup);
				//pickupDetails.setDestinationDrop(driverPickup);

				pickupDetailsList.add(pickupDetails);
			}
		}
		
		return pickupDetailsList;
	}

	
	private List<BalePickupAssignment> removeDuplicateSellCustomers(
			List<BalePickupAssignment> assignments) {
		HashSet<Integer> sellCustomerIds = new HashSet<Integer>();
		List<BalePickupAssignment> tempAssignments = new ArrayList<BalePickupAssignment>();
		for (int i = 0; i < assignments.size(); i++) {
			boolean equalFlag = false;
			innerLoop: for (int j = i + 1; j < assignments.size(); j++) {
				if (assignments
						.get(i)
						.getSellCustomerSite()
						.getCustomerSiteId()
						.equals(assignments.get(j).getSellCustomerSite()
								.getCustomerSiteId())) {
					tempAssignments.add(assignments.get(i));
					break innerLoop;
				}
			}

		}
		for (BalePickupAssignment assignment : tempAssignments) {
			assignments.remove(assignment);
		}

		return assignments;
	}

	private List<PickupInformation> getAllWeekNumbersAndDays(List<String> dates)
			throws ParseException {

		List<PickupInformation> weekAndDay = new ArrayList<PickupInformation>();
		for (String date : dates) {
			String format = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat);
			int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1)
				weeknumber = weeknumber - 1;

			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;

			PickupInformation pickupInformation = new PickupInformation();
			pickupInformation.setPickupDate(date);
			pickupInformation.setWeekNumber(weeknumber);
			pickupInformation.setDayNumber(day);

			weekAndDay.add(pickupInformation);
		}
		return weekAndDay;
	}

	private List<Integer> getAllDays(List<String> dates) throws ParseException {
		List<Integer> days = new ArrayList<Integer>();
		for (String date : dates) {
			String format = "MM/dd/yyyy";

			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();

			cal.setTime(dateFormat);
			// cal.setFirstDayOfWeek(cal.get(Calendar.MONDAY));
			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;
			days.add(day);
		}
		return days;
	}

	@Override
	public PickupDetailsWithMasterSets getPickupDetails(PickupDetails pickup) {

		List<BalePickupTrip> pickupTrip = balePickupAssignment.getTripDetails(
				pickup.getAssignmentId(), pickup.getUserId(),
				pickup.getPickupDate());
		List<String> dates = new ArrayList<String>();
		dates.add(pickup.getPickupDate());

		List<PickupInformation> weekNumbersAndDays = new ArrayList<PickupInformation>();

		List<Integer> days = null;
		Set<Integer> weekNumberKeys = null;
		List<Integer> weekNumbers = null;
		try {
			weekNumbersAndDays = getAllWeekNumbersAndDays(dates);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		BalePickupAssignment pickupAssignmentById = balePickupAssignment
				.getAssignmentById(pickup.getAssignmentId());
		BalePickupAssignment pickupAssignment = null;
		if (pickupAssignmentById.getFrequency() != 3) {
			pickupAssignment = balePickupAssignment.getAssignmentDetails(pickup
					.getAssignmentId(), weekNumbersAndDays.get(0)
					.getWeekNumber(), weekNumbersAndDays.get(0).getDayNumber());
		} else {
			pickupAssignment = pickupAssignmentById;
		}
		HashSet<IncidentType> incidentList = incidentType
				.getAllActiveIncidents();
		HashSet<Material> materials = new HashSet<Material>();
		PickupDetailsWithMasterSets pickupDetaiMasterSets = new PickupDetailsWithMasterSets();
		if (pickupAssignment != null) {

			List<BalePickupMaterialConfiguration> customerMaterialMappings = balePickupAssignment
					.getAllMaterialConfiguationMappingsByCustomerSite(pickupAssignment
							.getBuyCustomerSite());
			for (BalePickupMaterialConfiguration customerMaterialMapping : customerMaterialMappings) {
				materials.add(customerMaterialMapping.getMaterial());
			}
			PickupDetails pickupDetails = new PickupDetails();
			HashSet<Commodity> commodities = new HashSet<Commodity>();
			Destination destination = new Destination();
			destination.setDestinationId(pickupAssignment.getSellCustomerSite()
					.getCustomerSiteId());
			destination.setName(pickupAssignment.getSellCustomerSite()
					.getSiteName());
			String address = pickupAssignment.getBuyCustomerSite()
					.getLocation().getHouseNumber()
					+ " "
					+ pickupAssignment.getBuyCustomerSite().getLocation()
							.getAddress3()
					+ " "
					+ pickupAssignment.getBuyCustomerSite().getLocation()
							.getAddress4()
					+ " "
					+ pickupAssignment.getBuyCustomerSite().getLocation()
							.getAddress5()
					+ " "
					+ pickupAssignment.getBuyCustomerSite().getLocation()
							.getPostCode();
			pickupDetails.setAddress(address);
			pickupDetails.setStoreId(pickupAssignment.getBuyCustomerSite()
					.getCustomerSiteId());
			pickupDetails.setStoreName(pickupAssignment.getBuyCustomerSite()
					.getSiteName());
			pickupDetails.setDestination(destination);

			if (pickupTrip != null && pickupTrip.size() != 0) {

				pickupDetails
						.getBalesPicked(pickupTrip.get(0).getBalesPicked());
				pickupDetails.getBalesRemaining(pickupTrip.get(0)
						.getBalesRemaining());
				pickupDetails.setIncidentType(pickupTrip.get(0)
						.getIncidentType());
				pickupDetails.setIncident(pickupTrip.get(0).isIncident());
				pickupDetails.setComments(pickupTrip.get(0).getComments());
				pickupDetails.setAssignmentId(pickup.getAssignmentId());
				System.out.println("Trip size-->" + pickupTrip.size());
				pickupDetails.setBOL(pickupTrip.get(0).getBOL());

				Map<Integer, List<String>> tripIdWithImages = new HashMap<Integer, List<String>>();
				Set<Image> allImages = new HashSet<Image>();
				for (BalePickupTrip trip : pickupTrip) {

					Set<Image> balePickupImages = balePickupAssignment
							.getBalePickupImages(trip.getTripId());
					allImages.addAll(balePickupImages);

					if (trip.getWeekNumber().equals(
							pickupAssignment.getWeekNumber())
							&& trip.getDay().equals(pickupAssignment.getDay())) {
						Commodity commodity = new Commodity();
						commodity.setCommodityId(trip.getMaterial()
								.getMaterialId());
						commodity.setName(trip.getMaterial().getShortName());
						commodity.setBalesPicked(trip.getBalesPicked());
						commodity.setBalesRemaining(trip.getBalesRemaining());
						commodities.add(commodity);
					}
				}
				pickupDetails.setCommodities(commodities);
				pickupDetails.setTripImages(allImages);

			}

			pickupDetaiMasterSets.setIncidentTypes(incidentList);
			pickupDetaiMasterSets.setMaterials(materials);
			pickupDetaiMasterSets.setPickupDetails(pickupDetails);
		}
		return pickupDetaiMasterSets;

	}

	@Override
	public void addBalePickupAssignments(BalePickupAssignmentDTO assignmentDTO,
			UserDTO user) {
		
		
		System.out.println("=====getCustomerSiteIdList====="+assignmentDTO.getCustomerSiteIdList());

		
		if(assignmentDTO.getAction().equals("delete") && assignmentDTO.getAssignments() != null
				&& assignmentDTO.getAssignments().size() != 0 &&  assignmentDTO.getCustomerSiteIdList() != null 
				&& assignmentDTO.getCustomerSiteIdList().size() > 0){
			
			System.out.println("=====getCustomerSiteIdList=2===="+assignmentDTO.getCustomerSiteIdList());
			balePickupAssignment.disableBalePickupAssignments(assignmentDTO.getCustomerSiteIdList());

		}
		
		
		if (assignmentDTO.getAction().equals("delete")
				&& assignmentDTO.getOldAssignments() != null
				&& assignmentDTO.getOldAssignments().size() != 0
				&& assignmentDTO.getAssignments() != null
				&& assignmentDTO.getAssignments().size() != 0) {

			System.out.println("======here==================");
			balePickupAssignment.delete(assignmentDTO.getOldAssignments());

		}
		for (BalePickupAssignment assignment : assignmentDTO.getAssignments()) {
			assignment.setCreateDate(new Date());
			assignment.setUpdatedAt(new Date());
			assignment.setUpdatedBy(user.getFirstName());
			balePickupAssignment.persist(assignment);
		}

	}

	public void disableBalePickupsAssignments(
			List<BalePickupAssignment> assignments) {

		balePickupAssignment.delete(assignments);
	}

	@Override
	public Set<BalePickupAssignment> getPickupAssignments(
			AssignmentFilterDTO assignmentFilter) {
		List<CustomerSite> buyCustomerSites = new ArrayList<CustomerSite>(
				masterDataDAO
						.getAllCustomerSitesForBuyCustomer(assignmentFilter
								.getBuyCustomer(),assignmentFilter.getState()));

		Integer frequency = assignmentFilter.getFrequency();
		HashSet<Integer> dayList = assignmentFilter.getDayList();
		HashSet<Integer> monthWeekList = assignmentFilter.getMonthWeekList();

		List<User> users = new ArrayList<User>();
		users.add(assignmentFilter.getUser());
		List<CustomerSite> sellCustomerSites = null;
		if (assignmentFilter.getDestination() != null) {
			sellCustomerSites = new ArrayList<CustomerSite>(
					masterDataDAO
							.getAllCustomerSitesForBuyCustomer(assignmentFilter
									.getDestination(),assignmentFilter.getState()));

		}

		Supplier supplier = assignmentFilter.getSupplier();
		Set<BalePickupAssignment> assignments = new HashSet<BalePickupAssignment>();
		Set<BalePickupAssignment> newAssignments = new HashSet<BalePickupAssignment>();
		assignments = balePickupAssignment.getAllAssignmentForActivity(
				buyCustomerSites, users, sellCustomerSites, supplier,
				frequency, dayList, monthWeekList);


		for (BalePickupAssignment assignment : assignments) {

			boolean isCompleted = false;
			List<BalePickupTrip> trips = balePickupTripDAO
					.getTripsForAssignment(assignment
							.getBalePickupAssignmentId());
			if (trips != null && trips.size() != 0) {

				for (BalePickupTrip trip : trips) {
					if (trip.getLoadTripId() != null) {

						isCompleted = true;
					}
				}
			}
			
			
			if (!isCompleted ) {

				
				if(assignment.getFrequency() != 3){
					assignment.setNextPickupDate(BaleUtils
							.convertWeekNumberAndDayToDate(assignment
									.getWeekNumber(), assignment.getDay(),
									assignment.getBuyCustomerSite()
											.getBalePickupStartDate()));
				}else{
					DateFormat _dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					Date date =new Date();
					assignment.setNextPickupDate(_dateFormat.format(date));
				}
				
				newAssignments.add(assignment);
			}
		}

		System.out.println("====newAssignments====" + newAssignments.size());
		return newAssignments;
	}

	@Override
	public void updateBalePickupAssignmentstoAssignActivity(
			List<BalePickupAssignment> assignments, UserDTO user) {
		for (BalePickupAssignment assignment : assignments) {

			assignment.setUpdatedAt(new Date());
			assignment.setUpdatedBy(user.getFirstName());
			balePickupAssignment.merge(assignment);
		}

	}

	@Override
	public HashSet<Destination> getAllPickupsForOnCall(
			OnCallInputPayload inputPayLoad) {
		List<BalePickupAssignment> assignments = null;

		assignments = balePickupAssignment.getAllOnCallAssignments();

		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {
			if (assignment.getTrips() != null
					&& assignment.getTrips().size() != 0) {
				for (BalePickupTrip trip : assignment.getTrips()) {
					if (trip.getPickupDate().equals(
							new Date(inputPayLoad.getOnCallDate()))
							&& trip.getLoadTripId() != null) // filter out those
																// assignments
																// which are
																// completed in
																// Trip table.
						continue loop1;
				}
			}
			PickupDetails pickUpDetails = new PickupDetails();

			pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
					.getCustomerSiteId());
			pickUpDetails.setAssignmentId(assignment
					.getBalePickupAssignmentId());
			pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
					.getSiteName());
			Destination destination = new Destination();
			if (assignment.getSellCustomerSite() != null)
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
			pickUpDetails.setDestination(destination);
			String address = assignment.getBuyCustomerSite().getLocation()
					.getHouseNumber()
					+ " "
					+

					assignment.getBuyCustomerSite().getLocation().getAddress3()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress4()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress5()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getPostCode();
			pickUpDetails.setAddress(address);

			pickUpDetails.setPickupDate(inputPayLoad.getOnCallDate());

			pickupDetailsList.add(pickUpDetails);

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {

				Destination destination = new Destination();
				HashSet<PickupDetails> pickupsForDestination = new HashSet<PickupDetails>();
				if (assignment.getSellCustomerSite() != null)
					destination.setName(assignment.getSellCustomerSite()
							.getSiteName());
				if (assignment.getSellCustomerSite() != null)
					destination.setDestinationId(assignment
							.getSellCustomerSite().getCustomerSiteId());

				for (PickupDetails pickupDetails : pickupDetailsList) {

					if (pickupDetails.getDestination().getDestinationId() == destination
							.getDestinationId()) {
						pickupsForDestination.add(pickupDetails);
					}
				}
				destination.setPickupDetails(pickupsForDestination);

				destinations.add(destination);

			}
			destinations = removeDestinationsWithoutDestination(destinations);
		}

		return destinations;
	}

	
	public List<PickupDetails> getAllPickupsForOnCallNew(
			OnCallInputPayload inputPayLoad) {

		String date=inputPayLoad.getOnCallDate();
		Integer userId=inputPayLoad.getUserId();
		List<DriverPickups> allDriverPickups=new ArrayList<DriverPickups>();
		List<PickupDetails> pickupDetailsList= new ArrayList<PickupDetails>();
		
			try{
				Integer day =-1;
				Integer weekNumber =-1;
				List<DriverPickups> driverPickups=
						balePickupAssignment.getDriverPickupsByDate(userId,date,day,weekNumber);
				
				if(driverPickups !=null && driverPickups.size()>0){
					allDriverPickups.addAll(driverPickups);
				}
			}catch(Exception e){
				LOGGER.error("Error  : getAllPickupsForOnCallNew == > " +e);
			}
		
		
		if(allDriverPickups.size()>0){
			for(DriverPickups driverPickup : allDriverPickups){
				PickupDetails pickupDetails=new PickupDetails();
				pickupDetails.setStoreId(driverPickup.getBuyCustomerSiteId());
				pickupDetails.setAssignmentId(driverPickup.getBalePickupAssignmentId());
				pickupDetails.setStoreName(driverPickup.getBuyCustomerSiteName());
				
				String address = driverPickup.getBuyCustomerHouseNumber()
						+ " "
						+ driverPickup.getBuyCustomerAddress3()
						+ " "
						+ driverPickup.getBuyCustomerAddress4()
						+ " "
						+ driverPickup.getBuyCustomerAddress5()
						+ " "
						+ driverPickup.getBuyCustomerPostCode();
				
				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(driverPickup.getPickupDate());
				
				Destination destination=new Destination();
				destination.setDestinationId(driverPickup.getSellCustomerSiteId());
				
				pickupDetails.setDestination(destination);
				//pickupDetails.setIncident(driverPickup);
				//pickupDetails.setDestinationDrop(driverPickup);

				pickupDetailsList.add(pickupDetails);
			}
		}
		
		return pickupDetailsList;
	}

	
	private HashSet<Destination> removeDestinationsWithoutDestination(
			HashSet<Destination> destinations) {
		HashSet<Destination> destinationTemp = new HashSet<Destination>();
		for (Destination destination : destinations) {
			if (destination.getDestinationId() == null) {
				destinationTemp.add(destination);
			}
		}
		for (Destination destination : destinationTemp) {
			destinations.remove(destination);
		}
		return destinations;
	}

	private String convertWeekNumberAndDayToDate(int weekNumber, int day,
			Date startDate) {

		if (startDate == null) {
			startDate = new Date("01/02/1970");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
		boolean entryFlag = false;
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		Calendar startDateCalendar = Calendar.getInstance();
		Calendar todaysDatecalendar = Calendar.getInstance();
		Calendar dateToReturnCalendar = Calendar.getInstance();
		if (startDate.after(new Date())) {
			if (startDate.after(new Date(dateToreturn))) {
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				startDateCalendar.setTime(startDate);
				cal.set(Calendar.MONTH,
						startDateCalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		if ((startDate.before(new Date()) || startDate.equals(new Date()))) {

			if (new Date().getMonth() == (new Date(dateToreturn)).getMonth()
					&& new Date().getYear() == (new Date(dateToreturn))
							.getYear()
					&& new Date().getDate() == (new Date(dateToreturn))
							.getDate()) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH, todaysDatecalendar.getTime().getMonth());
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());
			}

			else if (new Date().after(new Date(dateToreturn))) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH,
						todaysDatecalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		return dateToreturn;

	}

	@Override
	public Set<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite) {
		HashSet<BalePickupAssignment> assignments = balePickupAssignment
				.getPickupAssignmentsForCustomerSite(customerSite);
		return assignments;
	}

	// for all bale pick ups
	@Override
	public List<Pickupsview> getPickUps(BalePickupFilterDTO balePickupFilter) {

		List<Pickupsview> assignments = new ArrayList<Pickupsview>();
		assignments = balePickupAssignment.getAllPickups(balePickupFilter);
		return assignments;
	}

	@Override
	public Set<PendingReport> getAllPendingPickups(
			PendingStoreReportDTO pendingStoreReportDTO) {
		Set<PendingReport> allPendingReports = new HashSet<PendingReport>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		List<String> dateList = BaleUtils.getLastOneWeekDates();
		System.out.println("dates comming are" + dateList);

		for (String date : dateList) {

			System.out.println("==============Date=======" + date);

			try {
				Integer day = BaleUtils.getDay(date);
				Integer weekNumber = BaleUtils.getWeekNumber(date);
				Integer buyCustomerId = pendingStoreReportDTO
						.getBuyCustomerId();
				Integer supplierId = pendingStoreReportDTO.getSupplierId();

				Set<PendingReport> pendingreports = balePickupAssignment
						.getPendingPickups(buyCustomerId, supplierId, day,
								weekNumber, date);

				if (!pendingreports.isEmpty()) {

					for (PendingReport report : pendingreports) {
						System.out.println(report.getPickupDate());
						report.setPickupDate(date);
					}

					allPendingReports.addAll(pendingreports);
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return allPendingReports;

	}

	// for all bale pick ups
	@Override
	public List<String> getBalePickupImages(Integer balePickupId) {

		Set<Image> balePickupImages = balePickupAssignment
				.getBalePickupImages(balePickupId);

		List<String> byteArrayList = new ArrayList<String>();

		if (balePickupImages.size() > 0) {
			for (Image image : balePickupImages) {
				String filePath = "/brtaresources" + image.getPath();
				System.out.println("====filePath===" + filePath);

				String encodedString = BaleUtils.encodedBase64String(filePath);
				if (encodedString.length() > 0) {
					byteArrayList.add(encodedString);
				}
			}
		}

		return byteArrayList;
	}

	@Override
	public String assignNewDriver(BalePickupAssignment assignment)
			throws Exception {
		String status = null;
		if (assignment == null) {
			status = ApplicationCommonConstants.INSERTION_FAILURE_MESSAGE;
		}
		balePickupAssignment.mergeAssignment(assignment);
		status = ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;
		return status;
	}

	@Override
	public PickupDetails createPickupDetailsObject(
			PickupDetailsFromExcel pickupDetailsFromExcel) {

		PickupDetails pickupDetails = null;
		Boolean checkStatus = false;
		try {
			Integer day = BaleUtils.getDay(pickupDetailsFromExcel
					.getPickupDate());
			Integer week = BaleUtils.getWeekNumber(pickupDetailsFromExcel
					.getPickupDate());
			String commodityName = pickupDetailsFromExcel.getCommodity();

			Material material = null;

			CustomerSite customerSite = customerSiteDAO
					.getBuyCustomerSite(pickupDetailsFromExcel.getStoreName());

			if (customerSite != null) {
				BalePickupAssignment baleAssignment = balePickupAssignment
						.getAssignment(customerSite.getCustomerSiteId(), day,
								week);

				if (baleAssignment != null) {
					Set<Material> materials = balePickupMaterialConfigurationDAO
							.getAllMaterial(customerSite.getCustomerSiteId());

					if (!materials.isEmpty()) {

						for (Material materialObj : materials) {
							if (materialObj.getDescription().equals(
									commodityName)) {
								material = materialObj;
								checkStatus = true;
								break;
							}
						}

						if (checkStatus == true) {
							pickupDetails = new PickupDetails();
							LOGGER.info("----inside if------");

							HashSet<Commodity> commodities = new HashSet<Commodity>();
							Commodity commodity = new Commodity();
							commodity.setCommodityId(material.getMaterialId());
							commodity.setName(material.getShortName());
							commodity.setBalesPicked(pickupDetailsFromExcel
									.getBalesPicked());
							commodity.setBalesRemaining(pickupDetailsFromExcel
									.getBalesRemaining());
							commodities.add(commodity);
							LOGGER.info("----commodity------" + commodity);
							pickupDetails.setCommodities(commodities);
							pickupDetails.setAssignmentId(baleAssignment
									.getBalePickupAssignmentId());
							pickupDetails.setPickupDate(pickupDetailsFromExcel
									.getPickupDate());
							pickupDetails.setBalesPicked(pickupDetailsFromExcel
									.getBalesPicked());
							pickupDetails
									.setBalesRemaining(pickupDetailsFromExcel
											.getBalesRemaining());
							pickupDetails.setBOL(pickupDetailsFromExcel
									.getBol());
							Long userId = baleAssignment.getUser().getUserId();
							pickupDetails.setUserId(userId.intValue());

							LOGGER.info("----userId------" + userId);

							Destination destination = new Destination();
							destination.setDestinationId(baleAssignment
									.getSellCustomerSite().getCustomerSiteId());
							destination.setName(baleAssignment
									.getSellCustomerSite().getSiteName());

							LOGGER.info("----destination------" + destination);

							pickupDetails.setDestination(destination);
						}

					} else {
						// System.out.println("---materials not found-----");
					}

				} else {
					// System.out.println("---baleAssignment not found-----");
				}

			} else {
				// System.out.println("--------customer not found========");

			}

		} catch (Exception e) {
			// System.out.println("====exception in service e=======" + e);
		}

		return pickupDetails;
	}

	private String convertWeekNumberAndDayToDate(int weekNumber, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		return dateToreturn;

	}

	@Override
	public String removeNewDriver(BalePickupAssignment assignment) {
		assignment.setDivertDate(null);
		assignment.setDivertedUser(null);
		balePickupAssignment.merge(assignment);
		return ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;
	}

	@Override
	public String getBalePickupImagesFromName(String imageName) {

		String filePath = "/brtaresources" + imageName;
		String encodedString = BaleUtils.encodedBase64String(filePath);

		return encodedString;
	}

}