function fn_changeSel(selObj){
	var selValue;
	var jsonArr;
	var tabHeadTr;
	var tabBodyTr;
	var className;
	var classMethod;
	var $items;
	var index;
	tabHeadTr='';
	tabBodyTr='';
	selValue=$(selObj).val();
	if("-1"!=selValue)
	{
		$.ajax({
			async:false,
			url: "../../ajax/interface/search",
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			type:"POST",
			datatype:'json',
			data:{"jarfile":selValue},
			success: function(result){
				jsonArr=JSON.parse(result);
				$("#webwidget_scroller_tab").empty();
				tabHeadTr+='<ul>';
				$(jsonArr).each(function(jsonIndex, jsonEle) {
					if(0==jsonIndex)
					{
						tabHeadTr+='<li class="formedit selected">';
						tabHeadTr+='</li>';
					}
					else
					{
						tabHeadTr+='<li class="formedit">';
						tabHeadTr+='</li>';
					}
				});
				tabHeadTr+='</ul>';
				$("#webwidget_scroller_tab").append(tabHeadTr);
				$(jsonArr).each(function(jsonIndex, jsonEle) {
					className=jsonEle.classinfo;
					tabBodyTr+='<div id="divContent">';
					tabBodyTr+='<h4>'+jsonEle.classinfo+'</h4>';
					tabBodyTr+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">';
					tabBodyTr+="<tr>";
					tabBodyTr+="<td style='width:25%;'>";
					tabBodyTr+="<input type='checkbox' value='"+className+"' onclick='fn_checkClassAll(this);' />";
					tabBodyTr+="选择";
					tabBodyTr+="</td>";
					tabBodyTr+="<td style='width:75%;'>";
					tabBodyTr+="类名";
					tabBodyTr+="</td>";
					tabBodyTr+="</tr>";
					$(jsonEle.methods).each(function(methIndex, methEle) {
						classMethod=JSON.stringify({"classinfo":jsonEle.classinfo,"method":methEle.name,"rettype":methEle.rettype,"params":methEle.params});
						tabBodyTr+="<tr>";
						tabBodyTr+="<td>";
						tabBodyTr+="<input name='"+className+"' type='checkbox' value='"+classMethod+"' onclick='fn_checkInfo();'/>";
						tabBodyTr+="</td>";
						tabBodyTr+="<td>";
						tabBodyTr+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">';
						tabBodyTr+='<tbody>';
						tabBodyTr+='<tr>';
						tabBodyTr+='<td style="width:25%;">';
						tabBodyTr+='类名';
						tabBodyTr+='</td>';
						tabBodyTr+='<td style="width:75%;">';
						tabBodyTr+=jsonEle.classinfo;
						tabBodyTr+='</td>';
						tabBodyTr+='</tr>';
						tabBodyTr+='<tr>';
						tabBodyTr+='<td style="width:25%;">';
						tabBodyTr+='方法名';
						tabBodyTr+='</td>';
						tabBodyTr+='<td style="width:75%;">';
						tabBodyTr+=methEle.rettype+" "+methEle.name;
						tabBodyTr+='</td>';
						tabBodyTr+='</tr>';
						tabBodyTr+='<tr>';
						tabBodyTr+='<td style="width:100%;" colspan="2">';
						$(methEle.params).each(function(paramIndex, paramEle) {
							tabBodyTr+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">';
							tabBodyTr+='<tbody>';
							tabBodyTr+='<tr>';
							tabBodyTr+='<td style="width:25%;">';
							tabBodyTr+='参数类型';
							tabBodyTr+='</td>';
							tabBodyTr+='<td style="width:75%;">';
							tabBodyTr+=paramEle.param;
							tabBodyTr+='</td>';
							tabBodyTr+='</tr>';
							tabBodyTr+='</tbody>';
							tabBodyTr+='</table>';
						});
						tabBodyTr+='</td>';
						tabBodyTr+='</tr>';
						tabBodyTr+='</tbody>';
						tabBodyTr+='</table>';
						tabBodyTr+="</td>";
						tabBodyTr+="</tr>";
					});
					tabBodyTr+="</table>";
					tabBodyTr+='</div>';
                });
				tabBodyTr+='</div>';
				$("#webwidget_scroller_tab").append(tabBodyTr);
				$("#hAllLogicInfo").val(result);
				$("#webwidget_scroller_tab").css("minHeight",($(jsonArr).size()*110+20)+"px");
				$items = $('#webwidget_scroller_tab>ul>li');
            	$items.mouseover(function() {
                	$items.removeClass('selected');
                	$(this).addClass('selected');
                	index = $items.index($(this));
                	$('#webwidget_scroller_tab>div').hide().eq(index).show();
				}).eq(0).mouseover();
			}
		});
	}
	else
	{
		$("#webwidget_scroller_tab").empty();
		$("#hAllLogicInfo").val("");
	}
}

function fn_checkClassAll(checkObj){
	var className;
	var isChecked;
	className=$(checkObj).val();
	isChecked=$(checkObj).prop('checked');
	$('input[name="'+className+'"]').each(function(i, eleObj) {
        $(eleObj).prop('checked',isChecked);
    });
	fn_checkInfo();
}

function fn_checkInfo()
{
	var jsonAllInfo;
	var allInfo;
	var list;
	list=[];
	$("#hLogicInfo").val("[]");
	allInfo=$("#hAllLogicInfo").val();
	if(''!=allInfo)
	{
		jsonAllInfo=JSON.parse(allInfo);
		$(jsonAllInfo).each(function(i, eleObj) {
			$("input[name='"+eleObj.classinfo+"']").each(function(methindex, methodObj) {
                if($(methodObj).prop("checked")==true)
				{
					list.push(JSON.parse($(methodObj).val()));
				}
            });
        });
		$("#hLogicInfo").val(JSON.stringify(list));
	}
}