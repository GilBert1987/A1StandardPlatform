function fn_changSel(obj){
	var jsonObj;
	var valInfo;
	var treeObj;
	var setting;
	var sysid;
	setting={
		view: {
			selectedMulti: false
		}
	};
	valInfo=$(obj).val();
	sysid=$("#dbinfo").val();
	if("-1"!=valInfo && ""!=sysid){
		jsonObj=JSON.parse(valInfo);
		$("#CCT_Code").val(jsonObj.code);
		$("#CCT_Name").val(jsonObj.name);
		$("#CCT_Index").val(jsonObj.index);
		$("#CCT_Remark").val(jsonObj.remark);
		$("#CCT_ID").val(jsonObj.id);
		$("#tenant_id").val(jsonObj.tenantid);
		$("#frame").attr("src","../frame/SR_2016050611250001.frame?sysid="+sysid+"&property="+jsonObj.id);
	}
	else
	{
		$("#CCT_Code").val("");
		$("#CCT_Name").val("");
		$("#CCT_Index").val("");
		$("#CCT_Remark").val("");
		$("#id").val("");
		$("#tenant_id").val("");
		$("#frame").attr("src","about:blank");
	}
}
