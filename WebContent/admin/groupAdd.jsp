<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		$('#parentID').combotree({
			url : '${pageContext.request.contextPath}/groupController/tree',
			parentField : 'parentID',
			lines : true,
			panelHeight : 'auto',
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			}
		});
		/* 将负责人拓展为下拉框的方法 
		$('#leader').combobox({
			data : $.iconData,
			onLoadSuccess : function() {
				parent.$.messager.progress('close');
			},
			formatter : function(v) {
				return $.formatString('<span class="{0}" style="display:inline-block;vertical-align:middle;width:16px;height:16px;"></span>{1}', v.value, v.value);
				
			}
		}); */
		
		parent.$.messager.progress('close');

		$('#form').form({
			url : '${pageContext.request.contextPath}/groupController/add',
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
				}else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<table class="table table-hover table-condensed">
				<tr>
					<th >组名</th>
					<td ><input  name="name"  data-options="editable:false" /></td>	
				</tr>
				<tr>
				<th >组类型</th>
				    <td><select name="groupType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							  <option value="0">行政组</option>
                              <option value="1">项目组</option>
					</select></td>
				</tr>
				<tr>
				<th>负责人</th>
					<!-- <td ><input  name="leaderId"  data-options="editable:false" /></td> -->
					<td><select name="leaderId" class="easyui-combobox" data-options="width:205,editable:false,panelHeight:'auto'">
							<c:forEach items="${userList}" var="user">
								<option value="${user.id}">${user.displayName}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
				<th >上级组</th>
					<td><select id="parentID" name="parentID" style="width: 140px; height: 29px;"></select>
				</td>
				</tr>
				<tr>
				<th class="span2">备注</th>
					<td ><textarea name="remark" rows="" cols="" class="span5" style="width: 200px; "></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div> 
