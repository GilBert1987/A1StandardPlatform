$(document).ready(function(){
	init_data();
});
function init_data(){
	var list;
	var strTemp;
	$('#btnlist').empty();
	list=JSON.parse($('#buttons').val());
	$(list).each(function(i,item){
		strTemp="<tr>";
		strTemp+="<td width='5%'>";
		strTemp+="<input name='selbtn' type='radio' value='"+item.name+"' onclick='clickTable(this);'>";
		strTemp+="</td>";
		strTemp+="<td width='10%'>";
		strTemp+=item.name;
		strTemp+="</td>";
		strTemp+="<td width='45%'>";
		strTemp+=item.icon;
		strTemp+="</td>";
		strTemp+="<td width='20%'>";
		strTemp+=decodeURIComponent(item.jsclick).replace(/"/g, "&quot;").replace(/'/g, "&apos;");
		strTemp+="</td>";
		strTemp+="<td width='20%'>";
		strTemp+=item.order;
		strTemp+="</td>";
		strTemp+="</tr>";
		$('#btnlist').append(strTemp);
	});
}
function clickTable(input){
	var key;
	key=$(input).val();
	$('#hbutton').val(key);
}
function fn_add(id){
	openWin(800,300,'添加按钮','SF_201412250001.form?id='+id,null,null);
	return false;
}
function fn_edit(id){
	var list;
	var plist;
	var key;
	var btnObj;
	plist=[];
	key=$('#hbutton').val();
	if(''!=key)
	{
		list=JSON.parse($('#buttons').val());
		$(list).each(function(i,item){
			if(key == item.name)
			{
				btnObj=item;
				return false;
			}
		});
		openWin(800,300,'添加按钮','SF_201412250001.form?id='+id+"&btnid="+key,encodeURIComponent(JSON.stringify(btnObj)),null);
	}
	return false;
}
function fn_del(){
	var list;
	var plist;
	var key;
	plist=[];
	if(confirm("确定删除按钮?")==true)
	{
		key=$('#hbutton').val();
		if(''!=key)
		{
			list=JSON.parse($('#buttons').val());
			$(list).each(function(i,item){
				if(key != item.name)
				{
					plist.push(item);
				}
			});
			$('#buttons').val(JSON.stringify(plist));
			init_data();
		}
	}
	return false;
}
function fn_save(){
	var list;
	list=JSON.parse($('#buttons').val());
	parent.window.updateButton(list);
	return false;
}
function addButton(button){
	var list;
	list=JSON.parse($('#buttons').val());
	list.push(button);
	$('#buttons').val(JSON.stringify(list));
	init_data();
}
function editButton(button){
	var list;
	list=JSON.parse($('#buttons').val());
	$(list).each(function(i,item){
			if(button.btnid == item.name)
			{
				item.name=button.name;
				item.icon=button.icon;
				item.order=button.order;
				item.jsclick=button.jsclick;
				return false;
			}
	});
	$('#buttons').val(JSON.stringify(list));
	init_data();
}
