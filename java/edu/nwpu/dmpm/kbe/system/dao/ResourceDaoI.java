package edu.nwpu.dmpm.kbe.system.dao;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDao;
import edu.nwpu.dmpm.kbe.system.model.Tresource;

/**
 * 资源数据库操作类
 * 
 * @author wangc
 * 
 */
public interface ResourceDaoI extends MPMHibernateDao<Tresource> {

	public String getUrl(String op);
}
