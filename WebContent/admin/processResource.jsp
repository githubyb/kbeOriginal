<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  parent.$.messager.progress('close');
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div>
         <img id="processDiagram" 
        src="${pageContext.request.contextPath}/deployController/read-resource?pdid=${id}&resourceName=${name}" />
    </div>
	</div>
</div>
