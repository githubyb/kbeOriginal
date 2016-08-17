package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.GroupDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tgroup;

/*
 * Tgroup 数据库操作类
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
@Repository
public class GroupDaoImpl extends MPMHibernateDaoImpl<Tgroup> implements GroupDaoI {

}
