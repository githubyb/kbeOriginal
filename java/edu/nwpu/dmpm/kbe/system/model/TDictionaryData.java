package edu.nwpu.dmpm.kbe.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "mpm_DICTIONARYDATA")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TDictionaryData {

	private String ID;
	private TDictionary dictionary;
	private String key;
	private String value;
	
	private Tuser creator;
	private Date creatTime;
	private String description;	
	
	
	@Id
	@Column(name="ID",nullable=false,length=64)
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DICTIONARY_ID")
	public TDictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(TDictionary dictionary) {
		this.dictionary = dictionary;
	}
	
	@Column(name="KEY",length=64)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	@Column(name="VALUE",length=128)
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	
	@Column(name="description",length=255)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
