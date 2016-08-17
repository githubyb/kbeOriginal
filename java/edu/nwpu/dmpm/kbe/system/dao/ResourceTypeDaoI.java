package edu.nwpu.dmpm.kbe.system.dao;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDao;
import edu.nwpu.dmpm.kbe.system.model.Tresourcetype;

/**
 * 资源类型数据库操作类
 * 
 * @author wangc
 * 
 */
public interface ResourceTypeDaoI extends MPMHibernateDao<Tresourcetype> {

	/**
	 * 通过ID获得资源类型
	 * 
	 * @param id
	 * @return
	 */
	public Tresourcetype getById(String id);

}
