package edu.nwpu.dmpm.kbe.system.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.nwpu.dmpm.kbe.common.controller.BaseController;
import edu.nwpu.dmpm.kbe.common.pageModel.DataGrid;
import edu.nwpu.dmpm.kbe.common.pageModel.Json;
import edu.nwpu.dmpm.kbe.common.pageModel.Tree;
import edu.nwpu.dmpm.kbe.common.util.ConfigUtil;
import edu.nwpu.dmpm.kbe.system.model.Tgroup;
import edu.nwpu.dmpm.kbe.system.model.Tuser;
import edu.nwpu.dmpm.kbe.system.pageModel.PageGroup;
import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.pageModel.User;
import edu.nwpu.dmpm.kbe.system.service.GroupService;
import edu.nwpu.dmpm.kbe.system.service.UserService;

/*
 * 工作组控制器
 * 
 * @author zhangdn
 *
 * @date 2014年5月19日
 * 
 */
@Controller
@RequestMapping("/groupController")
public class GroupController extends BaseController {
	
	@Autowired
	private GroupService groupService;
	@Autowired
	private UserService userService;
	
	/**
	 * 跳转到工作组管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/group")
	public String manager() {
		return "/admin/group";
	}

	
	/**
	 * 获得工作组树tree
	 * 
	 * @return
	 */
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<PageGroup> tree() {
		return groupService.treeGrid();
	}
	
	/**
	 * 跳转到工作组datatGrid
	 * 
	 * @return
	 */
	@RequestMapping("/groupDatagrid")
	public String toPage() {
		return "/admin/groupDataGrid";
	}
	
	/**
	 * 获得工作组dataGrid
	 * 
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid getDataGrid(int page, int rows) {
		return groupService.getDataGrid(page, rows);
	}
	
	/**
	 * 跳转到工作组添加页面
	 * 
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		List<Tuser> userList = userService.getAll();
		request.setAttribute("userList", userList);
		return "/admin/groupAdd";
	}

	/**
	 * 添加工作组
	 * 
	 * @param  pageGroup
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(PageGroup pageGroup) {
		Json j = new Json();
		try{
			pageGroup.setId(UUID.randomUUID().toString());
			//pageGroup.setBelong("PPM");
			groupService.add(pageGroup);
			j.setSuccess(true);
			j.setMsg("添加成功");
			j.setObj(pageGroup);
		} catch (Exception e) {			
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到工作组编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request,String id) {
		Tgroup tgroup = new Tgroup();
		//BeanUtils.copyProperties(groupService.get(id), tgroup);
		tgroup=groupService.get(id);
		if(tgroup.getParent()!=null){
			String parentID=tgroup.getParent().getId();
			request.setAttribute("parentID", parentID);
		}
		request.setAttribute("group", tgroup);
		List<Tuser> userList = userService.getAll();
		request.setAttribute("userList", userList);
		return "/admin/groupEdit";
	}

	/**
	 * 编辑工作组
	 * 
	 * @param pageGroup
	 * 
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(PageGroup pageGroup) {
		Json j = new Json();
		try{
			groupService.edit(pageGroup);
			j.setSuccess(true);
			j.setMsg("添加成功");
			j.setObj(pageGroup);
		} catch (Exception e) {			
			j.setMsg(e.getMessage());
		}
		return j;
	}


	/**
	 * 删除工作组
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String ids) {
		Json j = new Json();
		groupService.delete(ids);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	
	/**
	 * 跳转到工作组成员编辑页面
	 * 
	 * @param groupId
	 * 
	 * @return
	 */
	@RequestMapping("/groupUserEditPage")
	public String groupUser(HttpServletRequest request,String id) {
		User r = groupService.getallUserId(id);
		request.setAttribute("group", r);
		request.setAttribute("groupId", id);
		return "/admin/groupUserEditPage";
	}
	
	/**
	 * 获得资源树(包括所有资源类型)
	 * 
	 * 通过用户ID判断，他能看到的资源
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/allTree")
	@ResponseBody
	public List<Tree> allTree() {
		//SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		return groupService.allTree();
	}
	
	/**
	 * 获得工作组成员dataGrid
	 * 
	 * @param id
	 * 
	 * @return
	 */
	@RequestMapping("/getGroupUser")
	@ResponseBody
	public DataGrid getGroupUser(String id) {
		return groupService.getGroupUser(id);
	}
	
	/**
	 * 获得非本工作组用户dataGrid
	 * 
	 * @param id
	 *
	 * @return
	 */
	@RequestMapping("/getNoGroupUser")
	@ResponseBody
	public DataGrid getNoGroupUser(String id,int page, int rows) {
		return groupService.getNoGroupUser(id, page, rows);
	}
	
	/**
	 *添加工作组成员
	 * 
	 * @param id, userIds
	 * 
	 * @return
	 */
	@RequestMapping("/addGroupUser")
	@ResponseBody
	public Json addGroupUser(String id,String groupIDs) {
		Json j = new Json();
		groupService.addGroupUser(id, groupIDs);
		j.setMsg("添加成功！");
		j.setSuccess(true);
		return j;
	}
	
	/**
	 *删除工作组成员
	 * 
	 * @param id, userIds
	 * 
	 * @return
	 */
	@RequestMapping("/deleteGroupUser")
	@ResponseBody
	public Json deleteGroupUser(String id,String userIds) {
		Json j = new Json();
		groupService.deleteGroupUser( id, userIds);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}
	
	@RequestMapping("/getGroupByParent")
	@ResponseBody
	public List<PageGroup> getGroupByParent(String parentID){
		return groupService.getGroupByParent(parentID);
	}
	
	@RequestMapping("/getGroupTree")
	@ResponseBody
	public List<Tree> getGroupTree(){
		return groupService.getGroupTree();
	}
	
	/**
	 * 获得工作组列表
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("/treeGrid")
	@ResponseBody
	public List<PageGroup> treeGrid() {
		return groupService.treeGrid();
	}
}
