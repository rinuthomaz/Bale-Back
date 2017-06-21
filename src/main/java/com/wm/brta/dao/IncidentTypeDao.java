package com.wm.brta.dao;

import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.IncidentType;


@Repository
public class IncidentTypeDao {
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IncidentTypeDao.class);


	public List<IncidentType> getAllIncidents() {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		query = session.createQuery("from IncidentType order by createdDate desc");

		List<IncidentType> users = null;
		try{
			users = query.list();
		}
		catch(RuntimeException e){
		LOGGER.error("Error:IncidentTypeDao:getAllIncidents=====>" +e);
		}
		return users;
	}

	public long save(IncidentType persistenceObject) {
		
		Session session = sessionFactory.getCurrentSession();
		int id = 0;
		
		
		try {
			
			id = (int) session.save(persistenceObject);
			
			
		} catch (RuntimeException e) {
			
			
		LOGGER.error("Error:IncidentTypeDao:save=====>" +e);
		} finally {
			
		}
		return id;
	}

	public void merge(IncidentType incident) {
		
		System.out.println("==incident==="+incident);
		
		Session session = sessionFactory.getCurrentSession();
		try {
			
			session.merge(incident);
			
			
		} catch (RuntimeException e) {
			
			
		LOGGER.error("Error:IncidentTypeDao:merge=====>" +e);
		} finally {
			
		}
		
		
	}

	public HashSet<IncidentType> getAllActiveIncidents() {
Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		
		query = session.createQuery("from IncidentType where enabled=true order by createdDate desc");
		
		
		List<IncidentType> users = null;
		HashSet<IncidentType> hashedIncidents = null;
		try{
			users = query.list();
			hashedIncidents = new HashSet<IncidentType>(users);
		}
		catch(RuntimeException e){
		LOGGER.error("Error:IncidentTypeDao:getAllActiveIncidents=====>" +e);
		}
		return hashedIncidents;
	}
	
	
	
	public Boolean checkIncidenttypeUnique(
			String incidentDescription) {
		Session session = sessionFactory.getCurrentSession();
		List<IncidentType> incidentTypes = null;
		Boolean status= false;
		Query query = session.createQuery("from IncidentType where incidentDescription = :incidentDescription order by createdDate desc");
		try {
			
			query.setParameter("incidentDescription",incidentDescription);
			incidentTypes = query.list();
			if(incidentTypes.size() > 0){
				status=true;
			}
			} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:checkIncidenttypeUnique=====>" +e);
		} finally {
			
		}
		return status;
		
	}
	
	
	}
	
	
	
	


