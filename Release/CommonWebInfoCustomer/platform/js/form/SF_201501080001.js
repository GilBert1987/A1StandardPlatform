$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var cutlist;
	var jsonList;
	var menulist;
	var strTemp;
	var setting;
	var treeObj;
	jsonList=[];
	setting={
		view: {
			selectedMulti: false
		}
	};
	strTemp="";
	$('#cutlist').empty();
	$('#menulist').empty();
	cutlist=JSON.parse($('#listShortCuts').val());
	menulist=JSON.parse($('#listSubMenus').val());
	cutlist.sort(function(a,b){return a.order-b.order;});
	$(cutlist).each(function(i,cutitem) {
        strTemp+='<tr>';
		strTemp+='<td width="10%">';
		strTemp+='<input type="radio" name="cutsel" value="'+cutitem.code+'" onclick="fn_clickTable(this,0);">';
		strTemp+='</td>';
		strTemp+='<td width="10%">';
		strTemp+=cutitem.code;
		strTemp+='</td>';
		strTemp+='<td width="20%">';
		strTemp+=cutitem.name;
		strTemp+='</td>';
		strTemp+='<td width="20%">';
		strTemp+=cutitem.pic;
		strTemp+='</td>';
		strTemp+='<td width="30%">';
		strTemp+=cutitem.url;
		strTemp+='</td>';
		strTemp+='<td width="10%">';
		strTemp+=cutitem.order;
		strTemp+='</td>';
		strTemp+='</tr>';
    });
	$('#cutlist').append(strTemp);
	jsonList=fn_getJsonMenuList(jsonList,'-1',0,menulist);
	treeObj=$.fn.zTree.getZTreeObj("tree");
	if(null==treeObj)
	{
		$.fn.zTree.init($("#tree"), setting, [{"code":"root","name":"根目录","level":-1,"urlInfo":"","order":1,open:true,children:jsonList,click:"fn_clickTree('-1','根目录','1','',1);"}]);
	}
	else
	{
		treeObj.destroy();
		$.fn.zTree.init($("#tree"), setting, [{"code":"root","name":"根目录","level":-1,"urlInfo":"","order":1,open:true,children:jsonList,click:"fn_clickTree('-1','根目录','1','',1);"}]);
	}
}

function fn_getJsonMenuList(jsonList,parCode,num,list){
	var menu;
	var strInfo;
	var menulist;
	var childlist;
	num+=1;
	strInfo='';
	menulist=[];
	list.sort(function(a,b){return a.order-b.order;});
	$(list).each(function(i,item){
		if(item.code!=parCode)
		{
			menulist.push(item);
		}
	});
	for(var i=0;i<list.length;i++)
	{
		menu=list[i];
		if(parCode==menu.parentcode)
		{
			childlist=[];
			jsonList.push({"code":menu.code,"name":menu.name,"level":num,"urlInfo":menu.url,"order":menu.order,open:true,children:childlist,click:"fn_clickTree('"+menu.code+"','"+menu.name+"','"+num+"','"+menu.url+"','"+menu.order+"');"});
			fn_getJsonMenuList(childlist,menu.code,num,menulist);
		}
	}
	return jsonList;
}
function fn_delcut(){
	var cutlist;
	var type;
	var clickvalue;
	var list;
	list=[];
	clickvalue=$('#hvalue').val();
	type=$('#htype').val();
	cutlist=JSON.parse($('#listShortCuts').val());
	if('0'==type && ''!=clickvalue)
	{
		$(cutlist).each(function(i,eleitem) {
            if(clickvalue!=eleitem.code)
			{
				list.push(eleitem);
			}
        });
		$('#listShortCuts').val(JSON.stringify(list));
		init_data();
	}
	return false;
}
function fn_clickTree(code,name,num,url,order){
	$('#hvalue').val(code);
	$('#htype').val(1);
	$('#hlevel').val(num);
}
function fn_clickTable(input,type)
{
	$('#hvalue').val($(input).val());
	$('#htype').val(type);
	$('#hlevel').val($(input).attr('level'));
}
function fn_delmenu(){
	var menulist;
	var type;
	var clickvalue;
	var list;
	var blInfo;
	list=[];
	blInfo=false;
	clickvalue=$('#hvalue').val();
	type=$('#htype').val();
	menulist=JSON.parse($('#listSubMenus').val());
	if('1'==type && ''!=clickvalue && '-1'!=clickvalue)
	{
		$(menulist).each(function(i,eleitem) {
			if(clickvalue==eleitem.parentcode)
			{
				blInfo=true;
				return false;
			}
		});
		if(blInfo==false)
		{
			$(menulist).each(function(i,eleitem) {
				if(clickvalue!=eleitem.code)
				{
					list.push(eleitem);
				}
			});
			$('#listSubMenus').val(JSON.stringify(list));
			init_data();
		}
		else
		{
			alert('菜单有子项不能删除!');
		}
	}
	else{
		if('root'==clickvalue)
		{
			alert('根节点不能删除!');
		}
		if(''==clickvalue || '1'!=type)
		{
			alert('请选择菜单');
		}
	}
	return false;
}
function fn_addcut(id){
	openWin(800,300,'添加工具项','SF_201501080002.form?id='+id,null,null);
	return false;
}
function fn_addmenu(id){
	var type;
	var clickvalue;
	var level;
	level=$('#hlevel').val();
	clickvalue=$('#hvalue').val();
	type=$('#htype').val();
	if('1'==type && ''!=clickvalue && '3'!=level)
	{
		openWin(800,300,'添加菜单项','SF_201501080003.form?id='+id+'&pid='+clickvalue,null,null);
	}
	else
	{
		if('3'==level)
		{
			alert('3级菜单不能再建子菜单!');
		}
		else
		{
			alert('请选择菜单!');
		}
	}
	return false;
}
function updateCuts(list){
	var action;
	$('#listShortCuts').val(JSON.stringify(list));
	action=$('#pageForm').attr('action')
	if(action.indexOf('SF_201501080001_save')==-1)
	{
		$('#btnsave').click();
	}
	else
	{
		$('#btnsaveinfo').click();
	}
}
function updateMenus(list){
	var action;
	$('#listSubMenus').val(JSON.stringify(list));
	action=$('#pageForm').attr('action')
	if(action.indexOf('SF_201501080001_save')==-1)
	{
		$('#btnsave').click();
	}
	else
	{
		$('#btnsaveinfo').click();
	}
}
function fn_editcut(id){
	var type;
	var clickvalue;
	clickvalue=$('#hvalue').val();
	type=$('#htype').val();
	if('0'==type && ''!=clickvalue)
	{
		openWin(800,300,'编辑工具项','SF_201501080002.form?id='+id+'&cutid='+clickvalue,null,null);
	}
	return false;
}
function fn_editmenu(id){
	var type;
	var clickvalue;
	clickvalue=$('#hvalue').val();
	type=$('#htype').val();
	if('1'==type && ''!=clickvalue && '-1'!=clickvalue)
	{
		openWin(800,300,'编辑菜单项','SF_201501080003.form?id='+id+'&menuid='+clickvalue,null,null);
	}
	return false;
}