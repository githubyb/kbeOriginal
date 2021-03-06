<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>工作组管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/groupController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/groupController/groupUserEditPage')}">
	<script type="text/javascript">
		$.canEditUser = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/groupController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		treeGrid = $('#treeGrid').treegrid({
			url : '${pageContext.request.contextPath}/groupController/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'parentID',
			fit : true,
			fitColumns : false,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '组名',
				width : 200
			}, {
				field : 'leaderName',
				title : '负责人',
				width : 230
			}, {
				field : 'number',
				title : '成员数',
				width : 150
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0);" class="icon-edit" onclick="editFun(\'{0}\');"  title="编辑"></a>', row.id);
					}
					str += '&nbsp;&nbsp;';
					if ($.canEditUser) {
						str += $.formatString('<a href="javascript:void(0);" class="icon-group-edit" onclick="editUserFun(\'{0}\');"  title="编辑组员"></a>', row.id);
					}
					str += '&nbsp;&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<a href="javascript:void(0);" class="icon-delete" onclick="deleteFun(\'{0}\');"  title="删除"></a>', row.id);
					}
					return str;
				}
			},{
				field : 'remark',
				title : '备注',
				width : 150
			} ] ],
			toolbar : '#toolbar',
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');

				$(this).treegrid('tooltip');
			}
		});
	});

	function deleteFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var ids = [];
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前工作组？', function(b) {
				if (b) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					ids.push(node.id);
					$.post('${pageContext.request.contextPath}/groupController/delete', {
						'ids':ids.join(',') 
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				}
			});
		}
	}

	function editFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑工作组',
				width : 500,
				height : 300,
				href : '${pageContext.request.contextPath}/groupController/editPage?id=' + node.id,
				buttons : [ {
					text : '编辑',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		}
	}

	function editUserFun(id){
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑组员',
				width : 500,
				height : 500,
				href : '${pageContext.request.contextPath}/groupController/groupUserEditPage?id=' + node.id,
				buttons : [ {
					text : '保存',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		}
		
	}
	
	function addFun() {
		parent.$.modalDialog({
			title : '添加工作组',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/groupController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function redo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resourceController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
		<a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-unfold'">展开</a> 
		<a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-fold'">折叠</a> 
		<a onclick="treeGrid.treegrid('reload');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
	</div>

	<%-- <div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resourceController/addPage')}">
			<div onclick="addFun();" data-options="iconCls:'icon-add'">增加</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resourceController/editPage')}">
			<div onclick="editFun();" data-options="iconCls:'icon-group-edit'">编辑</div>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resourceController/delete')}">
			<div onclick="deleteFun();" data-options="iconCls:'icon-delete'">删除</div>
		</c:if>
		
	</div> --%>
</body>
</html>