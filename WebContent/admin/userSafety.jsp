<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户安全管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
 
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/userSafetyController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '登录名称',
				width : 100,
				sortable : true
			} ,{
				field : 'displayName',
				title : '姓名',
				width : 100,
				sortable : true
			}, {
				field : 'abbreviation',
				title : '缩写',
				width : 100,
				sortable : true
			}] ],
			columns : [ [ {
				field : 'createdatetime',
				title : '创建时间',
				width : 150,
				sortable : true,
				formatter:function(value,row,index){  
                    var unixTimestamp = new Date(value);  
                    return unixTimestamp.toLocaleString();  
                    }
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150,
				sortable : true,
				formatter:function(value,row,index){  
                    var unixTimestamp = new Date(value);  
                    return unixTimestamp.toLocaleString();  
                    }
			}, {
				field : 'roleIds',
				title : '所属角色ID',
				width : 150,
				hidden : true
			}, {
				field : 'roleNames',
				title : '所属角色名称',
				width : 150
			}, 
			{
				field : 'securityclass',
				title : '密级',
				width : 60,
				sortable : true
			}, 
			{
				field : 'flag',
				title : '状态',
				width : 60,
				sortable : true
			}, 
			{
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if(row.flag=='锁定'){
						str += $.formatString('<a href="javascript:void(0);" class="icon-active" onclick="changestate(\'{0}\',1);"  title="激活"></a>', row.id);
					}else{
						str += $.formatString('<a href="javascript:void(0);" class="icon-lock-gif" onclick="changestate(\'{0}\',0);"  title="锁定"></a>', row.id);
					}
					
					str += '&nbsp;&nbsp;';
					
					str += $.formatString('<a href="javascript:void(0);" class="icon-authorize" onclick="grantFun(\'{0}\');"  title="授权"></a>', row.id);
					str += '&nbsp;&nbsp;';
					
					str += $.formatString('<a href="javascript:void(0);" class="icon-security" onclick="changeSecurityclass(\'{0}\');"  title="密级"></a>', row.id);
					str += '&nbsp;&nbsp;';
					
					str += $.formatString('<a href="javascript:void(0);" class="icon-key" onclick="editPwdFun(\'{0}\');"  title="修改密码"></a>', row.id);
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});

	//修改用户状态：锁定或解锁
	function changestate(id,state)
	{
		$.post('${pageContext.request.contextPath}/userSafetyController/changeState', {
			id : id,
			state:state
		}, function(result) {
			if (result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
				dataGrid.datagrid('reload');
			}
			parent.$.messager.progress('close');
		}, 'JSON');
	}
	
	//修改用户密码	
	function editPwdFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '编辑用户密码',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userSafetyController/editPwdPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	//选择密码校验规则
	function matchPWR() {
		parent.$.messager.progress('close');
		parent.$.modalDialog({			
			title : '密码匹配规则',
			width : 400,
			height : 300,
			href : '${pageContext.request.contextPath}/userSafetyController/passWordRulePage',		
			buttons : [ {
				text : '确定',
				handler : function() {					
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				
				}
			} ]
		});
	}

	//批量用户授权
	function batchGrantFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			parent.$.modalDialog({
				title : '用户授权',
				width : 500,
				height : 300,
				href : '${pageContext.request.contextPath}/userSafetyController/grantPage?ids=' + ids.join(','),
				buttons : [ {
					text : '授权',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要授权的记录！'
			});
		}
	}

	//单个用户授权
	function grantFun(id) {
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '用户授权',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userSafetyController/grantPage?ids=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	
	//修改用户密级
	function changeSecurityclass(id)
	{
		dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		parent.$.modalDialog({
			title : '修改用户密级',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/userSafetyController/changeSecurityclassPage?id=' + id,
			buttons : [ {
				text : '修改',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 140px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>登录名</th>
						<td><input name="name" placeholder="可以模糊查询登录名" class="span2" /></td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td><input class="span2" name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input class="span2" name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
					</tr>
					<tr>
						<th>最后修改时间</th>
						<td><input class="span2" name="modifydatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input class="span2" name="modifydatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/userSafetyController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-authorize'">批量授权</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-filter',plain:true" onclick="searchFun();">过滤条件</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanFun();">清空条件</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-rules',plain:true" onclick="matchPWR();">密码匹配规则</a>
	</div>	
	<div id="datagridtest">
	</div>
</body>
</html>