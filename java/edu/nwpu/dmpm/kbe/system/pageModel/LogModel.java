package edu.nwpu.dmpm.kbe.system.pageModel;

import java.io.Serializable;
import java.util.Date;

/*
 * Auditlog 页面类
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
public class LogModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 312622864328975257L;
	
	private String logowner;//使用人
	private Date logtimeStart;//时间
	private Date logtimeEnd;
	private String loglevel;//日志等级
	private String module;//功能模块
	private String logType;
	private String ipAddress;
	
	public String getLogowner() {
		return logowner;
	}
	public void setLogowner(String logowner) {
		this.logowner = logowner;
	}
	public Date getLogtimeStart() {
		return logtimeStart;
	}
	public void setLogtimeStart(Date logtimeStart) {
		this.logtimeStart = logtimeStart;
	}
	public Date getLogtimeEnd() {
		return logtimeEnd;
	}
	public void setLogtimeEnd(Date logtimeEnd) {
		this.logtimeEnd = logtimeEnd;
	}
	public String getLoglevel() {
		return loglevel;
	}
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
