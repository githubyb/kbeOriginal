/*
 * author:fanl
 * version:1.0
 * description: 文件上传类
 * */  
	var Class = {
		create : function() {
			return function() {
				this.initialize.apply(this, arguments);
			}
		}
	}

	var Extend = function(destination, source) {
		for ( var property in source) {
			destination[property] = source[property];
		}
	}

	var Bind = function(object, fun) {
		return function() {
			return fun.apply(object, arguments);
		}
	}

	var Each = function(list, fun) {
		for ( var i = 0, len = list.length; i < len; i++) {
			fun(list[i], i);
		}
	};
	
	function GetFileNameByPath(filepath)
	 {
	    var filename='';
	    if(filepath.length>0){		  
		  var nameAttr=filepath.split("\\");		 
		  filename=nameAttr[nameAttr.length-1];
		}else{
		  filename=filepath;
		}		
		return filename;
	 }
	// 文件上传类
	var FileUpload = Class.create();	
	FileUpload.prototype = {
		// 表单对象，文件控件存放空间
		initialize : function(options) {
			this.Files = [];// 文件集合
			this.SetOptions(options);
			this.Form = document.getElementById(this.options.Form);// 表单
			this.Folder = document.getElementById(this.options.Folder);// 文件控件存放空间
			this.FileName = this.options.FileName;
			this.FileList = this.options.FileList;
			this._FrameName = this.options.FrameName;
			this.Limit = this.options.Limit;
			this.Distinct = !!this.options.Distinct;
			this.ShowFilePath=this.options.ShowFilePath;
			this.ExtIn = this.options.ExtIn;
			this.ExtOut = this.options.ExtOut;            
			this.onIniFile = this.options.onIniFile;
			this.onEmpty = this.options.onEmpty;
			this.onNotExtIn = this.options.onNotExtIn;
			this.onExtOut = this.options.onExtOut;
			this.onLimite = this.options.onLimite;
			this.onSame = this.options.onSame;
			this.onFail = this.options.onFail;
			this.onIni = this.options.onIni;
			// 整理一次
			this.Ini();
		},
		// 设置默认属性
		SetOptions : function(options) {
			this.options = {// 默认值
				Form : "",
				Folder : "",
				FileList:"",
				FileName : "upfile",// 文件上传控件的name，配合后台使用
				FrameName : "",// iframe的name，要自定义iframe的话这里设置name
				Limit : 10,// 文件数限制，0为不限制
				Distinct : true,// 是否不允许相同文件
				ExtIn : [ "gif", "jpg", "rar", "zip", "iso", "swf", "png" ],// 允许后缀名
				ExtOut : [],// 禁止后缀名，当设置了ExtIn则ExtOut无效
				ShowFilePath:true,//是否显示文件全路径
				onIniFile : function(file) {
					file.value ? file.style.display = "none" : this.Folder
							.removeChild(file);
				},
				onEmpty : function() {
					alert("请选择一个文件");
				},
				onLimite : function() {
					alert("上传文件数量超过上传限制");
				},
				onSame : function() {
					alert("已经有相同文件");
				},
				onNotExtIn : function() {
					alert("只允许上传" + this.ExtIn.join("，") + "格式的文件！");
				},
				onFail : function(file) {
					this.Folder.removeChild(file);
				},
				onIni : function() {
					// 显示文件列表
					var arrRows = [];
					if (this.Files.length) {
						var oThis = this;
						Each(this.Files, function(o) {
							var a = document.createElement("a");
							a.innerHTML = "删除";
							a.href = "javascript:void(0);";
							a.className = "file_div_link";
							a.onclick = function() {
								oThis.Delete(o);
								return false;
							};
							if (oThis.ShowFilePath) {
								arrRows.push([ o.value, a ]);
							} else {
								var file_name = GetFileNameByPath(o.value);
								arrRows.push([ file_name, a ]);
							}
						});
						
					} else {
						// arrRows.push(["<font color='gray'>没有添加文件</font>",
						// "&nbsp;"]);
					}
					AddList(arrRows,this.FileList);
				}
			};
			Extend(this.options, options || {});
		},
		// 整理空间
		Ini : function() {
			// 整理文件集合
			this.Files = [];
			// 整理文件空间，把有值的file放入文件集合
			Each(this.Folder.getElementsByTagName("input"), Bind(this, function(o) {
				if (o.type == "file") {
					o.value && this.Files.push(o);
					this.onIniFile(o);
				}
			}))
			// 插入一个新的file
			var file = document.createElement("input");
			file.name = this.FileName;
			file.type = "file";
			file.onchange = Bind(this, function() {
				this.Check(file);
				this.Ini();
			});
			this.Folder.appendChild(file);
			// 执行附加程序
			this.onIni();
		},
		// 检测file对象
		Check : function(file) {
			// 检测变量
			var bCheck = true;
			// 空值、文件数限制、后缀名、相同文件检测
			if (!file.value) {
				bCheck = false;
				this.onEmpty();
			} else if (this.Limit && this.Files.length >= this.Limit) {
				bCheck = false;
				this.onLimite();
			} /*else if (!!this.ExtIn.length
					&& !RegExp("\.(" + this.ExtIn.join("|") + ")$", "i").test(
							file.value)) {
				// 检测是否允许后缀名
				bCheck = false;
				this.onNotExtIn();
			} */else if (!!this.ExtOut.length
					&& RegExp("\.(" + this.ExtOut.join("|") + ")$", "i").test(
							file.value)) {
				// 检测是否禁止后缀名
				bCheck = false;
				this.onExtOut();
			} else if (!!this.Distinct) {
				Each(this.Files, function(o) {
					if (o.value == file.value) {
						bCheck = false;
					}
				})
				if (!bCheck) {
					this.onSame();
				}
			}
			// 没有通过检测
			!bCheck && this.onFail(file);
		},
		// 删除指定file
		Delete : function(file) {
			// 移除指定file
			this.Folder.removeChild(file);
			this.Ini();
		},
		// 删除全部file
		Clear : function() {
			// 清空文件空间
			Each(this.Files, Bind(this, function(o) {
				this.Folder.removeChild(o);
			}));
			this.Ini();
		}
	}    
	//用来添加文件列表的函数
	function AddList(rows,fileList) {
		// 根据数组来添加列表
		var FileList = document.getElementById(fileList), oFragment = document
				.createDocumentFragment();
		// 用文档碎片保存列表
		Each(rows, function(cells) {
			// var row = document.createElement("tr");
			var row = document.createElement("div");
			row.className = "file_div";
			Each(cells, function(o) {
				// var cell = document.createElement("td");
				var cell = document.createElement("div");
				if (typeof o == "string") {
					cell.className = "file_div_name";
					cell.setAttribute("title",o);
					cell.innerHTML = o;
				} else {
					cell.className = "file_div_delete";
					cell.appendChild(o);
				}
				row.appendChild(cell);
			});
			oFragment.appendChild(row);
		});
		// ie的table不支持innerHTML所以这样清空table
		while (FileList.hasChildNodes()) {
			FileList.removeChild(FileList.firstChild);
		}
		FileList.appendChild(oFragment);
	}


