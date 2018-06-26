function fn_add(typeid){
	parent.parent.parent.window.openWin(1100,600,'报表配置','../form/SF_2017111512000001.form?authority=SF_2017111512000001_add&typeid='+typeid,null,searchQuery);
}
function fn_edit(typeid){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.window.openWin(1100,600,'报表配置','../form/SF_2017111512000001.form?authority=SF_2017111512000001_edit&id='+strKey+'&typeid='+typeid,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}