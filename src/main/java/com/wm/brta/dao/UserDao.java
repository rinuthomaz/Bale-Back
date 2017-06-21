package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.Role;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.domain.UserAccount;


@Repository
public class UserDao {
	
	@Autowired
    protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
	public long save(User transientInstance,Role
			role) {
//	Query queryDriver=null;
		Session session = sessionFactory.getCurrentSession();
		long id = 0;
		Transaction tx = session.beginTransaction();
		try {
			
			id = (long) session.save(transientInstance);
			tx.commit();
			} catch (RuntimeException e) {
				LOGGER.error("Error:UserDao:save=====>" +e);
		} saveDriver(id,role,tx);
		

		return id;
	}
	
	public void saveDriver(long id,Role role, Transaction tx){
		
		System.out.println("=========id======"+id);
		Session session = sessionFactory.getCurrentSession();
	 
        Query queryDriver = session.createQuery("from User where userId= :userId");
        queryDriver.setParameter("userId", id);
       
        List <User> userlist=null;
     
 //       Transaction tx1 = session.beginTransaction();
       userlist=queryDriver.list();
 
        System.out.println("size of list "+userlist.size());
               UserAccount useraccount = new UserAccount();
        
               
               System.out.println("=====userlist===="+userlist);
               
               for(User userac :userlist ){
                   System.out.println("=====user===="+userac);

                     System.out.println("getUserId(): "+userac.getUserId());
                     System.out.println("getCreateDate: "+userac.getCreateDate());
                     System.out.println("userac.getUpdatedBy()"+userac.getUpdatedBy());
                     System.out.println("userac.getUpdatedBy()"+userac.getUpdatedDate());
                     useraccount.setUser(userac);
                     useraccount.setCreatedDate(userac.getCreateDate());
                     useraccount.setUpdatedAt(userac.getUpdatedDate());
                     useraccount.setUpdatedBy(userac.getUpdatedBy());
                     useraccount.setEnabled(true);
                   
              //       System.out.println("==========roles========="+roleId);
                     useraccount.setRole(role);
                     System.out.println("==========useraccout data====="+useraccount);
                     try {
                         
                         long idx = (long) session.save(useraccount);
                         System.out.println("-----------useraccountdata------"+useraccount);
                             //   tx.commit();
                                } catch (RuntimeException e) {
                                       System.out.println(e);
                          }
               }      
               
        
              
               
	}
	
	
	
	
	
	public List<User> getAllUsers( User user) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		String stringQuery="select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier) from User usr, UserAccount userA where userA.user.userId = usr.userId "
				+ "and userA.role.roleId = :roleId and usr.enabled = :enabled order by usr.createDate desc";
			
		
		System.out.println("===stringQuery==="+stringQuery);

		if(user.getSupplierFilter()==0 && user.getStatusFilter().equals("Active")){
		query = session.createQuery(stringQuery);
			query.setParameter("enabled", true);
			query.setParameter("roleId", user.getRoleId());
		}
		
		if(user.getSupplierFilter()==0 && user.getStatusFilter().equals("Inactive")){
			query = session.createQuery(stringQuery);
				query.setParameter("enabled", false);
				query.setParameter("roleId", user.getRoleId());

			}
		
