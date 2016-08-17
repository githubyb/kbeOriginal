<%@page import="edu.nwpu.dmpm.kbe.system.pageModel.SessionInfo,edu.nwpu.dmpm.kbe.common.util.ConfigUtil;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>字典管理</title>
<%SessionInfo sessionInfo=(SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName()); 
   String useid=sessionInfo.getId();%>
<jsp:include page="../inc.jsp"></jsp:include>

<script type="text/javascript" >
/* var tree,grid,dict;
 */
var tree;
var grid; 
var lastIndex; //正在编辑的行号
$(function() {
	
	grid=$('#grid').datagrid({
		idField:'ID',
		striped:true,
		sortOrder:'asc',
/* 		checkOnSelect : false,
		selectOnCheck : false, */
		rownumbers:true,
		fitColumns:false,
		fit:true,
		singleSelect:true,
		columns : [[ 
			{field:'key',title:'键',width:150,align:'center',editor:{type:'validatebox',options:{required:true}}},
			{field:'value',title:'值',width:150,align:'center',editor:{type:'validatebox',options:{required:true}}},
		    {field:'type',title : '类别',width : 100,align:'center',
		    	formatter:function(value,row,index){
	    			if(value=='0'){
	    				return '系统';
	    			}else{
	    				return '用户创建';
	    			}
		    	}},
		    
		    {field:'description',title:'描述',width:150,align:'center',editor:{type:'text'}}
		]],
		toolbar:[{
			text:'添加',
			iconCls:'icon-add',
			handler:function(){
				addData();
			}
		},'-',{
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				deleteData();
			}
		},'-',{
			text:'编辑',
			iconCls:'icon-edit',
			handler:function(){
				editData();
			}
		},'-',{
			text:'结束编辑',
			iconCls:'icon-end',
			handler:function(){
				endEdit();
			}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				saveData();
			}
		}],
		loadFilter:function(data){ return data;},
		onRowContextMenu:function(e,rowIndex,rowData){
			e.preventDefault();
			$('#partMenu').menu('show',{
				left:e.pageX,
				top:e.pageY
			});
		},
		onBeforeEdit:function(rIndex,rData){
			if(lastIndex!=null){
				grid.datagrid('endEdit',lastIndex);
			}
			lastIndex=rIndex;
		},
		onAfterEdit:function(rIndex,rData,changes){
			lastIndex=null;
		},
		onCancelEdit:function(rIndex,rData){
			lastIndex=null;
		}
	});
	tree=$('#tree').tree({
		url:'${pageContext.request.contextPath}/dictionaryController/dictTree',
		onLoadSuccess:function(node,data){
			parent.$.messager.progress('close');
		},
		onContextMenu:function(e,node){
			e.preventDefault();
			$('#treeMenu').menu('show',{
				left:e.pageX,
				top:e.pageY
			});
		},
		onClick:function(node){
			$.post("${pageContext.request.contextPath}/dictionaryController/getDictionaryData",{'id':node.id},function(data){
                 if(data){
                	 grid.datagrid('loadData',data);
                 }
			},"json");
		}
	});
	
});

function addNode(){
	var node=tree.tree('getSelected');
	if(node){
		parent.$.modalDialog({
			title : '添加字典',
			width : 300,
			height : 300,
			href : '${pageContext.request.contextPath}/dictionaryController/dictionaryAddPage?id='+node.id,
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.tree = tree;
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});	
	}else{
		$.messager.alert('提示','请先选择节点');
	}

}
function editNode(){
	var node=tree.tree('getSelected');
	if(node.id=='0'){
		$.messager.alert('提示','不能编辑根节点');
		return;
	}
	if(node){
		parent.$.modalDialog({
			title : '编辑字典',
			width : 300,
			height : 300,
			href : '${pageContext.request.contextPath}/dictionaryController/dictionaryEditPage?id='+node.id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.tree = tree;
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});	
	}else{
		$.messager.alert('提示','请先选择节点');
	}
}

function addData(){
	var dict=tree.tree('getSelected');
	if(dict){
		if($('#session').attr('value')=='0'){
			dict.type='0';
		}else{
			dict.type='1';
		}
		var index=grid.datagrid('getRows').length;
		grid.datagrid('appendRow',{type:'0',dictID:dict.id,id:index,type:dict.type});
		grid.datagrid('beginEdit',index);
	}else{
		$.messager.alert('提示','请在左边选择字典项');
	}
}

