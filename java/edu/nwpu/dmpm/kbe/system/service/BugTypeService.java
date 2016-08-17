package edu.nwpu.dmpm.kbe.system.service;

import java.util.List;

import edu.nwpu.dmpm.kbe.system.model.Tbugtype;

/**
 * 
 * @author wangc
 * 
 */
public interface BugTypeService {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();
	public void removeCache();

}
