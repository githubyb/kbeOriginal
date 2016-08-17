package edu.nwpu.dmpm.kbe.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.system.dao.BugTypeDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tbugtype;
import edu.nwpu.dmpm.kbe.system.service.BugTypeService;

@Service
@Transactional
public class BugTypeServiceImpl implements BugTypeService {

	@Autowired
	private BugTypeDaoI bugType;

	@Override
	@Cacheable("bugTypeServiceCache")
	public List<Tbugtype> getBugTypeList() {
		return bugType.find("from Tbugtype t");
	}
	@CacheEvict(value="bugTypeServiceCache",allEntries=true)
	public void removeCache(){
		System.out.println("removeCache");
	}

}
