$(document).ready(function(e) {
	var initValue;
	var initData;
	var jsonData;
	var baseData;
	var selOption;
	var jsonFormCode;
	selOption="";
	initValue=parent.$("#hinitdata").val();
	if(""!=initValue && null!=initValue)
	{
		initValue=decodeURIComponent(initValue);
		jsonData=JSON.parse(initValue);
		if(""!=initData)
		{
			initData=JSON.parse(jsonData.initData);
			if(null!=initData)
			{
				$(initData).each(function(index, eleform) {
					selOption+=("<option value=\""+eleform.code+"\">"+eleform.code+"</option>");
                });
				$("#startFormkey").append(selOption);
				$("#endFormkey").append(selOption);
			}
			$("#asynchronousdefinition").append("<option value=\"false\">否</option>");
			$("#asynchronousdefinition").append("<option value=\"true\">是</option>");
			$("#exclusivedefinition").append("<option value=\"Yes\">是</option>");
			$("#exclusivedefinition").append("<option value=\"No\">否</option>");
			$("#multiinstance_sequential").append("<option value=\"Yes\">是</option>");
			$("#multiinstance_sequential").append("<option value=\"No\">否</option>");
			$("#isforcompensation").append("<option value=\"false\">否</option>");
			$("#isforcompensation").append("<option value=\"true\">是</option>");
		}
		if(""!=jsonData.baseInfo){
			baseData=JSON.parse(jsonData.baseInfo);
			if(null!=baseData)
			{
				if("" !=baseData.formkeydefinition)
				{
					jsonFormCode=JSON.parse(decodeURIComponent(baseData.formkeydefinition));
					$("#startFormkey").val((null==jsonFormCode.startcode?"":jsonFormCode.startcode));
					$("#endFormkey").val((null==jsonFormCode.endcode?"":jsonFormCode.endcode));
				}
				$("#duedatedefinition").val(baseData.duedatedefinition);
				$("#prioritydefinition").val(baseData.prioritydefinition);
				$("#tasklisteners").val(baseData.tasklisteners);
				$("#asynchronousdefinition").val(baseData.asynchronousdefinition);
				$("#exclusivedefinition").val(baseData.exclusivedefinition);
				$("#multiinstance_sequential").val(baseData.multiinstance_sequential);
				$("#multiinstance_cardinality").val(baseData.multiinstance_cardinality);
				$("#multiinstance_collection").val(decodeURIComponent(baseData.multiinstance_collection));
				$("#multiinstance_variable").val(baseData.multiinstance_variable);
				$("#multiinstance_condition").val(decodeURIComponent(baseData.multiinstance_condition));
				$("#multiinstance_type").val(decodeURIComponent(baseData.multiinstance_type));
				$("#isforcompensation").val(baseData.isforcompensation);
			}
		}
		$("#hidata").val(jsonData.initData);
		$("#hbaseinfo").val(jsonData.baseInfo);
	}
});

function fn_save(){
	var baseInfo;
	baseInfo={};
	baseInfo.formkeydefinition=encodeURIComponent("{\"startcode\":\""+$("#startFormkey").val()+"\",\"endcode\":\""+$("#endFormkey").val()+"\"}");
	baseInfo.formproperties="";
	baseInfo.duedatedefinition=$("#duedatedefinition").val();
	baseInfo.prioritydefinition=$("#prioritydefinition").val();
	baseInfo.tasklisteners=$("#tasklisteners").val();
	baseInfo.asynchronousdefinition=(null==$("#asynchronousdefinition").val()?"false":$("#asynchronousdefinition").val());
	baseInfo.exclusivedefinition=$("#exclusivedefinition").val();
	baseInfo.looptype="None";
	baseInfo.multiinstance_sequential=(null==$("#multiinstance_sequential").val()?"false":$("#multiinstance_sequential").val());
	baseInfo.multiinstance_cardinality=$("#multiinstance_cardinality").val();
	baseInfo.multiinstance_collection=encodeURIComponent(decodeURIComponent($("#multiinstance_collection").val()));
	baseInfo.multiinstance_variable=$("#multiinstance_variable").val();
	baseInfo.multiinstance_condition=encodeURIComponent(decodeURIComponent($("#multiinstance_condition").val()));
	baseInfo.multiinstance_type=encodeURIComponent(decodeURIComponent($("#multiinstance_type").val()));
	baseInfo.isforcompensation=$("#isforcompensation").val();
	parent.window.fn_saveBase(baseInfo);
	closeWin();
}