		if(user.getSupplierFilter()!=0 && (user.getStatusFilter()==null || user.getStatusFilter()=="")){
			stringQuery="select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
					+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier) from User usr, UserAccount userA where userA.user.userId = usr.userId "
					+ "and userA.role.roleId = :roleId and usr.supplier.supplierId = :supplierId order by usr.createDate desc";
			
			
			query = session.createQuery(stringQuery);
				query.setParameter("supplierId", user.getSupplierFilter());
				System.out.println("============supplierId============"+user.getSupplierFilter());
				query.setParameter("roleId", user.getRoleId());

			}
		
		
	if(user.getSupplierFilter()!=0 && user.getStatusFilter()!=null && user.getStatusFilter().equals("Active")){
		
		stringQuery="select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier) from User usr, UserAccount userA where userA.user.userId = usr.userId "
				+ "and userA.role.roleId = :roleId and usr.supplier.supplierId  = :supplierId and  usr.enabled = :enabled  order by usr.createDate desc";
		
		query = session.createQuery(stringQuery);
		query.setParameter("supplierId",user.getSupplierFilter());
		System.out.println("============supplierId============"+user.getSupplierFilter());

		query.setParameter("enabled", true);
		query.setParameter("roleId", user.getRoleId());

		}
	
	if(user.getSupplierFilter()!=0 && user.getStatusFilter()!=null && user.getStatusFilter().equals("Inactive")){
		stringQuery="select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
				+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier) from User usr, UserAccount userA where userA.user.userId = usr.userId "
				+ "and userA.role.roleId = :roleId and usr.supplier.supplierId  = :supplierId and  usr.enabled = :enabled  order by usr.createDate desc";
		
		query = session.createQuery(stringQuery);
		query.setParameter("supplierId",user.getSupplierFilter());
		System.out.println("============supplierId============"+user.getSupplierFilter());

		query.setParameter("enabled", false);
		query.setParameter("roleId", user.getRoleId());

		}
	
		List<User> users = null;
		try{
			users = query.list();
			System.out.println("======"+users.size());
			System.out.println("======"+users.size());

		}
		catch(RuntimeException e){
		LOGGER.error("Error:UserDao:getAllUsers=====>" +e);
		}
		return users;
	}


	public void merge(User user) {
		Session session = sessionFactory.getCurrentSession();
		long id = 0;
		
		
		try {
			
			session.merge(user);
			
			
		} catch (RuntimeException e) {
			
			
		LOGGER.error("Error:UserDao:merge=====>" +e);
		} finally {
			
		}
	}
	public List<User> getUserByEmailAndPhone(User user) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		
		Query query = session.createQuery("from User where emailId=:email and mobilePhone=:mobile and enabled= :status order by createDate desc");
		try {
			query.setParameter("email",user.getEmailId().trim());
			query.setParameter("mobile",user.getMobilePhone());
			query.setParameter("status",true);
			users = query.list();
			} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getUserByEmailAndPhone=====>" +e);
		} finally {
			
		}
		return users;
	}
	public List<User> getAllUsers(Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		Query query = session.createQuery("from User where supplier.supplierId= :supplierId and enabled = true order by createDate  desc");
		try {
			query.setParameter("supplierId", supplier.getSupplierId());
			users = query.list();			
		} catch (RuntimeException e) {
		LOGGER.error("Error:UserDao:getAllUsers=====>" +e);
		} finally {
			
		}
		return users;
	}
	public List<User> getAllActiveUsers(Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		Query query = session.createQuery("from User where supplier.supplierId= :supplierId and enabled= true order by createDate  desc");
		try {
			query.setParameter("supplierId", supplier.getSupplierId());
			users = query.list();			
		} catch (RuntimeException e) {
		LOGGER.error("Error:UserDao:getAllActiveUsers=====>" +e);
		} finally {
			
		}
		return users;
	}
	
	public User getUserById(
			Long userId) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		Query query = session.createQuery("from User where userId = :userId");
		try {
			
			query.setParameter("userId",userId);
			users = query.list();
			} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:getUserById=====>" +e);
		} finally {
			
		}
		return users.get(0);
		
	}
	
	public Boolean checkMobileNoUnique(
			String mobilePhone) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		Boolean status= false;
		Query query = session.createQuery("from User where mobilePhone = :mobilePhone");
		try {
			
			query.setParameter("mobilePhone",mobilePhone);
			users = query.list();
			if(users.size() > 0){
				status=true;
			}
			} catch (RuntimeException e) {
			LOGGER.error("Error:UserDao:checkMobileNoUnique=====>" +e);
		} finally {
			
		}
		return status;
		
	}
	
	public Boolean checkEmailIdUnique(
			String emailId) {
		Session session = sessionFactory.getCurrentSession();
		List<User>users = null;
		Boolean status= false;
		Query query = session.createQuery("from User where emailId = :emailId");
		try {
			
			query.setParameter("emailId",emailId);
			users = query.list();
			if(users.size() > 0){
				status=true;
			}
			} catch (RuntimeException e) {
		LOGGER.error("Error:UserDao:checkEmailIdUnique=====>" +e);
		} finally {
			
		}
		return status;
		
	}
	
	/*public Supplier getSupplierForUser(Integer userId){
		Session session = sessionFactory.getCurrentSession();
		List<Supplier>suppliers = null;
		Query query = session.createQuery("from Supplier where user.supplierId= :supplierId and enabled= true");
		try {
			query.setParameter("supplierId", supplier.getSupplierId());
			suppliers = query.list();
			
			System.out.println("returned users-->"+users);
			
			
		} catch (RuntimeException re) {
			
			
			throw re;
		} finally {
			
		}
		return hashedUsers;
		
	}*/
	
	
}
