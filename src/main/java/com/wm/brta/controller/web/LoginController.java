/**
 * 
 */
package com.wm.brta.controller.web;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;
import com.wm.brta.utility.UserDetailsUtility;

/**
 * @fileName BaleRouteLoginController.java
 * @author rthoma24
 * @Project BaleRoute
 * @Date Nov 29, 2016
 * @Sprint
 * @UserStory
 * @Theme BaleRouteLoginController
 *
 */
@Controller
public class LoginController {

	@Autowired
	private UserDetailsUtility userDetails;

	@Autowired
	private LoginService loginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndexPage(HttpServletRequest request) {
		UserDTO user;
		try {
			try {
				user = userDetails.getUserDetails(request);
				String sm_groups = request.getHeader("sm_groups")
						.replace('\n', ' ').trim();
			} catch (Exception e) {
				user = new UserDTO();
				user.setFirstName("nmehta");
				user.setRole("ROBRCSR");
				user.setRoles("ROBRCSR");
				
				/*user.setRole("ROBRMANAGER");
				user.setRoles("ROBRMANAGER");*/
				LOGGER.info("Error:Login Controller:getUserDetails=====>" + e);
			}

			HttpSession session = request.getSession();
			
			
			Double versionNo=1.04;
			session.setAttribute("versionNo", versionNo);
			session.setAttribute("user", user);
			session.setAttribute("sm_groups", user.getRole());
			LOGGER.debug("Logged in user name:" + user.getFirstName());
			LOGGER.debug("Logged in user role:" + user.getRole());
		} catch (Exception e) {
			LOGGER.error("Error:Login Controller:getRole,getFirstName=====>"+ e);
		}

		return "landing";
	}

	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();

		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {

				if ("SMSESSION".equals(cookies[i].getName())) {
					cookies[i].setValue("null");
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					cookies[i].setDomain(".wm.com");
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);

				} else if ("JSESSIONID".equals(cookies[i].getName())) {

					cookies[i].setValue("null");
					// cookies[i].setPath(cookiePath);
					cookies[i].setMaxAge(0);
					// cookies[i].setDomain(cookieDomain);
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);
				} else {
					cookies[i].setValue("");
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);
				}

			}
		session.invalidate();
		String siteuri = "/";
		response.sendRedirect(siteuri);
	}

	@RequestMapping(value = "/common/getdetails/user", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getLoggedInUserDetails(HttpServletRequest request) {

		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");

		return user;
	}

	

}
