function fn_addClass(){
	openWin(1100,550,'添加教学班','../form/SF_201504120001.form',null,searchQuery);
}
function fn_editClass(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		openWin(1100,550,'编辑教学班','../form/SF_201504120001.form?id='+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}