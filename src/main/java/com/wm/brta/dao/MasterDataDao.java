package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;
import com.wm.brta.domain.BaleRouteCustomerMapping;
import com.wm.brta.domain.BaleRouteCustomerMaterialMapping;
import com.wm.brta.domain.BaleRouteCustomerSupplierMapping;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;


@Repository
public class MasterDataDao {
	
	@Autowired
	 protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterDataDao.class);
	public HashSet<Supplier> getAllSuppliers() {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from Supplier");
	List<Supplier> suppliers = null;
	HashSet<Supplier> hashedSuppliers = null;
	try{
		suppliers = query.list();
		hashedSuppliers = new HashSet<Supplier>(suppliers);
	}
	catch(RuntimeException e){
		LOGGER.error("Error:MasterDataDao:getAllSuppliers=====>" +e);
	}
	return hashedSuppliers;
}



public HashSet<BaleRouteCustomerMapping> getAllCustomers() {
	Session session = sessionFactory.getCurrentSession();
	Query query = session.createQuery("from BaleRouteCustomerMapping");
	List<BaleRouteCustomerMapping> customers = null;
	HashSet<BaleRouteCustomerMapping> mappedCustomers = null;
	try{
		customers = query.list();
		mappedCustomers = new HashSet<BaleRouteCustomerMapping>(customers);
	}
	catch(RuntimeException e){
		LOGGER.error("Error:MasterDataDao:getAllCustomers=====>" +e);
	}
	return mappedCustomers;
}



public HashSet<CustomerSite> getAllCustomerSitesForBuyCustomer(
		Customer selectedBuyCustomer,String state) {
	Session session = sessionFactory.getCurrentSession();
	List<CustomerSite>customerSites = null;
	
	Query query=null;
	String queryStr=null;
	HashSet<CustomerSite> hashedCustomerSites = null;
	
	 queryStr = "from CustomerSite where customer.customerId= :customerId";
	 
	 if(state!=null){

         queryStr="from CustomerSite where customer.customerId= :customerId and "

                         + " location.locationId in (select locationId from Location where address4= :state)";
	 }
	 query=session.createQuery(queryStr);
	 
	query.setParameter("customerId", selectedBuyCustomer.getCustomerId());
			if(state != null){
		
		        query.setParameter("state", state);
		
		}


	try{
		customerSites = query.list(); 
		for(CustomerSite customerSite:customerSites ){
			List<BalePickupMaterialConfiguration>materialConfig = customerSite.getBalePickupMaterialConfiguration();
			List<BalePickupSupplierSiteConfiguration>supplierSiteConfig = customerSite.getBalePickupSupplierSiteConfiguration();
			List<String>configuredMaterials = new ArrayList<String>();
			List<String>configuredSupplierSites = new ArrayList<String>();
			for(BalePickupMaterialConfiguration materialConfiguration: materialConfig){
				configuredMaterials.add(materialConfiguration.getMaterial().getDescription()+"("+materialConfiguration.getAvgBaleWeight()+" lbs)");
			}
			customerSite.setConfiguredMaterials(configuredMaterials);
			for(BalePickupSupplierSiteConfiguration supplierSiteConfiguration: supplierSiteConfig){
				configuredSupplierSites.add(supplierSiteConfiguration.getSupplierSite().getSiteName());
			}
			customerSite.setConfiguredMaterials(configuredMaterials);
			customerSite.setConfiguredSupplierSites(configuredSupplierSites);
			
		}
		hashedCustomerSites = new HashSet<CustomerSite>(customerSites);
	}
	catch(RuntimeException e){
		LOGGER.error("Error:MasterDataDao:getAllCustomerSitesForBuyCustomer=====>" +e);
	}
	return hashedCustomerSites;
}



public HashSet<BaleRouteCustomerSupplierMapping> getAllSupplierSitesForBuyCustomer(Customer customer) {
	Session session = sessionFactory.getCurrentSession();
	List<BaleRouteCustomerSupplierMapping>supplierMappings = null;
	HashSet<BaleRouteCustomerSupplierMapping> hashedSupplierMappings = null;
	
	Query query = session.createQuery("from BaleRouteCustomerSupplierMapping where buyCustomer.customerId= :customerId");
	query.setParameter("customerId", customer.getCustomerId());
	try{
		supplierMappings = query.list();
		hashedSupplierMappings = new HashSet<BaleRouteCustomerSupplierMapping>(supplierMappings);
	}
	catch(RuntimeException e){
	LOGGER.error("Error:MasterDataDao:getAllSupplierSitesForBuyCustomer=====>" +e);
	}
	return hashedSupplierMappings;
}



public HashSet<SupplierSite> getAllSupplierSitesForSupplier(Supplier supplier) {
	Session session = sessionFactory.getCurrentSession();
	List<SupplierSite>supplierSites = null;
	HashSet<SupplierSite> hashedSupplierSites = null;
	
	Query query = session.createQuery("from SupplierSite where supplierId= :supplierId");
	query.setParameter("supplierId", supplier.getSupplierId());
	try{
		supplierSites = query.list();
		hashedSupplierSites = new HashSet<SupplierSite>(supplierSites);
	}
	catch(RuntimeException e){
	LOGGER.error("Error:MasterDataDao:getAllSupplierSitesForSupplier=====>" +e);
	}
	return hashedSupplierSites;
}



public List<BaleRouteCustomerMaterialMapping> getAllMaterialMappingsByCustomer(
		Customer customer) {
	Session session = sessionFactory.getCurrentSession();
	List<BaleRouteCustomerMaterialMapping>materialMappings = null;
	
	
	Query query = session.createQuery("from BaleRouteCustomerMaterialMapping where buyCustomerId= :customerId");
	query.setParameter("customerId", customer.getCustomerId());
	try{
		materialMappings = query.list();
		
	}
	catch(RuntimeException e){
	LOGGER.error("Error:MasterDataDao:getAllMaterialMappingsByCustomer=====>" +e);
	}
	return materialMappings;
}


public void mergeCustomerSite(CustomerSite customerSite){
	Session session = sessionFactory.getCurrentSession();
	try{
	session.merge(customerSite);
	}
	catch(Exception e){
	LOGGER.error("Error:MasterDataDao:mergeCustomerSite=====>" +e);
	}
	
}

}



