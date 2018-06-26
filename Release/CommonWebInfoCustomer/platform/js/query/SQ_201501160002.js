function fn_roleadd(){
	openWin(1100,250,'添加角色','../form/SF_201501160002.form',null,searchQuery);
}
function fn_roleedit(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		openWin(1100,250,'编辑角色','../form/SF_201501160002.form?id='+strKey,null,searchQuery);
	}
	else
	{
		alert("请选择角色!");
	}
}
function fn_roledel(){
	var strKey;
	if(confirm("是否删除角色!")==true)
	{
		strKey=fn_getchecked("id");
		if(strKey !=null && strKey !='')
		{
			fn_delTable('shiro','sc_role','id',strKey,function(msg){
				if(msg!=null)
				{
					alert('sucess'==msg.msg?"删除成功!":"删除失败!");
					searchQuery();
				}
			});
		}
		else
		{
			alert("请选择角色!");
		}
	}
}
function searchQuery(){
	$('#searchButton').click();
}