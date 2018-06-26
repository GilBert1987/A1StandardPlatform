$(document).ready(function() {
	var initdata;
	var jsonValue;
	var jsonObj;
	var controlsObj;
	initdata=null;
	if(null!=parent.window)
	{
		initdata=parent.window.$("#hinitdata").val();
	}
	if(null!=initdata)
	{
		jsonValue=decodeURIComponent(initdata);
		jsonObj=JSON.parse(jsonValue);
		if(null!=jsonObj)
		{
			$("#controls").val(jsonObj.controls);
			$("#currcontrol").val(JSON.stringify(jsonObj.currcontrol));
			$("#datatable").val(jsonObj.datatable);
			if(null!=jsonObj.controls && ""!=jsonObj.controls)
			{
				controlsObj=JSON.parse(jsonObj.controls);
				$(controlsObj).each(function(index, eleControl) {
                    if(jsonObj.currcontrol.id==eleControl.control[0].id)
					{
						$("#colName").val(eleControl.control[0].dataBind);
						$("#colName").prop("readonly",true);
						$("#infoname").val(decodeURIComponent(eleControl.control[0].infoname));
						$("#maxlength").val(eleControl.control[0].datamaxlength==null?128:eleControl.control[0].datamaxlength);
						$("input[name='required']").each(function(i, element) {
                            if($(element).val()==eleControl.control[0].required)
							{
								$(element).prop("checked",true);
								return false;
							}
                        });
						return false;
					}
                });
			}
		}
	}
	$("#colName").on("blur",function(){
		var bootstrapValidator;
		var dataValue;
		var dataObj;
		var blInfo;
		blInfo=false;
		bootstrapValidator=$('#pageForm').data('bootstrapValidator');
		dataValue=$("#datatable").val();
		dataObj=JSON.parse(dataValue);
		if(false==$("#colName").prop("readonly"))
		{
			for(var param in dataObj)
			{
				if($(this).val()==param && "1"==dataObj[param].isbind){
					blInfo=true;
					break;
				}
			}
		}
		if(true==blInfo)
		{
			bootstrapValidator.updateStatus("colName","INVALID","hasCode");
			bootstrapValidator.validateField("colName");
		}
		else
		{
			bootstrapValidator.updateStatus("colName","VALID","hasCode");
			bootstrapValidator.validateField("colName");
		}
	});
	setTimeout(function(){
		$("#colName").parent().append("<small class=\"help-block\" data-bv-validator=\"hasCode\" data-bv-for=\"colName\" data-bv-result=\"VALID\" style=\"display: none;\">数据库已存在"+$("#colName").val()+"字段不能重复绑定!</small>");	
	},500);
});
function fn_Validator(){
	var bootstrapValidator;
	bootstrapValidator=$('#pageForm').data('bootstrapValidator');
	bootstrapValidator.validate();
	return bootstrapValidator.isValid();
}
function fn_save(){
	var colName;
	var conrolsText;
	var conrolsObjt;
	var conrolText;
	var conrolObjt;
	var eleControlObj;
	var infoname;
	var currObj;
	var datatableText;
	var datatableObj;
	var currRequired;
	conrolObjt=null;
	eleControlObj=null;
	colName=$("#colName").val();
	infoname=$("#infoname").val();
	conrolsText=$("#controls").val();
	conrolText=$("#currcontrol").val();
	datatableText=$("#datatable").val();
	if(""!=datatableText)
	{
		datatableObj=JSON.parse(datatableText);
	}
	else
	{
		datatableObj={};
	}
	if(""!=conrolsText)
	{
		conrolsObjt=JSON.parse(conrolsText);
	}
	else
	{
		conrolsObjt=[];
	}
	if(""!=conrolText)
	{
		conrolObjt=JSON.parse(conrolText);
	}
	$("input[name='required']").each(function(index, ele) {
		if($(ele).prop("checked")==true){
			currRequired=$(ele).val();
			return false;
		}
    });
	currObj={"control":[
			{
				"id":conrolObjt.id,
				"type":"input",
				"datamaxlength":$("#maxlength").val(),
				"dataBind":$("#colName").val(),
				"infoname":$("#infoname").val(),
				"backtype":"s",
				"required":currRequired
			}
		]
	};
	if(null!=conrolObjt)
	{
		$(conrolsObjt).each(function(index, eleControl) {
			if(conrolObjt.id==eleControl.control[0].id)
			{
				eleControlObj=eleControl;
				return false;
			}
		});
		if(null!=eleControlObj)
		{
			eleControlObj.control=currObj.control;
		}
		else
		{
			datatableObj[$("#colName").val()]={"length":$("#maxlength").val(),"description":$("#infoname").val(),"type":"varchar","isbind":1};
			conrolsObjt.push(currObj);
		}
	}
	parent.fn_updateControl(datatableObj,conrolsObjt,currObj);
	closeWin();
	return false;
}