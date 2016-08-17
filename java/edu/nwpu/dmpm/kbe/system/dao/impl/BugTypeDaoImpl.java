package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.BugTypeDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tbugtype;

@Repository
public class BugTypeDaoImpl extends MPMHibernateDaoImpl<Tbugtype> implements BugTypeDaoI {

	@Override
	@Cacheable(value = "bugTypeDaoCache", key = "#id")
	public Tbugtype getById(String id) {
		return super.get(Tbugtype.class, id);
	}

}
