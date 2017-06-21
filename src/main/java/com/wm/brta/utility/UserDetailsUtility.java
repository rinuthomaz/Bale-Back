package com.wm.brta.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;



import org.springframework.stereotype.Component;

import com.wm.brta.dto.UserDTO;

@Component
public class UserDetailsUtility {
	
	
	@Autowired
	private UserDTO user;
	
	
	
	
	public UserDTO getUserDetails(HttpServletRequest request) throws Exception{
		
	user.setFirstName(request.getHeader("sm_fname").replace('\n',' ').trim());
	user.setLastName(request.getHeader("sm_lname").replace('\n',' ').trim());
    String role = extractRole(request.getHeader("sm_groups").replace('\n',' ').trim());
    user.setRoles(role);
  
	return user;
	
		
	}




	private static String  extractRole(String smGroup) {
		String [] roleFields = smGroup.split(",");
	     
		String roleReturned = null;
		outerloop:
		for(String roles: roleFields){
			
			String [] tempArray = roles.split("=");
			for(String role:tempArray){
				
				if(role.contains("ROBR")){
					roleReturned = role;
					break outerloop;
				}
			}
		}
		
		return roleReturned;
	}
	
	
	/*public static void main(String[] args) {
		
		extractRole("cn=cn,ou=ROBRMANAGER,ou=roles,ou=app");
	}*/

}
