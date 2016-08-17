package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.ResourceTypeDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tresourcetype;

@Repository
public class ResourceTypeDaoImpl extends MPMHibernateDaoImpl<Tresourcetype> implements ResourceTypeDaoI {

	@Override
	@Cacheable(value = "resourceTypeDaoCache", key = "#id")
	public Tresourcetype getById(String id) {
		return super.get(Tresourcetype.class, id);
	}

}
