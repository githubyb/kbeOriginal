package edu.nwpu.dmpm.kbe.system.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.nwpu.dmpm.kbe.db.MPMHibernateDaoImpl;
import edu.nwpu.dmpm.kbe.system.dao.ResourceDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tresource;

@Repository
public class ResourceDaoImpl extends MPMHibernateDaoImpl<Tresource> implements ResourceDaoI {
	public String getUrl(String op){
		String hql="from Tresource r where r.op=:op";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("op", op);
		Tresource r=this.get(hql, params);
		if(r!=null){
			return r.getUrl();
		}else{
			return "";
		}
	}
}
