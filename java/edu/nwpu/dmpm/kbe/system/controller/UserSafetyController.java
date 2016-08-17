package edu.nwpu.dmpm.kbe.system.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nwpu.dmpm.kbe.common.controller.BaseController;
import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.pageModel.Json;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.PassWordRule;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.PassWordRuleService;
import edu.nwpu.dmpm.kbe.system.service.ResourceService;
import edu.nwpu.dmpm.kbe.system.service.UserSafetyService;
import edu.nwpu.dmpm.kbe.system.service.UserService;

/**
 * 用户控制器
 * 
 * @author wangc
 * 
 */
@Controller
@RequestMapping("/userSafetyController")
public class UserSafetyController extends BaseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private PassWordRuleService passWordRuleServiceI;
	
	@Autowired
	private UserSafetyService userSafetyService;

	
	/**
	 * 跳转到用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager() {
		return "/admin/userSafety";
	}

	/**
	 * 跳转到密码匹配规则页面
	 * 
	 * @return
	 */
	@RequestMapping("/passWordRulePage")
	public String passWordRulePage(HttpServletRequest request) {	
		Map<String,Object> map= passWordRuleServiceI.getRules();
		request.setAttribute("min", map.get("min").toString().trim());
		request.setAttribute("max", map.get("max").toString().trim());
		
		if (map.get("needNum").toString().trim().equals("on"))
		{request.setAttribute("needNum", "checked");}
		else{request.setAttribute("needNum", "");}
		
		if( map.get("needWord").toString().trim().equals("on")){
			request.setAttribute("needWord", "checked");
		}else{request.setAttribute("needWord", "");}
		
		if( map.get("needCharacter").toString().trim().equals("on")){
			request.setAttribute("needCharacter","checked");
		}else{request.setAttribute("needCharacter","");}
		
		return "/user/passWordRule";
	}
	
	/**
	 * 存储密码匹配规则
	 * 
	 * @return
	 */
	@RequestMapping("/addpassWordRule")
	@ResponseBody
	public Json allWordRule(PassWordRule passWordRules) {
		Json j = new Json();
		passWordRuleServiceI.allWordRule(passWordRules);
		j.setSuccess(true);
		j.setMsg("设置成功！");
		return j;
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
		return userSafetyService.dataGrid(user, ph);
	}
	/**
	 * 状态激活
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/changeState")
	@ResponseBody
	public Json state(String id,String state) {
		Json j = new Json();
		userSafetyService.changeState(id,state);
		j.setSuccess(true);
		j.setMsg("设置成功！");
		return j;
	}
	/**
	 * 跳转到用户授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/grantPage")
	public String grantPage(String ids, HttpServletRequest request) {
		request.setAttribute("ids", ids);
		if (ids != null && !ids.equalsIgnoreCase("") && ids.indexOf(",") == -1) {
			User u = userService.get(ids);
			request.setAttribute("user", u);
		}
		return "/admin/userGrant";
	}
	/**
	 * 用户授权
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/grant")
	@ResponseBody
	public Json grant(String ids, User user) {
		Json j = new Json();
		userService.grant(ids, user);
		j.setSuccess(true);
		j.setMsg("授权成功！");
		return j;
	}
	
	/**
	 * 跳转到编辑用户密码页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/editPwdPage")
	public String editPwdPage(String id, HttpServletRequest request) {
		User u = userService.get(id);
		request.setAttribute("user", u);
		return "/admin/userEditPwd";
	}
	/**
	 * 编辑用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/editPwd")
	@ResponseBody
	public Json editPwd(User user) {
		Json j = new Json();
		userService.editPwd(user);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
	/**
	 * 跳转到修改用户密级页面
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeSecurityclassPage")
	public String changeSecurityclassPage(String id, HttpServletRequest request) {
		User u = userService.get(id);
		request.setAttribute("id", id);
		request.setAttribute("username", u.getName());
		return "/admin/userSecurityclassChange";		
	}
	/**
	 * 修改用户密级
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/changeSecurityclass")
	@ResponseBody
	public Json changeSecurityclass(String id,String securityclass) {
		Json j = new Json();
		userSafetyService.changeSecurityclass(id,securityclass);
		j.setSuccess(true);
		j.setMsg("编辑成功！");
		return j;
	}
}
