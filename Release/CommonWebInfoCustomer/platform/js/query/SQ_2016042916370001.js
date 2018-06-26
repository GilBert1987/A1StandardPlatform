function fn_addTag(){
	parent.openWin(1100,550,'添加控件','../form/SF_2016050117110001.form',null,searchQuery);
}
function fn_editTag(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.openWin(1100,550,'编辑控件','../frame/SR_2016042916470001.frame?id='+strKey,null,searchQuery);
	}
	else
	{
		alert("请选择控件!");
	}
}