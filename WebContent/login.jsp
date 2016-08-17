<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登陆</title>
<jsp:include page="inc.jsp"></jsp:include>
<style type="text/css">
	input{
		margin:0 0 0 40px;
	}
	.line{
		background:url("jslib/jquery-easyui-1.3.2/themes/images/login.png");
		border-bottom:2px solid #ccc;
		margin-bottom:5px;
		width:318px;
		height:50px;
	}
	.divv{
		background:transparent url("jslib/jquery-easyui-1.3.2/themes/images/login.png") no-repeat;
		float:left;
	}
	.ddiv{
		border:1px solid #fff;
		height:30px;
		width:260px;
		margin:0px auto 2px auto;
		padding:5px 0;
	}
	.imgg{
		position:absolute;
		height:30px;
		width:30px;
	}
	.ico_u{ 
		background-image:url("jslib/jquery-easyui-1.3.2/themes/images/user.png");
	}
	.ico_l{ 
		background-image:url("jslib/jquery-easyui-1.3.2/themes/images/password.png");
	}
</style>
<script type="text/javascript" charset="utf-8">
	var loginDialog;
	$(function() {
		loginDialog = $('#loginDialog').show().dialog({
			modal : true,
			closable : false,
			buttons : [{
				text : '登录',
				iconCls:'icon-login',
				handler : function() {
					loginFun();
				}
			} ]
		});
		
		var sessionInfo_userId = '${sessionInfo.id}';
		if (sessionInfo_userId) {/*目的是，如果已经登陆过了，那么刷新页面后也不需要弹出登录窗体*/
			document.location="${pageContext.request.contextPath}/index.jsp";
		}

		$('#loginDialog input').keyup(function(event) {
			if (event.keyCode == '13') {
				loginFun();
			}
		});
	});
	function loginFun() {

            var form =$('#form');
			if (form.form('validate')) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/userController/login', form.serialize(), function(result) {
					if (result.success) {
						$('#sessionInfoDiv').html($.formatString('[<strong>{0}</strong>]，欢迎你！您使用[<strong>{1}</strong>]IP登录！', result.obj.name, result.obj.ip));
						document.location = "${pageContext.request.contextPath}/index.jsp";
					} else {
						$.messager.alert('错误', result.msg, 'error');
					}
					parent.$.messager.progress('close');
				}, "JSON");
			}
		
	}
</script>
</head>
<body>
<div id="loginDialog" title="登录入口" style="width: 330px; height: 220px; overflow: hidden; display: none;">
	<div class="line" align="center" style="overflow: hidden;">	
	</div>
	<div>
		<form id="form" method="post">
			<div class="ddiv">
				<b class="imgg ico_u"></b>
				<input name="name" type="text" style="width:190px" placeholder="请输入登录名"  data-options="required:true" value="superadmin">
			</div>
			<div class="ddiv">
				<b class="imgg ico_l"></b>
				<input name="pwd" type="password" style="width:190px" placeholder="请输入密码" data-options="required:true" value="superadmin">
			</div>
		</form>
	</div>
</div>
</body>
</html>