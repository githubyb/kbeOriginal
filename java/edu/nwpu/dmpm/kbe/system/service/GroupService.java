package edu.nwpu.dmpm.kbe.system.service;

import java.util.List;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.pageModel.Tree;
import edu.nwpu.dmpm.kbe.system.model.Tgroup;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageGroup;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;

/*
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
public interface GroupService {
	
	/**
	 * 获得所有工作组
	 * 
	 * @return
	 */
	public List<Tgroup> getAll();
	
	/**
	 * 获得工作组dataGrid
	 * 
	 * @return
	 */
	public DataGrid getDataGrid(int page, int rows);
	
	/**
	 * 添加工作组
	 * 
	 * @param group
	 * 
	 * @return
	 */
	public void add(PageGroup pageGroup) throws Exception;
	
	/**
	 * 编辑工作组
	 * 
	 * @param pageGroup
	 * 
	 * @return
	 */
	public void edit(PageGroup pageGroup) throws Exception;
	
	/**
	 * 保存工作组
	 * 
	 * @param group
	 * 
	 * @return
	 */
	public void saveGroup(Tgroup tgroup);
	
	public void updateGroupInProUnit(String groupId, String proUnitId);
	
	/**
	 * 获取工作组
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public Tgroup get(String id) ;
	
	/**
	 * 更新工作组
	 * 
	 * @param tgroup
	 * 
	 * @return
	 */
	public void update(Tgroup tgroup) throws Exception;
	
	/**
	 * 删除组
	 * 
	 * @param ids
	 * 
	 * @return
	 */
	public void delete(String ids);
	
	/**
	 * 获得本组成员dataGrid
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public DataGrid getGroupUser(String id);
	
	/**
	 * 获取本组成员 List<Tuser> 用name排序
	 * @param groupID
	 * @return
	 * @author zhujz 2014-7-2
	 */
	public List<Tuser> getGroupUsers(String groupID);
	public List<User> getGroupUsersByProUnit(String proUnitId);
	
	/**
	 * 获得非本组用户dataGrid
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public DataGrid getNoGroupUser(String id,int page, int rows);
	
	/**
	 * 添加工作组成员
	 * 
	 * @param  id, userIds
	 * 
	 * @return
	 */
	public void addGroupUser(String id,String userIds);
	
	/**
	 * 删除工作组成员
	 * 
	 * @param  id, userIds
	 * 
	 * @return
	 */
	public void deleteGroupUser(String id,String userIds);
	
	/**
	 * 获取某人所在厂的所有工作组
	 * @param type 工作组类别代号   0-行政组，1-项目组
	 * @param userID 人员ID
	 * @return
	 * @modified by zhujz 2014-7-15
	 */
	public Tgroup getGroupByType(String type,String userID);
	
	/**
	 * 获取某人属于某厂
	 * @param userID 人员ID
	 * @return
	 * @author zhujz 2014-7-15
	 */
	public Tgroup getFactoryByUser(String userID);
	
	/**
	 * 获取所有厂
	 * @return
	 * @author zhujz 2014-7-14
	 */
	public List<Tgroup> getFactory();
	
	/**
	 * 获取工作组的父ID获取工作组
	 * @param parentID 
	 * @return
	 * @author zhujz 2014-7-14
	 */
	public List<PageGroup> getGroupByParent(String parentID);
	
	/**
	 * 获取工作组树，所有
	 * @return
	 * @author zhujz 2014-7-14
	 */
	public List<Tree> getGroupTree();
	
	/**
	 * 获取某用户的厂
	 * @param userID 
	 * @return
	 */
	public Tgroup getUserFactory(String userID);
	
	/**
	 * 获取工作组的treegrid
	 */
	public List<PageGroup> treeGrid();
	/**
	 * 获得人员树
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public List<Tree> allTree();
	
	/**
	 * 获得组里所有人员
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param sessionInfo
	 * @return
	 */
	public User getallUserId(String id);
	public List<Tgroup> getAllProjectGroup();
}
