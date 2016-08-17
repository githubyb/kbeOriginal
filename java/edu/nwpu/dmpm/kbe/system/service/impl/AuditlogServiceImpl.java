package edu.nwpu.dmpm.kbe.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.util.ConfigUtil;
import edu.nwpu.dmpm.kbe.system.dao.AuditlogDaoI;
import edu.nwpu.dmpm.kbe.system.model.Auditlog;
import edu.nwpu.dmpm.kbe.system.pageModel.LogModel;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.service.AuditlogService;

/*
 *@author zhangdn
 *
 *@date 2014年5月19日
 */
@Service("auditlogServiceImpl")
@Transactional
public class AuditlogServiceImpl implements AuditlogService {

	@Autowired
	private AuditlogDaoI auditlogdao;

	@Override
	public DataGrid getLogInfo(LogModel log,int page ,int rows){
		DataGrid dg = new DataGrid();
		if(log.getLoglevel()==null && log.getLogowner()==null && log.getLogtimeEnd()==null 
				&& log.getLogtimeStart()==null && log.getModule()==null){
			List<Auditlog> l=new ArrayList<Auditlog>();
			dg.setRows(l);
			dg.setTotal((long)l.size());
		}else {
			Map<String, Object> params = new HashMap<String, Object>();
			String hql = " from Auditlog a ";
			List<Auditlog> l = auditlogdao.find(hql + whereHql(log, params), params,page,rows);
			String totalhql = "select count(*) from Auditlog a ";
			Long total = auditlogdao.count(totalhql + whereHql(log, params), params);
			dg.setRows(l);
			dg.setTotal(total);
		}
		return dg;
	}
	private String whereHql(LogModel log, Map<String, Object> params) {
		String whereHql = "";
		if (log != null) {
			
			whereHql += " where 1=1 ";
			//owner
			if(!log.getLogowner().isEmpty()){
				whereHql+=" and a.userId like :logowner";
				params.put("logowner", "%%"+log.getLogowner()+"%%");
			}
			
			//loglevel
			if(!log.getLoglevel().isEmpty()){
				whereHql+=" and a.loglevel = :loglevel";
				params.put("loglevel", log.getLoglevel());
			}
			
			//logType
			/*if(log.getModule()!=null){
				whereHql+=" and a.module = :module";
				params.put("module", log.getModule());
			}*/
			if(!log.getLogType().isEmpty()){
				whereHql+=" and a.logType = :logType";
				params.put("logType", log.getLogType());
			}
			if(!log.getIpAddress().isEmpty()){
				whereHql+=" and a.ipAddress = :ipAddress";
				params.put("ipAddress", log.getIpAddress());
			}
			//time
			if(log.getLogtimeStart()!=null){
				whereHql += " and a.logtime >= :logtimeStart";
				params.put("logtimeStart", log.getLogtimeStart());
			}
			if(log.getLogtimeEnd()!=null){
				whereHql += " and a.logtime <= :logtimeEnd";
				params.put("logtimeEnd", log.getLogtimeEnd());
			}
			
		}
		return whereHql;
	}

	private String add(Auditlog dt) {
		String str="";
		try {
			str=auditlogdao.save(dt).toString();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return str;
	}

	@Override
	public String addLog(HttpSession session, String level,String module, String action, String message){
		Auditlog log=new Auditlog();
		
		SessionInfo sessionInfo=(SessionInfo)session.getAttribute(ConfigUtil.getSessionInfoName());
		log.setLogid(UUID.randomUUID().toString());
		log.setUserName(sessionInfo.getName());
		log.setIpAddress(sessionInfo.getIp());
		log.setNetCardId(sessionInfo.getMac());
		log.setLogtime(new Date());
		log.setLoglevel(level);
		log.setModule(module);
		log.setOperation(action);
		log.setMessage(message);
		
		return add(log);
	}

}
