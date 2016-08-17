package edu.nwpu.dmpm.kbe.system.service;

import java.util.Map;

import edu.nwpu.dmpm.kbe.system.pageModel.PassWordRule;

public interface PassWordRuleService {

	public void allWordRule(PassWordRule passWordRules);

	public Map<String, Object> getRules();
	

}
