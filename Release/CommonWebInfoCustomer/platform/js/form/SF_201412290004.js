$(document).ready(function(e) {
	var key;
	var initdata;
	var jsonObj;
	initdata=decodeURIComponent(parent.$("#hinitdata").val());
	if(null!=initdata && "undefined"!=initdata && ""!=initdata)
	{
		jsonObj=JSON.parse(initdata);
		$('#code').val(jsonObj.code);
		$('#name').val(jsonObj.name);
		$('#url').val(jsonObj.url);
		$('#order').val(jsonObj.order);
		$("#icon").val("{\"btnclass\":\""+jsonObj.btnclass+"\",\"style\":\""+jsonObj.iconstyle+"\",\"icon\":\""+jsonObj.icon+"\"}");
	}
	else
	{
		key=createKey("com","sc_redis","id");
		$('#code').val(key);
		$("#icon").val("{\"btnclass\":\"\",\"style\":\"\",\"icon\":\"\"}");
	}
});
function fn_save(){
	var code;
	var name;
	var url;
	var order;
	var jsonObj;
	var btnclass;
	var icon;
	var iconstyle;
	code=$('#code').val();
	name=$('#name').val();
	url=$('#url').val();
	order=$('#order').val();
	icon="";
	iconstyle="";
	btnclass="";
	if($("#icon").val()!='')
	{
		jsonObj=JSON.parse($("#icon").val());
		btnclass=jsonObj.btnclass;
		icon=jsonObj.icon;
		iconstyle=jsonObj.style;
	}
	if(''!=code && ''!=name && ''!=url && NaN!=Number(order))
	{
		parent.window.updateItem({"order":Number(order),"name":name,"code":code,"url":url,"btnclass":btnclass,"icon":icon,"iconstyle":iconstyle});
	}
	return false;
}