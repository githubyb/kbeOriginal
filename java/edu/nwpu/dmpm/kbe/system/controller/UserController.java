package edu.nwpu.dmpm.kbe.system.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import edu.nwpu.dmpm.kbe.common.controller.BaseController;
import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.pageModel.Json;
import edu.nwpu.dmpm.kbe.common.util.ConfigUtil;
import edu.nwpu.dmpm.kbe.common.util.IpUtil;
import edu.nwpu.dmpm.kbe.common.util.MD5Util;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.PassWordRuleService;
import edu.nwpu.dmpm.kbe.system.service.ResourceService;
import edu.nwpu.dmpm.kbe.system.service.RoleService;
import edu.nwpu.dmpm.kbe.system.service.UserSafetyService;
import edu.nwpu.dmpm.kbe.system.service.UserService;

/**
 * 用户控制器
 * 
 * @author wangc
 * 
 */
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private UserSafetyService userSafetyService;
	
	@Autowired
	private PassWordRuleService passWordRuleServiceI;

	
	//2014.11.6
	@RequestMapping("/userMessage")
	public String userMessage(){
		return "/user/userMessage";
	}
	
	/**
	 * 用户登录
	 * 
	 * @param user
	 *            用户对象
	 * @param session
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Json login(HttpSession session, HttpServletRequest request) {
		Json j = new Json();
		String userName=request.getParameter("name");
		String userPwd=request.getParameter("pwd" );
		Tuser myUser=userService.getUserByName(userName);
		if(myUser!=null){
			if(myUser.getActive()==1){
				if(myUser.getPwd().equals(MD5Util.md5(userPwd))){
				//	User u = userService.login(user);
					userService.changeLoginTime(myUser);
					
					SessionInfo sessionInfo = new SessionInfo();
					BeanUtils.copyProperties(myUser, sessionInfo);
					sessionInfo.setIp(IpUtil.getIpAddr(request));
					sessionInfo.setResourceList(userService.resourceList(myUser.getId()));
					session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);

					j.setObj(sessionInfo);
					j.setSuccess(true);
					j.setMsg("登陆成功！");
				}else{
					int logintime= userService.subtractOneLoginTime(userName);
				    j.setCause("wrongPsw");
				    if(logintime!=0){
				    	j.setMsg("密码错误！剩余登陆次数"+logintime);
				    }else{
				    	j.setMsg("密码错误！该用户已经被锁定，请联系管理员！");
				    }
					
				}
			}else{
				j.setCause("freezed");
				j.setMsg("该用户已经被锁定，请联系管理员！");
			}		
		}else{
			j.setCause("wrongName");
			j.setMsg("用户不存在！");
		}
		return j;
	}
	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public Json logout(HttpSession session) {
		Json j = new Json();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/user";
	}

	/**
	 * 获取用户数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(User user, PageHelper ph) {
		return userService.dataGrid(user, ph);
	}

	/**
	 * 跳转到添加用户页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/admin/userAdd";
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(User user) {
		Json j = new Json();
		try {
			userService.add(user);
			j.setSuccess(true);
			j.setMsg("添加成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到用户修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEdit";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(User user) {
		Json j = new Json();
		try {
			userService.edit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(user);
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil
				.getSessionInfoName());
		Json j = new Json();
		if (id != null && !id.equalsIgnoreCase(sessionInfo.getId())) {// 不能删除自己
			userService.delete(id);
		}
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 *            ('0','1','2')
	 * @return
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session) {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}
	

	/**
	 * 跳转到编辑自己的密码页面
	 * 
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwdPage")
	public String editCurrentUserPwdPage(HttpServletRequest request) {
		Map<String,Object> map= passWordRuleServiceI.getRules();
		request.setAttribute("min", map.get("min").toString().trim());
		request.setAttribute("max", map.get("max").toString().trim());		
        request.setAttribute("needNum", map.get("needNum").toString().trim());	
	    request.setAttribute("needWord", map.get("needWord").toString().trim());	
		request.setAttribute("needCharacter",map.get("needCharacter").toString().trim());
		return "/user/userEditPwd";
	}

	/**
	 * 修改自己的密码
	 * 
	 * @param session
	 * @param pwd
	 * @return
	 */
	@RequestMapping("/editCurrentUserPwd")
	@ResponseBody
	public Json editCurrentUserPwd(HttpSession session, String oldPwd,
			String pwd) {
		Json j = new Json();
		if (session != null) {
			SessionInfo sessionInfo = (SessionInfo) session
					.getAttribute(ConfigUtil.getSessionInfoName());
			if (sessionInfo != null) {							
				String flag=userSafetyService.checkPassword(pwd);
				if(flag.equals(""))
				{
					if (userService.editCurrentUserPwd(sessionInfo, oldPwd, pwd)) {
						j.setSuccess(true);
						j.setMsg("编辑密码成功，下次登录生效！");
					} else {
						j.setMsg("原密码错误！");
						return j;
					}
				}
				if(flag.equals("wrongLength"))
				{
					j.setMsg("长度不符");
					return j;
				}
				if(flag.equals("noNum"))
				{
					j.setMsg("密码必须包含数字");
					return j;
				}
				if(flag.equals("noWord"))
				{
					j.setMsg("密码必须包含字母");
					return j;
				}
				if(flag.equals("noCharacter"))
				{
					j.setMsg("密码必须包含特殊字符");
					return j;
				}
			
			} else {
				j.setMsg("登录超时，请重新登录！");
			}
		} else {
			j.setMsg("登录超时，请重新登录！");
		}
		return j;
	}

	/**
	 * 跳转到显示用户角色页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserRolePage")
	public String currentUserRolePage(HttpServletRequest request,
			HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil
				.getSessionInfoName());
		request.setAttribute("userRoles",
				JSON.toJSONString(roleService.tree(sessionInfo)));
		return "/user/userRole";
	}

	/**
	 * 跳转到显示用户权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/currentUserResourcePage")
	public String currentUserResourcePage(HttpServletRequest request,
			HttpSession session) {
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil
				.getSessionInfoName());
		request.setAttribute("userResources",
				JSON.toJSONString(resourceService.allTree(sessionInfo)));
		return "/user/userResource";
	}

	/**
	 * 用户登录时的autocomplete
	 * 
	 * @param q
	 *            参数
	 * @return
	 */
	@RequestMapping("/loginCombobox")
	@ResponseBody
	public List<User> loginCombobox(String q) {
		return userService.loginCombobox(q);
	}

	/**
	 * 用户登录时的combogrid
	 * 
	 * @param q
	 * @param ph
	 * @return
	 */
	@RequestMapping("/loginCombogrid")
	@ResponseBody
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		return userService.loginCombogrid(q, ph);
	}
	
	@RequestMapping("/avicit")
	public String avicit(HttpServletResponse response,HttpServletRequest request) throws SQLException {
		Map<String, Object> filter = new HashMap<String, Object>();
	//	filter = GetPageParams.GetParams(request);
		String url=userService.getUrl(filter);
		return "redirect:/" +url;
	}
	@RequestMapping("/getUsers")
	@ResponseBody
	public List<Map<String, String>> getUsers(){
		return userService.getUsers();
	}

}
