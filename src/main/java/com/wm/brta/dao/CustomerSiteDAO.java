package com.wm.brta.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.CustomerSite;


@Repository
public class CustomerSiteDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSiteDAO.class);


	public CustomerSite getBuyCustomerSite(String storeName) {

		CustomerSite customerSite=null;
		
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupMaterialConfiguration> materialMappings = null;

		Query query = session
				.createQuery("from CustomerSite where alternativeSearchReference = :storeName");
		query.setParameter("storeName", storeName);

		List<CustomerSite> customerSiteList = query.list();
		if(customerSiteList !=null){
			customerSite=customerSiteList.get(0);
		}

		return customerSite;
	}

}
