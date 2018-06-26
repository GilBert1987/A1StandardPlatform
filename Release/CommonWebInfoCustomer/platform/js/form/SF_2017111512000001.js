$(document).ready(function(e) {
	var rowkey;
	var colkey;
	var valkey;
	var rowname;
	var colname;
	var valname;
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '1000',
		scroller_window_height: '400',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
	rowkey=$("#rowkey").val();
	colkey=$("#colkey").val();
	valkey=$("#valkey").val();
	rowname=$("#rowname").val();
	colname=$("#colname").val();
	valname=$("#valname").val();
	if(""!=rowkey && ""!=colkey && ""!=valkey)
	{
		$("#row").val("["+rowkey+"]"+rowname);
		$("#col").val("["+colkey+"]"+colname);
		$("#value").val("["+valkey+"]"+valname);
	}
	fn_initTableWidth('querylist_table');
	
});

function fn_selCol(radioObj){
	var radioValue;
	var jsonObj;
	var ulInfo;
	var valInfo;
	radioValue=$(radioObj).val();
	valInfo=$($(radioObj).parent().parent().children().get(1)).children().text();
	$("#queryidinfo").val(valInfo);
	if(null!=radioValue && ""!=radioValue)
	{
		$("#tdcol").empty();
		ulInfo="<ul style=\"width:150px;height:135px;overflow-x:hidden;overflow-y:auto;\">";
		jsonObj=JSON.parse(radioValue);
		$(jsonObj).each(function(i,ele){
			ulInfo+="<li style=\"width:170px;height:24px;line-height:24px; font-size:12px;color:#6699ff;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;border-bottom:1px #ff8000 dashed;\">";
			ulInfo+="<span title=\""+ele.name+"\" alt=\""+ele.name+"\">"+ele.name+"</span>";
			ulInfo+="</li>";
			ulInfo+="<li>";
			ulInfo+="<span style=\"color:blue;cursor:pointer;\" onclick=\"fn_saverow('"+ele.key+"','"+ele.name+"');\">[添加行]</span>";
			ulInfo+="<span style=\"color:blue;cursor:pointer;\" onclick=\"fn_savecol('"+ele.key+"','"+ele.name+"');\">[添加列]</span>";
			if("string"!=ele.type)
			{
				ulInfo+="<span style=\"color:blue;cursor:pointer;\" onclick=\"fn_saveval('"+ele.key+"','"+ele.name+"');\">[添加值]</span>";
			}
			ulInfo+="</li>";
		});
		ulInfo+="</ul>";
		$("#tdcol").append(ulInfo);
	}
}

function fn_saverow(key,name){
	$("#row").val("["+key+"]"+name);
	$("#rowkey").val(key);
	$("#rowname").val(name);
}
function fn_savecol(key,name){
	$("#col").val("["+key+"]"+name);
	$("#colkey").val(key);
	$("#colname").val(name);
}
function fn_saveval(key,name){
	$("#value").val("["+key+"]"+name);
	$("#valkey").val(key);
	$("#valname").val(name);
}