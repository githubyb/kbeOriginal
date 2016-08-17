package edu.nwpu.dmpm.kbe.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/*
 * 工作组实体类
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
@Entity
@Table(name = "KBE_GROUP")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Tgroup implements Serializable {

	private static final long serialVersionUID = 6590646728669062860L;
	
	private String id;
	private String name;
	private String remark;
	private String number;
	
	private String leaderId;//组负责人Id
	private String leaderName;//组负责人名 
	
	private String belong;//所属系统
	private String groupType; // 组类别，行政组、团队、项目组等  addBy zhjz 2014-6-11 0-行政组，1-项目组，
	
	// 父子结构关系 addBy zhjz 2014-6-20
	private Tgroup parent;
	private List<Tgroup> children;

	//private String icon;图标，暂不考虑，功能完成后再添加
	private Set<Tuser> tusers = new HashSet<Tuser>(0);
	
	private String proUnitId;//所属项目单元
	
	@Column(name = "prounitid", length=64)
	public String getProUnitId() {
		return proUnitId;
	}

	public void setProUnitId(String proUnitId) {
		this.proUnitId = proUnitId;
	}

	/**
	 * @addBy zhjz 2014-6-11
	 * @param name 工作组名称
	 * @param groupType 工作组类别 0-行政组，1-团队，2-项目组
	 */
	public Tgroup(String name,String groupType){
		this.id=UUID.randomUUID().toString();
		this.name=name;
		this.groupType=groupType;
	}
	
	public Tgroup(){
		
	}
	
	public Tgroup(String id){
		this.id = id;
	}
	
	public Tgroup(String id ,String name, String remark, Set<Tuser> tusers){
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.tusers = tusers;
	}
	
	@Column(name = "group_Type", length=8)
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name = "LEADERID", length = 80)
	public String getLeaderId() {
		return leaderId;
	}
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}
	
	
	@Column(name = "LEADERNAME", length = 80)
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@ManyToMany(fetch = FetchType.LAZY)                                                                                                                                                                                                                                                                                           
	@JoinTable(name = "kbe_group_user", joinColumns = { @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	public Set<Tuser> getTusers() {
		return tusers;
	}
	public void setTusers(Set<Tuser> tusers) {
		this.tusers = tusers;
	}
	
	@Column(name = "NO", length = 100)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column(name = "BELONGS", length = 100)
	public String getBelong() {
		return belong;
	}

	public void setBelong(String belong) {
		this.belong = belong;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="P_ID")
	public Tgroup getParent() {
		return parent;
	}

	public void setParent(Tgroup parent) {
		this.parent = parent;
	}

	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	public List<Tgroup> getChildren() {
		return children;
	}

	public void setChildren(List<Tgroup> children) {
		this.children = children;
	}
}
