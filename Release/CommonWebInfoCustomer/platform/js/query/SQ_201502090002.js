function fn_deluser(){
	var strKey;
	strKey=fn_getchecked("id");
	if(""!=strKey)
	{
		fn_delTable('shiro','sc_user_role','id',strKey,function(msg){
			if(msg!=null)
			{
				alert('sucess'==msg.msg?"删除成功!":"删除失败!");
				window.location.reload();
			}
		});
	}
}

function fn_adduser(roleid)
{
	if(''!=roleid)
	{
		parent.parent.window.openWin(900,450,'角色配置','../form/SF_201502190003.form?roleid='+roleid,null,searchQuery);
	}
}

function searchQuery(){
	$('#searchButton').click();
}