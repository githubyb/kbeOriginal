<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/jslib/css/file.css"></link>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/jslib/uploadfile.js"></script>
<script type="text/javascript">
$(function() {
	parent.$.messager.progress('close');
	
	var fu = new FileUpload({
		Form:"form",
		Folder:"addFile",
		FileName:"upfile",
		FileList:"idFileList",
		ShowFilePath:false		
	});
	
	$('#form').form({
		url : '${pageContext.request.contextPath}/deployController/deploy',
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post" enctype="multipart/form-data">
			<table class="table table-hover table-condensed">
				<tr>
				<td align="right">上传文件</td>
					<td colspan="5"><a href="javascript:void(0);" class="files"
						id="addFile"></a>
						<div class="file_content" id="idFileList"></div></td>
				</tr>
			</table>
		</form>
	</div>
	
	 
</div>
