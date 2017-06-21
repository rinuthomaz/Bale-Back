package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.ConfigurationDao;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ConfigurationService;

@Component("configurationService")
@Transactional
public class BalePickupConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired 
	ConfigurationDao configDao;
	
	@Autowired 
	MasterDataDao masterDataDao;

	@Autowired
	BalePickUpAssignmentDAO balePickupassignmentDAO;

	//@Autowired BalePickupServiceImpl balePickupServiceImpl;

	@Override
	public String addSupplierMaterialConfigData(
			List<BalePickupSupplierSiteConfiguration> supplierSiteConfigList,List<BalePickupMaterialConfiguration> materialConfigList,
			boolean editAction,UserDTO user) {
		boolean errorFlag = false;
		
		CustomerSite  customerSite = supplierSiteConfigList.get(0).getCustomerSite();
		masterDataDao.mergeCustomerSite(customerSite);
		
		if(editAction){
			configDao.deleteSupplierConfigurationRecordsForCustomerSite(customerSite);
			configDao.deleteMaterialConfigurationRecordsForCustomerSite(customerSite);
		}
		
		for(BalePickupSupplierSiteConfiguration supplierSiteConfig:supplierSiteConfigList){
		supplierSiteConfig.setUpdatedBy(user.getFirstName());
		supplierSiteConfig.setCreateDate(new Date());
		supplierSiteConfig.setUpdatedAt(new Date());
		supplierSiteConfig.setEnabled(true);
		int supplierSiteConfigId = configDao.addSupplierSiteConfigData(supplierSiteConfig);
		if(supplierSiteConfigId==0){
			errorFlag = true;
		}
		}
		
		// TODO Update the BalePickupAssignment table to disable all the records by customerSite
		
		List<BalePickupAssignment> _assignments = new ArrayList<BalePickupAssignment>(balePickupassignmentDAO.getPickupAssignmentsForCustomerSite(customerSite));
		balePickupassignmentDAO.delete(_assignments);
		
		for(BalePickupMaterialConfiguration materialConfig:materialConfigList){
		materialConfig.setUpdatedBy(user.getFirstName());
        materialConfig.setUpdatedAt(new Date());
        materialConfig.setCreateDate(new Date());
        
        int materialConfigId = configDao.addMaterialConfigData(materialConfig);
        if(materialConfigId==0){
			errorFlag = true;
		}
		}
        if(!errorFlag){
             
        	return ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;
        	
        }
        else{
        	return ApplicationCommonConstants.INSERTION_FAILURE_MESSAGE;
        }
		
      
	}

	

}
