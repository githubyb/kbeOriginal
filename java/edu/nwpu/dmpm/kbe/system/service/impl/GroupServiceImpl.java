package edu.nwpu.dmpm.kbe.system.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.pageModel.Tree;
import edu.nwpu.dmpm.kbe.common.util.StringUtil;
import edu.nwpu.dmpm.kbe.system.dao.GroupDaoI;
import edu.nwpu.dmpm.kbe.system.dao.UserDaoI;
import edu.nwpu.dmpm.kbe.system.model.Tgroup;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageGroup;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.GroupService;

/*
 * 方法实现类
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 */
@Service("groupServiceImpl")
@Transactional
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupDaoI groupDao;
	
	@Autowired
	private UserDaoI userDao;

	@Override
	public List<Tgroup> getAll() {
		return groupDao.find("from Tgroup");
	}
	
	
	
	@Override
	public DataGrid getDataGrid(int page, int rows) {
		DataGrid dg = new DataGrid();
		Long total = groupDao.count("select count(*) from Tgroup ");
		List<Tgroup> List=groupDao.find("from Tgroup",page, rows);
		List<PageGroup> pageList = new ArrayList<PageGroup>();
		for (Tgroup t : List) {
			PageGroup g = new PageGroup();
			BeanUtils.copyProperties(t, g);
			
			if(t.getParent() != null){
				g.setParentID(t.getParent().getId());
			}
			//Tuser user = userDao.get(Tuser.class, t.getLeaderId());
			//g.setLeaderName(user.getDisplayName());
			pageList.add(g);
		}
		dg.setTotal(total);
		dg.setRows(pageList);
		return dg;
	}

	@Override
	public void add(PageGroup pageGroup) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("number", pageGroup.getNumber());
		Long count=groupDao.count("select count(*) from Tgroup t where t.number = :number",
				params);
		if (count> 0) {
			throw new Exception("该编号已存在！");
		} else {
			Tgroup t = new Tgroup();
			t.setNumber("0");
			BeanUtils.copyProperties(pageGroup, t);
			Tuser user = userDao.get(Tuser.class, t.getLeaderId());
			t.setLeaderName(user.getDisplayName());
			if(StringUtil.isNotEmpty(pageGroup.getParentID())){
				Tgroup g=groupDao.get(Tgroup.class, pageGroup.getParentID());
				if(g!=null){
					t.setParent(g);
				}
			}
			groupDao.save(t);
		}
		
	}

	@Override
	public void edit(PageGroup pageGroup) throws Exception {
		Tgroup t = this.get(pageGroup.getId());
		BeanUtils.copyProperties(pageGroup, t);
		Tuser user = userDao.get(Tuser.class, t.getLeaderId());
		t.setLeaderName(user.getDisplayName());
		Tgroup g=groupDao.get(Tgroup.class, pageGroup.getParentID());
		if(g!=null){
			t.setParent(g);
		}
		groupDao.update(t);

	}

	@Override
	public void saveGroup(Tgroup tgroup){
		groupDao.save(tgroup);
	}
	
	public void updateGroupInProUnit(String groupId, String proUnitId){
		Tgroup group=getGroupByProUnit(proUnitId);
		if(group!=null){
			return;//已存在不用更新
		}else{
			Tgroup g=get(groupId);
			g.setProUnitId(proUnitId);
			saveGroup(g);
		}
	}
	private Tgroup getGroupByProUnit(String proUnitId){
		String hql="from Tgroup g where g.proUnitId=:proUnitId";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("proUnitId", proUnitId);
		Tgroup group=groupDao.get(hql, params);
		return group;
	}

	@Override
	public Tgroup get(String id) {
		return groupDao.get( Tgroup.class, id );
	}

	@Override
	public void update(Tgroup tgroup) throws Exception {
		groupDao.update(tgroup);
	}

	@Override
	public void delete(String ids) {
		String[] id = ids.split(",");
		for(int i = 0; i < id.length; i++){
			Tgroup tgroup = groupDao.get(Tgroup.class,id[i]);
			
			if(tgroup.getGroupType().equals("1")){ // addBy zhujz 2014-7-15 删除项目组时，有序项目对象与组对象关联，防止出错
				try{
					groupDao.delete(tgroup);
				}catch(Exception ex){
					continue;
				}
			}
			groupDao.delete(tgroup);
		}
	}

	@Override
	public DataGrid getGroupUser(String id) {
		DataGrid dg = new DataGrid();
		List<User> pageList = new ArrayList<User>();
		Tgroup t = this.get(id);
		Set<Tuser> users = t.getTusers();
		for(Tuser tu : users){
			User user = new User();
			BeanUtils.copyProperties(tu, user);
			// 暂时没有分页的方法，需要考虑
			pageList.add(user);
		}
		dg.setTotal((long)users.size());
		dg.setRows(pageList);
		return dg;
	}

	@Override
	public List<Tuser> getGroupUsers(String groupID) {
		Set<Tuser> user=get(groupID).getTusers();
		List<Tuser> lu=new ArrayList(user);
		Collections.sort(lu,new Comparator<Tuser>(){
			public int compare(Tuser t0,Tuser t1){
				return t0.getName().compareTo(t1.getName());
			}
		});
		return lu;
	}
	public List<User> getGroupUsersByProUnit(String proUnitId){
		List<Tuser> users=new ArrayList<Tuser>();
		List<User> pageUsers=new ArrayList<User>();
		Tgroup group=getGroupByProUnit(proUnitId);
		if(group!=null){
			users.addAll(group.getTusers());
		}
		for(Tuser user: users){
			User pageUser=new User();
			BeanUtils.copyProperties(user, pageUser);
			pageUsers.add(pageUser);
		}
		return pageUsers;
	}
	@Override
	public DataGrid getNoGroupUser(String id, int page, int rows) {
		//List<Tuser> ltu=userDao.find("select distinct u from Tuser u left join fetch u.group as g where g.groupType not in ( '0','1')");
		
		DataGrid dg = new DataGrid();
		//Map<String, Object> params = new HashMap<String, Object>();
		//params.put("belong", "PPM");
		List<User> pageList = new ArrayList<User>();
		Tgroup tgroup = groupDao.get(Tgroup.class,id);
		Set<Tuser> tuser = tgroup.getTusers();
		String hql = "";
		if(tuser.size()==0){
			hql += "from Tuser t where ";
		}else{
			hql += "from Tuser t where ";
			for(Tuser tu : tuser){
				hql +="t.id <>'";
				hql +=tu.getId();
				hql +="'";
				hql +=" and ";
			}
		}
		hql += "1 = 1";
		//考虑使用静态方法，不能直接使用userDao
		List<Tuser> userList = userDao.find(hql,page, rows);
		for(Tuser t : userList){
			User user = new User();
			BeanUtils.copyProperties(t, user);
			
			//addBy zhujz 2014-7-15
			String gNames="";
			String gIDs="";
			for(Tgroup g: t.getGroup()){
				gNames+=g.getName()+",";
				gIDs+=g.getId()+",";
			}
			user.setGroupIDs(gIDs);
			user.setGroupNames(gNames);
			
			pageList.add(user);
		}
		String couthql = "select count(*)"+hql;
		long total = userDao.count(couthql);
		dg.setTotal(total);
		dg.setRows(pageList);
		return dg;
	}

	@Override
	public void addGroupUser(String id, String userIds) {
		Tgroup tgroup = groupDao.get(Tgroup.class,id);
		Set<Tuser> t = new HashSet<Tuser>(); 
		if (tgroup.getTusers()!= null) {
			 t =  tgroup.getTusers();
		}
		String[] userId = userIds.split(",");
		for(int i = 0; i < userId.length; i++){
			Tuser tuser = userDao.get(Tuser.class,userId[i]);
			t.add(tuser);
		}
		tgroup.setTusers(t);
		//将组员数赋给
		int number = tgroup.getTusers().size();
		tgroup.setNumber(String.valueOf(number));
		groupDao.save(tgroup);

	}

	@Override
	public void deleteGroupUser(String id, String userIds) {
		Tgroup tgroup = groupDao.get(Tgroup.class,id);
		Set<Tuser> t = tgroup.getTusers();
		String[] userId = userIds.split(",");
		for(int i = 0; i < userId.length; i++){
			Tuser tuser = userDao.get(Tuser.class,userId[i]);
			t.remove(tuser);
		}
		tgroup.setTusers(t);
		int number = tgroup.getTusers().size();
		tgroup.setNumber(String.valueOf(number));
		groupDao.save(tgroup);
	}

	@Override
	public Tgroup getGroupByType(String type,String userID) {
		
		Tgroup factory=getFactoryByUser(userID);
		factory.setChildren(getChildrenByType(type, factory));
		return factory;
		
		/*for(Tgroup g : factory.getChildren()){
			if(g.getGroupType().equals("0")){
				g.setChildren(getChildrenByType(type, g));
			}
		}
		return groupDao.find("select t from Tgroup t where t.groupType='"+type+"'");*/
		
	}
	
	private List<Tgroup> getChildrenByType(String type,Tgroup g){
		List<Tgroup> res=new ArrayList<Tgroup>(10);
		for(Tgroup gc : g.getChildren()){
			if(gc.getGroupType().equals("0")){
				gc.setChildren(getChildrenByType(type, gc));
				res.add(gc);
			}
		}
		return res;
	}
	
	@Override
	public List<Tgroup> getFactory() {
		// TODO Auto-generated method stub
		
		return groupDao.find("select t from Tgroup t left join fetch t.parent as p where p.id is null order by t.name");
	}

	@Override
	public List<PageGroup> getGroupByParent(String parentID) {
		// TODO Auto-generated method stub
		List<Tgroup> ltg=new ArrayList<Tgroup>(10);
		
		if(parentID.equals("")){
			ltg=groupDao.find("select t from Tgroup t left join fetch t.parent as p where p.id is null order by t.name");
		}else{
			ltg=groupDao.find("select t from Tgroup t left join fetch t.parent as p where p.id = '"+parentID+"' order by t.name");
		}
		
		List<PageGroup> pageList = new ArrayList<PageGroup>();
		for (Tgroup t : ltg) {
			PageGroup g = new PageGroup();
			BeanUtils.copyProperties(t, g);
			if(t.getChildren().size()==0){
				g.setState("open");
			}
			if(t.getParent() != null){
				g.setParentID(t.getParent().getId());
			}
			//Tuser user = userDao.get(Tuser.class, t.getLeaderId());
			//g.setLeaderName(user.getDisplayName());
			pageList.add(g);
		}
		return pageList;
	}



	@Override
	public List<Tree> getGroupTree() {
		// TODO Auto-generated method stub
		List<Tgroup> ltg=groupDao.find("select distinct t from Tgroup t");
		List<Tree> lt=new ArrayList<Tree>();
		for(Tgroup g:ltg){
			Tree t=new Tree();
			t.setId(g.getId());
			t.setText(g.getName());
			if(g.getParent()!=null){
				t.setPid(g.getParent().getId());
			}
			lt.add(t);
		}
		return lt;
	}

	@Override
	public Tgroup getUserFactory(String userID) {
		// TODO Auto-generated method stub
		
		Tuser u=userDao.get(Tuser.class,userID);
		Tgroup tg = null;
		for(Tgroup g:u.getGroup()){
			if(g.getGroupType().equals("0")){
				tg=getFactory(g);
			}
		}
		return tg;
	}
	
	private Tgroup getFactory(Tgroup g){
		Tgroup gp = g.getParent();
		if(gp.getParent()!=null){
			gp=getFactory(gp);
		}
		return gp;
	}



	@Override
	public Tgroup getFactoryByUser(String userID) {
		// TODO Auto-generated method stub
		Tgroup res=new Tgroup();
		Tuser u=userDao.get(Tuser.class,userID);
		for(Tgroup g: u.getGroup()){
			if(g.getGroupType().equals("0")){
				res=getFactory(g);
				break;
			}
		}
		return res;
	}
	
	public List<PageGroup> treeGrid(){
		List<Tgroup> l = null;
		List<PageGroup> lr = new ArrayList<PageGroup>();

		Map<String, Object> params = new HashMap<String, Object>();
		l = groupDao.find("select distinct t from Tgroup t ", params);
		

		if (l != null && l.size() > 0) {
			for (Tgroup t : l) {
				PageGroup r = new PageGroup();
				BeanUtils.copyProperties(t, r);
				String type=t.getGroupType();
				if(type.equals("-1")){
					r.setIconCls("icon-factory");
				}else if(type.equals("0")){
					r.setIconCls("icon-admin-group");
				}else if(type.equals("1")){
					r.setIconCls("icon-team-group");
				}
				//modify by daijj 2016/3/25
				r.setText(t.getName());
				
				if (t.getParent() != null) {
					r.setParentID(t.getParent().getId());
				}
				lr.add(r);
			}
		}

		return lr;
	}
	@Override
	public List<Tree> allTree(){
		List<Tuser> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		l = userDao.find("select distinct t from Tuser t ", params);
		if (l != null && l.size() > 0) {
			for (Tuser r : l) {
				Tree tree = new Tree();
				BeanUtils.copyProperties(r, tree);
				tree.setText(r.getDisplayName()+r.getName());//modify by daijj
				tree.setIconCls("icon-user");
				lt.add(tree);
			}
		}
		return lt;
		
	}
	@Override
	public User getallUserId(String id) {
		User r = new User();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tgroup t = groupDao.get( Tgroup.class, id );
		if (t != null) {
			BeanUtils.copyProperties(t, r);
			Set<Tuser> s = t.getTusers();
			if (s != null && !s.isEmpty()) {
				boolean b = false;
				String ids = "";
				String names = "";
				for (Tuser tr : s) {
					if (b) {
						ids += ",";
						names += ",";
					} else {
						b = true;
					}
					ids += tr.getId();
					names += tr.getName();
				}
				r.setGroupIDs(ids);
				r.setGroupNames(names);
			}
		}
		return r;
	}
	public List<Tgroup> getAllProjectGroup(){
		String hql="from Tgroup g where g.groupType='1'";
		return groupDao.find(hql);
	}
}
