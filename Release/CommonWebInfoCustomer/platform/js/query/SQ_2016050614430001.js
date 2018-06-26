function fn_addLogic(type){
	parent.parent.parent.openWin(1100,550,'添加逻辑','../form/SF_2016050615010001.form?type='+type,null,searchQuery);
}
function fn_editLogic(type){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.openWin(1100,550,'编辑逻辑','../tree/SE_2016050911330001.treelist?type='+type+'&id='+strKey,null,searchQuery);
	}
	else
	{
		alert("请选择列表项!");
	}
}