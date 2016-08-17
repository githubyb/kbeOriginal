package edu.nwpu.dmpm.kbe.system.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;



/**
 * 用户Service
 * 
 * @author wangc
 * 
 */
public interface UserService {

	/**
	 * 金航业务系统用户登录
	 * @param userName 用户名
	 * @return 用户对象
	 */
	public User avicitlogin(String userName);
	/**
	 * 用户登录
	 * 
	 * @param user
	 *            里面包含登录名和密码
	 * @return 用户对象
	 */
	public User login(User user);

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	public DataGrid dataGrid(User user, PageHelper ph);

	/**
	 * 添加用户
	 * 
	 * @param user
	 */
	public void add(User user) throws Exception;

	/**
	 * 获得用户对象
	 * 
	 * @param id
	 * @return
	 */
	public User get(String id);

	/**
	 * 编辑用户
	 * 
	 * @param user
	 */
	public void edit(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @param user
	 *            需要user.roleIds的属性值
	 */
	public void grant(String ids, User user);

	/**
	 * 获得用户能访问的资源地址
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	public List<String> resourceList(String id);

	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 */
	public void editPwd(User user);

	/**
	 * 修改用户自己的密码
	 * 
	 * @param sessionInfo
	 * @param oldPwd
	 * @param pwd
	 * @return
	 */
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd, String pwd);

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	public List<User> loginCombobox(String q);
	public List<User> getUserCombobox(String q);
	public List<User> getUserComboboxByProUnit(String q,String proUnitId);

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	public DataGrid loginCombogrid(String q, PageHelper ph);

	/**
	 * 用户创建时间图表
	 * 
	 * @return
	 */
	public List<Long> userCreateDatetimeChart();
	
	/**
	 * 根据用户ID获取Tuser对象
	 * Add by zhjz
	 * @param id
	 * @return
	 */
	public Tuser getByID(String id);
	
	/**
	 * 获取所有用户的List
	 * 
	 * @author zhangdn
	 * @date 2014-06-11
	 * @param 
	 * @return
	 */
	public List<Tuser> getAll();
	
	/**
	 * 根据工作组和角色获取人员
	 * @param roleID
	 * @param gtoupID
	 * @return
	 * @author zhujz 2014-7-2
	 */
	public List<Tuser> getUserByGroupRole(String roleID,String groupID);

	/**
	 * 冻结用户
	 * @param user
	 */
	public boolean changeLoginTime(User user);
	public boolean changeLoginTime(Tuser user);

	/**
	 * 通过名称查找用户
	 * @param name
	 * @return
	 */
	public Tuser getUserByName(String name);

	/**
	 * 密码输入错误导致登录失败则登录机会减一
	 * @param user
	 * @return
	 */
	public int subtractOneLoginTime(String name);
	
	/**
	 * 获取待办任务的URl
	 * @param taskId
	 * @return
	 */
	public String getUrl(Map<String, Object> filter) throws SQLException;
	
	public List<Map<String, String>> getUsers();
	public List<Map<String, String>> getUsersByProUnit(String proUnitId);
	public List<Map<String, String>> getUsersByGroup(String groupId);
	public List<Map<String, String>> getUsersByRole(String roldId);
	public List<Map<String, String>> getUserNamesByRole(String roldId);
}
