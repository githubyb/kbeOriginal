package edu.nwpu.dmpm.kbe.system.service;

import java.util.List;

import edu.nwpu.dmpm.kbe.common.pageModel.Tree;
import edu.nwpu.dmpm.kbe.system.model.Trole;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.Role;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;

/**
 * 角色业务逻辑
 * 
 * @author wangc
 * 
 */
public interface RoleService {

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	public void add(Role role, SessionInfo sessionInfo);

	/**
	 * 获得角色
	 * 
	 * @param id
	 * @return
	 */
	public Role get(String id);

	/**
	 * 编辑角色
	 * 
	 * @param role
	 */
	public void edit(Role role);

	/**
	 * 获得角色treeGrid
	 * 
	 * @return
	 */
	public List<Role> treeGrid(SessionInfo sessionInfo);

	/**
	 * 删除角色
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 获得角色树(只能看到自己拥有的角色)
	 * 
	 * @return
	 */
	public List<Tree> tree(SessionInfo sessionInfo);

	/**
	 * 获得角色树
	 * 
	 * @return
	 */
	public List<Tree> allTree();

	/**
	 * 为角色授权
	 * 
	 * @param role
	 */
	public void grant(Role role);
	
	/**
	 * 获取某角色下的所有人员
	 * @param roleID 角色ID
	 * @return 该角色的人员列表
	 * @author zhujz 2014-7-1
	 */
	public List<Tuser> getUserByRole(String roleID);
	
	/**
	 * 根据ID获取Trole对象
	 * @param id
	 * @return
	 */
	public Trole getRole(String id);

}
