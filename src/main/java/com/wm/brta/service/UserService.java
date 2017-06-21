package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;

import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.DriverSupplierDTO;
import com.wm.brta.dto.UserDTO;

public interface UserService {
	
	public List<User> getAllUsersWithFilters(User user);
	public List<User> getActiveUsers(Supplier supplierSelected);

	public Boolean checkMobileNoUnique(String mobileNo);
	public Boolean checkEmailIdUnique(String emailId);
	
	public List<User> add(DriverSupplierDTO driverSupplierDTO, UserDTO userDTO) throws Exception;


}
