<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript" charset="utf-8">
	$(function() {

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
				}
			} ]
		});

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
		<div id="layout_east_calendar"></div>
	</div>
	<div data-options="region:'center',border:false" style="overflow: hidden;">
		<div id="layout_east_onlinePanel" data-options="fit:true,border:false" title="问题处理中心">
			<div class="well well-small" style="margin: 3px;">
				<div>
					<span class="label label-important">技术支持</span><br /> 西北工业大学制造软件研究所技术二部
					</div>
				<div>
					<span class="label label-success">联系电话</span><br />029-88491371
				</div>
				<div>
					<span class="label label-important">电子邮件</span><br />email:wangc1217@126.com
				</div>
			</div>
		</div>
	</div>
</div>