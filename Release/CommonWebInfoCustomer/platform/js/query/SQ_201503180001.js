function adduser(){
	openWin(600,350,'添加用户信息','../form/SF_201503180002.form',null,searchQuery);
}
function edituser(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		openWin(600,350,'修改用户信息','../form/SF_201503180002.form?id='+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}