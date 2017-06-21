package com.wm.brta.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.wm.brta.config.ConfigValues;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.ConfigurationDao;
import com.wm.brta.dao.IncidentTypeDao;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.MaterialInfo;
import com.wm.brta.domain.SavedPickups;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.util.BaleUtils;

@Transactional
@Component("baleRouteTripService")
public class BaleRouteTripServiceImpl implements BaleRouteTripService {

	@Autowired
	BalePickUpAssignmentDAO balePickupAssignment;

	@Autowired
	IncidentTypeDao incidentType;

	@Autowired
	MasterDataDao masterDataDAO;

	@Autowired
	BalePickupTripDAO balePickupTripDAO;

	@Autowired
	ConfigurationDao configDao;

	@Autowired
	UserDao userDao;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickupServiceImpl.class);

	@Override
	public String addTripDetails(PickupDetails pickupDetails) {

		int id = 0;

		String status = null;
		boolean errorFlag = false;

		try {

			int day = getDay(pickupDetails.getPickupDate());
			int weekNumber = getWeekNumber(pickupDetails.getPickupDate());

			BalePickupAssignment assignmentById = balePickupAssignment
					.getAssignmentById(pickupDetails.getAssignmentId());
			BalePickupAssignment assignment = null;
			if (assignmentById.getFrequency() != 3)
				assignment = balePickupAssignment.getAssignmentDetails(
						pickupDetails.getAssignmentId(), weekNumber, day);
			else {
				assignment = assignmentById;
				day = -1;
				weekNumber = -1;
			}
			BalePickupAssignment newAssignment = new BalePickupAssignment();
			newAssignment.setBalePickupAssignmentId(assignment
					.getBalePickupAssignmentId());
			if (assignment == null) {
				errorFlag = true;
			}
			if (assignment != null) {

				balePickupTripDAO.deleteTrip(pickupDetails.getAssignmentId(),
						weekNumber, day, pickupDetails.getStoreId(),
						pickupDetails.getPickupDate(),
						pickupDetails.getUserId());

				// delete images for the assignment
				// balePickupTripDAO.deleteImagesForTrip(pickupDetails.getAssignmentId(),weekNumber,day,pickupDetails.getStoreId()
				// ,pickupDetails.getPickupDate(),pickupDetails.getUserId());

				for (Commodity commodity : pickupDetails.getCommodities()) {

					BalePickupTrip tripDetails = new BalePickupTrip();
					Material material = new Material();
					material.setMaterialId(commodity.getCommodityId());
					material.setDescription(commodity.getName());

					BalePickupMaterialConfiguration materialConfig = configDao
							.getMaterialConfigByCustomerSiteAndMaterial(
									assignment.getBuyCustomerSite(), material);
					tripDetails.setMaterial(material);
					tripDetails.setBalesPicked(commodity.getBalesPicked());
					tripDetails
							.setBalesRemaining(commodity.getBalesRemaining());
					tripDetails.setAverageBaleWeight(materialConfig
							.getAvgBaleWeight().floatValue());

					tripDetails.setBalePickupAssignment(newAssignment);
					tripDetails.setBOL(pickupDetails.getBOL());
					tripDetails.setBuyCustomerSite(assignment
							.getBuyCustomerSite());
					tripDetails.setComments(pickupDetails.getComments());
					tripDetails.setSellCustomerSite(assignment
							.getSellCustomerSite());
					Date date = new Date(pickupDetails.getPickupDate());
					tripDetails.setPickupDate(date);
					tripDetails.setCreateDate(new Date());
					tripDetails.setSupplier(assignment.getSupplier());
					tripDetails.setPickedBaleTotalWeight(0.0f);
					tripDetails.setIncident(pickupDetails.isIncident());
					tripDetails
							.setIncidentType(pickupDetails.getIncidentType());
					if (assignment.getFrequency() != 3) {
						tripDetails.setWeekNumber(getWeekNumber(pickupDetails
								.getPickupDate()));
						tripDetails
								.setDay(getDay(pickupDetails.getPickupDate()));
					} else {
						tripDetails.setWeekNumber(-1);
						tripDetails.setDay(-1);
					}
					tripDetails.setUpdatedAt(new Date());
					if (assignment.getUser() != null
							&& assignment.getDivertedUser() == null)
						tripDetails.setUser(assignment.getUser());
					else if (assignment.getUser() != null
							&& assignment.getDivertedUser() != null)
						tripDetails.setUser(assignment.getDivertedUser());
					else {

						User user = userDao.getUserById(Long
								.valueOf(pickupDetails.getUserId()));
						tripDetails.setUser(user);
					}

					id = balePickupTripDAO.saveTripDetails(tripDetails);

					if (pickupDetails.isIncident() && id != 0) {
						/*
						 * File imagesDir = new
						 * File(ConfigValues.getConfigValue(
						 * "INCIDENT_IMAGE_LOCATION"), "images");
						 * if(!imagesDir.exists() || !imagesDir.isDirectory()){
						 * imagesDir.setWritable(true); imagesDir.mkdir(); }
						 */
						try {
							int count = 1;
							for (String image : pickupDetails.getImages()) {

								System.out
										.println("---before incode==========="
												+ image);

								byte[] imageByte = Base64.decodeBase64(image);

								System.out
										.println("---before incode==========="
												+ imageByte.length);

								String path = "/INCIDENT_IMAGE_" + id + "_"
										+ new Date().getDate() + "_"
										+ new Date().getMonth() + "_"
										+ new Date().getHours() + "_" + count;

								System.out.println("--path--" + path);

								File file = new File("/brtaresources" + path);

								count = count + 1;
								FileOutputStream outputStream = new FileOutputStream(
										file);
								outputStream.write(imageByte);
								outputStream.close();

								Image imageObject = new Image();
								imageObject.setTripId(id);
								// imageObject.setImageUId(id);

								System.out.println("==path====" + path);

								imageObject.setPath(path);
								imageObject.setCreateDate(new Date());
								imageObject.setEnabled(true);
								imageObject.setUpdatedAt(new Date());
								imageObject.setUpdatedBy("mobile client");
								balePickupTripDAO.saveImageDetails(imageObject);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					if (id == 0) {
						errorFlag = true;
						status = "Insertion Failure";
						break;
					}
					id = balePickupTripDAO.saveTripDetails(tripDetails);

					if (pickupDetails.isIncident() && id != 0) {
						/*
						 * File imagesDir = new
						 * File(ConfigValues.getConfigValue(
						 * "INCIDENT_IMAGE_LOCATION"), "images");
						 * if(!imagesDir.exists() || !imagesDir.isDirectory()){
						 * imagesDir.setWritable(true); imagesDir.mkdir(); }
						 */
						try {
							int count = 1;
							for (String image : pickupDetails.getImages()) {

								byte[] imageByte = Base64.decodeBase64(image);

								String path = "/INCIDENT_IMAGE_" + id + "_"
										+ new Date().getDate() + "_"
										+ new Date().getMonth() + "_"
										+ new Date().getHours() + "_" + count;

								System.out.println("--path--" + path);

								File file = new File("/brtaresources" + path);

								count = count + 1;
								FileOutputStream outputStream = new FileOutputStream(
										file);
								outputStream.write(imageByte);
								outputStream.close();

								Image imageObject = new Image();
								imageObject.setTripId(id);
								// imageObject.setImageUId(id);

								imageObject.setPath(path);
								imageObject.setCreateDate(new Date());
								imageObject.setEnabled(true);
								imageObject.setUpdatedAt(new Date());
								imageObject.setUpdatedBy("mobile client");
								balePickupTripDAO.saveImageDetails(imageObject);

							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = "Insertion Failure";
		}

		if (!errorFlag)
			status = "Successfull";
		else
			status = "Insertion Failure";
		return status;
	}

	private Integer getWeekNumber(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);

		if (cal.get(Calendar.DAY_OF_WEEK) == 1)
			weeknumber = weeknumber - 1;

		return weeknumber;
	}

	private Integer getDay(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0)
			day = 7;

		return day;
	}

	@Override
	public void completeTrip(CompleteTripInputPayLoad completeTripPayLoad) {
		List<BalePickupTrip> trips = balePickupTripDAO
				.getAllTripsForAssignment(completeTripPayLoad);
		int count = 0;

		for (BalePickupTrip trip : trips) {
			count++;

			String loadTripId = generateParentTripId(trip.getSellCustomerSite()
					.getSiteName(), trip.getPickupDate(), trip.getUser()
					.getFirstName(), trip.getUser().getLastName(),
					trip.getTripId());

			loadTripId = loadTripId + trips.size();

			String childTripId = loadTripId + "-" + count;
			trip.setLoadTripId(loadTripId);

			trip.setChildLoadTripId(childTripId);
			balePickupTripDAO.completeTrip(trip);

		}
	}

	private String generateParentTripId(String sellCustomerSiteName, Date date,
			String firstName, String lastName, Integer assignmentId) {

		System.out.println("----date-----" + date);
		String id = "";
		String siteNamePrefix = sellCustomerSiteName.substring(0, 3)
				.toUpperCase();

		String userPrefix = lastName.substring(0, 1).toUpperCase()
				+ firstName.substring(0, 1).toUpperCase();
		SimpleDateFormat format = new SimpleDateFormat();
		format = new SimpleDateFormat("MMddyy");
		String datePrefix = format.format(date);
		id = siteNamePrefix + userPrefix + datePrefix;

		System.out.println("===id===" + id);
		return id;

	}

	@Override
	public HashSet<Destination> getSavedOnlyTrips(
			ActivityListInputPayload inputPayload) {
		ListMultimap<Integer, Integer> weekNumbersAndDays = null;
		List<Integer> days = null;
		try {
			weekNumbersAndDays = getAllWeekNumbersAndDays(inputPayload
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
				.getAllAssignments(inputPayload.getUserId());
		assignmentsOnCall = balePickupAssignment.getAllOnCallAssignments();
		for (String date : inputPayload.getDates()) {
			System.out.println("Date coming->" + date);
			Date dateToday = new Date();
			SimpleDateFormat format = new SimpleDateFormat();
			format = new SimpleDateFormat("MM/dd/yyyy");
			String dateFormat = format.format(dateToday);
			if (dateFormat.equals(date)) {
				inputHasTodayDate = true;
			}

		}
		if (inputHasTodayDate) // get on call assignments only if payload
								// contains current date.
			assignmentsForUser.addAll(assignmentsOnCall);
		System.out.println("Assignments-->" + assignmentsForUser.size());
		// ignore assignments diverted to another user

		for (BalePickupAssignment assignmentForUser : assignmentsForUser) {
			if (assignmentForUser.getFrequency() == 3) {
				assignments.add(assignmentForUser);
			}

			Set<Integer> entries = weekNumbersAndDays.keySet();
			for (Integer entry : entries) {
				List<Integer> valuesForEachKey = weekNumbersAndDays.get(entry);
				for (Integer value : valuesForEachKey) {

					if (assignmentForUser.getWeekNumber().equals(entry)
							&& assignmentForUser.getDay().equals(value)
							&& (assignmentForUser.getDivertedUser() == null || (assignmentForUser
									.getDivertedUser() != null && assignmentForUser
									.getDivertedUser().getUserId() == inputPayload
									.getUserId()))) {

						assignments.add(assignmentForUser);
					}
				}
			}
		}

		// get trips ,if any, for the assignments

		List<BalePickupTrip> trips = balePickupTripDAO.getTripsForAssignments(
				assignments, inputPayload.getDates());

		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {
			boolean hasTrip = false;
			HashSet<Commodity> commodities = new HashSet<Commodity>();
			// System.out.println("Trip-->" + assignment.getTrips());
			if (trips != null && trips.size() != 0) {
				for (BalePickupTrip trip : trips) {

					if (trip.getLoadTripId() == null
							&& trip.getBalePickupAssignment()
									.getBalePickupAssignmentId()
									.equals(assignment
											.getBalePickupAssignmentId())
							&& assignment.getWeekNumber().equals(
									trip.getWeekNumber())
							&& assignment.getDay().equals(trip.getDay())) {

						Commodity commodity = new Commodity();
						commodity.setBalesPicked(trip.getBalesPicked());
						commodity.setBalesRemaining(trip.getBalesRemaining());
						commodity.setCommodityId(trip.getMaterial()
								.getMaterialId());
						commodity.setName(trip.getMaterial().getDescription());
						commodities.add(commodity);
						if (trip.getBalePickupAssignment()
								.getBalePickupAssignmentId()
								.equals(assignment.getBalePickupAssignmentId())) {
							hasTrip = true;

						}
					}
				}
			}
			if (hasTrip) {
				PickupDetails pickUpDetails = new PickupDetails();

				pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
						.getCustomerSiteId());
				pickUpDetails.setAssignmentId(assignment
						.getBalePickupAssignmentId());
				pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
						.getSiteName());
				Destination destination = new Destination();
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
				pickUpDetails.setDestination(destination);
				String address = assignment.getBuyCustomerSite().getLocation()
						.getHouseNumber()
						+ " "
						+

						assignment.getBuyCustomerSite().getLocation()
								.getAddress3()
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
				pickUpDetails.setCommodities(commodities);
				if (assignment.getFrequency() != 3)
					pickUpDetails.setPickupDate(BaleUtils
							.convertWeekNumberAndDayToDate(
									assignment.getWeekNumber(),
									assignment.getDay()));
				else {
					Date dateToday = new Date();
					SimpleDateFormat format = new SimpleDateFormat();
					format = new SimpleDateFormat("MM/dd/yyyy");
					pickUpDetails.setPickupDate(format.format(dateToday));
				}

				pickupDetailsList.add(pickUpDetails);
			}

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {
				if (balePickupTripDAO.getTripsForAssignment(
						assignment.getBalePickupAssignmentId()).size() != 0) {

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

	public List<PickupDetails> getSavedOnlyTripsNew(
			ActivityListInputPayload inputPayload) {
		List<String> dates = inputPayload.getDates();
		Integer userId = inputPayload.getUserId();
		List<SavedPickups> allSavedPickups = new ArrayList<SavedPickups>();
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		for (String date : dates) {
			try {
				List<SavedPickups> savedPickups = balePickupAssignment
						.getSavedOnlyTrip(userId, date);

				if (savedPickups != null && savedPickups.size() > 0) {
					allSavedPickups.addAll(savedPickups);
				}
			} catch (Exception e) {
				LOGGER.error("Error  : getAllDestinationsNew == > " + e);
			}
		}

		if (allSavedPickups.size() > 0) {
			for (SavedPickups pickups : allSavedPickups) {

				List<MaterialInfo> materials = balePickupAssignment
						.getMaterialByAssignmentId(
								pickups.getBalePickupAssignmentId(),
								pickups.getPickupDate());
				HashSet<Commodity> commodities = new HashSet<Commodity>();
				for (MaterialInfo material : materials) {
					Commodity commodity = new Commodity();
					commodity.setBalesPicked(material.getBalesPicked());
					commodity.setBalesRemaining(material.getBalesRemaining());
					commodity.setName(material.getShortName());
					commodity.setCommodityId(material.getMaterialId());
					commodities.add(commodity);

				}

				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setCommodities(commodities);
				pickupDetails.setStoreId(pickups.getBuyCustomerSiteId());
				pickupDetails.setAssignmentId(pickups
						.getBalePickupAssignmentId());
				pickupDetails.setStoreName(pickups.getBuyCustomerSiteName());

				String address = pickups.getBuyCustomerHouseNumber() + " "
						+ pickups.getBuyCustomerAddress3() + " "
						+ pickups.getBuyCustomerAddress4() + " "
						+ pickups.getBuyCustomerAddress5() + " "
						+ pickups.getBuyCustomerPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(pickups.getPickupDate());

				Destination destination = new Destination();
				destination.setDestinationId(pickups.getSellCustomerSiteId());

				pickupDetails.setDestination(destination);
				// pickupDetails.setIncident(driverPickup);
				// pickupDetails.setDestinationDrop(driverPickup);

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

	private ListMultimap<Integer, Integer> getAllWeekNumbersAndDays(
			List<String> dates) throws ParseException {
		ListMultimap<Integer, Integer> weekAndDay = ArrayListMultimap.create();
		for (String date : dates) {
			String format = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat);
			// cal.setFirstDayOfWeek(cal.get(Calendar.MONDAY));
			int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1)
				weeknumber = weeknumber - 1;

			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;
			weekAndDay.put(weeknumber, day);
			// weekNumbersAndDates.add(weekAndDay);
		}
		return weekAndDay;
	}
}
