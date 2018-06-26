function add(typeid){
	parent.parent.parent.window.openWin(1100,550,'框架信息','../form/SF_201412310002.form?typeid='+typeid,null,searchQuery);
}
function edit(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.window.openWin(1100,550,'框架信息','../form/SF_201412310002.form?id='+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}