<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="css/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">	
var min= "${min}";
var max= "${max}";
var needWord = "${needWord}";
var needNum = "${needNum}";
var needCharacter = "${needCharacter}";
	$(function() {
		parent.$.messager.progress('close');		
		$('#editCurrentUserPwdForm')
				.form(
						{
							url : '${pageContext.request.contextPath}/userController/editCurrentUserPwd',
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
									parent.$.messager.alert('提示', result.msg,
											'info');
									parent.$.modalDialog.handler
											.dialog('close');
								} else {
									parent.$.messager.alert('不合格', result.msg,
											'error');
								}
							}
						});
	                  });

		
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;">
		<c:if test="${sessionInfo.name == null}">
			<img
				src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.2/themes/images/blue_face/bluefaces_35.png"
				alt="" />
			<div>登录已超时，请重新登录，然后再刷新本功能！</div>
			<script type="text/javascript" charset="utf-8">
				try {
					parent.$.messager.progress('close');
				} catch (e) {
				}
			</script>
		</c:if>
		<c:if test="${sessionInfo.name != null}">
			<form id="editCurrentUserPwdForm" method="post">
				<table class="table table-hover table-condensed">
					 <tr>
						<th>登录名</th>
						<td>${sessionInfo.name}</td>
					</tr>
					<tr>
						<th>原密码</th>
						<td><input name="oldPwd" type="password" placeholder="请输入原密码"
							class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>新密码</th>
						<td>
						<input name="pwd" type="password" id="pwd" placeholder="请输入新密码"/>							
						</td>
					</tr>
					
					<tr>
						<th>密码规则</th>
						<td>						
							<span style="display:block">密码长度为${min}至${max}</span>
							<c:if test="${needNum=='on'}">
					           <span style="display:block">必须包含数字</span>
				            </c:if>
							<c:if test="${needWord=='on'}">
					           <span style="display:block">必须包含字母</span>
				            </c:if>
				            <c:if test="${needCharacter=='on'}">
					           <span style="display:block">必须包含特殊字符</span>
				            </c:if>
							
						</td>
					</tr>
					<tr>
						<th>确认密码</th>
						<td><input name="rePwd" type="password"
							placeholder="请再次输入新密码" class="easyui-validatebox"
							data-options="required:true,validType:'eqPwd[\'#editCurrentUserPwdForm input[name=pwd]\']'"></td>
					</tr>
				</table>
			</form>
		</c:if>
	</div>
</div>