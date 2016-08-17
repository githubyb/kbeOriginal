<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>amtv</title>
<script type="text/javascript">
function OpenFile(str) {
//	var  filename=location.href;
//	var index = filename.lastIndexOf(':/');
//        filename=filename.substr(index-1,filename.lastIndexOf('/')-index+1);  

//	alert(filename);  
        AMTViewer.OpenFile(str);
    }
    function ChooseModelBySegName(str)
    {
	AMTViewer.ChooseModelBySegName(str);
    }
    function ChooseSeqByID(str)
    {
	AMTViewer.ChooseSeqByID(str,0);
    }
    function GetAllOperStep()
    {
	str =  AMTViewer.GetAllOperStep();
	alert(str);
	return str;
    }
</script>
</head>
<body>
	<OBJECT ID="AMTViewer" WIDTH=1000 HEIGHT=800
 CLASSID="CLSID:AF0099DC-3AC8-4E61-B004-1D6CA5407879">
</OBJECT>
<input type="button" value="打开文件" id="btnOK" onclick="javascript:OpenFile('d:/lm.amtv');;" />

<input type="button" value="选择工艺1" id="btnOK" onclick="javascript:ChooseSeqByID('201501');;" />
<input type="button" value="选择工艺2" id="btnOK" onclick="javascript:ChooseSeqByID('201506');;" />
<input type="button" value="选择工艺3" id="btnOK" onclick="javascript:ChooseSeqByID('201509');;" />
<input type="button" value="选择工艺4" id="btnOK" onclick="javascript:ChooseSeqByID('201515');;" />
<input type="button" value="选择工艺5" id="btnOK" onclick="javascript:ChooseSeqByID('201518');;" />
<input type="button" value="选择工艺5" id="btnOK" onclick="javascript:ChooseSeqByID('201519');;" />
<input type="button" value="选择工艺5" id="btnOK" onclick="javascript:ChooseSeqByID('201523');;" />
<input type="button" value="选择工艺5" id="btnOK" onclick="javascript:ChooseSeqByID('201526');;" />
<input type="button" value="获取全部工艺" id="btnOK" onclick="javascript:GetAllOperStep();;" />
</body>
</html>