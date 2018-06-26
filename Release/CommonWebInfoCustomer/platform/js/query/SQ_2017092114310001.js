function fn_add(){
	parent.parent.parent.window.openWin(1100,550,'主键生成规则','../form/SF_2017092114430001.form',null,searchQuery);
}
function fn_edit(){
	var strKey;
	strKey=fn_getchecked("CK_Id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.window.openWin(1100,550,'主键生成规则','../form/SF_2017092114430001.form?id='+strKey,null,searchQuery);
	}
}
