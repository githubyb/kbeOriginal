package edu.nwpu.dmpm.kbe.system.service;

import java.util.List;

import edu.nwpu.dmpm.kbe.system.pageModel.ResourceType;

/**
 * 资源类型服务
 * 
 * @author wangc
 * 
 */
public interface ResourceTypeService {

	/**
	 * 获取资源类型
	 * 
	 * @return
	 */
	public List<ResourceType> getResourceTypeList();

}