function endEdit(){
	grid.datagrid('endEdit',lastIndex);
}
function editData(){
	var se=grid.datagrid('getSelected');
	if(se){
		var seIndex=grid.datagrid('getRowIndex',se);
		grid.datagrid('beginEdit',seIndex);
	}else{
		$.messager.alert('提示','请选中要编辑的行');
	}
}
function saveData(){
	var changes=grid.datagrid('getChanges');

	if(changes){
		var lpd=JSON.stringify(changes);
		
		var effectRow = new Object();
		effectRow["rows"] = lpd;
		
		$.post("${pageContext.request.contextPath}/dictionaryController/saveData",
				effectRow,
				function(result) {
			if(result.success){
				grid.datagrid('rejectChanges');// 回滚至自创建以来或最后一次调用acceptChanges方法时的状态
				
				grid.datagrid('acceptChanges');//提交changes
				//明天完成data部分save/delete的功能，node部分save/delete的功能，写好给外部调用的接口，并发布测试
				//data部分save，后台需要改成接受list的方式      2014-6-26
				//后台赢改成PDictionaryData[] 方式接收
				$.each(result.obj,function(i,n){
					grid.datagrid('appendRow',n);
				});
			}else{
				//tree.tree('remove',ed.target);
			}
			$.messager.alert('提示',result.msg);
				}, "JSON");
	
	}
}
function deleteData(){
	
	var se=grid.datagrid('getSelected');
	if(se){
		$.messager.confirm('提示	','是否删除所选数据',function(b){
			if(b){
				var pd=JSON.stringify(se);
				$.ajax({
					type:'POST',
					url:'${pageContext.request.contextPath}/dictionaryController/deleteData',
					data:pd,
					dataType:'json',
					contentType:'application/json;charset=UTF-8',
					success:function(result){
						if(result.success){
							grid.datagrid('deleteRow',grid.datagrid('getRowIndex',se.iD));
						}
						$.messager.alert('提示',result.msg);
					}
				}); 
			}
		});
	}else{
		$.messager.alert('提示','请选中要删除的行');
	}
}
function deleteNode(){
	var node=tree.tree('getSelected');
	if(node){
		if(node.id=='0'){
			$.messager.alert('提示','不能删除根节点');
			return ;
		}
		$.messager.confirm('提示	','是否删除所选节点（包括其数据项、子节点及其数据项）',function(b){
			if(b){
				$.post('${pageContext.request.contextPath}/dictionaryController/deleteDict',{'dictID':node.id},function(result){
					if(result.success){
						grid.datagrid('loadData',{total:0,rows:[]});
						tree.tree('remove',node.target);
					}
					$.messager.confirm('提示',result.msg);
				},'json');
			}
		});
	}else{
		$.messager.alert('提示','请选中要删除的节点');
	}
}
</script>
</head>
<body>
	<div id="layout" class="easyui-layout" style="" fit=true>
		<div region="west" title="数据字典" split="true" iconCls="icon-dictionary" style="width:300px;">
			<ul id="tree" fit=true></ul>
		</div>
		<div region="center" split="true" fit=true>
		<div id="grid" fit=true></div>
		</div>
	</div>
	<div id="treeMenu" class="easyui-menu" style="width:150px;">
		<div iconCls="icon-add" onclick="addNode()">添加</div>
		<div iconCls="icon-edit" onclick="editNode()">编辑</div>
		<div iconCls="icon-delete" onclick="deleteNode()">删除</div>
	</div>
	<div id="partMenu" class="easyui-menu" style="width:150px">
		<div iconCls="icon-add" onclick="addData()">添加</div>
		<div iconCls="icon-remove" onclick="deleteData()">删除</div>
		<div iconCls="icon-edit" onclick="editData()">编辑</div>
		<div iconCls="icon-end" onclick="endEdit()">结束编辑</div>
		<div iconCls="icon-save" onclick="saveData()">保存</div>
	</div>
	<input id="session" type="text" value="<%=useid %>" style="display:none;"/>
</body>
</html>