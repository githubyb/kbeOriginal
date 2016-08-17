package edu.nwpu.dmpm.kbe.system.service;

import javax.servlet.http.HttpSession;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.system.pageModel.LogModel;

/*
 *@author zhangdn
 *
 *@date 2014年5月19日
 */
public interface AuditlogService {
	
	public String addLog(HttpSession session, String level,String module, String action, String message);
	
	public DataGrid getLogInfo(LogModel log ,int page ,int rows);

}
