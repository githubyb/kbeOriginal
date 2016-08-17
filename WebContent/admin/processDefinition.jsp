<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>流程定义</title>
<jsp:include page="../inc.jsp"></jsp:include>
<script type="text/javascript">
	var processGrid;
	$(function() {
		processGrid = $('#processGrid').datagrid({
			url : '${pageContext.request.contextPath}/deployController/processDefinitionGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'key',
			sortOrder : 'asc',
			singleSelect:true,
			columns : [ [
			{
				field : 'id',
				title : '标识',
				width : 140
			},
			{
				field : 'key',
				title : 'key',
				width : 120,
				sortable : true
			},{
				field : 'name',
				title : '名称',
				width : 100
			}, {
				field : 'version',
				title : '版本',
				width : 30
			}, {
				field : 'deploymentId',
				title : '部署ID',
				width : 50
			}, {
				field : 'suspend',
				title : '状态',
				width : 50,
				formatter:function(value, row, index){
					if(value==1){
						return '激活';
					}else if(value==2){
						return '挂起';
					}
				}
			}, {
				field : 'resourceName',
				title : 'bpmn',
				width : 50,
				hidden:true
			},{
				field : 'diagramResourceName',
				title : '图片',
				width : 50,
				hidden:true
			},{
				field : 'action',
				title : '操作',
				width : 150,
				formatter : function(value, row, index) {
					var str = '';
					str += $.formatString('<a target="_blank" class="icon-export-xml" href="${pageContext.request.contextPath}/deployController/read-resource?pdid={0}&resourceName={1}"  title="查看XML"></a>', row.id,row.resourceName);
					str += '&nbsp;&nbsp;';
					
					str += $.formatString('<a href="javascript:void(0);" class="icon-photo" onclick="viewChart(\'{0}\',\'{1}\');"  title="查看流程图"></a>', row.id,row.diagramResourceName);
					str += '&nbsp;&nbsp;';
					if (row.suspend==1) {
						str += $.formatString('<a href="javascript:void(0);" class="icon-suspend" onclick="execState(\'{0}\',\'{1}\');"  title="挂起"></a>', row.id,'suspend');
					}else if(row.suspend==2){
						str += $.formatString('<a href="javascript:void(0);" class="icon-active" onclick="execState(\'{0}\',\'{1}\');"  title="激活"></a>', row.id,'active');
					}
					
					str += '&nbsp;&nbsp;';				
					str += $.formatString('<a href="javascript:void(0);" class="icon-down" onclick="downloadResourceFun(\'{0}\',\'{1}\');"  title="下载BPMN"></a>', row.id,row.resourceName);
					str += '&nbsp;&nbsp;';				
					str += $.formatString('<a href="javascript:void(0);" class="icon-download" onclick="downloadResourceFun(\'{0}\',\'{1}\');"  title="下载流程图"></a>', row.id,row.diagramResourceName);
					str += '&nbsp;&nbsp;';				
					str += $.formatString('<a href="javascript:void(0);" class="icon-delete" onclick="deleteDefinition(\'{0}\');"  title="删除"></a>', row.deploymentId);
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
	});

function viewChart(id,name){
	parent.$.modalDialog({
		title : '查看流程图',
		width : 700,
		height : 400,
		href : '${pageContext.request.contextPath}/deployController/processResource?id='+id+'&name='+name,
	});
}
function deleteDefinition(id){
	if(id){
		parent.$.messager.confirm('询问', '您是否要删除当前记录？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/deployController/delete-deployment', {
					deploymentId : id
				}, function(result) {
					if (result.success) {
						processGrid.datagrid('reload');
					}
					parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}else{
		parent.$.messager.alert('提示', '请选择要删除的行', 'info');
	}
	
}
function execState(id,state){
	parent.$.modalDialog({
		width : 400,
		height : 280,
		href : '${pageContext.request.contextPath}/deployController/processState?id=' + id+'&state='+state,
		buttons : [ {
			text : '确定',
			handler : function() {
				parent.$.modalDialog.datagrid = processGrid;
				var f = parent.$.modalDialog.handler.find('#form');
				f.submit();
			}
		}, {
			text : '取消',
			handler : function() {
				parent.$.modalDialog.handler.dialog('close');
			}
		} ]
	});
}

function addResource(){
	parent.$.modalDialog({
		title : '添加资源',
		width : 500,
		height : 300,
		href : '${pageContext.request.contextPath}/deployController/processResourceAdd',
		buttons : [ {
			text : '确定',
			handler : function() {
				parent.$.modalDialog.datagrid = processGrid;
				var f = parent.$.modalDialog.handler.find('#form');
				f.submit();
			}
		} ]
	});
}

function searchFun(){
	var key=$('#key').val();
	var name=$('#name').val();
	processGrid.datagrid('reload',{key:key,name:name});
}
function downloadResourceFun(id,name){
	window.location.href= '${pageContext.request.contextPath}/deployController/downloadResource?id='+id+'&name='+name;
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			<table id="processGrid"></table>
		</div>
	</div>
	
	<div id="toolbar" style="padding:2px;height:auto">
		<div style="margin-bottom:3px">
		    <a onclick="addResource();" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加资源</a>
		</div>
		<div>
		<table>
					<tr>
						<td><input id="key" name="key" placeholder="请输入key值" /></td>
						<td><input id="name" name="name" placeholder="请输入名称" /></td>
						<td>
						<a onclick="searchFun();" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
					</tr>
				</table>
		</div>
	</div>
</body>
</html>