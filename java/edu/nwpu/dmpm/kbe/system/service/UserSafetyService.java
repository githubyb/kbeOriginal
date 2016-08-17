package edu.nwpu.dmpm.kbe.system.service;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.User;



/**
 * 用户Service
 * 
 * @author wangc
 * 
 */
public interface UserSafetyService {


	/**
	 * 修改密码时校验密码
	 * @param password
	 * @return
	 */
	
	public String checkPassword(String password);

	/**
	 * 用户安全管理界面数据表格
	 * @param user
	 * @param ph
	 * @return
	 */
	public DataGrid dataGrid(User user, PageHelper ph);

	/**
	 * 修改用户状态
	 * @param data
	 */
	public void changeState(String id, String state);

	/**
	 * 修改用户密级
	 * @param id
	 * @param securityclass
	 */
	public void changeSecurityclass(String id, String securityclass);
}
