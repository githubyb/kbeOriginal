package edu.nwpu.dmpm.kbe.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 *@author zhjz
 *@date 2014-6-20
 */
@Entity
@Table(name = "mpm_DICTIONARY")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TDictionary implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6099089975555264429L;
	
	private String id;
	private String text;
	private String name;
	private TDictionary parent;
	private Integer sqe;
	private String type="1";  // 类别属性，0-系统字典，不能删除  1-用户创建
	
	private Tuser creator;
	private Date creatTime;
	
	private List<TDictionary> children=new ArrayList<TDictionary>(0);	
	private List<TDictionaryData> datas=new ArrayList<TDictionaryData>(0);
	
	
	public TDictionary(){}
	
	public TDictionary(String text,String type){
		this.id=UUID.randomUUID().toString();
		this.text=text;
		this.type=type;
		this.creatTime=new Date();
	}
	
	@Id
	@Column(name="ID",nullable=false,length=64)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="TEXT",length=64)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Column(name="name",unique=true,nullable=false,length=64)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="SQE")
	public Integer getSqe() {
		return sqe;
	}
	
	public void setSqe(Integer sqe) {
		this.sqe = sqe;
	}	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="P_ID")
	public TDictionary getParent() {
		return parent;
	}
	public void setParent(TDictionary parent) {
		this.parent = parent;
	}
	@OneToMany(fetch=FetchType.LAZY,mappedBy="parent")
	public List<TDictionary> getChildren() {
		return children;
	}
	public void setChildren(List<TDictionary> children) {
		this.children = children;
	}
	@Column(name="TYPE",length=8)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@OneToMany(fetch=FetchType.EAGER,mappedBy="dictionary")
	public List<TDictionaryData> getDatas() {
		return datas;
	}
	public void setDatas(List<TDictionaryData> datas) {
		this.datas = datas;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATOR_ID")
	public Tuser getCreator() {
		return creator;
	}
	public void setCreator(Tuser creator) {
		this.creator = creator;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
}
