package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.Image;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.PickupDetails;


@Repository
public class BalePickupTripDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	BalePickUpAssignmentDAO assignmentDAO;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickupTripDAO.class);

	
	
	
	public Integer saveTripDetails(BalePickupTrip persistentObject){
		
		Session session =  sessionFactory.getCurrentSession();
		return (Integer)session.save(persistentObject);
		
	}
	
public Integer saveImageDetails(Image persistentObject){
		
		Session session =  sessionFactory.getCurrentSession();
		return (Integer)session.save(persistentObject);
		
	}



	public List<BalePickupTrip> getAllTripsForAssignment(
			CompleteTripInputPayLoad completeTripPayLoad) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip> trips = null;
		List<BalePickupAssignment> assignments = new ArrayList<BalePickupAssignment>();
		List<Date> dates = new ArrayList<Date>();
		
		for(PickupDetails pickUpDetails: completeTripPayLoad.getCompletePickups()){
			
			assignments.add(assignmentDAO.getAssignmentById(pickUpDetails.getAssignmentId()));
			dates.add(new Date(pickUpDetails.getPickupDate()));
			
		}
	  
		Query query = session.createQuery("from BalePickupTrip where"
				+ " balePickupAssignment in :assignments  and pickupDate in :dates");
		query.setParameterList("assignments", assignments);
		query.setParameterList("dates", dates);
		//query.setParameter("userId", Long.parseLong(completeTripPayLoad.getUserId().toString()));
		trips = query.list();
		return trips;
	}



	public void completeTrip(BalePickupTrip trip) {
		Session session = sessionFactory.getCurrentSession();
		session.merge(trip);
		
	}



	public List<BalePickupTrip> getAllTripsWithMaterial(
			HashSet<Commodity> commodities, int weekNumber, int day, Integer storeId, String date
			) {
		List<BalePickupTrip> trips = null;
		Date pickupDate  = new Date(date);
		try{
		Session session = sessionFactory.getCurrentSession();
		 trips = new ArrayList<BalePickupTrip>();
		List<Integer> materialIds = new ArrayList<Integer>();
		for(Commodity commodity : commodities){
			materialIds.add(commodity.getCommodityId());
		}		
		Query query = session.createQuery("from BalePickupTrip where material.materialId in :materialids and weekNumber = :weekNumber and day= :day"
				+ " and buyCustomerSite.customerSiteId = :storeId and pickupDate = :pickupDate");
		query.setParameterList("materialids", materialIds);
		query.setParameter("weekNumber",weekNumber);
		query.setParameter("day", day);
		query.setParameter("storeId",storeId);
		query.setParameter("pickupDate", pickupDate);
	      trips = query.list();
		}
		catch(Exception e){
			LOGGER.error("Error:BalePickupTripDAO:getAllTripsWithMaterial=====>" +e);
		}
		return trips;
	}



	public void deleteTrip(Integer assignmentId, int weekNumber, int day, Integer storeId, String date,Integer userId) {
		Session session = sessionFactory.getCurrentSession();
	List<Integer>ids = new ArrayList<Integer>();
	Date pickupDate  = new Date(date);
	java.sql.Date sqlDate = new java.sql.Date(pickupDate.getTime());
		/*for(Commodity trip:assignmentId){
			ids.add(trip.getCommodityId());
		}*/
		try{
			Query query = session.createQuery("delete BalePickupTrip where balePickupAssignment.balePickupAssignmentId = :assignmentId and weekNumber = :weekNumber and day= :day"
					+ " and buyCustomerSite.customerSiteId = :storeId and pickupDate = :pickupDate and user.userId = :userId");
			query.setParameter("assignmentId", assignmentId);
			query.setParameter("weekNumber",weekNumber);
			query.setParameter("day", day);
			query.setParameter("storeId",storeId);
			query.setParameter("pickupDate", sqlDate);
			query.setParameter("userId", Long.valueOf(userId));
			query.executeUpdate();
			}
		catch(Exception e){
			LOGGER.error("Error:BalePickupTripDAO:deleteTrip=====>" +e);
		}
		
	}



	public List<BalePickupTrip> getTripsForAssignments(
			List<BalePickupAssignment> assignments,List<String>dates) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip>trips = null;
		List<Date> javaDates = new ArrayList<Date>();
		List<Integer>ids = new ArrayList<Integer>();
		for(BalePickupAssignment assignment:assignments){
			ids.add(assignment.getBalePickupAssignmentId());
		}
		for(String date:dates){
			javaDates.add(new Date(date));
		}
		
		try{
			Query query = session.createQuery("from BalePickupTrip where balePickupAssignment.balePickupAssignmentId in :ids "
					+ " and pickupDate in :dates");
			query.setParameterList("ids", ids);
			query.setParameterList("dates", javaDates);
			trips = query.list();
		}
			catch(Exception e){
				LOGGER.error("Error:BalePickupTripDAO:getTripsForAssignments=====>" +e);
			}
		
		return trips;
	}
	
	public List<BalePickupTrip> getTripsForAssignment(
			Integer assignmentId) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip>trips = null;
		List<Integer>ids = new ArrayList<Integer>();
		
		
		try{
			Query query = session.createQuery("from BalePickupTrip where balePickupAssignment.balePickupAssignmentId = :id");
			query.setParameter("id", assignmentId);
			trips = query.list();
		}
			catch(Exception e){
				LOGGER.error("Error:BalePickupTripDAO:getTripsForAssignment=====>" +e);
			}
		
		return trips;
	}
	
	public List<BalePickupTrip> getAllSavedOnlyTrips(Integer userId,
			List<String> pickupDates,List<BalePickupAssignment>assignments) {
		List<BalePickupTrip> trips = null;
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		for(String date:pickupDates){
			java.util.Date utilDate = new java.util.Date(date);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			dates.add(sqlDate);
		}		
		
		Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery("from BalePickupTrip where user.userId= :userId and pickupDate in "
					+ ":pickupDates and loadTripId is null and childLoadTripId is null and balePickupAssignment in :assignments");
			query.setParameter("userId", Long.parseLong(userId.toString()));
			query.setParameterList("pickupDates", dates);
			query.setParameterList("assignments", assignments);
	        trips = query.list();
			
		
		
		return trips;
	}

	public void deleteImagesForTrip(Integer assignmentId, int weekNumber,
			int day, Integer storeId, String date, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer>ids = new ArrayList<Integer>();
		Date pickupDate  = new Date(date);
		java.sql.Date sqlDate = new java.sql.Date(pickupDate.getTime());
		List<BalePickupTrip> trips = new ArrayList<BalePickupTrip>();
		List<Integer> tripIds = new ArrayList<Integer>();
			try{
			BalePickupAssignment assignment =	assignmentDAO.getAssignmentById(assignmentId);
			Query query = session.createQuery("from BalePickupTrip where balePickupAssignment = :assignment and weekNumber = :weekNumber and day= :day"
					+ " and buyCustomerSite.customerSiteId = :storeId and pickupDate = :pickupDate and user.userId = :userId");
			query.setParameter("assignment", assignment);
			query.setParameter("weekNumber",weekNumber);
			query.setParameter("day", day);
			query.setParameter("storeId",storeId);
			query.setParameter("pickupDate", sqlDate);
			query.setParameter("userId", Long.valueOf(userId));
			trips = query.list();
			
			for(BalePickupTrip trip :trips){
				tripIds.add(trip.getTripId());
				
			}
			Query queryForImages = session.createQuery("delete Image i where i.tripId in :tripids" );
			queryForImages.setParameterList("tripids", tripIds);
			queryForImages.executeUpdate();
			}
			catch(Exception e){
				LOGGER.error("Error:BalePickupTripDAO:deleteImagesForTrip=====>" +e);
			}
			
		}
		
	}


