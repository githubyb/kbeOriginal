package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.BugDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tbug;

@Repository
public class BugDaoImpl extends MPMHibernateDaoImpl<Tbug> implements BugDaoI {

}
