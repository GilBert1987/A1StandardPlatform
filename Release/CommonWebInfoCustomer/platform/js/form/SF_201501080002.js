$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var key;
	var list;
	var cutid;
	cutid=$('#cutid').val();
	if(''!=cutid)
	{
		list = JSON.parse($('#listShortCuts').val());
		$(list).each(function(i,eleitem) {
			if(cutid==eleitem.code)
			{
				$('#code').val(eleitem.code);
				$('#name').val(eleitem.name);
				$('#pic').val(eleitem.pic);
				$('#url').val(eleitem.url);
				$('#order').val(eleitem.order);
				return false;
			}
		});
	}
	else
	{
		key=createKey("shiro","sc_menu_shortcut","id");
		$('#code').val(key);
	}
}
function fn_save(){
	var code;
	var name;
	var pic;
	var url;
	var order;
	var list;
	var cutid;
	var blInfo;
	blInfo=false;
	code=$('#code').val();
	name=$('#name').val();
	pic=$('#pic').val();
	url=$('#url').val();
	order=$('#order').val();
	cutid=$('#cutid').val();
	if(code!="" && NaN != Number(order))
	{
		list = JSON.parse($('#listShortCuts').val());
		$(list).each(function(i,eleitem) {
			if(cutid==eleitem.code)
			{
				return true;
			}
			if(code==eleitem.code)
			{
				blInfo=true;
				return false;
			}
		});
		if(false==blInfo)
		{
			
			if(""!=cutid)
			{
				$(list).each(function(i,eleitem) {
					if(cutid==eleitem.code)
					{
						eleitem.code=code;
						eleitem.name=name;
						eleitem.pic=pic;
						eleitem.url=url;
						eleitem.order=order;
					}
				});
			}
			else
			{
				list.push({"code":code,"name":name,"pic":pic,"url":url,"order":order});
			}
			if(existKey("shiro","sc_menu_shortcut","code",code,"code",cutid)==false || existKey("shiro","sc_menu_submenu","code",code,"code",cutid)==false)
			{
				alert("权限项不能重复!");
			}
			else
			{
				parent.window.updateCuts(list);
			}
		}
	}
	else
	{
		if(code =="")
		{
			alert("编号不能为空!");
		}
		if(NaN == Number(order))
		{
			alert("请正确输入排序编号!");
		}
	}
	return false;
}
