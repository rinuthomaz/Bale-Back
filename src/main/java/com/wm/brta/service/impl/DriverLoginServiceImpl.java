package com.wm.brta.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;

@Component("loginService")
@Transactional
public class DriverLoginServiceImpl implements LoginService {
	
	@Autowired
	UserDao userDao;
	@Autowired
	UserDTO userDTO;

	@Override
	public UserDTO authenticate(User inputUser) throws Exception {
		
		List<User> users= userDao.getUserByEmailAndPhone(inputUser);
		
		if(users!=null && users.size()==0 ){
			userDTO.setFirstName(null);
			userDTO.setLastName(null);
			userDTO.setEmailId(inputUser.getEmailId());
			userDTO.setMobilePhone(inputUser.getMobilePhone());
			userDTO.setAuthenticationErrorFlag(true);
			userDTO.setMessage(ApplicationCommonConstants.AUTHENTICATION_UNSUCCESSFULL_MESSAGE);
			return userDTO;
		}
		
		
		else{
			User user = users.get(0);
			userDTO.setUserId(user.getUserId());
			userDTO.setFirstName(user.getFirstName());
			userDTO.setAuthenticationErrorFlag(false);
			userDTO.setLastName(user.getLastName());
			userDTO.setMobilePhone(user.getMobilePhone());
			userDTO.setEmailId(user.getEmailId());
		    userDTO.setMessage(ApplicationCommonConstants.AUTHENTICATION_SUCCESSFULL_MESSAGE);
		    return userDTO;
		}
	}

}
