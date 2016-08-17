<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">
	var needWord = "${needWord}";
	var needNum = "${needNum}";
	var needCharacter = "${needCharacter}";

	$(function() {

		parent.$.messager.progress('close');
		$('#form')
				.form(
						{
							url : '${pageContext.request.contextPath}/userSafetyController/addpassWordRule',
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
									parent.$.modalDialog.handler.dialog('close');
								}
							}
						});

	});
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<form id="form" method="post">
		<table class="table table-hover table-condensed">
			<tr>
				<th>密码最小位数</th>
				<td><input id="min" name="min" value="${min}" required="required"
					style="width: 80px;"></td>
			</tr>
			<tr>
				<th>密码最大位数</th>
				<td><input id="max" name="max" value="${max}" required="required"
					style="width: 80px;"></td>
			</tr>

			<tr>
				<th>必须包含数字</th>
				<c:if test="${needNum=='checked'}">
					<td><input id="needNum" type="checkbox" name="needNum"
						checked="" /></td>
				</c:if>
				<c:if test="${needNum==''}">
					<td><input id="needNum" type="checkbox" name="needNum" /></td>
				</c:if>

			</tr>


			<tr>
				<th>必须包含字母</th>
				<c:if test="${needWord=='checked'}">
					<td><input id="needWord" type="checkbox" name="needWord"
						checked="" /></td>
				</c:if>
				<c:if test="${needWord==''}">
					<td><input id="needWord" type="checkbox" name="needWord" /></td>
				</c:if>
			</tr>

			<tr>
				<th>必须包含特殊字符</th>
				<c:if test="${needCharacter=='checked'}">
					<td><input id="needCharacter" type="checkbox"
						name="needCharacter" checked="" /></td>
				</c:if>
				<c:if test="${needCharacter==''}">
					<td><input id="needCharacter" type="checkbox"
						name="needCharacter" /></td>
				</c:if>
			</tr>
		</table>
	</form>
</div>
