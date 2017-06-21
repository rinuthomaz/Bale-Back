package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.MaterialInfo;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.domain.SavedPickups;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.Destination;

@Repository
public class BalePickUpAssignmentDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickUpAssignmentDAO.class);

	public List<BalePickupAssignment> getAllAssignments(int userId) {

		List<BalePickupAssignment> assignments = null;
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session
					.createQuery("from BalePickupAssignment where (userId= :userId or divertedUserId= :divertedUserId "
							+ ") and " + "disabled= :disabledFlag");
			query.setParameter("userId", userId);
			query.setParameter("divertedUserId", userId);

			query.setParameter("disabledFlag", false);
			assignments = query.list();
		}

		catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllAssignments=====>"
					+ e);

		}
		return assignments;

	}

	public BalePickupAssignment getAssignmentById(int id) {

		List<BalePickupAssignment> assignments = null;

		Session session = sessionFactory.getCurrentSession();

		System.out.println("====getAssignmentById===" + id);

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where id = :id and disabled = false");
			query.setParameter("id", id);

			assignments = query.list();
		}

		catch (RuntimeException e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getAssignmentById=====>"
					+ e);
		}
		return assignments.get(0);

	}

	public List<BalePickupTrip> getTripDetails(Integer assignmentId,
			int userId, String pickupDate) {
		List<BalePickupTrip> trips = new ArrayList<BalePickupTrip>();
		try {
			Session session = sessionFactory.getCurrentSession();
			BalePickupAssignment assignment = getAssignmentById(assignmentId);
			Date formatDate = new Date(pickupDate);

			Query query = session
					.createQuery("from BalePickupTrip"
							+ " where  balePickupAssignment = :assignment and userId = :userId and pickupDate = :formatDate");

			query.setParameter("userId", userId);
			query.setParameter("formatDate", formatDate);
			query.setParameter("assignment", assignment);
			trips = query.list();
		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getTripDetails=====>"
					+ e);
		}
		if (trips.size() != 0)
			return trips;
		else
			return null;
	}

	public BalePickupAssignment getAssignmentDetails(int assignmentId,
			int weekNumber, int day) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("from BalePickupAssignment"
						+ " where balePickupAssignmentId = :assignmentId and weekNumber = :weekNumber and day= :day and disabled = false ");
		query.setParameter("weekNumber", weekNumber);
		query.setParameter("day", day);
		query.setParameter("assignmentId", assignmentId);
		List<BalePickupAssignment> assignments = query.list();
		if (assignments != null && assignments.size() != 0)
			return assignments.get(0);
		else
			return null;
	}

	public List<BalePickupMaterialConfiguration> getAllMaterialConfiguationMappingsByCustomerSite(
			CustomerSite customerSite) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupMaterialConfiguration> materialMappings = null;

		Query query = session
				.createQuery("from BalePickupMaterialConfiguration where buyCustomerSiteId= :customerId");
		query.setParameter("customerId", customerSite.getCustomerSiteId());
		try {
			materialMappings = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllMaterialConfiguationMappingsByCustomerSite=====>"
					+ e);
		}
		return materialMappings;
	}

	public void persist(BalePickupAssignment assignment) {

		Session session = sessionFactory.getCurrentSession();
		try {
			CustomerSite customerSite = (CustomerSite) session.get(
					"com.wm.brta.domain.CustomerSite", assignment
							.getBuyCustomerSite().getCustomerSiteId());
			customerSite.setHasFrequency(true);
			session.merge(customerSite);
			session.persist(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:persist=====>" + e);
		}

	}

	public Set<BalePickupAssignment> getAllAssignmentForActivity(
			List<CustomerSite> buyCustomerSites, List<User> users,
			List<CustomerSite> sellCustomerSites, Supplier supplier,
			Integer frequency, HashSet<Integer> dayList,
			HashSet<Integer> monthWeekList) {
		Session session = sessionFactory.getCurrentSession();

		String queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
				+ "and supplier = :supplier and (user = :user or user is null) and disabled = false";

		if ((frequency != null) && (!dayList.isEmpty())) {
			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier = :supplier and (user = :user or user is null) and "
					+ "frequency = :frequency and day in :dayList and disabled = false";
		} else if ((frequency != null) && (!monthWeekList.isEmpty())) {

			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier = :supplier and (user = :user or user is null) and "
					+ "frequency = :frequency and weekNumber in :monthWeekList and disabled = false";
		} else if (frequency != null) {

			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier = :supplier and (user = :user or user is null) and frequency = :frequency"
					+ " and disabled = false";
		}

		Set<BalePickupAssignment> resultAssignments = new HashSet<BalePickupAssignment>();

		try {
			Query query = session.createQuery(queryStr);

			query.setParameterList("buyCustomerSites", buyCustomerSites);
			query.setParameter("user", users.get(0));
			query.setParameter("supplier", supplier);

			if (frequency != null) {
				query.setParameter("frequency", frequency);
			}
			if (!dayList.isEmpty()) {
				query.setParameterList("dayList", dayList);
			}
			if (!monthWeekList.isEmpty()) {
				query.setParameterList("monthWeekList", monthWeekList);
			}

			List<BalePickupAssignment> assignments = query.list();
			resultAssignments = new HashSet<BalePickupAssignment>(assignments);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllAssignmentForActivity=====>"
					+ e);
		}
		return resultAssignments;

	}

	public void merge(BalePickupAssignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.merge(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:merge=====>" + e);

		}

	}

	public List<BalePickupAssignment> getAllOnCallAssignments() {
		List<BalePickupAssignment> assignments = null;

		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where  weekNumber = :weekNumber and day = :day"
							+ " and disabled= :disabledFlag");
			query.setParameter("weekNumber", -1);
			query.setParameter("day", -1);
			query.setParameter("disabledFlag", false);
			assignments = query.list();
		}

		catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllOnCallAssignments=====>"
					+ e);

		}
		return assignments;
	}

	public HashSet<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite) {

		List<BalePickupAssignment> assignments = null;
		HashSet<BalePickupAssignment> hashedResults = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where buyCustomerSite.customerSiteId= :customerSiteId and"
							+ " disabled = false");
			query.setParameter("customerSiteId",
					customerSite.getCustomerSiteId());
			assignments = query.list();
			hashedResults = new HashSet<BalePickupAssignment>(assignments);

		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getPickupAssignmentsForCustomerSite=====>"
					+ e);

		}
		return hashedResults;
	}

	public void delete(List<BalePickupAssignment> assignments) {

		System.out.println("======delete dao==================");

		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();
		for (BalePickupAssignment balePickupAssignment : assignments) {
			ids.add(balePickupAssignment.getBalePickupAssignmentId());
		}

		System.out.println("--ids----" + ids);
		if (ids.size() <= 0) {

			return;
		}

		try {
			Query query = session
					.createQuery("update BalePickupAssignment balePickupAss set balePickupAss.disabled = true where"
							+ " balePickupAss.balePickupAssignmentId in :ids");
			query.setParameterList("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:delete=====>" + e);
		} finally {

		}

	}

	public void disableBalePickupAssignments(List<Integer> customerSiteIds) {

		System.out.println("======delete dao=11================="
				+ customerSiteIds);

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createQuery("update BalePickupAssignment balePickupAss set balePickupAss.disabled = true where"
							+ " balePickupAss.buyCustomerSite.customerSiteId in :ids");
			query.setParameterList("ids", customerSiteIds);
			query.executeUpdate();
		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:disableBalePickupAssignments=====>"
					+ e);
		} finally {

		}

	}

	public Set<PendingReport> getPendingPickups(Integer buyCustomerId,
			Integer supplierId, Integer day, Integer weekNumber,
			String pickupDate) {

		List<PendingReport> assignments = null;
		Set<PendingReport> resultBalePickups = null;

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createSQLQuery(
							"{CALL GetPendingPickups"
									+ "(:buyCustomerId,:supplierId,:pickupDate,:weekNumber,:day)}")
					.addEntity(PendingReport.class);

			query.setParameter("buyCustomerId", buyCustomerId);
			query.setParameter("supplierId", supplierId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("weekNumber", weekNumber);
			query.setParameter("day", day);

			List<PendingReport> result = query.list();

			resultBalePickups = new HashSet<PendingReport>(result);
			LOGGER.info(" resultBalePickups " + resultBalePickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getPendingPickups=====>"
					+ e);
		}

		return resultBalePickups;

	}

	public List<DriverPickups> getDriverPickupsByDate(Integer driverId,
			String pickupDate, Integer day, Integer weekNumber) {

		Session session = sessionFactory.getCurrentSession();
		List<DriverPickups> driverPickups = new ArrayList<DriverPickups>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetDriverPickupsByDate"
							+ "(:driverId,:pickupDate,:weekNumber,:day)}")
					.addEntity(DriverPickups.class);

			query.setParameter("driverId", driverId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("weekNumber", weekNumber);
			query.setParameter("day", day);

			driverPickups = query.list();
			LOGGER.info(" driverPickups " + driverPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getDriverPickupsByDate=====>"
					+ e);
		}

		return driverPickups;

	}

	public List<SavedPickups> getSavedOnlyTrip(Integer driverId,
			String pickupDate) {

		Session session = sessionFactory.getCurrentSession();
		List<SavedPickups> savedPickups = new ArrayList<SavedPickups>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetSavedPickups" + "(:driverId,:pickupDate)}")
					.addEntity(SavedPickups.class);

			query.setParameter("driverId", driverId);
			query.setParameter("pickupDate", pickupDate);

			savedPickups = query.list();
			LOGGER.info(" driverPickups " + savedPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getSavedOnlyTrip=====>"
					+ e);
		}

		return savedPickups;

	}

	public List<MaterialInfo> getMaterialByAssignmentId(Integer assignmehtaId,
			String pickupDate) {

		Session session = sessionFactory.getCurrentSession();
		List<MaterialInfo> savedPickups = new ArrayList<MaterialInfo>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetMaterialsByAssignment"
							+ "(:assignmehtaId,:pickupDate)}").addEntity(
					MaterialInfo.class);

			query.setParameter("assignmehtaId", assignmehtaId);
			query.setParameter("pickupDate", pickupDate);

			savedPickups = query.list();
			LOGGER.info(" driverPickups " + savedPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getMaterialByAssignmentId=====>"
					+ e);
		}

		return savedPickups;

	}

	public List<Pickupsview> getAllPickups(BalePickupFilterDTO balePickupFilter) {
		Session session = sessionFactory.getCurrentSession();

		Integer buycustomerId =null;
		if(balePickupFilter.getBuyCustomer()!=null){
			 buycustomerId = balePickupFilter.getBuyCustomer()
						.getCustomerId();
		}
		
		Integer supplierId =null;
		if(balePickupFilter.getSupplier()!=null){
			supplierId = balePickupFilter.getSupplier()
						.getSupplierId();
		}
		
		Date startDate = balePickupFilter.getStartDate();
		Date endDate = balePickupFilter.getEndDate();
		IncidentType incidentType = balePickupFilter.getIncidentType();
		String buyCustomerAddress4 = null;
		String description = null;
		String stringQuery = null;
		stringQuery = "from Pickupsview where pickupDate between :startDate and :endDate ";
		
		if(buycustomerId !=null){
			stringQuery=stringQuery+ " and buyCustomerId = :buycustomerid ";
		}
		
		if(supplierId !=null){
			stringQuery=stringQuery+ " and supplierId = :supplierid ";
		}
		
		if (incidentType != null) {
			description = incidentType.getIncidentDescription();
			if ((description != null) && (description.equals("All Incidents")))
			stringQuery=stringQuery+ " and  incidentType is not null ";
			else
			stringQuery=stringQuery+ " and incidentType = :incidentType ";

		}

		if (balePickupFilter.getState() != null) {
			buyCustomerAddress4 = balePickupFilter.getState();
			stringQuery=stringQuery +" and buyCustomerAddress4= :buyCustomerAddress4  ";
		}
		Query query = session.createQuery(stringQuery);
		
		
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		if(buycustomerId!=null){
			query.setParameter("buycustomerid", buycustomerId);
		}
		
		if(supplierId!=null){
			query.setParameter("supplierid", supplierId);
		}
		
		if ((incidentType != null)
				&& ((description != null) && (!description
						.equals("All Incidents")))) {
			query.setParameter("incidentType", incidentType.getIncidentTypeId());
		}
		
		if(buyCustomerAddress4!=null){
			query.setParameter("buyCustomerAddress4", buyCustomerAddress4);
		}
		

		List<Pickupsview> pickupList = query.list();
		return pickupList;

	}

	public Set<Image> getBalePickupImages(Integer balePickupId) {

		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from Image where tripId = :balePickupId ";

		Query query = session.createQuery(stringQuery);
		query.setParameter("balePickupId", balePickupId);

		List<Image> imageList = query.list();

		Set<Image> resultBalePickupImages = new HashSet<Image>(imageList);

		return resultBalePickupImages;

	}

	public void mergeAssignment(BalePickupAssignment assignment) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.merge(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:mergeAssignment=====>"
					+ e);
		}

	}

	public HashSet<Destination> getDestination(String storeName) {
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from Pickupsview where buyCustomerSiteName = :storeName ";

		Query query = session.createQuery(stringQuery);

		query.setParameter("storeName", storeName);

		List<Pickupsview> destinationList = query.list();

		HashSet<Destination> destinations = new HashSet<Destination>();

		for (Pickupsview pickupsview : destinationList) {
			Destination destination = new Destination();
			destination.setName(pickupsview.getSellCustomerSiteName());
			destination.setDestinationId(pickupsview.getSellCustomerSiteId());
			destinations.add(destination);
		}

		return destinations;

	}

	public BalePickupAssignment getAssignment(Integer customerSiteId,
			Integer day, Integer weekNumber) {

		BalePickupAssignment balePickupAssignment = null;
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from BalePickupAssignment where buyCustomerSite.customerSiteId = :customerSiteId and"
				+ " day = :day and weekNumber = :weekNumber and disabled = false";

		Query query = session.createQuery(stringQuery);

		query.setParameter("customerSiteId", customerSiteId);
		query.setParameter("day", day);
		query.setParameter("weekNumber", weekNumber);

		List<BalePickupAssignment> balePickupAssignmentList = query.list();

		if (!balePickupAssignmentList.isEmpty()) {
			balePickupAssignment = balePickupAssignmentList.get(0);
		}

		return balePickupAssignment;
	}

}