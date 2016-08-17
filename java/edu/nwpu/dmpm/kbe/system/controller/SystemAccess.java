package edu.nwpu.dmpm.kbe.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nwpu.dmpm.kbe.common.controller.BaseController;
import edu.nwpu.dmpm.kbe.common.util.ConfigUtil;
import edu.nwpu.dmpm.kbe.common.util.IpUtil;
import edu.nwpu.dmpm.kbe.common.util.StringUtil;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.ResourceService;
import edu.nwpu.dmpm.kbe.system.service.UserService;

/**
 * @author wangc
 * 系统访问入口
 *
 */
@Controller
@RequestMapping("/systemAccess")
public class SystemAccess extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResourceService resourceService;
	/**
	 * 时间：2014.11.6
	 * 金航业务系统访问工艺业务管理系统入口
	 * @param userName 用户名
	 * @param url 访问页面；默认进入系统主界面
	 * @param session 
	 * @param request
	 * @return 访问页面
	 */
	@RequestMapping("/avicit")
	public String avicitlogin(String userName,String action, HttpSession session, HttpServletRequest request) {
		User u = userService.avicitlogin(userName);
		if (u != null ) {
			SessionInfo sessionInfo = new SessionInfo();
			BeanUtils.copyProperties(u, sessionInfo);
			sessionInfo.setIp(IpUtil.getIpAddr(request));
			sessionInfo.setResourceList(userService.resourceList(u.getId()));
			session.setAttribute(ConfigUtil.getSessionInfoName(), sessionInfo);
		} else{
			return "redirect:/userController/userMessage";
		}
		if(StringUtil.isNullOrEmpty(action)){
			return "redirect:/";
		}else{
			String url=resourceService.getUrl(action);
			return "redirect:"+url;
		}
		
	}
}
