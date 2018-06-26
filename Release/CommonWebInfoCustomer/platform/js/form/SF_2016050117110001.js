function fn_changeSel(selObj){
	var selValue;
	var jsonArr;
	var tabHeadTr;
	var tabBodyTr;
	tabHeadTr='';
	tabBodyTr='';
	selValue=$(selObj).val();
	if("-1"!=selValue)
	{
		$.ajax({
			async:false,
			url: "../../ajax/jarfile/search",
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			type:"POST",
			datatype:'json',
			data:{"jarfile":selValue},
			success: function(result){
				jsonArr=JSON.parse(result);
				$("#tabControl tbody").empty();
				tabHeadTr="<tr>";
				tabHeadTr+="<td style='width:25%;'>";
				tabHeadTr+="选择";
				tabHeadTr+="</td>";
				tabHeadTr+="<td style='width:25%;'>";
				tabHeadTr+="缩写";
				tabHeadTr+="</td>";
				tabHeadTr+="<td style='width:50%;'>";
				tabHeadTr+="格式";
				tabHeadTr+="</td>";
				tabHeadTr+="</tr>";
				$("#tabControl tbody").append(tabHeadTr);
				$(jsonArr).each(function(jsonIndex, jsonEle) {
                    tabBodyTr+="<tr>";
					tabBodyTr+="<td>";
					tabBodyTr+="<input id='rIndex"+"_"+jsonIndex+"' name='rIndex' type='radio' value='"+jsonEle.uri+"' />";
					tabBodyTr+="</td>";
					tabBodyTr+="<td>";
					tabBodyTr+=jsonEle.shortname;
					tabBodyTr+="</td>";
					tabBodyTr+="<td>";
					tabBodyTr+="<%@ taglib prefix=\"{0}\" uri=\""+jsonEle.uri+"\" %>";
					tabBodyTr+="</td>";
					tabBodyTr+="</tr>";
                });
				$("#tabControl tbody").append(tabBodyTr);
				$("#hControlInfo").val(result);
			}
		});
	}
	else
	{
		$("#tabControl").empty();
	}
}