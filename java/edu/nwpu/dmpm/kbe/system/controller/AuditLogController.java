package edu.nwpu.dmpm.kbe.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nwpu.dmpm.kbe.common.controller.BaseController;
import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.system.pageModel.LogModel;
import edu.nwpu.dmpm.kbe.system.service.AuditlogService;

/*
 * AuditLog 控制器
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
@Controller
@RequestMapping("/auditLogController")
public class AuditLogController extends BaseController {
	
	@Autowired
	private AuditlogService auditlogService;
	
	 
	@RequestMapping("/log")
	public String manager(HttpServletRequest request) {
		return "/admin/log";
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LogModel log,int page, int rows) {
		return auditlogService.getLogInfo(log,page, rows);
	}
	
	@RequestMapping("/logStat")
	public String logStat(HttpServletRequest request){
		return "/admin/logstat";
	}

}
