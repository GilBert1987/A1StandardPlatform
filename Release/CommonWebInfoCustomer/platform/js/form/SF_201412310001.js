$(document).ready(function(e) {
    init_data();
});

function init_data(){
	var list;
	var strTemp;
	list=JSON.parse($('#listNode').val());
	$(list).each(function(i,item){
		if(""!=item.code)
		{
			strTemp="<tr>";
			strTemp+="<td width='10%'>";
			strTemp+="<input name='selnode' type='radio' value='"+item.level+"' onclick='clickTable(this);'>";
			strTemp+="</td>";
			strTemp+="<td  width='20%'>";
			strTemp+=item.level;
			strTemp+="</td>";
			strTemp+="<td  width='70%'>";
			strTemp+=item.name;
			strTemp+="</td>";
		 	strTemp+="</tr>";
		 	$('#tblist').append(strTemp);
		}
	});
}
function clickTable(input){
	$('#hselItem').val($(input).val());
}
function fn_add(id){
	$('#hselItem').val('');
	openWin(1000,400,'树形节点设置','SF_201501040001.form?id='+id,null,null);
	return false;
}
function fn_edit(id){
	var key;
	key=$('#hselItem').val();
	if(''!=key){
		openWin(1000,400,'树形节点设置','SF_201501040001.form?id='+id+'&key='+key,null,null);
	}
	return false;
}
function updateNodes(items){
	$('#listNode').val(JSON.stringify(items));
	resizeWin();
	$('#btnsave').click();
}
function fn_del(){
	var key;
	var list;
	var newlist;
	newlist=[];
	key=$('#hselItem').val();
	if(""!=key)
	{
		list=JSON.parse($('#listNode').val());
		$(list).each(function(i,item){
			if(key!=item.level)
			{
				newlist.push(item);
			}
		});
		$('#listNode').val(JSON.stringify(newlist));
		resizeWin();
		$('#btnsave').click();
	}
	return false;
}
function fn_save(){
	var name;
	var blInfo;
	blInfo=false;
	name=$('#name').val();
	if(""!=name)
	{
		blInfo=true;
	}
	return blInfo;
}