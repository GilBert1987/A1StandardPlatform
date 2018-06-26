$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var list;
	var type;
	type=$('#type').val();
	list=JSON.parse($('#nodelist').val());
	switch(type)
	{
		case "0":
			fn_getLUDTable(list);
			break;
		case "1":
			fn_getLRTable(list);
			break;
		case "2":
			fn_getUDTable(list);
			break;
	};
}
function fn_getLRTable(list){
	var strTemp;
	var litem;
	var ritem;
	litem=null;
	ritem=null;
	$(list).each(function(i, eleitem) {
        if("0"==eleitem.type)
		{
			litem=eleitem;
		}
		if("1"==eleitem.type)
		{
			ritem=eleitem;
		}
    });
	strTemp='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='左'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="lWidth" value="'+(null==litem?0:litem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="lUrl" style="width:90%;" value="'+(null==litem?'':litem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='右'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="rWidth" value="'+(null==ritem?0:ritem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="rUrl" style="width:90%;" value="'+(null==ritem?'':ritem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	$('#tblist').append(strTemp);
}
function fn_getUDTable(list){
	var strTemp;
	var uitem;
	var ditem;
	uitem=null;
	ditem=null;
	$(list).each(function(i, eleitem) {
        if("4"==eleitem.type)
		{
			uitem=eleitem;
		}
		if("5"==eleitem.type)
		{
			ditem=eleitem;
		}
    });
	strTemp='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='上'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="uWidth" value="'+(null==uitem?0:uitem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="uUrl" style="width:90%;" value="'+(null==uitem?'':uitem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='下'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="dWidth" value="'+(null==ditem?0:ditem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="dUrl" style="width:90%;" value="'+(null==ditem?'':ditem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	$('#tblist').append(strTemp);
}
function fn_getLUDTable(list){
	var strTemp;
	var litem;
	var ruitem;
	var rditem;
	litem=null;
	ruitem=null;
	rditem=null;
	$(list).each(function(i, eleitem) {
        if("0"==eleitem.type)
		{
			litem=eleitem;
		}
		if("2"==eleitem.type)
		{
			ruitem=eleitem;
		}
		if("3"==eleitem.type)
		{
			rditem=eleitem;
		}
    });
	strTemp='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='左'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="lWidth" value="'+(null==litem?0:litem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="lUrl" style="width:90%;" value="'+(null==litem?'':litem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='右上'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="ruWidth" value="'+(null==ruitem?0:ruitem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="ruUrl" style="width:90%;" value="'+(null==ruitem?'':ruitem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='<tr>';
	strTemp+='<td width="25%">';
	strTemp+='右下'
	strTemp+='</td>';
	strTemp+='<td width="75%">';
	strTemp+='<table border="0" cellpadding="1" cellspacing="1" width="100%">';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='宽度'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="rdWidth" value="'+(null==rditem?0:rditem.number)+'" />';
	strTemp+='</td>';
	strTemp+='<tr>';
	strTemp+='<td width="20%">';
	strTemp+='链接'
	strTemp+='</td>';
	strTemp+='<td width="30%">';
	strTemp+='<input type="input" id="rdUrl" style="width:90%;" value="'+(null==rditem?'':rditem.url)+'" />';
	strTemp+='</td>';
	strTemp+='</tr>';
	strTemp+='</table>';
	strTemp+='</td>';
	strTemp+='</tr>';
	$('#tblist').append(strTemp);
}
function fn_save(){
	var list;
	var type;
	type=$('#type').val();
	switch(type)
	{
		case "0":
			list=fn_saveLUDTable();
			break;
		case "1":
			list=fn_saveLRTable();
			break;
		case "2":
			list=fn_saveUDTable();
			break;
	};
	parent.window.updateNodeList(list);
	return false;
}
function fn_saveLUDTable(){
	var list;
	list=[];
	list.push({"type":0,"url":$('#lUrl').val(),"number":$('#lWidth').val()});
	list.push({"type":2,"url":$('#ruUrl').val(),"number":$('#ruWidth').val()});
	list.push({"type":3,"url":$('#rdUrl').val(),"number":$('#rdWidth').val()});
	return list;
}
function fn_saveLRTable(){
	var list;
	list=[];
	list.push({"type":0,"url":$('#lUrl').val(),"number":$('#lWidth').val()});
	list.push({"type":1,"url":$('#rUrl').val(),"number":$('#rWidth').val()});
	return list;
}
function fn_saveUDTable(){
	var list;
	list=[];
	list.push({"type":4,"url":$('#uUrl').val(),"number":$('#uWidth').val()});
	list.push({"type":5,"url":$('#dUrl').val(),"number":$('#dWidth').val()});
	return list;
}
