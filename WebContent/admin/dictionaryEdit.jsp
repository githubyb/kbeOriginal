<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">

var pId='${pId}';

	$(function() {
		parent.$.messager.progress('close');

		$('#form').form({
			url : '${pageContext.request.contextPath}/dictionaryController/saveDict?pId='+pId,
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
					parent.$.modalDialog.tree.tree('reload');
					parent.$.modalDialog.handler.dialog('close');
				} else {
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
			    <input name="id" type="hidden" value="${dic.id}" />
				<th>字段名</th>
				<td><input name="name" type="text" placeholder="请输入字段名" class="easyui-validatebox span2" readonly="readonly" value="${dic.name}"></td>
				</tr>
				<tr>
				<th>名称</th>
				<td><input name="text" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value="${dic.text}"></td>
				</tr>
				<tr>
				<th>类别</th>
					<td>
					<select class="easyui-combobox" name="type" style="width:140px;">   
                           <option value="0">系统</option>   
                           <option value="1">用户</option>   
                     </select>
					</td>
				</tr>
				<tr>
					<th>排序</th>
					<td>
					<input name="sqe" value="${dic.sqe}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:true,min:1">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>