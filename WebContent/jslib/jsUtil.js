/**
 * js扩展工具类
 * 
 * wangc
 */

var npu = {};

/**
 * 为js扩展
 */
(function($) {

	/**
	 * 增加formatString功能
	 * 
	 * 使用方法：npu.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
	 */
	npu.fs = function(str) {
		for ( var i = 0; i < arguments.length - 1; i++) {
			str = str.replace("{" + i + "}", arguments[i + 1]);
		}
		return str;
	};

	/**
	 * 增加命名空间功能
	 * 
	 * 使用方法：npu.ns('jQuery.bbb.ccc','jQuery.eee.fff');
	 */
	npu.ns = function() {
		var o = {}, d;
		for ( var i = 0; i < arguments.length; i++) {
			d = arguments[i].split(".");
			o = window[d[0]] = window[d[0]] || {};
			for ( var k = 0; k < d.slice(1).length; k++) {
				o = o[d[k + 1]] = o[d[k + 1]] || {};
			}
		}
		return o;
	};

	/**
	 * 获得项目根路径
	 * 
	 * 使用方法：npu.bp();
	 */
	npu.bp = function() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
		return (localhostPaht + projectName);
	};

	/**
	 * 对Date的扩展
	 * 
	 * 将 Date 转化为指定格式的String
	 * 
	 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
	 * 
	 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
	 * 
	 * (new Date()).pattern("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
	 * 
	 * (new Date()).pattern("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
	 * 
	 * (new Date()).pattern("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
	 * 
	 * (new Date()).pattern("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
	 * 
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

})(jQuery);

/**
 * 为jQuery扩展
 */
(function($) {

	/**
	 * 设置ajax默认值
	 */
	$.ajaxSetup({
		cache : false,
		dataType : "json",
		type : "POST",
		error : function(responseBodyModel) {
			if (responseBodyModel.msg) {
				alert(responseBodyModel.msg);
			} else {
				alert(responseBodyModel.responseText);
			}
		}
	});

	/**
	 * 重写AJAX使用方式
	 */
	npu.ajax = function(options) {
		var ajaxDefaultsOptions = {
			url : "",
			data : $("form").serialize(),
			success : function(responseBodyModel) {
				if (responseBodyModel.success) {
					alert(responseBodyModel.msg);
				} else {
					alert(responseBodyModel.msg);
				}
			},
			cache : false,
			dataType : "json",
			type : "POST",
			error : function(responseBodyModel) {
				if (responseBodyModel.msg) {
					alert(responseBodyModel.msg);
				} else {
					alert(responseBodyModel.responseText);
				}
			}
		};
		var opts = $.extend({}, ajaxDefaultsOptions, options);
		$.ajax(opts);
	};

	/**
	 * 显示AJAX开始时的提示信息
	 */
	npu.showLoadingDiv = function() {
		var loadingDiv = $('#_AJAXLOADINGDIV_');
		if (loadingDiv.length < 1) {
			$("body").append('<div id="_AJAXLOADINGDIV_" style="z-index: 9999999; position: absolute; top: 0px; right: 0px; background-color:#FFFEE6; color:#8F5700; padding:5px;"><div>数据处理中，请稍候。。。。</div></div>');
		} else {
			loadingDiv.show();
		}
	};
	/**
	 * AJAX结束时隐藏提示信息
	 */
	npu.hideLoadingDiv = function() {
		$('#_AJAXLOADINGDIV_').hide('slow');
	};
	/**
	 * 为jQuery的ajax提供等待提示
	 */
	$(document).ajaxStart(npu.showLoadingDiv).ajaxStop(npu.hideLoadingDiv);

})(jQuery);

/**
 * 为easyUI扩展
 */
(function($) {

	/**
	 * 为datagrid增加错误提示
	 */
	$.fn.datagrid.defaults.onLoadError = function() {
		$.messager.alert('提示', '数据表格加载错误！请重新登录后再试。', 'error');
	};

	/**
	 * 增加验证方法
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		eqPassword : {// 验证密码
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '密码不一致！'
		},
		ip : {// 验证ip
			validator : function(value) {
				return /^(2[5][0-5]|2[0-4]\d|1\d{2}|\d{1,2})\.(25[0-5]|2[0-4]\d|1\d{2}|\d{1,2})\.(25[0-5]|2[0-4]\\d|1\d{2}|\d{1,2})\.(25[0-5]|2[0-4]\d|1\d{2}|\d{1,2})$/i.test(value);
			},
			message : '输入有效ip！'
		},
		phone: {// 验证电话号码
            validator: function (value) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
            },
            message: '格式不正确,请使用下面格式:020-88888888'
        },
        mobile: {// 验证手机号码
            validator: function (value) {
                return /^(13|15|18)\d{9}$/i.test(value);
            },
            message: '手机号码格式不正确'
        },
        zip: {// 验证邮政编码
            validator: function (value) {
                return /^[1-9]\d{5}$/i.test(value);
            },
            message: '邮政编码格式不正确'
        },
        idcard: {// 验证身份证
            validator: function (value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        },
        english: {// 验证英文
            validator: function (value) {
                return /^[A-Za-z]+$/i.test(value);
            },
            message: '请输入英文'
        }
        /*idcard: {// 验证邮箱
            validator: function (value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message: '身份证号码格式不正确'
        },*/
	});

	/**
	 * 添加tab方法
	 */
	npu.addTab = function(options) {
		var tabDefaultsOptions = {
			jqObj : $(''),
			title : '',
			url : '',
			closable : true
		};

		var opts = $.extend({}, tabDefaultsOptions, options);

		if (opts.jqObj.tabs('exists', opts.title)) {
			opts.jqObj.tabs('close', opts.title);
		}
		opts.jqObj.tabs('add', {
			title : opts.title,
			content : '<iframe scrolling="auto" frameborder="0" src="' + opts.url + '" style="width:100%;height:100%;"></iframe>',
			closable : opts.closable
		});

	};

})(jQuery);

jQuery(function() {

	jQuery(document).bind("contextmenu", function(e) {/* 禁止右键 */
		/* return false; */
	});

});
jQuery(window).load(function() {

	if ((jQuery.browser.msie && (jQuery.browser.version == "6.0") && !jQuery.support.style)) {
	}

});
jQuery(window).unload(function() {
});
