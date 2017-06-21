package com.wm.brta.service;

import java.util.HashSet;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;

public interface SupplierService
{
	
	public HashSet<Supplier>getAllSuppliersByBuyCustomer(Customer customer);
	public HashSet<SupplierSite> getAllSupplierSitesBySupplier(Supplier supplier);
	

}
