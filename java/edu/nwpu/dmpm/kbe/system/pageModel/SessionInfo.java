package edu.nwpu.dmpm.kbe.system.pageModel;

import java.util.List;

/**
 * session信息模型
 * 
 * @author wangc
 * 
 */
public class SessionInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4069281193658067294L;
	
	private String id;// 用户ID
	private String name;// 用户登录名
	private String ip;// 用户IP
	private String mac;//MAC地址
	private String displayName;//显示用户姓名
	private List<String> resourceList;// 用户可以访问的资源地址列表

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public List<String> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<String> resourceList) {
		this.resourceList = resourceList;
	}
	@Override
	public String toString() {
		return this.name;
	}

}
