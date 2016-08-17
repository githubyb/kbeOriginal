package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.PassWordRuleDaoI;
import edu.nwpu.dmpm.kbe.system.model.TPassWordRule;

@Repository
public class PassWordRuleDaoImpl extends MPMHibernateDaoImpl<TPassWordRule> implements PassWordRuleDaoI {
	
}
