package edu.nwpu.dmpm.kbe.system.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.nwpu.dmpm.kbe.system.service.InitService;

/**
 * 初始化数据库控制器
 * 
 * @author wangc
 * 
 */
@Controller
@RequestMapping("/initController")
public class InitController {

	@Autowired
	private InitService initService;

	/**
	 * 初始化数据库后转向到首页
	 * 
	 * @return
	 */
	@RequestMapping("/init")
	public String init(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		
		initService.init();//暂时删除
		
		return "redirect:/";
	}

}
