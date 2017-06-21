package com.wm.brta.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.StoreInputDTO;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.MasterService;
import com.wm.brta.service.SupplierService;


@RestController
public class MasterDataController {
	
	
     @Autowired
	MasterService supplierMasterService;
     
     @Autowired
 	SupplierService supplierService;
	
	@Autowired
	CustomerService customerService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataController.class);

	
	
	@RequestMapping(value="/get/supplier/all",method=RequestMethod.GET)
    public @ResponseBody HashSet<Supplier> getAllSuppliers() {
    	HashSet<Supplier>suppliers = null;
		
			
        try {
        	suppliers = supplierMasterService.getAll();
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAll=====>" +e);
		}
        return suppliers;
    }
	
	
	@RequestMapping(value="/get/customersites/bybuycustomer",method=RequestMethod.POST)
    public @ResponseBody HashSet<CustomerSite> getAllCustomerSitesForBuyCustomer(@RequestBody StoreInputDTO storeInputDTO) {
    	HashSet<CustomerSite>customerSites = null;
		
			
        try {
        	customerSites = customerService.getCustomerSites(storeInputDTO.getBuyCustomer(),storeInputDTO.getState());
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getCustomerSites=====>" +e);	
		}
        return customerSites;
    }
	
	
	@RequestMapping(value="/common/get/supplier/bybuycustomer",method=RequestMethod.POST)
    public @ResponseBody HashSet<Supplier> getAllSuppliersForBuyCustomer(@RequestBody Customer customer) {
    	HashSet<Supplier>suppliers = null;
		
			
        try {
        	suppliers = supplierService.getAllSuppliersByBuyCustomer(customer);
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllSuppliersByBuyCustomer=====>" +e);	
		}
        return suppliers;
    }
	
	@RequestMapping(value="/common/get/allSupplierWithBuyCustomer",method=RequestMethod.GET)
    public @ResponseBody Map<String, List<Supplier>> getAllSupplierWithBuyCustomer() {
    	HashSet<Supplier>suppliers = null;
		
    	HashSet<Customer> buyCustomers = customerService.getAllBuyCustomers();
		Map<String, List<Supplier>> suppliersWithCustomer=new HashMap<String, List<Supplier>>();
      
		try {
        	for(Customer customer : buyCustomers){
            	suppliers = supplierService.getAllSuppliersByBuyCustomer(customer);
            	suppliersWithCustomer.put(customer.getCustomerId().toString(), new ArrayList<Supplier>(suppliers));
        	}
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllSupplierWithBuyCustomer=====>" +e);	
		}
        return suppliersWithCustomer;
    }
	
	@RequestMapping(value="/get/sellcustomer/bybuycustomer",method=RequestMethod.POST)
    public @ResponseBody HashSet<Customer> getAllSellCustomersForBuyCustomer(@RequestBody Customer customer) {
    	HashSet<Customer>sellCustomers = null;
		
			
        try {
        	sellCustomers = customerService.getAllSellCustomersForBuyCustomer(customer);
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>" +e);	
		}
        return sellCustomers;
    }
	
	@RequestMapping(value="/get/suppliersite/bysupplier",method=RequestMethod.POST)
    public @ResponseBody HashSet<SupplierSite> getAllSupplierSitesBySupplier(@RequestBody Supplier supplier) {
    	HashSet<SupplierSite>supplierSites = null;
		
			
        try {
        	supplierSites = supplierService.getAllSupplierSitesBySupplier(supplier);
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllSupplierSitesBySupplier=====>" +e);
		}
        return supplierSites;
    }
	
	@RequestMapping(value="/common/get/material/bycustomer",method=RequestMethod.POST)
    public @ResponseBody HashSet<Material> getAllMaterialsByCustomer(@RequestBody Customer customer) {
    	HashSet<Material>materials = null;	
			
        try {
        	materials = customerService.getAllMaterialsByCustomer(customer);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllMaterialsByCustomer=====>" +e);
		}
        return materials;
    }
	
	
	@RequestMapping(value="/common/get/buycustomers/all",method=RequestMethod.GET)
    public @ResponseBody HashSet<Customer> getAllBuyCustomers() {
    	HashSet<Customer>buyCustomers = null;
		
			
        try {
        	buyCustomers = customerService.getAllBuyCustomers();
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllBuyCustomers=====>" +e);
		}
        return buyCustomers;
    }
	
	@RequestMapping(value="/get/destination",method=RequestMethod.POST)
	public @ResponseBody HashSet<Destination> getDestinationOfStore(@RequestBody String storeName){
		
		HashSet<Destination> destinations = null;
		
		
        try {
        	destinations = customerService.getAllDestination(storeName);
        	
        	
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
		LOGGER.error("Error:MasterDataController:getAllDestination=====>" +e);
		}
        return destinations;
	}
	
	
	

}
