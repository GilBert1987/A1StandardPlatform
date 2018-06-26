function fn_add(){
	parent.parent.window.openWin(1100,550,'平台信息','../form/SF_201501080001.form',null,searchQuery);
}
function fn_edit(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.window.openWin(1100,550,'平台信息','../form/SF_201501080001.form?id='+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}