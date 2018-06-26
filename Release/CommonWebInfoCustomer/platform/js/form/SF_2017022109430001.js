$(document).ready(function(){
	var colId;
	var colObjs;
	var columns;
	var jsonObj;
	var initValue;
	initValue=parent.$("#hinitdata").val();
	columns=$("#columns").val();
	if(""!=columns && null!=columns)
	{
		colObjs=JSON.parse(columns);
		$(colObjs).each(function(i,ele) {
            if(null==ele.typeid || "0"==ele.typeid)
			{
				$("#relColId").append("<option value=\""+ele.key+"\">"+ele.key+"("+ele.name+")"+"</option>");
			}
        });
	}
	if(""!=initValue && null!=initValue)
	{
		$("#column").val(initValue);
		initValue=decodeURIComponent(initValue);
		jsonObj=JSON.parse(initValue);
		colId=$("#colId").val();
		$("#dataSysId").val(jsonObj.dataSysId);
		$("#titleSysId").val(jsonObj.titleSysId);
		$("#dataSqlInfo").val(decodeURIComponent(jsonObj.dataSqlInfo));
		$("#titleSqlInfo").val(decodeURIComponent(jsonObj.titleSqlInfo));
		$("#titColId").val(jsonObj.titColId);
		$("#currColId").val(jsonObj.currColId);
		$("#currValId").val(jsonObj.currValId);
		$("#relColId").val(jsonObj.relColId);
	}
});

function fn_save(){
	var jsonObj;
	var colId;
	var blInfo;
	var colValue;
	blInfo=false;
	colId=$("#colId").val();
	if(""==colId){
		blInfo=true;
		colId=createKey("com","sc_redis","id");
		jsonObj={"key":colId,"name":colId,"grouptype":"","type":""};
		jsonObj.title={"width":100,"order":1,"isshow":"0","isedit":"0","align":"left"};
	}
	else
	{
		colValue=$("#column").val();
		colValue=decodeURIComponent(colValue);
		jsonObj=JSON.parse(colValue);
	}
	jsonObj.dataSysId=$("#dataSysId").val();
	jsonObj.titleSysId=$("#titleSysId").val();
	jsonObj.dataSqlInfo=encodeURIComponent(decodeURIComponent($("#dataSqlInfo").val().replace(/%/g,'%25')));
	jsonObj.titleSqlInfo = encodeURIComponent(decodeURIComponent($("#titleSqlInfo").val().replace(/%/g,'%25')));
	jsonObj.titColId=$("#titColId").val();
	jsonObj.currColId=$("#currColId").val();
	jsonObj.currValId=$("#currValId").val();
	jsonObj.relColId=$("#relColId").val();
	jsonObj.typeid = $("#typeid").val();
	if(true==blInfo)
	{
		parent.addColumn(jsonObj);
	}
	else
	{
		parent.editColumn(jsonObj);
	}
	closeWin();
	return false;
}