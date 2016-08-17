
/**
 * 远程文件服务器地址
 * @author NWPU_IMS
 * 
 */
var fileServerUrl='http://localhost:8080/fileserver/fileServerController';
function downloadFun(fileName,type,fullFilePath) {
	window.location.href = fileServerUrl+'/download?fileName='
			+ fileName + '&fullFilePath=' + fullFilePath+'&type='+type;
}

function addTab(params) {
	var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
	var t = parent.$('#index_tabs');
	var opts = {
		title : params.title,
		closable : true,
		iconCls : params.iconCls,
		content : iframe,
		border : false,
		fit : true
	};
	if (t.tabs('exists', opts.title)) {
		t.tabs('select', opts.title);
		parent.$.messager.progress('close');
	} else {
		t.tabs('add', opts);
	}
}

/**
 * 使panel和datagrid在加载时提示
 * 
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			for ( var i = 0; i < frame.length; i++) {
				frame[i].src = '';
				frame[i].contentWindow.document.write('');
				frame[i].contentWindow.close();
			}
			frame.remove();
			if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
				try {
					CollectGarbage();
				} catch (e) {
				}
			}
		}
	} catch (e) {
	}
};

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
	$.messager.progress('close');
	$.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
	e.preventDefault();
	var grid = $(this);/* grid本身 */
	var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	if (!headerContextMenu) {
		var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
		var fields = grid.datagrid('getColumnFields');
		for ( var i = 0; i < fields.length; i++) {
			var fildOption = grid.datagrid('getColumnOption', fields[i]);
			if (!fildOption.hidden) {
				$('<div iconCls="tick" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			} else {
				$('<div iconCls="bullet_blue" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(tmenu);
			}
		}
		headerContextMenu = this.headerContextMenu = tmenu.menu({
			onClick : function(item) {
				var field = $(item.target).attr('field');
				if (item.iconCls == 'tick') {
					grid.datagrid('hideColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'bullet_blue'
					});
				} else {
					grid.datagrid('showColumn', field);
					$(this).menu('setIcon', {
						target : item.target,
						iconCls : 'tick'
					});
				}
			}
		});
	}
	headerContextMenu.menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * grid tooltip参数
 * 
 * @author NWPU_IMS
 */
var gridTooltipOptions = {
	tooltip : function(jq, fields) {
		return jq.each(function() {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function() {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function() {
				var content = $(this).text();
				if (content.replace(/(^\s*)|(\s*$)/g, '').length > 5) {
					//1.3.3+的版本才有tooltip组件
					/*$(this).tooltip({
						content : content,
						trackMouse : true,
						position : 'bottom',
						onHide : function() {
							$(this).tooltip('destroy');
						},
						onUpdate : function(p) {
							var tip = $(this).tooltip('tip');
							if (parseInt(tip.css('width')) > 500) {
								tip.css('width', 500);
							}
						}
					}).tooltip('show');*/					
				}
			});
		}
	}
};
/**
 * Datagrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").datagrid('tooltip'); 所有列
 * 
 * $("#dg").datagrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author NWPU_IMS
 */
$.extend($.fn.datagrid.methods, gridTooltipOptions);

/**
 * Treegrid扩展方法tooltip 基于Easyui 1.3.3，可用于Easyui1.3.3+
 * 
 * 简单实现，如需高级功能，可以自由修改
 * 
 * 使用说明:
 * 
 * 在easyui.min.js之后导入本js
 * 
 * 代码案例:
 * 
 * $("#dg").treegrid('tooltip'); 所有列
 * 
 * $("#dg").treegrid('tooltip',['productid','listprice']); 指定列
 * 
 * @author NWPU_IMS
 */
$.extend($.fn.treegrid.methods, gridTooltipOptions);

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
	eqPwd : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其可以获取实心节点
 */
$.extend($.fn.tree.methods, {
	getCheckedExt : function(jq) {// 获取checked节点(包括实心)
		var checked = $(jq).tree("getChecked");
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2, function() {
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	},
	getSolidExt : function(jq) {// 获取实心节点
		var checked = [];
		var checkbox2 = $(jq).find("span.tree-checkbox2").parent();
		$.each(checkbox2, function() {
			var node = $.extend({}, $.data(this, "tree-node"), {
				target : this
			});
			checked.push(node);
		});
		return checked;
	}
});

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展tree，使其支持平滑数据格式
 */
$.fn.tree.defaults.loadFilter = function(data, parent) {
	var opt = $(this).data().tree.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展treegrid，使其支持平滑数据格式
 */
$.fn.treegrid.defaults.loadFilter = function(data, parentId) {
	var opt = $(this).data().treegrid.options;
	var idFiled, textFiled, parentField;
	if (opt.parentField) {
		idFiled = opt.idFiled || 'id';
		textFiled = opt.textFiled || 'text';
		parentField = opt.parentField;
		var i, l, treeData = [], tmpMap = [];
		for (i = 0, l = data.length; i < l; i++) {
			tmpMap[data[i][idFiled]] = data[i];
		}
		for (i = 0, l = data.length; i < l; i++) {
			if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				if (!tmpMap[data[i][parentField]]['children'])
					tmpMap[data[i][parentField]]['children'] = [];
				data[i]['text'] = data[i][textFiled];
				tmpMap[data[i][parentField]]['children'].push(data[i]);
			} else {
				data[i]['text'] = data[i][textFiled];
				treeData.push(data[i]);
			}
		}
		return treeData;
	}
	return data;
};

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展combotree，使其支持平滑数据格式
 */
$.fn.combotree.defaults.loadFilter = $.fn.tree.defaults.loadFilter;

/**
 * @author NWPU_IMS
 * 
 * @requires jQuery,EasyUI
 * 
 * 创建一个模式化的dialog
 * 
 * @returns $.modalDialog.handler 这个handler代表弹出的dialog句柄
 * 
 * @returns $.modalDialog.xxx 这个xxx是可以自己定义名称，主要用在弹窗关闭时，刷新某些对象的操作，可以将xxx这个对象预定义好
 */
$.modalDialog = function(options) {
	if ($.modalDialog.handler == undefined) {// 避免重复弹出
		var opts = $.extend({
			title : '',
			width : 840,
			height : 680,
			modal : true,
			onClose : function() {
				$.modalDialog.handler = undefined;
				$(this).dialog('destroy');
			},
			onOpen : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
			}
		}, options);
		opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
		return $.modalDialog.handler = $('<div/>').dialog(opts);
	}
};

/**
 * @author NWPU_IMS * 
 * @requires jQuery,EasyUI * 
 * 扩展datagrid，清空datagrid数据
 */
$.extend($.fn.datagrid.methods, {
	clearData:function(jq){
		return jq.each(function(){
			$(this).datagrid('loadData',{total:0,rows:[]});
		});
	}
});

/**
 * 对Date的扩展 * 
 * 将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符 
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.pattern = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, /* 月份 */
		"d+" : this.getDate(), /* 日 */
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, /* 小时 */
		"H+" : this.getHours(), /* 小时 */
		"m+" : this.getMinutes(), /* 分 */
		"s+" : this.getSeconds(), /* 秒 */
		"q+" : Math.floor((this.getMonth() + 3) / 3), /* 季度 */
		/* 毫秒 */
		"S" : this.getMilliseconds()
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};

/**
 * @author NWPU_IMS
 * 
 * 定义一些小图标样式的数组
 */
$.iconData = [ {
	value : '',
	text : '默认'
},{
	value : 'icon-assign',
	text : 'assign'
},{
	value : 'icon-bill',
	text : 'bill'
},{
	value : 'icon-todo',
	text : 'todo'
},{
	value : 'icon-userSafe',
	text : 'userSafe'
},{
	value : 'icon-active',
	text : 'active'
},{
	value : 'icon-security',
	text : 'security'
},{
	value : 'icon-dictionary',
	text : 'dictionary'
},{
	value : 'icon-modeling',
	text : 'modeling'
},{
	value : 'icon-resource',
	text : 'resource'
}, {
	value : 'icon-menu',
	text : 'menu'
}, {
	value : 'icon-log',
	text : 'log'
},{
	value : 'icon-system',
	text : 'system'
},{
	value : 'icon-add',
	text : 'add'
}, {
	value : 'icon-edit',
	text : 'edit'
}, {
	value : 'icon-remove',
	text : 'remove'
}, {
	value : 'icon-save',
	text : 'save'
}, {
	value : 'icon-cut',
	text : 'cut'
}, {
	value : 'icon-close',
	text : 'close'
}, {
	value : 'icon-ok',
	text : 'ok'
}, {
	value : 'icon-no',
	text : 'no'
}, {
	value : 'icon-cancel',
	text : 'cancel'
}, {
	value : 'icon-reload',
	text : 'reload'
}, {
	value : 'icon-search',
	text : 'search'
}, {
	value : 'icon-print',
	text : 'print'
}, {
	value : 'icon-help',
	text : 'help'
}, {
	value : 'icon-undo',
	text : 'undo'
}, {
	value : 'icon-redo',
	text : 'redo'
}, {
	value : 'icon-back',
	text : 'back'
}, {
	value : 'icon-sum',
	text : 'sum'
}, {
	value : 'icon-class',
	text : 'class'
}, {
	value : 'icon-anchor',
	text : 'anchor'
}, {
	value : 'icon-clear',
	text : 'clear'
}, {
	value : 'icon-filter',
	text : 'filter'
}, {
	value : 'icon-man',
	text : 'man'
}, {
	value : 'icon-lock-png',
	text : 'lock-png'
}, {
	value : 'icon-lock-gif',
	text : 'lock-gif'
}, {
	value : 'icon-more',
	text : 'more'
}, {
	value : 'icon-agree',
	text : 'agree'
}, {
	value : 'icon-authorize',
	text : 'authorize'
}, {
	value : 'icon-book',
	text : 'book'
}, {
	value : 'icon-browser',
	text : 'browser'
}, {
	value : 'icon-bug',
	text : 'bug'
}, {
	value : 'icon-chart-bar',
	text : 'chart-bar'
}, {
	value : 'icon-chart-curve',
	text : 'chart-curve'
}, {
	value : 'icon-chart-pie',
	text : 'chart-pie'
}, {
	value : 'icon-cog',
	text : 'cog'
}, {
	value : 'icon-clock',
	text : 'clock'
}, {
	value : 'icon-comment',
	text : 'comment'
}, {
	value : 'icon-comment-add',
	text : 'comment-add'
}, {
	value : 'icon-computer',
	text : 'computer'
}, {
	value : 'icon-connect',
	text : 'connect'
}, {
	value : 'icon-container',
	text : 'container'
}, {
	value : 'icon-copy',
	text : 'copy'
}, {
	value : 'icon-database',
	text : 'database'
}, {
	value : 'icon-data',
	text : 'data'
},{
	value : 'icon-database-table',
	text : 'database-table'
}, {
	value : 'icon-date',
	text : 'date'
}, {
	value : 'icon-datePicker',
	text : 'datePicker'
}, {
	value : 'icon-details',
	text : 'details'
}, {
	value : 'icon-disagree',
	text : 'disagree'
}, {
	value : 'icon-disconnect',
	text : 'disconnect'
}, {
	value : 'icon-down',
	text : 'down'
}, {
	value : 'icon-download',
	text : 'download'
}, {
	value : 'icon-download-rar',
	text : 'download-rar'
}, {
	value : 'icon-edit-sort',
	text : 'edit-sort'
}, {
	value : 'icon-email',
	text : 'email'
}, {
	value : 'icon-empty',
	text : 'empty'
}, {
	value : 'icon-error',
	text : 'error'
}, {
	value : 'icon-exchange',
	text : 'exchange'
}, {
	value : 'icon-export',
	text : 'export'
}, {
	value : 'icon-export-excel',
	text : 'export-excel'
}, {
	value : 'icon-export-html',
	text : 'export-html'
}, {
	value : 'icon-export-xml',
	text : 'export-xml'
}, {
	value : 'icon-export-zip',
	text : 'export-zip'
}, {
	value : 'icon-female',
	text : 'female'
}, {
	value : 'icon-folder',
	text : 'folder'
}, {
	value : 'icon-font',
	text : 'font'
}, {
	value : 'icon-Form',
	text : 'Form'
}, {
	value : 'icon-grid',
	text : 'grid'
}, {
	value : 'icon-group',
	text : 'group'
}, {
	value : 'icon-history',
	text : 'history'
}, {
	value : 'icon-house',
	text : 'house'
}, {
	value : 'icon-html',
	text : 'html'
}, {
	value : 'icon-image',
	text : 'image'
}, {
	value : 'icon-import',
	text : 'import'
}, {
	value : 'icon-import-zip',
	text : 'import-zip'
}, {
	value : 'icon-key',
	text : 'key'
}, {
	value : 'icon-layout',
	text : 'layout'
}, {
	value : 'icon-link',
	text : 'link'
}, {
	value : 'icon-clock',
	text : 'clock'
}, {
	value : 'icon-lock-temp',
	text : 'lock-temp'
}, {
	value : 'icon-male',
	text : 'male'
}, {
	value : 'icon-modify',
	text : 'modify'
}, {
	value : 'icon-move',
	text : 'move'
}, {
	value : 'icon-move-in',
	text : 'move-in'
}, {
	value : 'icon-move-out',
	text : 'move-out'
}, {
	value : 'icon-music',
	text : 'music'
}, {
	value : 'icon-need-do',
	text : 'need-do'
}, {
	value : 'icon-need-not-do',
	text : 'need-not-do'
}, {
	value : 'icon-new',
	text : 'new'
}, {
	value : 'icon-new-tab',
	text : 'tab'
}, {
	value : 'icon-new-window',
	text : 'new-window'
}, {
	value : 'icon-note',
	text : 'note'
},{
	value : 'icon-password',
	text : 'password'
}, {
	value : 'icon-package',
	text : 'package'
}, {
	value : 'icon-page',
	text : 'page'
}, {
	value : 'icon-page-attach',
	text : 'page-attach'
}, {
	value : 'icon-part',
	text : 'part'
}, {
	value : 'icon-phase',
	text : 'phase'
}, {
	value : 'icon-photo',
	text : 'photo'
}, {
	value : 'icon-projects',
	text : 'projects'
}, {
	value : 'icon-project',
	text : 'project'
}, {
	value : 'icon-project-plan',
	text : 'project-plan'
}, {
	value : 'icon-projectprocess',
	text : 'projectprocess'
}, {
	value : 'icon-property',
	text : 'property'
}, {
	value : 'icon-report',
	text : 'report'
}, {
	value : 'icon-return',
	text : 'return'
}, {
	value : 'icon-role-tree',
	text : 'role-tree'
}, {
	value : 'icon-rollback',
	text : 'rollback'
}, {
	value : 'icon-script',
	text : 'script'
}, {
	value : 'icon-submit',
	text : 'submit'
}, {
	value : 'icon-tab',
	text : 'tab'
}, {
	value : 'icon-table',
	text : 'table'
}, {
	value : 'icon-tag',
	text : 'tag'
}, {
	value : 'icon-tag',
	text : 'tag'
}, {
	value : 'icon-tag-blue',
	text : 'tag-blue'
}, {
	value : 'icon-template',
	text : 'template'
}, {
	value : 'icon-thumb-down',
	text : 'thumb-down'
}, {
	value : 'icon-thumb-up',
	text : 'thumb-up'
}, {
	value : 'icon-type',
	text : 'type'
}, {
	value : 'icon-unlock',
	text : 'unlock'
}, {
	value : 'icon-up',
	text : 'up'
}, {
	value : 'icon-upload',
	text : 'upload'
}, {
	value : 'icon-user',
	text : 'user'
}, {
	value : 'icon-user-comment',
	text : 'user-comment'
}, {
	value : 'icon-view',
	text : 'view'
}, {
	value : 'icon-wrench',
	text : 'wrench'
}, {
	value : 'icon-write',
	text : 'write'
}, {
	value : 'icon-xhtml',
	text : 'xhtml'
}, {
	value : 'icon-role',
	text : 'role'
}, {
	value : 'icon-administrator',
	text : 'administrator'
}, {
	value : 'icon-abort',
	text : 'abort'
}, {
	value : 'icon-asCreator',
	text : 'asCreator'
}, {
	value : 'icon-asExecutor',
	text : 'asExecutor'
}, {
	value : 'icon-asMaster',
	text : 'asMaster'
}, {
	value : 'icon-assign',
	text : 'assign'
}, {
	value : 'icon-changeExecutor',
	text : 'changeExecutor'
}, {
	value : 'icon-daiban',
	text : 'daiban'
}, {
	value : 'icon-decompose',
	text : 'decompose'
}, {
	value : 'icon-finish',
	text : 'finish'
}, {
	value : 'icon-suspend',
	text : 'suspend'
}, {
	value : 'icon-pending',
	text : 'pending'
}, {
	value : 'icon-ready',
	text : 'ready'
}, {
	value : 'icon-reject',
	text : 'reject'
}, {
	value : 'icon-start',
	text : 'start'
}, {
	value : 'icon-submit',
	text : 'submit'
}, {
	value : 'icon-flag-blue',
	text : 'flag-blue'
}, {
	value : 'icon-flag-green',
	text : 'flag-green'
}, {
	value : 'icon-flag-orange',
	text : 'flag-orange'
}, {
	value : 'icon-flag-pink',
	text : 'flag-pink'
}, {
	value : 'icon-flag-purple',
	text : 'flag-purple'
}, {
	value : 'icon-flag-red',
	text : 'flag-red'
}, {
	value : 'icon-flag-yellow',
	text : 'flag-yellow'
} ,{
	value : 'icon-end',
	text : 'end'
},{
	value : 'icon-structure',
	text : 'structure'
},{
	value : 'icon-paste',
	text : 'paste'
},{
	value : 'icon-publish',
	text : 'publish'
},{
	value : 'icon-task',
	text : 'task'
},{
	value : 'icon-file',
	text : 'file'
},{
	value : 'icon-themes',
	text : 'themes'
},{
	value : 'icon-panel',
	text : 'panel'
},{
	value : 'icon-login',
	text : 'login'
},{
	value : 'icon-logout',
	text : 'logout'
},{
	value : 'icon-accept',
	text : 'accept'
},{
	value : 'icon-scheme',
	text : 'scheme'
},{
	value : 'icon-plane',
	text : 'plane'
},{
	value : 'icon-atom',
	text : 'atom'
},{
	value : 'icon-change',
	text : 'change'
},{
	value : 'icon-ecn',
	text : 'ecn'
},{
	value : 'icon-dsc',
	text : 'dsc'
},{
	value : 'icon-fo',
	text : 'fo'
},{
	value : 'icon-nc',
	text : 'nc'
},{
	value : 'icon-doc',
	text : 'doc'
},{
	value : 'icon-png',
	text : 'png'
},{
	value : 'icon-gif',
	text : 'gif'
},{
	value : 'icon-xls',
	text : 'xls'
},{
	value : 'icon-ppt',
	text : 'ppt'
},{
	value : 'icon-jpg',
	text : 'jpg'
},{
	value : 'icon-pdf',
	text : 'pdf'
},{
	value : 'icon-role',
	text : 'role'
},{
	value : 'icon-admin-group',
	text : 'admin-group'
},{
	value : 'icon-team-group',
	text : 'team-group'
},{
	value : 'icon-factory',
	text : 'factory'
},{
	value : 'icon-rules',
	text : 'rules'
},{
	value : 'icon-extend',
	text : 'extend'
},{
	value : 'icon-java',
	text : 'java'
},{
	value : 'icon-closeAll',
	text : 'closeAll'
},{
	value : 'icon-collect',
	text : 'collect'
},{
	value : 'icon-compare',
	text : 'compare'
},{
	value : 'icon-favorite',
	text : 'favorite'
},{
	value : 'icon-bom',
	text : 'bom'
},{
	value : 'icon-check',
	text : 'check'
},{
	value : 'icon-double-check',
	text : 'double-check'
},{
	value : 'icon-data',
	text : 'data'
}];

