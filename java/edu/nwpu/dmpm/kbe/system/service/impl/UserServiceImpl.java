package edu.nwpu.dmpm.kbe.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.util.DateUtil;
import edu.nwpu.dmpm.kbe.common.util.MD5Util;
import edu.nwpu.dmpm.kbe.common.util.StringUtil;
import edu.nwpu.dmpm.kbe.common.util.UUIDUtil;
import edu.nwpu.dmpm.kbe.system.dao.ResourceDaoI;
import edu.nwpu.dmpm.kbe.system.dao.RoleDaoI;
import edu.nwpu.dmpm.kbe.system.dao.UserDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tresource;
import edu.nwpu.dmpm.kbe.system.model.Trole;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.UserService;

@Service("userServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private RoleDaoI roleDao;

	@Autowired
	private ResourceDaoI resourceDao;
	
	

	public User avicitlogin(String userName){
		User user=new User();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", userName);
		Tuser t = userDao.get(
				"from Tuser t where t.name = :name", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}		
		return null;
	}
	
	@Override
	public User login(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.md5(user.getPwd()));
		Tuser t = userDao.get(
				"from Tuser t where  t.name = :name and t.pwd = :pwd", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			return user;
		}		
		return null;
	}

	@Override
	public DataGrid dataGrid(User user, PageHelper ph) {
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params)
				+ orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Trole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);					
				}
				u.setTeam(t.getTeam());
			    if(u.getActive()==0)
				  {
				      u.setFlag("锁定");
				  }else{
					  u.setFlag("激活");
				  }
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count(
				"select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			
			hql += " where 1=1 "; 
			//modify by daijj 2016/3/24
			if(user.getId()!=null){
				hql += " and t.id!='0' ";
			}
			if (user.getName() != null) {
				hql += " and t.name like :name";
				params.put("name", "%%" + user.getName() + "%%");
			}
			if (user.getCreatedatetimeStart() != null) {
				hql += " and t.createdatetime >= :createdatetimeStart";
				params.put("createdatetimeStart", user.getCreatedatetimeStart());
			}
			if (user.getCreatedatetimeEnd() != null) {
				hql += " and t.createdatetime <= :createdatetimeEnd";
				params.put("createdatetimeEnd", user.getCreatedatetimeEnd());
			}
			if (user.getModifydatetimeStart() != null) {
				hql += " and t.modifydatetime >= :modifydatetimeStart";
				params.put("modifydatetimeStart", user.getModifydatetimeStart());
			}
			if (user.getModifydatetimeEnd() != null) {
				hql += " and t.modifydatetime <= :modifydatetimeEnd";
				params.put("modifydatetimeEnd", user.getModifydatetimeEnd());
			}
		}
		
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	@Override
	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		if (userDao.count("select count(*) from Tuser t where t.name = :name",
				params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			if(StringUtil.isNullOrEmpty(u.getId())){
				u.setId(UUIDUtil.getUUID());
			}
			u.setCreatedatetime(DateUtil.currentDate());
			u.setModifydatetime(DateUtil.currentDate());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setTeam(user.getTeam());
			u.setLoginTime(3);
			u.setActive(0);
			userDao.save(u);
			
			
		}
	}

	@Override
	public User get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao
				.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id",
						params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if (t.getTroles() != null && !t.getTroles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Trole role : t.getTroles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	@Override
	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("name", user.getName());
		if (userDao
				.count("select count(*) from Tuser t where t.name = :name and t.id != :id",
						params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getId());
//			BeanUtils.copyProperties(user, u, new String[] { "pwd",
//					"createdatetime" });
			u.setName(user.getName());
			u.setModifydatetime(new Date());
			u.setDisplayName(user.getDisplayName());
			u.setAbbreviation(user.getAbbreviation());
			userDao.update(u);
		}
	}

	@Override
	public void delete(String id) {
		Tuser user=userDao.get(Tuser.class, id);
		userDao.delete(user);
	}

	@Override
	public void grant(String ids, User user) {
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					Tuser t = userDao.get(Tuser.class, id);
					t.setTroles(new HashSet<Trole>(roles));
				}
			}
		}
	}

	@Override
	public List<String> resourceList(String id) {
		List<String> resourceList = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao
				.get("from Tuser t join fetch t.troles role join fetch role.tresources resource where t.id = :id",
						params);
		if (t != null) {
			Set<Trole> roles = t.getTroles();
			if (roles != null && !roles.isEmpty()) {
				for (Trole role : roles) {
					Set<Tresource> resources = role.getTresources();
					if (resources != null && !resources.isEmpty()) {
						for (Tresource resource : resources) {
							if (resource != null && resource.getUrl() != null) {
								resourceList.add(resource.getUrl());
							}
						}
					}
				}
			}
		}
		return resourceList;
	}

	@Override
	public void editPwd(User user) {
		if (user != null && user.getPwd() != null
				&& !user.getPwd().trim().equalsIgnoreCase("")) {
			Tuser u = userDao.get(Tuser.class, user.getId());
			u.setPwd(MD5Util.md5(user.getPwd()));
			u.setModifydatetime(new Date());
		}
	}

	@Override
	public boolean editCurrentUserPwd(SessionInfo sessionInfo, String oldPwd,
			String pwd) {
		Tuser u = userDao.get(Tuser.class, sessionInfo.getId());
		if (u.getPwd().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPwd(MD5Util.md5(pwd));
			u.setModifydatetime(new Date());
			return true;
		}
		return false;
	}

	@Override
	public List<User> loginCombobox(String q) {
		if (q == null) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find(
				"from Tuser t where t.name like :name order by name", params,
				1, 10);
		List<User> ul = new ArrayList<User>();
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				ul.add(u);
			}
		}
		return ul;
	}
	public List<User> getUserCombobox(String q){
		if (StringUtil.isNullOrEmpty(q)) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		params.put("abbreviation", "%%" + q.trim() + "%%");
		params.put("displayName", "%%" + q.trim() + "%%");
		
		List<Tuser> tl = userDao.find(
				"from Tuser t where "
				+ "t.name like :name or "
				+ "t.abbreviation like :abbreviation or "
				+ "t.displayName like :displayName "
				+ "order by name", 
				params,
				1, 5);
		List<User> ul = new ArrayList<User>();
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setId(t.getId());
				u.setName(t.getName());
				u.setDisplayName(t.getDisplayName());
				ul.add(u);
			}
		}
		return ul;
	}
	public List<User> getUserComboboxByProUnit(String q,String proUnitId){
		if (StringUtil.isNullOrEmpty(q)) {
			q = "";
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		params.put("abbreviation", "%%" + q.trim() + "%%");
		params.put("displayName", "%%" + q.trim() + "%%");
		
		List<Tuser> tl = userDao.find(
				"from Tuser t  inner join fetch t.group g"
				+ " where "
				+ "(t.name like :name or "
				+ "t.abbreviation like :abbreviation or "
				+ "t.displayName like :displayName) and g.proUnitId='"+proUnitId+"' "
				+ "order by t.name", 
				params,
				1, 5);
		List<User> ul = new ArrayList<User>();
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setId(t.getId());
				u.setName(t.getName());
				u.setDisplayName(t.getDisplayName());
				ul.add(u);
			}
		}
		return ul;
	}

	@Override
	public DataGrid loginCombogrid(String q, PageHelper ph) {
		if (q == null) {
			q = "";
		}
		DataGrid dg = new DataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", "%%" + q.trim() + "%%");
		List<Tuser> tl = userDao.find(
				"from Tuser t where t.name like :name order by " + ph.getSort()
						+ " " + ph.getOrder(), params, ph.getPage(),
				ph.getRows());
		if (tl != null && tl.size() > 0) {
			for (Tuser t : tl) {
				User u = new User();
				u.setName(t.getName());
				u.setCreatedatetime(t.getCreatedatetime());
				u.setModifydatetime(t.getModifydatetime());
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count(
				"select count(*) from Tuser t where t.name like :name", params));
		return dg;
	}

	@Override
	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("s", k);
			params.put("e", k + 2);
			k = k + 2;
			l.add(userDao
					.count("select count(*) from Tuser t where HOUR(t.createdatetime)>=:s and HOUR(t.createdatetime)<:e",
							params));
		}
		return l;
	}

	@Override
	public Tuser getByID(String id) {
		return userDao.get("select u from Tuser u where u.id='" + id + "'");
	}

	@Override
	public List<Tuser> getAll() {
		return userDao.find("from Tuser");
	}

	@Override
	public List<Tuser> getUserByGroupRole(String roleID, String groupID) {
		List<Tuser> lu = userDao
				.find("select distinct u from Tuser u left join fetch u.group as g left join fetch u.troles as r where g.id = '"
						+ groupID + "' and r.id = '" + roleID + "'");
		return lu;
	}

	@Override
	public boolean changeLoginTime(User user) {		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",user.getId());
		Tuser t = userDao.get(
				"from Tuser t where t.id = :id", params);
		if (t != null) {
		    t.setLoginTime(3);
		    userDao.update(t);
		    return true;
		}			
		return false;
	}
	public boolean changeLoginTime(Tuser t) {		
		if (t != null) {
		    t.setLoginTime(3);
		    userDao.update(t);
		    return true;
		}			
		return false;
	}
	
	@Override
	public Tuser getUserByName(String name)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name",name);
		Tuser t = userDao.get(
				"from Tuser t where t.name = :name", params);
		if (t != null) {
		   return t;
		}	else{
			t=null;
		}		
		return t;		
	}
	
	@Override
	public int subtractOneLoginTime(String name) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		Tuser t = userDao.get("from Tuser t where t.name = :name", params);
		if (t != null) {
			if ((t.getLoginTime() - 1) == 0) {
				t.setLoginTime(0);
				t.setActive(0);
			} else {
				t.setLoginTime(t.getLoginTime() - 1);
			}
			userDao.update(t);
		}
		return t.getLoginTime();
	}
	
	@Override
	public String getUrl(Map<String, Object> filter) throws SQLException {
		String url="";
		String op=filter.get("op").toString();
		if (op.equals("gotask")) {
			String taskId = filter.get("taskId").toString();
		//	url = TemUtil.getFlowService().getdoTaskUrl(taskId);
		}
		if(op.equals("startflow")){
			url="startflowController/getFlow#";
		}
		return url;
	}
	public List<Map<String, String>> getUsers(){
		List<Map<String, String>> lm=new ArrayList<Map<String,String>>();
		List<Tuser> users=getAll();
		for(Tuser user: users){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", user.getId());
			map.put("text", user.getDisplayName()+"["+user.getName()+"]");
			lm.add(map);
		}
		return lm;
	}
	public List<Map<String, String>> getUsersByProUnit(String proUnitId){
		List<Map<String, String>> lm=new ArrayList<Map<String,String>>();
		List<Tuser> users = userDao
				.find("select distinct u from Tuser u left join fetch u.group as g  where g.proUnitId = '"
						+ proUnitId+"'");
		for(Tuser user: users){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", user.getId());
			map.put("text", user.getDisplayName()+"["+user.getName()+"]");
			lm.add(map);
		}
		return lm;
	}
	public List<Map<String, String>> getUsersByGroup(String groupId){
		List<Map<String, String>> lm=new ArrayList<Map<String,String>>();
		List<Tuser> users = userDao
				.find("select distinct u from Tuser u left join fetch u.group as g  where g.id = '"
						+ groupId+"'");
		for(Tuser user: users){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", user.getId());
			map.put("text", user.getDisplayName()+"["+user.getName()+"]");
			lm.add(map);
		}
		return lm;
	}
	public List<Map<String, String>> getUsersByRole(String roldId){
		List<Map<String, String>> lm=new ArrayList<Map<String,String>>();
		List<Tuser> users = userDao
				.find("select distinct u from Tuser u left join fetch u.troles as r  where r.id = '"
						+ roldId+"'");
		for(Tuser user: users){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", user.getId());
			map.put("text", user.getDisplayName()+"["+user.getName()+"]");
			lm.add(map);
		}
		return lm;
	}
	public List<Map<String, String>> getUserNamesByRole(String roldId){
		List<Map<String, String>> lm=new ArrayList<Map<String,String>>();
		List<Tuser> users = userDao
				.find("select distinct u from Tuser u left join fetch u.troles as r  where r.id = '"
						+ roldId+"'");
		for(Tuser user: users){
			Map<String, String> map=new HashMap<String, String>();
			map.put("id", user.getName());
			map.put("text", user.getDisplayName()+"["+user.getName()+"]");
			lm.add(map);
		}
		return lm;
	}
}
