package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.RoleDaoI;
import edu.nwpu.dmpm.kbe.system.model.Trole;

@Repository
public class RoleDaoImpl extends MPMHibernateDaoImpl<Trole> implements RoleDaoI {

}
