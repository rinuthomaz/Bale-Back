package com.wm.brta.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;

@Component
public class AuthenticationFilter implements Filter {

	@Autowired
	private LoginService loginService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
				
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
				(HttpServletResponse) res);

		MyRequestWrapper multiReadRequest = new MyRequestWrapper(
				(HttpServletRequest) req);

		String payloadRequest = getBody(multiReadRequest);
		JSONObject jsonObj = new JSONObject(payloadRequest);

		Boolean status = false;
		
		String url = ((HttpServletRequest) multiReadRequest).getRequestURL()
				.toString();
		
		try {

			String emailId = jsonObj.getString("emailId");
			String mobilePhone = jsonObj.getString("mobilePhone");

			User user = new User();
			user.setEmailId(emailId);
			user.setMobilePhone(mobilePhone);
			UserDTO userDTO = loginService.authenticate(user);
						
			if (!userDTO.isAuthenticationErrorFlag()) {
				if(url.contains("authenticate")){
					chain.doFilter(multiReadRequest, res);
				}else{
					String userId = jsonObj.getString("userId");

					if(userId.equals(userDTO.getUserId().toString())){
						chain.doFilter(multiReadRequest, res);
					}else{
						responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Unauthorized");
					}
				}
			} else {
				responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Unauthorized");
			}

		} catch (Exception e) {
			System.out.println("========exception======="+e);
			responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
					"Unauthorized");
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		InputStream inputStream = request.getInputStream();

		try {

			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		inputStream.close();
		return body;
	}

	public static String getBody1(HttpServletRequest request)
			throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

}
