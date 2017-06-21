package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.domain.BaleRouteCustomerMapping;
import com.wm.brta.domain.BaleRouteCustomerMaterialMapping;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.MasterService;
import com.wm.brta.service.SupplierService;

@Component("customerService")
@Transactional
public class CustomerServiceImpl implements  MasterService,CustomerService
{
	@Autowired
	MasterDataDao masterDao;
	
	@Autowired
	BalePickUpAssignmentDAO balePickUpAssignmentDAO;
	@Override
	public HashSet<BaleRouteCustomerMapping> getAll() throws Exception {
		return null;
		
	}

	@Override
	public HashSet<Customer> getAllBuyCustomers() {
		HashSet<BaleRouteCustomerMapping>mappedCustomers = new HashSet<BaleRouteCustomerMapping>();
		
		HashSet<Customer> buyCustomers = new HashSet<Customer>();
		mappedCustomers = masterDao.getAllCustomers();
		for(BaleRouteCustomerMapping mappedCustomer:mappedCustomers){
			buyCustomers.add(mappedCustomer.getBuyCustomer());
			
		}
		return buyCustomers;
	}

	@Override
	public HashSet<CustomerSite> getCustomerSites(Customer selectedBuyCustomer,String state)  {
      HashSet<CustomerSite>customerSites = new HashSet<CustomerSite>();
		customerSites = masterDao.getAllCustomerSitesForBuyCustomer(selectedBuyCustomer,state);
		
		return customerSites;
	}

	@Override
	public HashSet<Material> getAllMaterialsByCustomer(Customer customer) {
		List<BaleRouteCustomerMaterialMapping> customerMaterialMappings= new ArrayList<BaleRouteCustomerMaterialMapping>();
		HashSet<Material>materials= new HashSet<Material>();
		customerMaterialMappings = masterDao.getAllMaterialMappingsByCustomer(customer);
		for(BaleRouteCustomerMaterialMapping materialMapping:customerMaterialMappings){
			materials.add(materialMapping.getMaterial());
		}
		return materials;
	}

	@Override
	public HashSet<Customer> getAllSellCustomersForBuyCustomer(
			Customer buyCustomer) {
	HashSet<BaleRouteCustomerMapping>mappedCustomers = new HashSet<BaleRouteCustomerMapping>();
		mappedCustomers= masterDao.getAllCustomers();
		HashSet<Customer> sellCustomers = new HashSet<Customer>();
		for(BaleRouteCustomerMapping mapping:mappedCustomers){
			if(mapping.getBuyCustomer().getCustomerId().equals(buyCustomer.getCustomerId()))
			sellCustomers.add(mapping.getSellCustomer());
		}
				return sellCustomers;
	}
	@Override
	public HashSet<Destination> getAllDestination(String storeName) {

		HashSet<Destination> destinations = new HashSet<Destination>();
		
		destinations = balePickUpAssignmentDAO.getDestination(storeName);
		return destinations;
	}

	
	

}
