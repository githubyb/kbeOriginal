package edu.nwpu.dmpm.kbe.system.pageModel;

import java.io.Serializable;

/*
 * Tgroup 页面类
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
public class PageGroup implements Serializable {

	private static final long serialVersionUID = 2472267942133116781L;
	
	private String id;
	private String name;
	private String remark;
	private String leaderId;
	private String leaderName;
	private String number;
	private String belong;
	private String iconCls;
	
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	//add by zhu 2014-7-14
	private String groupType;
	private String parentID;
	private String state="open";
	//add by daijj 2015-1-13
	private String text;
	
	private String proUnitId;
	private String proUnitNo;
	
	
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getProUnitId() {
		return proUnitId;
	}
	public void setProUnitId(String proUnitId) {
		this.proUnitId = proUnitId;
	}
	public String getProUnitNo() {
		return proUnitNo;
	}
	public void setProUnitNo(String proUnitNo) {
		this.proUnitNo = proUnitNo;
	}

}
