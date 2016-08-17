<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>日志查询</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url :'${pageContext.request.contextPath}/auditLogController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'logid',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : '',
			sortOrder : '',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			frozenColumns : [ [ {
				field : 'logid',
				title : '序列号',
				width : 150,
				hidden : true
			}] ],
			columns : [ [ {
				field : 'logType',
				title : '日志类型',
				width : 90
			}, {
				field : 'loglevel',
				title : '日志等级',
				width : 60
			}, {
				field : 'logtime',
				title : '时间',
				width : 40
			}, {
				field : 'userId',
				title : '登录名',
				width : 70
			}, {
				field : 'userName',
				title : '用户姓名',
				width : 50
			},{
				field : 'ipAddress',
				title : 'IP地址',
				width : 50
			},{
				field : 'netCardId',
				title : 'MAC地址',
				width : 80
			},{
				field : 'module',
				title : '模块',
				width : 80
			} ,{
				field : 'operation',
				title : '操作',
				width : 80
			},{
				field : 'objectId',
				title : '对象标识',
				width : 80
			},{
				field : 'objectClassId',
				title : '对象类型',
				width : 80
			},{
				field : 'objectName',
				title : '对象名称',
				width : 80
			},{
				field : 'objectSecurity',
				title : '对象密级',
				width : 80
			}] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 150px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
					    <th>日志类型</th>
					    <td><select name="logType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
								<option value="kbeSystemLog">系统日志</option>
								<option value="KnowledgeStructureLog">知识库结构日志</option>
								<option value="KnowledgeDataLog">知识库数据日志</option>
								
						</select></td>
						<th>日志级别</th>
						<td><select name="loglevel" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
								<option value="LL1">信息日志</option>
								<option value="LL2">警告日志</option>
								<option value="LL3">错误日志</option>
								<option value="LL4">重大事件日志</option>
						</select></td>
						
						
					</tr>
					<tr>
						<th>开始时间</th>
						<td ><input class="span2" name="logtimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					     <th>结束时间</th>
						<td ><input class="span2" name="logtimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
					</tr>
				<tr>
				        <th>登录名</th>
						<td><input name="logowner" placeholder="可以模糊查询" class="span2" /></td>
						<th>IP地址</th>
						<td><input name="ipAddress" placeholder="可以模糊查询" class="span2" /></td>
				</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-filter',plain:true" onclick="searchFun();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">重置</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">当天查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">近一周查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">近半月查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">近一月查询</a>
		
	</div>

</body>
</html>