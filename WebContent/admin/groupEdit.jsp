<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

	$(function() {
		
		parent.$.messager.progress('close');
		
		var groupType=[ 
		           {"id":"0","text":"行政组"}, 
			       {"id":"1","text":"项目组"}
			         ];
		$('#groupType').combobox({   
			data:groupType, 
			valueField:'id',   
			textField:'text'  
		 }); 
		
		$('#parentID').combotree({
			url : '${pageContext.request.contextPath}/groupController/tree',
			parentField : 'parentID',
			lines : true,
			panelHeight : 'auto',
			value : '${parentID}',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});

	  
	   

		$('#form').form({
			url : '${pageContext.request.contextPath}/groupController/edit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');
					$.modalDialog.handler.dialog('close');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<input type="hidden" name="id" value="${group.id}"/>
				<input type="hidden" name="id1" value="${parentID}"/>
				<tr>
					<th>组名</th>
					<td ><input  name="name" value="${group.name}"  data-options="editable:false" /></td>
				</tr>
				<tr>
				 <th >组类型</th>
				    <td><select id="groupType" name="groupType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
				    <option value="${group.groupType}"<c:if test="${groupType.id == group.groupType}">selected="selected"</c:if>>${groupType.text}</option>
					</select></td>
				</tr>
				<tr>
				<th>负责人</th>
					<td><select name="leaderId"  class="easyui-combobox" data-options="width:210,editable:false,panelHeight:'250'">
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}" <c:if test="${user.id == group.leaderId}">selected="selected"</c:if>>${user.displayName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
				<th >上级组</th>
					<td><select id="parentID" name="parentID" style="width: 140px; height: 29px;"></select></td>
				</tr>
				<tr>
				<th>备注</th>
					<td ><textarea name="remark" rows="" cols="" class="span5" style="width: 200px; ">${group.remark}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div> 
