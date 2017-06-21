package com.wm.brta.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.domain.BaleRouteCustomerMapping;
import com.wm.brta.domain.BaleRouteCustomerSupplierMapping;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.domain.User;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.MasterService;
import com.wm.brta.service.SupplierService;


@Component("supplierMasterService")
@Transactional
public class SupplierServiceImpl implements MasterService<Supplier>,SupplierService {

	@Autowired
    MasterDataDao masterDao;
	
	@Override
	public HashSet<Supplier> getAll() throws Exception {
		HashSet<Supplier>suppliers = new HashSet<Supplier>();
		suppliers = masterDao.getAllSuppliers();
		
		
		return suppliers;
	}

	@Override
	public HashSet<Supplier> getAllSuppliersByBuyCustomer(Customer customer) {
		HashSet<BaleRouteCustomerSupplierMapping>supplierMappings = new HashSet<BaleRouteCustomerSupplierMapping>();
		supplierMappings = masterDao.getAllSupplierSitesForBuyCustomer(customer);
		HashSet<Supplier>suppliers = new HashSet<Supplier>();
		for(BaleRouteCustomerSupplierMapping supplierMapping: supplierMappings){
			suppliers.add(supplierMapping.getSupplier());
		}
		return suppliers;
	}

	@Override
	public HashSet<SupplierSite> getAllSupplierSitesBySupplier(Supplier supplier) {
		HashSet<SupplierSite>supplierSites = new HashSet<SupplierSite>();
		supplierSites = masterDao.getAllSupplierSitesForSupplier(supplier);
		return supplierSites;
	}
	

}
