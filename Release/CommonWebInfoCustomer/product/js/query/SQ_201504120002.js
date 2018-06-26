function fn_addTeacher(){
	openWin(1100,550,'添加教师','../form/SF_201504120002.form',null,searchQuery);
}
function fn_editTeacher(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		openWin(1100,550,'编辑教师','../form/SF_201504120002.form?id='+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}