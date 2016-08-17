<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript" src="${ctx}/jslib/extBrowser.js" charset="utf-8"></script>

<!-- 引入my97日期时间控件 -->
<script type="text/javascript" src="${ctx}/jslib/My97DatePicker4.8b3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- 引入kindEditor插件 -->
<link rel="stylesheet" href="${ctx}/jslib/kindeditor-4.1.7/themes/default/default.css">
<script type="text/javascript" src="${ctx}/jslib/kindeditor-4.1.7/kindeditor-all-min.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<%-- <script src="${pageContext.request.contextPath}/jslib/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script> --%>
<script src="${ctx}/jslib/jquery-easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入Highcharts -->
<script src="${ctx}/jslib/Highcharts-3.0.1/js/highcharts.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入bootstrap样式 -->
<link href="${ctx}/jslib/bootstrap-2.3.1/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- <script charset="utf-8" src="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/js/bootstrap.min.js" charset="utf-8"></script> -->

<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/jquery-easyui-1.3.2/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css" type="text/css">
<link rel="stylesheet" href="${ctx}/jslib/jquery-easyui-1.3.2/themes/icon.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- 修复EasyUI1.3.3中layout组件的BUG -->
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.2/plugins/jquery.layout.js" charset="utf-8"></script>

<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${ctx}/jslib/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- 扩展EasyUI -->
<script type="text/javascript" src="${ctx}/jslib/extEasyUI.js?v=201305241044" charset="utf-8"></script>

<!-- 扩展EasyUI Icon -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/jslib/jquery-easyui-1.3.2/themes/extEasyUIIcon.css" type="text/css"> --%>

<!-- 扩展jQuery -->
<script type="text/javascript" src="${ctx}/jslib/extJquery.js?v=201305301341" charset="utf-8"></script>

