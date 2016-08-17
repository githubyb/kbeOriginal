package edu.nwpu.dmpm.kbe.system.dao;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDao;
import edu.nwpu.dmpm.kbe.system.model.Tbugtype;

public interface BugTypeDaoI extends MPMHibernateDao<Tbugtype> {

	/**
	 * 通过ID获得BUG类型
	 * 
	 * @param id
	 * @return
	 */
	public Tbugtype getById(String id);

}
