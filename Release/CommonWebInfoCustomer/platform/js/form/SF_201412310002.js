$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var list;
	var strTemp;
	list=JSON.parse($('#nodelist').val());
	$(list).each(function(i,item){
		strTemp="<tr>";
		strTemp+="<td width='15%'>";
		strTemp+=fn_getItemType(item.type);
		strTemp+="</td>";
		strTemp+="<td  width='15%'>";
		strTemp+=item.number;
		strTemp+="</td>";
		strTemp+="<td  width='70%'>";
		strTemp+=item.url;
		strTemp+="</td>";
		strTemp+="</tr>";
		$('#tblist').append(strTemp);
	});
}
function fn_getItemType(typeinfo)
{
	var strResult;
	strResult='';
	switch(typeinfo)
	{
		case "0":
			strResult='左';
			break;
		case "1":
			strResult='右';
			break;
		case "2":
			strResult='右上';
			break;
		case "3":
			strResult='右下';
			break;
		case "4":
			strResult='上';
			break;
		case "5":
			strResult='下';
			break;
	};
	return strResult;
}
function fn_edit(id){
	var type;
	type=$('#type').val();
	openWin(1000,400,'框架详细设置','SF_201501060001.form?id='+id+'&type='+type,null,null);
	return false;
}
function fn_save(){
	var name;
	var blInfo;
	var list;
	var type;
	var items;
	type=$('#type').val();
	blInfo=false;
	name=$('#name').val();
	list=JSON.parse($('#nodelist').val());
	items=[];
	if(""!=name)
	{
		blInfo=true;
	}
	$(list).each(function(i,item){
		if("0"==type)
		{
			if("0"==item.type || "3"==item.type || "2"==item.type)
			{
				items.push(item);
			}
		}
		if("1"==type)
		{
			if("0"==item.type || "1"==item.type)
			{
				items.push(item);
			}
		}
		if("2"==type)
		{
			if("4"==item.type || "5"==item.type)
			{
				items.push(item);
			}
		}
	});
	$('#nodelist').val(JSON.stringify(items));
	return blInfo;
}
function updateNodeList(items){
	$('#nodelist').val(JSON.stringify(items));
	resizeWin();
	$('#btnsave').click();
}