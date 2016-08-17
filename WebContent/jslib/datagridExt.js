(function($){
    function _registRowEditingHandler(jq){

        var getEditorButtonsPanelId = function(target){
            return $(target).attr('id')+'_editor_buttons_panel';
        }

        var deltaX = 120;
        
        var buildEditorButtonsPanel = function(target){
            var panelId = getEditorButtonsPanelId(target);
            if($('#'+panelId).length > 0) return;

            var panel = $(target).datagrid('getPanel');
            var datagrid_body = $('>div.datagrid-view>div.datagrid-view2>div.datagrid-body', panel);
            datagrid_body.css('position', 'relative');

            var edtBtnPanel = $('<div>', {id: panelId})
                .addClass('dialog-button')
                .appendTo(datagrid_body)
                .css({
                    'position': 'absolute',
                    'display': 'block',
                    'border-bottom': '1px solid #ddd',
                    'border-left': '1px solid #ddd',
                    'border-right': '1px solid #ddd',
                    'left': parseInt(panel.width()/2)-120,
                    'z-index': 2014,
                    'display': 'none',
                    'padding': '4px 5px'
                });

            $('<a href="javascript:void(0)">确定</a>')
                .css('margin-left','0px')
                //.linkbutton({iconCls: 'icon-ok'})
                .linkbutton()
                .click(function(){
                    var editIndex = $(target).datagrid('getRowIndex', $(target).datagrid('getEditingRow'));
                    $(target).datagrid('endEdit', editIndex);
                })
                .appendTo(edtBtnPanel);
            $('<a href="javascript:void(0)">取消</a>')
                .css('margin-left', '6px')
                //.linkbutton({iconCls: 'cancel'})
                .linkbutton()
                .click(function(){
                    var editIndex = $(target).datagrid('getRowIndex', $(target).datagrid('getEditingRow'));
                    $(target).datagrid('cancelEdit', editIndex);
                })
                .appendTo(edtBtnPanel);

        }
        
        var showEditorButtonsPanel = function(target, index){
            var opts = $.data(target, "datagrid").options;
            var tr = opts.finder.getTr(target, index, "body", 2);
            var position = tr.position();

            var edtBtnPanelId = '#'+getEditorButtonsPanelId(target);
            var state = $.data(target, 'datagrid');
            var datagrid_body = state.dc.body2;

            var fixPosition = function(){
                var trHeight = tr.height(), trWidth = tr.width();
                var top = position.top + datagrid_body.scrollTop(), left = position.left;
                var delta = 11;

                if(trWidth > datagrid_body.width()){
                    left = datagrid_body.width()/2 - deltaX;
                }else{
                    left = trWidth/2 - deltaX;
                }

                if(position.top>0&&position.top + (trHeight * 2 + delta) > datagrid_body.height()){
                    top = top - (trHeight  + delta);
                }else{
                    top = top + trHeight;
                } 
                return {top: top, left: left};
            }

            $(edtBtnPanelId).css(fixPosition()).show();
        }

        var hideEditorButtonsPanel = function(target){
            var edtBtnPanelId = '#'+getEditorButtonsPanelId(target);
            $(edtBtnPanelId).hide();
        }


        jq.each(function(){
            var target = this;
            var opts = $.data(target, "datagrid").options;
            
            var onLoadSuccessCallBack = opts.onLoadSuccess;
            var onBeforeEditCallBack = opts.onBeforeEdit;
            var onAfterEditCallBack = opts.onAfterEdit;
            var onCancelEditCallBack = opts.onCancelEdit;

            $(this).datagrid({
                onLoadSuccess: function(data){
                	buildEditorButtonsPanel(this);
                    onLoadSuccessCallBack.call(this, arguments);
                },
                onBeforeEdit: function(index, data){
                	//buildEditorButtonsPanel(this);
                    showEditorButtonsPanel(target, index);
                    onBeforeEditCallBack.call(this,  arguments[0],arguments[1]);
                },
                onAfterEdit: function(index, data,changes){
                    hideEditorButtonsPanel(target);
                    onAfterEditCallBack.call(this, arguments[0],arguments[1],arguments[2]);
                },
                onCancelEdit: function(index, data){
                    hideEditorButtonsPanel(target);
                    onCancelEditCallBack.call(this,  arguments[0],arguments[1]);
                }
            });

        });
    }

    $.fn.datagrid.defaults.customAttr={
        rowediting: true
    }

    $.extend($.fn.datagrid.methods, {
        followCustomHandle: function(jq){
            return jq.each(function(){
                var opts = $.extend(true, {}, $.fn.datagrid.defaults, $.data(this, 'datagrid').options);
                if(opts.customAttr.rowediting){
                    _registRowEditingHandler(jq);
                }
            });
        },
        getEditingRow: function(jq){
            var datagrid = $.data(jq[0], "datagrid");
            var opts = datagrid.options;
            var data = datagrid.data;
            var editingRow = [];
            opts.finder.getTr(jq[0], "", "allbody").each(function(){
                if($(this).hasClass('datagrid-row-editing')){
                    var index = parseInt($(this).attr('datagrid-row-index'));
                    editingRow.push(data.rows[index]);
                }
            });

            return editingRow.length>0?editingRow[0]:null;
        },
        
		addEditor : function(jq, param) {
 			if (param instanceof Array) {
 				$.each(param, function(index, item) {
 					var e = $(jq).datagrid('getColumnOption', item.field);
 					e.editor = item.editor;
 				});
 			} else {
 				var e = $(jq).datagrid('getColumnOption', param.field);
 				e.editor = param.editor;
 			}
 		},
 		removeEditor : function(jq, param) {
 			if (param instanceof Array) {
 				$.each(param, function(index, item) {
 					var e = $(jq).datagrid('getColumnOption', item);
 					e.editor = {};
 				});
 			} else {
 				var e = $(jq).datagrid('getColumnOption', param);
 				e.editor = {};
 			}
 		}
    });

    $.extend($.fn.datagrid.defaults.editors,{    // 给datagrid扩展autocomplete编辑器
		autocomplete:{
			init:function(container,option){
				var input=$('<input>').autocomplete(option.data,option.options).result(function(event,data,fromatted){$(this).attr('id',data.id);}).appendTo(container);
				return input;
			},
			getValue:function(target){
				if($(target).attr('id')){
					return $(target).val()+'|'+$(target).attr('id');
				}else{
					return $(target).val();
				}
			},
			setValue:function(target,value){
				//此处可能为字符串和对象
				//return $(target).val(value);
				if(typeof value=="string"){
					return $(target).val(value);
				}else if(typeof value=="object"){					
					if(jQuery.isEmptyObject(value)){
						return $(target).val("");						
					}else{
						if(value.hasOwnProperty('displayName')){
							return $(target).val(value['displayName']);
						}else{
							return $(target).val("");	
						}
					}					
				}else{
					return $(target).val(value);
				}	
			},
			resize:function(target,width){
				var input=$(target);
				if($.boxModel==true){
					input.width(width-(input.outWidth()-input.width()));
				}else{
					input.width(width);
				}
			}
		}
	});
 	/**
 	 * @author 我们不是程序猿，我们只是百度的搬运工
 	 * 
 	 * @requires jQuery,EasyUI
 	 * 
 	 * 扩展datagrid，添加动态增加或删除Editor的方法
 	 * 
 	 * 例子如下，第二个参数可以是数组
 	 * 
 	 * datagrid.datagrid('removeEditor', 'cpwd');
 	 * 
 	 * datagrid.datagrid('addEditor', [ { field : 'ccreatedatetime', editor : { type : 'datetimebox', options : { editable : false } } }, { field : 'cmodifydatetime', editor : { type : 'datetimebox', options : { editable : false } } } ]);
 	 * 
 	 */
 	/*$.extend($.fn.datagrid.methods, {
 		addEditor : function(jq, param) {
 			if (param instanceof Array) {
 				$.each(param, function(index, item) {
 					var e = $(jq).datagrid('getColumnOption', item.field);
 					e.editor = item.editor;
 				});
 			} else {
 				var e = $(jq).datagrid('getColumnOption', param.field);
 				e.editor = param.editor;
 			}
 		},
 		removeEditor : function(jq, param) {
 			if (param instanceof Array) {
 				$.each(param, function(index, item) {
 					var e = $(jq).datagrid('getColumnOption', item);
 					e.editor = {};
 				});
 			} else {
 				var e = $(jq).datagrid('getColumnOption', param);
 				e.editor = {};
 			}
 		}
 	});*/
    
})(jQuery)