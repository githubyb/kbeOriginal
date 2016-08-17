package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.DictionaryDaoI;
import edu.nwpu.dmpm.kbe.system.model.TDictionary;

/**
 *
 *@author zhjz
 *@date 2014-6-20
 */
@Repository
public class DictionaryDaoImpl extends MPMHibernateDaoImpl<TDictionary> implements DictionaryDaoI {

}
