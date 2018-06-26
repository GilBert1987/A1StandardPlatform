$(document).ready(function(){
	init_data();
});
function init_data(){
	var initData;
	var jsonData;
	initData=parent.$("#hinitdata").val();
	if(""!=initData){
		jsonData=JSON.parse(decodeURIComponent(initData));
		$('#name').val(jsonData.name);
		$('#icon').val(jsonData.icon);
		$('#order').val(jsonData.order);
		$('#jsclick').val(decodeURIComponent(jsonData.jsclick));
		$("#hbtnId").val(jsonData.name);
	}
}
function fn_save(){
	var name;
	var icon;
	var order;
	var jsclick;
	var jsonObj;
	var btnId;
	name=$('#name').val();
	icon=$('#icon').val();
	order=$('#order').val();
	jsclick=$('#jsclick').val();
	btnId=$("#hbtnId").val();
	if(''!=name)
	{
		if(btnId!='')
		{
			parent.window.editButton({"btnid":btnId,"name":name,"icon":icon,"order":order,"jsclick":encodeURIComponent(decodeURIComponent(jsclick))});
			closeWin();
		}
		else
		{
			parent.window.addButton({"name":name,"icon":icon,"order":order,"jsclick":encodeURIComponent(decodeURIComponent(jsclick))});
			closeWin();
		}
	}
	else
	{
		alert('请输入名称!');
	}
	return false;
}

