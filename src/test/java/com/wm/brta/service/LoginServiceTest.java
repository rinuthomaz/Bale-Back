package com.wm.brta.service;

import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wm.brta.Application;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.util.BaleUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
@TestPropertySource(locations = "classpath:application-test.properties")
public class LoginServiceTest {

	private static final Logger LOGGER = Logger.getLogger("LoginServiceTest");

	@Autowired
	LoginService loginService;

	@Test
	public void testAuthenticate() {

		UserDTO userDto=new UserDTO();
		userDto.setFirstName("chandan");
		userDto.setLastName("shyamal");
		userDto.setRole("ROBRCSR");
		userDto.setUserId(201l);
		userDto.setMobilePhone("9986022413");
		userDto.setEmailId("chandans@gmail.com");
		
		User user = new User();
		user.setEmailId("chandans@gmail.com");
		user.setMobilePhone("9986022413");

		BaleUtils.setUpMokito();

		loginService = Mockito.mock(LoginService.class);

		try {
			Mockito.when(loginService.authenticate(user)).thenReturn(userDto);
//
//			UserDTO userDTO = loginService.authenticate(user);
//			
//			System.out.println("----userDTO---"+userDTO);

		} catch (Exception e) {

		}

	}

}
