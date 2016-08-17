package edu.nwpu.dmpm.kbe.common.util;

import edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo;
import edu.nwpu.dmpm.kbe.system.service.UserService;

public class HtmlCodeCreator {
	
	private static UserService userservice;
	
	

	private static final String projectName = "/jquery";

	public static String getHtmlGridCode(SessionInfo sessionInfo) {
		
		//test
		userservice=(UserService)SpringContextUtil.getBean("userServiceImpl");
		String name=userservice.get("0").getName();
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table id=\"dg\" class=\"easyui-datagrid\" title=\"DataGridTest\"\n");
		sb.append("data-options=\"singleSelect:true,collapsible:true,pagination:true,");
		sb.append("pageSize:10,sortName:'name',sortOrder:'asc',rownumbers:true,pagination:true,");
		sb.append("url:'" + projectName);
		sb.append("/userController/dg',method:'get',toolbar:'#tool'\">\n");
		sb.append("<thead data-options=\"frozen:true\">\n");
		sb.append("<tr>\n");
		sb.append("<th data-options=\"field:'id',width:80,checkbox:true\">编号</th>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n");
		sb.append("<thead>\n");
		sb.append("<tr>\n");
		sb.append("<th data-options=\"field:'objectId',width:80\">对象标识</th>\n");
		sb.append("<th data-options=\"field:'classId',width:80\">对象类标识</th>\n");
		sb.append("<th data-options=\"field:'name',width:150\">名称</th>\n");
		sb.append("<th data-options=\"field:'sex',width:150\">性别</th>\n");
		sb.append("<th data-options=\"field:'phone',width:150\">电话</th>\n");
		sb.append("<th data-options=\"field:'version',width:150\">版本</th>\n");
		sb.append("<th data-options=\"field:'creator',width:150\">创建人</th>\n");
		// sb.append("<th data-options=\"field:'action',width:100,formatter:'format'\">操作</th>\n");
		sb.append("<th field=\"action\" formatter=\"format\" width=\"100\">操作</th>\n ");
		// sb.append(getFormatter());
		// sb.append("\">操作</th>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n");

		sb.append("</table>\n");

		/*
		 * sb.append("<script type=\"text/javascript\">\n"); sb.append(
		 * "var toolbar=[{text:'add',iconCls:'icon-add',handler:function(){alert('add')}}];"
		 * ); sb.append("</script>");
		 */

		sb.append("<div id=\"tool\" style=\"display: none;\">\n");
		sb.append("<div style=\"margin-bottom:5px\">\n");
		if (sessionInfo.getResourceList().contains("/userController/addPage")) {
			sb.append("<a onclick=\"addFun();\" href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'pencil_add'\">添加</a>\n");
		}

		if (sessionInfo.getResourceList().contains("/userController/grantPage")) {
			sb.append("<a onclick=\"batchGrantFun();\" href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'tux'\">批量授权</a>\n");
		}

		if (sessionInfo.getResourceList().contains(
				"/userController/batchDelete")) {
			sb.append("<a onclick=\"batchDeleteFun();\" href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'delete'\">批量删除</a>\n");
		}

		sb.append("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'brick_add',plain:true\" onclick=\"searchFun();\">过滤条件</a>\n");
		sb.append("<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"iconCls:'brick_delete',plain:true\" onclick=\"cleanFun();\">清空条件</a>\n");
		sb.append("</div>\n");
		sb.append("</div>\n");

		sb.append("<div id=\"mm\" class=\"easyui-menu\" style=\"width: 120px; display: none;\">");

		if (sessionInfo.getResourceList().contains("/userController/addPage")) {
			sb.append("<div onclick=\"addFun();\" data-options=\"iconCls:'pencil_add'\">增加</div>");
		}
		if (sessionInfo.getResourceList().contains("/userController/delete")) {
			sb.append("<div onclick=\"deleteFun();\" data-options=\"iconCls:'pencil_delete'\">删除</div>");
		}
		if (sessionInfo.getResourceList().contains("/userController/editPage")) {
			sb.append("<div onclick=\"editFun();\" data-options=\"iconCls:'pencil'\">编辑</div>");
		}

		sb.append("</div>");
		// sb.append("");

		return sb.toString();
	}

	public static String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\">\n");
		sb.append("var toolbar=[{text:'add',iconCls:'pencil_add',handler:function(){alert('add')}}];");
		sb.append("</script>");

		return sb.toString();
	}

	private static String getFormatter() {
		StringBuffer sb = new StringBuffer();
		sb.append("formatter:function(value, row, index) {\n");
		sb.append("return \"<img title=\"编辑\" src=\"/jquery/style/images/extjs_icons/pencil.png\" onclick=\"editFun('5')\"\";}");

		/*
		 * sb.append("var str='';\n"); sb.append(
		 * "if ($.canEdit) {	str += $.formatString('<img onclick=\"editFun(\'{0}\');\" src=\"{1}\" title=\"编辑\"/>', row.id, '/jquery/style/images/extjs_icons/pencil.png');}\n"
		 * ); sb.append("str += '&nbsp;';\n"); sb.append(
		 * "if ($.canGrant) {str += $.formatString('<img onclick=\"grantFun(\'{0}\');\" src=\"{1}\" title=\"授权\"/>', row.id, '/jquery/style/images/extjs_icons/key.png');}\n"
		 * ); sb.append("str += '&nbsp;';\n"); sb.append(
		 * "if ($.canDelete) {str += $.formatString('<img onclick=\"deleteFun(\'{0}\');\" src=\"{1}\" title=\"删除\"/>', row.id, '/jquery/style/images/extjs_icons/cancel.png');}\n"
		 * ); sb.append("str += '&nbsp;';\n"); sb.append(
		 * "if ($.canEditPwd) {str += $.formatString('<img onclick=\"editPwdFun(\'{0}\');\" src=\"{1}\" title=\"修改密码\"/>', row.id, '/jquery/style/images/extjs_icons/lock/lock_edit.png');}\n"
		 * );
		 */

		// sb.append("return str;}");

		return sb.toString();
	}
	
	public static String getHtmlFormCode(SessionInfo sessionInfo){
		StringBuffer sb=new StringBuffer();
		sb.append("<form id=\"searchForm\">\n");
		sb.append("<table class=\"table table-hover table-condensed\" style=\"display: none;\">\n");
		sb.append("<tr>\n");
		sb.append("<th>登录名</th>\n");
		sb.append("<td><input name=\"name\" placeholder=\"可以模糊查询登录名\" class=\"span2\" /></td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<th>创建时间</th>\n");
		sb.append("<td><input class=\"span2\" name=\"createdatetimeStart\" placeholder=\"点击选择时间\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})\" readonly=\"readonly\" />至<input class=\"span2\" name=\"createdatetimeEnd\" placeholder=\"点击选择时间\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})\" readonly=\"readonly\" /></td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<th>最后修改时间</th>");
		sb.append("<td><input class=\"span2\" name=\"modifydatetimeStart\" placeholder=\"点击选择时间\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})\" readonly=\"readonly\" />至<input class=\"span2\" name=\"modifydatetimeEnd\" placeholder=\"点击选择时间\" onclick=\"WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})\" readonly=\"readonly\" /></td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</form>");
		
		return sb.toString();
	}
}
