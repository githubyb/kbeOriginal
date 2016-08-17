package edu.nwpu.dmpm.kbe.system.pageModel;

import java.util.Date;
/**
 *
 *@author zhjz
 *@date 2014-6-23
 */

public class PDictionaryData  implements java.io.Serializable{

	private String ID;
	private String dictID;
	//private String pid;
	//private String name;
	//private String iconCls;
	//private String state="open";
	private String creatorName;
	private String creatorID;
	private Date creatTime;
	private String type;
	//private Integer sqe;
	
	private String key;
	private String value;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDictID() {
		return dictID;
	}
	public void setDictID(String dictID) {
		this.dictID = dictID;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		this.ID = id;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
