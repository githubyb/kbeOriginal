package edu.nwpu.dmpm.kbe.system.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class SysTest {
	@Autowired
	UserService userService;
	@Test
	public void testCache(){
		List<User> users=userService.getUserComboboxByProUnit("adgdgf", "d1d9af8b-f1ae-4fb6-a1e9-9d1c658a03a0");
		System.out.println(users.size());
	}
}
