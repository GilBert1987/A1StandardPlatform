function switchTab(n){  
	for(var i = 1; i <= 2; i++){  
		document.getElementById("tab_" + i).className = "";  
		document.getElementById("tab_con_" + i).style.display = "none";  
	}  
	document.getElementById("tab_" + n).className = "on";  
	document.getElementById("tab_con_" + n).style.display = "block";  
}

function setTableInfo(parminfo){
	var title;
	var sqlInfo;
	var postData;
	title=$('#title').val();
	sqlInfo="SELECT sc_control.cn_label,CONCAT(sc_control.cn_shorthand,':',sc_control_tld.code) AS cn_code,CONCAT(sc_control_tld_attr.name,':',sc_control_tld_attr.code) AS attr_info,sc_control_tld_attr.code as attr_code,sc_control_tld_attr.required AS attr_required,sc_control_tld_attr.rtexprvalue AS attr_rtexprvalue FROM sc_control JOIN sc_control_tld ON sc_control.id=sc_control_tld.control_id LEFT JOIN sc_control_tld_attr ON sc_control_tld.id=sc_control_tld_attr.tldid";
	sqlInfo+=" WHERE CONCAT(sc_control.cn_shorthand,':',sc_control_tld.code)='"+title+"'";
	postData={"type":"sql","dataname":"com","info":sqlInfo};
	$.ajax({
		url: "../../../../ajax/query/search",
		type: "POST",
		data: postData,
		dataType: "json",
		success: function(list){
			creteTabList(list,parminfo);
		}
	});
}
function creteTabList(list,parminfo)
{
	var label;
	var objInfo;
	var objList;
	var tabInfo;
	label='';
	objList=[];
	if(parminfo!=null)
	{
		$(list).each(function(index, ele) {
			objInfo = new Object();
			if(label=='')
			{
				label=ele.cn_label;
			}
			if(null!=ele.attr_code)
			{
				objInfo[ele.attr_code]=(null==parminfo[ele.attr_code]?"":parminfo[ele.attr_code]);
				objInfo["attr_required"]=ele.attr_required;
				objInfo["attr_text"]=ele.attr_info;
				objList.push(objInfo);
			}
		});
	}
	else
	{
		$(list).each(function(index, ele) {
			objInfo = new Object();
			if(label=='')
			{
				label=ele.cn_label;
			}
			if(null!=ele.attr_code)
			{
				objInfo[ele.attr_code]='';
				objInfo["attr_required"]=ele.attr_required;
				objInfo["attr_text"]=ele.attr_info;
				objList.push(objInfo);
			}
		});
	}
	$('#hlabel').val(label);
	$('#dynamic').empty();
	tabInfo='<table width="100%">';
	$(objList).each(function(index, ele) {
        for(var item in ele){
			if('function'!=typeof(ele[item])){
				if("showtype"!=item && "id"!=item && "attr_required"!=item && "attr_text"!=item)
				{
					tabInfo+='<tr>';
					tabInfo+='<td>';
					tabInfo+=ele.attr_text;
					tabInfo+='</td>';
					tabInfo+='<td>';
					tabInfo+='<input id="'+item+'" type="text" value="" required="'+('1'==ele.attr_required?'1':'0')+'" />';
					tabInfo+='</td>';
					tabInfo+='</tr>';
				}
			}
		}
    });
	tabInfo+='</table>';
	$('#dynamic').append(tabInfo);
	$(objList).each(function(index, ele) {
        for(var item in ele){
			if('function'!=typeof(ele[item])){
				if("id"!=item && "attr_required"!=item)
				{
					$("#"+item).val(decodeURIComponent(ele[item]));
				}
			}
		}
    });
}
$(document).ready(function(e){
	var parmobj;
	var parminfo;
	parmobj=editor.options.parmObj;
	parminfo=editor.options.parmInfo;
	$('#id').val(parmobj.id);
	$('#title').val(parmobj.title);
	setTableInfo(parminfo);
	dialog.onok = function (){
		var range;
		var img;
		var id;
		var title;
		var tableInfo;
		var parmId;
		var parmValue;
		var returnObj;
		var label;
		var imgHtml;
		label=$('#hlabel').val();
		returnObj = new Object();
		img=null;
		range=editor.selection.getRange();
		img=range.getClosedNode();
		imgHtml='';
		if(null==img)
		{
			id=($('#id').val()==null?'':$('#id').val());
			title=($('#title').val()==null?'':$('#title').val());
			if(''!=id && ''!=title)
			{
				imgHtml='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\''+id+'\',\'title\':\''+title+'\'}">';
				if(1==editor.options.parmIsadd)
				{
					editor.execCommand('insertHTML',imgHtml);
				}
			}
		}
		tableInfo=$('#dynamic').children()[0];
		for(var i=0;i<tableInfo.rows.length;i++)
		{
			for(var j=1;j<tableInfo.rows[i].cells.length;j++)
			{
				parmId=$(tableInfo.rows[i].cells[j]).children().attr('id');
				parmValue=$(tableInfo.rows[i].cells[j]).children().val();
				returnObj[parmId]=parmValue;
			}
		}
		returnObj['id']=$('#id').val();
		editor.options.retObj={"control":returnObj,"label":label,"imgHtml":imgHtml};
	}
});