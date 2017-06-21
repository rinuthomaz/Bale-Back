package com.wm.brta.service;

import java.util.HashSet;

import com.wm.brta.domain.BaleRouteCustomerMapping;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.dto.Destination;

public interface CustomerService {
	
	public HashSet<Customer> getAllBuyCustomers();
	public HashSet<CustomerSite>getCustomerSites(Customer selectedBuyCustomer,String state);
	public HashSet<Material> getAllMaterialsByCustomer(Customer customer);
	public HashSet<Customer> getAllSellCustomersForBuyCustomer(Customer buyCustomer);
	
	public HashSet<Destination> getAllDestination(String storeName);


}
