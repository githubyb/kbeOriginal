package edu.nwpu.dmpm.kbe.system.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.system.dao.PassWordRuleDaoI;
import edu.nwpu.dmpm.kbe.system.model.TPassWordRule;
import edu.nwpu.dmpm.kbe.system.pageModel.PassWordRule;
import edu.nwpu.dmpm.kbe.system.service.PassWordRuleService;

@Service
@Transactional
public class PassWordRuleServiceImpl implements PassWordRuleService {

	@Autowired
	private PassWordRuleDaoI passWordRuleDaoI;

	@Override
	public void allWordRule(PassWordRule passWordRules) {
		// TODO Auto-generated method stub
		// 存储密码最小位数
		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("id", "1");

		TPassWordRule tPassWordRule1 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params1);
		if (tPassWordRule1 != null) {
			tPassWordRule1.setRegex(passWordRules.getMin());
		}		

		// 存储密码最大位数		
		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", "2");

		TPassWordRule tPassWordRule2 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params2);
		if (tPassWordRule2 != null) {
			tPassWordRule2.setRegex(passWordRules.getMax());
		}

		// 存储是否必须包含数字
		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("id", "3");

		TPassWordRule	tPassWordRule3 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params3);
		if (tPassWordRule3 != null) {
			if(passWordRules.getNeedNum()!=null)
			{
				tPassWordRule3.setFlag(passWordRules.getNeedNum());
			}else{
				tPassWordRule3.setFlag("false");
			}
		}

		// 存储是否必须包含字母
		Map<String, Object> params4 = new HashMap<String, Object>();
		params4.put("id", "4");

		TPassWordRule tPassWordRule4 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params4);
		if (tPassWordRule4 != null) {
			if(passWordRules.getNeedWord()!=null)
			{
				tPassWordRule4.setFlag(passWordRules.getNeedWord());
			}else{
				tPassWordRule4.setFlag("false");
			}
		}

		// 存储是否必须包含特殊字符
		Map<String, Object> params5 = new HashMap<String, Object>();
		params5.put("id", "5");

		TPassWordRule tPassWordRule5 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params5);
		if (tPassWordRule5 != null) {
			if(passWordRules.getNeedCharacter()!=null)
			{
				tPassWordRule5.setFlag(passWordRules.getNeedCharacter());
			}else{
				tPassWordRule5.setFlag("false");
			}
		}
		
		passWordRuleDaoI.update(tPassWordRule1);
		passWordRuleDaoI.update(tPassWordRule2);
		passWordRuleDaoI.update(tPassWordRule3);
		passWordRuleDaoI.update(tPassWordRule4);
		passWordRuleDaoI.update(tPassWordRule5);
	}

	@Override
	public Map<String, Object> getRules() {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		// 存储密码最小位数
		TPassWordRule tPassWordRule1 = new TPassWordRule();

		Map<String, Object> params1 = new HashMap<String, Object>();
		params1.put("id", "1");

		tPassWordRule1 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params1);
		if (tPassWordRule1 != null) {
			map.put("min", tPassWordRule1.getRegex());
		}

		// 存储密码最大位数
		TPassWordRule tPassWordRule2 = new TPassWordRule();

		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", "2");

		tPassWordRule2 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params2);
		if (tPassWordRule2 != null) {
			map.put("max", tPassWordRule2.getRegex());
		}

		// 存储是否必须包含数字
		TPassWordRule tPassWordRule3 = new TPassWordRule();

		Map<String, Object> params3 = new HashMap<String, Object>();
		params3.put("id", "3");

		tPassWordRule3 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params3);
		if (tPassWordRule3 != null) {
			map.put("needNum", tPassWordRule3.getFlag());
		}

		// 存储是否必须包含字母
		TPassWordRule tPassWordRule4 = new TPassWordRule();

		Map<String, Object> params4 = new HashMap<String, Object>();
		params4.put("id", "4");

		tPassWordRule4 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params4);
		if (tPassWordRule4 != null) {
			map.put("needWord", tPassWordRule4.getFlag());
		}

		// 存储是否必须包含特殊字符
		TPassWordRule tPassWordRule5 = new TPassWordRule();

		Map<String, Object> params5 = new HashMap<String, Object>();
		params5.put("id", "5");

		tPassWordRule5 = passWordRuleDaoI.get(
				"select distinct t from TPassWordRule t where t.id = :id",
				params5);
		if (tPassWordRule5 != null) {
			map.put("needCharacter", tPassWordRule5.getFlag());
		}

		return map;
	}

}
