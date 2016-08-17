<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">

$(function() {
	
	parent.$.messager.progress('close');
	
	$('#form').form({
		url : '${pageContext.request.contextPath}/deployController/${state}',
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
				parent.$.modalDialog.datagrid.datagrid('reload');
				parent.$.modalDialog.handler.dialog('close');
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
		}
	});
});

	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="${title}" style="overflow: hidden;">
		<form id="form" method="post">
		<input type="hidden" name="processDefinitionId" value="${id}"/>
		
			<table class="table table-hover table-condensed">
			   <tr>
			      <th>执行时间</th>
			      <td>
			      <input type="radio" name="execTime" id="optionsRadios1" value="now" checked>现在
                     <br />
                    <input type="radio" name="execTime" id="optionsRadios2" value="timer"> 定时：
                    <input class="easyui-datetimebox" name="effectiveDate"  style="width:150px">
			      
			      </td>
			   </tr>
			   <tr>					
					<th>是否级联</th>
					<td>
					<input type="checkbox" name="cascade" checked="checked"/>同时级联所有与流程定义相关的流程实例
					</td>						
				</tr>
					
			</table>
		</form>
	</div>
</div>
