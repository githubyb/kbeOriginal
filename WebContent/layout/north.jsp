<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	/**
	 * @author
	 * 
	 * @requires jQuery,EasyUI,jQuery cookie plugin
	 * 
	 * 更换EasyUI主题的方法
	 * 
	 * @param themeName
	 *            主题名称
	 */
	function changeThemeFun(themeName) {
		if ($.cookie('easyuiThemeName')) {
			$('#layout_north_pfMenu').menu('setIcon', {
				target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
				iconCls : 'emptyIcon'
			});
		}
		$('#layout_north_pfMenu').menu('setIcon', {
			target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
			iconCls : 'tick'
		});

		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);

		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				try {
					$(ifr).contents().find('#easyuiTheme').attr('href', href);
				} catch (e) {
					try {
						ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
					} catch (e) {
					}
				}
			}
		}

		$.cookie('easyuiThemeName', themeName, {
			expires : 7
		});

	};

	function logoutFun(b) {
		$.getJSON('${pageContext.request.contextPath}/userController/logout', {
			t : new Date()
		}, function(result) {
			if (b) {
				location.replace('${pageContext.request.contextPath}/login.jsp');
			} else {
				window.close();
			//	$('#sessionInfoDiv').html('');
			//	$('#loginDialog').dialog('open');
			}
		});
	}
	
	function CloseWebPage() {  
		$.getJSON('${pageContext.request.contextPath}/userController/logout', {
			t : new Date()
		}, function(result) {
			
			if (navigator.userAgent.indexOf("MSIE") > 0) {  
	            if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {  
	                window.opener = null; window.close();  
	            }  
	            else {  
	                window.open('', '_top'); window.top.close();  
	            }  
	        }  
	        else if (navigator.userAgent.indexOf("Firefox") > 0) {  
	            window.location.href = 'about:blank ';  
	            //window.history.go(-2);  
	        }  
	        else {  
	            window.opener = null;   
	            window.open('', '_self', '');  
	            window.close();  
	        }  
		});
		
        
    }  
	
	function editCurrentUserPwd() {
		parent.$.modalDialog({
			title : '修改密码',
			width : 330,
			height : 350,
			href : '${pageContext.request.contextPath}/userController/editCurrentUserPwdPage',
			buttons : [ {
				text : '修改',
				handler : function() {
					var f = parent.$.modalDialog.handler.find('#editCurrentUserPwdForm');
					f.submit();
				}
			} ]
		});
	}
	function currentUserRole() {
		parent.$.modalDialog({
			title : '我的角色',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserRolePage'
		});
	}
	function currentUserResource() {
		parent.$.modalDialog({
			title : '我可以访问的资源',
			width : 300,
			height : 250,
			href : '${pageContext.request.contextPath}/userController/currentUserResourcePage'
		});
	}
</script>
<img src="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.2/themes/images/logo.png">
<div id="sessionInfoDiv" style="position: absolute;right: 15px; top: 3px;">
	<c:if test="${sessionInfo.id != null}"><strong>${sessionInfo.displayName}[${sessionInfo.name}]</strong>，欢迎你！当前IP:<strong>[${sessionInfo.ip}]</strong></c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'icon-themes'">更换皮肤</a> 
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-panel'">控制面板</a> 
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'icon-logout'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px;display: none;">
	<div onclick="changeThemeFun('default');" title="default">default</div>
	<div onclick="changeThemeFun('gray');" title="gray">gray</div>
	<div onclick="changeThemeFun('metro');" title="metro">metro</div>
	<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="changeThemeFun('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
	<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
	<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
	<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'icon-password'" onclick="editCurrentUserPwd();">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-role'" onclick="currentUserRole();">我的角色</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-authorize'" onclick="currentUserResource();">我的权限</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'icon-login'" onclick="logoutFun(true);">重新登录</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'icon-logout'" onclick="CloseWebPage();">退出系统</div>
</div>