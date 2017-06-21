package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hibernate.PersistentObjectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Role;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.DriverSupplierDTO;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.UserService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.utility.MailServiceUtility;

@Component("userService")
@Transactional
public class UserServiceImpl implements ApplicationService<User>, UserService {

	@Autowired
	UserDao userDao;

	@Override
	public List<User> add(DriverSupplierDTO driverSupplierDTO, UserDTO userDTO)
			throws Exception {

		User persistenceObject=driverSupplierDTO.getUser();
		Role role=driverSupplierDTO.getRole();
		

		persistenceObject.setCreateDate(new Date());
		persistenceObject.setUpdatedDate(new Date());

		List<User> users = new ArrayList<User>();
		long id = 0;
		persistenceObject.setUpdatedBy(userDTO.getFirstName());

		
		Boolean isValidUserDetails=false;
		String mobilePhone=persistenceObject.getMobilePhone();
		String email=persistenceObject.getEmailId();

		if ((BaleUtils.isValidEmailAddress(email))&&(mobilePhone !=null && mobilePhone.matches("[0-9]+") && mobilePhone.length() == 10)) {
			isValidUserDetails=true;
		}
				
		if(isValidUserDetails){
			id = userDao.save(persistenceObject,role);
			if (id != 0) {
				MailServiceUtility.sendMail(persistenceObject);
			}
		}
		
		
		
		if (!(persistenceObject.getStatusFilter() == null && persistenceObject
				.getSupplierFilter() == 0))
			users = getAllUsersWithFilters(persistenceObject);
		
		
		return users;
	}
	
	
	
	
	
	@Override
	public LinkedList<User> delete(User object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> edit(User user) throws Exception {
		user.setUpdatedDate(new Date());

		long id = 0;
		userDao.merge(user);
		List<User> users  = getAllUsersWithFilters(user);
		return users;
	}

	@Override
	public List<User> getAllUsersWithFilters(User user) {
		List<User> users = userDao.getAllUsers(user);
		return users;
	}

	@Override
	public List<User> getAll(Supplier supplier) throws Exception {
		List<User> users= userDao.getAllUsers(supplier);

		return users;
	}

	@Override
	public List<IncidentType> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getActiveUsers(Supplier supplierSelected) {
		List<User> users = userDao.getAllActiveUsers(supplierSelected);
		return users;
	}

	@Override
	public Boolean checkMobileNoUnique(String mobileNo) {
		
		Boolean status=false;
		status= userDao.checkMobileNoUnique(mobileNo);
		
		return status;
	}
	
	@Override
	public Boolean checkEmailIdUnique(String emailId) {
		
		Boolean status=false;
		status= userDao.checkEmailIdUnique(emailId);
		
		return status;
	}




	@Override
	public List<User> add(User persistenceObject, UserDTO userDto)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
