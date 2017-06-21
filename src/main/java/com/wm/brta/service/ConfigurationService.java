package com.wm.brta.service;

import java.util.List;

import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;

public interface ConfigurationService 
{
	
	public String addSupplierMaterialConfigData(List<BalePickupSupplierSiteConfiguration> list,
			
			List<BalePickupMaterialConfiguration> list2, boolean editAction, UserDTO user);

	

}
