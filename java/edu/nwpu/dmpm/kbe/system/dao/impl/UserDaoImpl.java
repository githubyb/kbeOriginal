package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.UserDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tuser;

@Repository
public class UserDaoImpl extends MPMHibernateDaoImpl<Tuser> implements UserDaoI {
	
	
}
