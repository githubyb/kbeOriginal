package edu.nwpu.dmpm.kbe.system.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/*
 * 日志数据库实体类
 * 
 * @author zhangdn
 * 
 * @date 2014年5月19日
 */
@Entity
@Table(name = "kbe_auditlog")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Auditlog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2600817188983155524L;
	
	private String logid;
	private String classId;//系统类型
	private String logType;//日志类型 英文拼写
	private String loglevel;//日志等级
	private Date logtime;//时间
	private String userId;//用户标识 登录名
	private String userName;//用户名 真实姓名
	private String userSecurity;//用户密级
	private String ipAddress;//Ip地址
	private String netCardId;//MAC地址
	private String module;//功能模块
	private String operation;//操作
	private String objectId;//对象标识
	private String objectClassId;//对象类型
	private String objectName;//对象名称
	private String objectSecurity;//对象密级
	private String message;//描述
	
	
	
	@Id
	@Column(name = "LOGID", unique = true, nullable = false)
	public String getLogid() {
		return logid;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGTIME")
	public Date getLogtime() {
		return logtime;
	}
	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}
	
	@Column(name = "LOGLEVEL")
	public String getLoglevel() {
		return loglevel;
	}
	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}
	
	
	
	
	
	@Column(name = "MODULE")
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	@Column(name = "CLASSID")
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	@Column(name = "LOGTYPE")
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	@Column(name = "USERID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name = "USERNAME")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "IPADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	@Column(name = "USERSECURITY")
	public String getUserSecurity() {
		return userSecurity;
	}
	public void setUserSecurity(String userSecurity) {
		this.userSecurity = userSecurity;
	}
	@Column(name = "NETCARDID")
	public String getNetCardId() {
		return netCardId;
	}
	public void setNetCardId(String netCardId) {
		this.netCardId = netCardId;
	}
	@Column(name = "OPERATION")
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	@Column(name = "OBJECTID")
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	@Column(name = "OBJECTCLASSID")
	public String getObjectClassId() {
		return objectClassId;
	}
	public void setObjectClassId(String objectClassId) {
		this.objectClassId = objectClassId;
	}
	@Column(name = "OBJECTNAME")
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	@Column(name = "OBJECTSECURITY")
	public String getObjectSecurity() {
		return objectSecurity;
	}
	public void setObjectSecurity(String objectSecurity) {
		this.objectSecurity = objectSecurity;
	}
	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
