package com.wm.brta.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.Material;


@Repository
public class BalePickupMaterialConfigurationDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickupMaterialConfigurationDAO.class);


	public Set<Material> getAllMaterial(Integer customerSiteId) {
		Set<Material> materialSet = new HashSet<Material>();
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from BalePickupMaterialConfiguration where customerSite.customerSiteId = :customerSiteId";
		Query query = session.createQuery(stringQuery);
		query.setParameter("customerSiteId", customerSiteId);

		List<BalePickupMaterialConfiguration> balePickupMaterialConfigurationList = query.list();
		
		if (balePickupMaterialConfigurationList != null) {
			for(BalePickupMaterialConfiguration balePickupMaterialConfiguration: balePickupMaterialConfigurationList){
				materialSet.add(balePickupMaterialConfiguration.getMaterial());
			}
		}

		

		return materialSet;
	}
}
