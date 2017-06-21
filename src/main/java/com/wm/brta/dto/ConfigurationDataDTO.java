package com.wm.brta.dto;

import java.util.List;

import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;

public class ConfigurationDataDTO {
	
	List<BalePickupSupplierSiteConfiguration> supplierSiteConfig;
	List<BalePickupMaterialConfiguration> materialConfig;
	boolean editAction;
	
	
	
	
	@Override
	public String toString() {
		return "ConfigurationDataDTO [supplierSiteConfig=" + supplierSiteConfig
				+ ", materialConfig=" + materialConfig + "]";
	}




	public List<BalePickupSupplierSiteConfiguration> getSupplierSiteConfig() {
		return supplierSiteConfig;
	}




	public void setSupplierSiteConfig(
			List<BalePickupSupplierSiteConfiguration> supplierSiteConfig) {
		this.supplierSiteConfig = supplierSiteConfig;
	}




	public List<BalePickupMaterialConfiguration> getMaterialConfig() {
		return materialConfig;
	}




	public void setMaterialConfig(
			List<BalePickupMaterialConfiguration> materialConfig) {
		this.materialConfig = materialConfig;
	}




	public boolean isEditAction() {
		return editAction;
	}




	public void setEditAction(boolean editAction) {
		this.editAction = editAction;
	}
	
	
	
	

}
