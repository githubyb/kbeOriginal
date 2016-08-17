package edu.nwpu.dmpm.kbe.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.system.dao.PassWordRuleDaoI;
import edu.nwpu.dmpm.kbe.system.dao.ResourceDaoI;
import edu.nwpu.dmpm.kbe.system.dao.UserDaoI;
import edu.nwpu.dmpm.kbe.system.model.TPassWordRule;
import edu.nwpu.dmpm.kbe.system.model.Trole;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageHelper;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.UserSafetyService;

@Service("userSafetyServiceImpl")
@Transactional
public class UserSafetyServiceImpl implements UserSafetyService {

	@Autowired
	private UserDaoI userDao;

	@Autowired
	private ResourceDaoI resourceDao;
	
	@Autowired
	private PassWordRuleDaoI passWordRuleDao;

	@Override
	public String checkPassword(String password) {
		// TODO Auto-generated method stub
		 String flag="";
		 int min=0;
		 int max=0;
		
		 //判断密码长度
		 
		TPassWordRule tPassWordRule1= passWordRuleDao.get("select distinct u from TPassWordRule u where u.id ='1'");//获得最小位数
		if (tPassWordRule1!=null)
		{
			min=Integer.valueOf(tPassWordRule1.getRegex());
		}	
		TPassWordRule tPassWordRule2= passWordRuleDao.get("select distinct u from TPassWordRule u where u.id ='2'");//获得最大位数
		if (tPassWordRule2!=null)
		{
			max=Integer.valueOf(tPassWordRule2.getRegex());
		}		
		 if(password.length()<min||password.length()>max)
		 {
			 flag="wrongLength";
			 return flag;
		 }
		 
		 //判断是否冒包含数字
		 String ruleflag="";
		 TPassWordRule tPassWordRule3= passWordRuleDao.get("select distinct u from TPassWordRule u where u.id ='3'");//获得必须包含数字的规则状态，是否选中
			if (tPassWordRule3!=null)
			{
				ruleflag=tPassWordRule3.getFlag();
			}
		 
			//如果选中，则校验密码中是否含有数字，包含则继续校验，不包含则return错误信息
			if(ruleflag.equals("on"))
			{
				 String regex =tPassWordRule3.getRegex();//获得判断是否包含数字的正则表达式
				 Pattern pat = Pattern.compile(regex);  
				   Matcher matcher = pat.matcher(password);
				   boolean result=matcher.find();
				   if (!result) { 
					   flag="noNum";
					   return flag;
				   } 
			}
		
			 //判断是否冒包含字母
			 TPassWordRule tPassWordRule4= passWordRuleDao.get("select distinct u from TPassWordRule u where u.id ='4'");//获得必须包含字母的规则状态，是否选中
				if (tPassWordRule4!=null)
				{
					ruleflag=tPassWordRule4.getFlag();
				}
			 
				//如果选中，则校验密码中是否含有字母，包含则继续校验，不包含则return错误信息
				if(ruleflag.equals("on"))
				{
					 String regex =tPassWordRule4.getRegex();//获得判断是否包含字母的正则表达式
					 Pattern pat = Pattern.compile(regex);  
					   Matcher matcher = pat.matcher(password);
					   boolean result=matcher.find();
					   if (!result) { 
						   flag="noWord";
						   return flag;
					   } 
				}
			
				 //判断是否冒包含特殊字符
				 TPassWordRule tPassWordRule5= passWordRuleDao.get("select distinct u from TPassWordRule u where u.id ='5'");//获得必须包含字母的规则状态，是否选中
					if (tPassWordRule5!=null)
					{
						ruleflag=tPassWordRule5.getFlag();
					}
				 
					//如果选中，则校验密码中是否含有字母，包含则继续校验，不包含则return错误信息
					if(ruleflag.equals("on"))
					{
						 String regex =tPassWordRule5.getRegex();//获得判断是否包含字母的正则表达式
						 Pattern pat = Pattern.compile(regex);  
						   Matcher matcher = pat.matcher(password);
						   boolean result=matcher.find();
						   if (!result) { 
							   flag="noCharacter";
							   return flag;
						   } 
					}			
	     return flag;
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
						
						//modify by daijj 2016/3/25
						if(!(tr.getName().equals("系统管理员")||tr.getName().equals("安全管理员")||tr.getName().equals("审计管理员"))){
						
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
							roleIds += tr.getId();
							roleNames += tr.getName();
						}
						
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
			    u.setSecurityclass(t.getSecurityclass());
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
	public void changeState(String id,String state) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		Tuser tuser=userDao.get("select distinct u from Tuser u where u.id = :id",params);
		if(tuser!=null){
			if(state.equals("0")){
				if(tuser.getActive()==1){
					tuser.setActive(0);
					userDao.update(tuser);;
				}
			}
			if(state.equals("1")){
				if(tuser.getActive()==0){
					tuser.setActive(1);
					tuser.setLoginTime(3);
					userDao.update(tuser);
				}
			}
		}
	}

	@Override
	public void changeSecurityclass(String id, String securityclass) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		Tuser tuser=userDao.get("select distinct u from Tuser u where u.id = :id",params);
		if(tuser!=null&&securityclass.equals("0"))
		{
			tuser.setSecurityclass("普通");
		}
		if(tuser!=null&&securityclass.equals("1"))
		{
			tuser.setSecurityclass("秘密");
		}
		if(tuser!=null&&securityclass.equals("2"))
		{
			tuser.setSecurityclass("机密");
		}
		if(tuser!=null&&securityclass.equals("3"))
		{
			tuser.setSecurityclass("绝密");
		}
		
		userDao.update(tuser);
	}	
	
}
