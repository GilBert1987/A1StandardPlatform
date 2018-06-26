$(document).ready(function(e) {
    init_data();
});

function init_data(){
	var list;
	$('#tblist').empty();
	list=JSON.parse($('#listitem').val());
	$(list).each(function(i,item){
		if(""!=item.code)
		{
			strTemp="<tr>";
			strTemp+="<td width='10%' style='word-break: break-all;' align='center' valign='middle'>";
			strTemp+="<input name='seltool' type='radio' value='"+item.code+"' onclick='clickTable(this);'>";
			strTemp+="</td>";
			strTemp+="<td width='10%' style='word-break: break-all;' align='center' valign='middle'>";
			strTemp+=item.code;
			strTemp+="</td>";
			strTemp+="<td width='20%' style='word-break: break-all;' align='center' valign='middle'>";
			strTemp+=item.name;
			strTemp+="</td>";
			strTemp+="<td width='50%' style='word-break: break-all;' align='left' valign='middle'>";
			strTemp+=item.url;
			strTemp+="</td>";
			strTemp+="<td width='10%' style='word-break: break-all;' align='center' valign='middle'>";
			strTemp+=item.order;
			strTemp+="</td>";
		 	strTemp+="</tr>";
		 	$('#tblist').append(strTemp);
		}
	});
}

function setgroup(id){
	openWin(1000,400,'工具组配置','SF_201412290005.form?id='+id,null,null);
	return false;
}

function addtool()
{
	openWin(1000,400,'添加工具项','SF_201412290004.form',null,null);
	return false;
}
function edittool(){
	var code;
	var itemInfo;
	itemInfo=null;
	code=$('#hselItem').val();
	list=JSON.parse($('#listitem').val());
	$(list).each(function(i,item){
		if(code==item.code)
		{
			itemInfo=item;
		}
	});
	if(null!=itemInfo)
	{
		openWin(1000,400,'添加工具项','SF_201412290004.form',encodeURIComponent(JSON.stringify(itemInfo)),null);
	}
	else
	{
		alert("请选择工具项!");
	}
	return false;
}
function deltool(){
	var code;
	var plist;
	plist=[];
	code=$('#hselItem').val();
	list=JSON.parse($('#listitem').val());
	$(list).each(function(i,item){
		if(code!=item.code)
		{
			plist.push(item);
		}
	});
	$('#listitem').val(JSON.stringify(plist));
	resizeWin();
	$('#btnsave').click();
	return false;
}

function updateItem(item){
	var list;
	var blInfo;
	blInfo=false;
	list=JSON.parse($('#listitem').val());
	$(list).each(function(index, ele) {
        if(ele.code==item.code)
		{
			list[index]=item;
			blInfo=true;
			return false;
		}
    });
	if(blInfo==false)
	{
		list.push(item);
	}
	$('#listitem').val(JSON.stringify(list));
	resizeWin();
	$('#btnsave').click();
	return false;
}

function updateListStyle(items){
	$('#liststyle').val(JSON.stringify(items));
	resizeWin();
	$('#btnsave').click();
	return false;
}

function clickTable(input){
	$('#hselItem').val($(input).val());
}