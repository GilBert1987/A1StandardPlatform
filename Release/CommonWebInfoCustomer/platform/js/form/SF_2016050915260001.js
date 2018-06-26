$(document).ready(function(e) {
    var id;
	var jsonObj;
	var strInfo;
	var params;
	var jsonHidden;
	params=[];
	strInfo='';
	id=$("#id").val();
	$.ajax({
		async:false,
		url: "../../ajax/method/search",
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		type:"POST",
		datatype:'json',
		data:{"id":id},
		success: function(result){
			$("#divInfo").empty();
			if(""!=result)
			{
				jsonObj=JSON.parse(result);
				jsonHidden={"fireclass":jsonObj.classinfo,"method":jsonObj.methodinfo,"parm":params}
				strInfo='<div class="form-group">';
				strInfo+='<label class="col-sm-2 control-label no-padding-right" for="name">';
				strInfo+='类名';
				strInfo+='</label>';
				strInfo+='<div class="col-sm-10">';
				strInfo+=jsonObj.classinfo;
				strInfo+='</div>';
				strInfo+='</div>';
				strInfo+='<div class="form-group">';
				strInfo+='<label class="col-sm-2 control-label no-padding-right" for="name">';
				strInfo+='方法名';
				strInfo+='</label>';
				strInfo+='<div class="col-sm-10">';
				strInfo+=jsonObj.methodinfo;
				strInfo+='</div>';
				strInfo+='</div>';
				$(jsonObj.params).each(function(i,eleObj) {
					params.push({"key":eleObj.param,"value":eleObj.init});
					strInfo+='<div class="form-group">';
					strInfo+='<label class="col-sm-2 control-label no-padding-right" for="name">';
					strInfo+='参数类型';
					strInfo+='</label>';
					strInfo+='<div class="col-sm-4">';
					strInfo+=eleObj.param;
					strInfo+='</div>';
					strInfo+='<label class="col-sm-2 control-label no-padding-right" for="name">';
					strInfo+='说明';
					strInfo+='</label>';
					strInfo+='<div class="col-sm-4">';
					strInfo+='<textarea style="width:90%;height:30px;" readonly="readonly">';
					strInfo+=decodeURIComponent(eleObj.remark);
					strInfo+='</textarea>';
					strInfo+='</div>';
					strInfo+='</div>';
					strInfo+='<div class="form-group">';
					strInfo+='<label class="col-sm-2 control-label no-padding-right" for="name">';
					strInfo+='参数值';
					strInfo+='</label>';
					strInfo+='<div class="col-sm-10">';
					strInfo+='<textarea name="param" style="width:90%;height:30px;">';
					strInfo+=decodeURIComponent(eleObj.init);
					strInfo+='</textarea>';
					strInfo+='</div>';
					strInfo+='</div>';
				});
				$("#hJson").val(JSON.stringify(jsonHidden));
				$("#divInfo").append(strInfo);
			}
		}
	});
});

function fn_saveButton(){
	var hJson;
	var params;
	if(""!=$("#hJson").val())
	{
		hJson=JSON.parse($("#hJson").val());
		$(hJson.parm).each(function(pi, peleObj) {
            $("textarea[name='param']").each(function(ti, teleObj) {
                if(pi==ti)
				{
					peleObj.value=$(teleObj).val();
					return false;
				}
            });
        });
	}
	window.parent.window.parent.addFireFun(hJson);
	window.parent.closeWin();
	return false;
};