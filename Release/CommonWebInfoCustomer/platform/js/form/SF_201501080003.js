$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var key;
	var list;
	var menuid;
	menuid=$('#menuid').val();
	if(''!=menuid)
	{
		list = JSON.parse($('#listSubMenus').val());
		$(list).each(function(i,eleitem) {
			if(menuid==eleitem.code)
			{
				$('#code').val(eleitem.code);
				$('#name').val(eleitem.name);
				$('#pic').val(eleitem.pic);
				$('#url').val(eleitem.url);
				$('#order').val(eleitem.order);
				$('#isshow').val(eleitem.isshow);
				$('#opentype').val(eleitem.opentype);
				$('#pid').val(eleitem.parentcode);
				return false;
			}
		});
	}
	else
	{
		key=createKey("shiro","sc_menu_submenu","id");
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
	var menuid;
	var blInfo;
	var isshow;
	var opentype;
	var parentcode;
	blInfo=false;
	code=$('#code').val();
	name=$('#name').val();
	pic=$('#pic').val();
	url=$('#url').val();
	order=$('#order').val();
	menuid=$('#menuid').val();
	isshow=$('#isshow').val();
	opentype=$('#opentype').val();
	parentcode=$('#pid').val();
	if(code!="" && NaN != Number(order) && parentcode!="")
	{
		list = JSON.parse($('#listSubMenus').val());
		$(list).each(function(i,eleitem) {
			if(menuid==eleitem.code)
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
			
			if(""!=menuid)
			{
				$(list).each(function(i,eleitem) {
					if(menuid==eleitem.code)
					{
						eleitem.code=code;
						eleitem.name=name;
						eleitem.pic=pic;
						eleitem.url=url;
						eleitem.order=order;
						eleitem.isshow=isshow;
						eleitem.opentype=opentype;
						eleitem.parentcode=parentcode;
					}
				});
				$(list).each(function(i,eleitem) {
					if(menuid==eleitem.parentcode)
					{
						eleitem.parentcode=code;
					}
				});
			}
			else
			{
				list.push({"code":code,"name":name,"pic":pic,"url":url,"order":order,"parentcode":parentcode,"isshow":isshow,"opentype":opentype});
			}
			if(existKey("shiro","sc_menu_shortcut","code",code,"code",menuid)==false || existKey("shiro","sc_menu_submenu","code",code,"code",menuid)==false)
			{
				alert("权限项不能重复!");
			}
			else
			{
				parent.window.updateMenus(list);
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
		if(""==parentcode)
		{
			alert("数据格式错误!");
		}
	}
	return false;
}
