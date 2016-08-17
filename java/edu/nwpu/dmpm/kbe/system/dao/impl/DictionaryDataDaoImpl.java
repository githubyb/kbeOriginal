package edu.nwpu.dmpm.kbe.system.dao.impl;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.DictionaryDataDaoI;
import edu.nwpu.dmpm.kbe.system.model.TDictionaryData;

/**
 *
 *@author zhjz
 *@date 2014-6-20
 */
@Repository
public class DictionaryDataDaoImpl extends MPMHibernateDaoImpl<TDictionaryData> implements
		DictionaryDataDaoI {

}
