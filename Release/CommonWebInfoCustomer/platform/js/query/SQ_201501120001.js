function fn_addpost(id){
	if(''!=id)
	{
		parent.parent.parent.window.openWin(1100,550,'添加职务','../form/SF_201501120001.form?orgid='+id,null,function(){
			window.location.reload();
		});
	}
	else
	{
		alert("请保存部门,再配置职务信息!");
	}
}
function fn_postUser(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.window.openWin(1100,550,'职务用户选择','../form/SF_2017092117010001.form?porid='+strKey,null,searchQuery);
	}
	else
	{
		alert("请选择职务!");
	}
}
function fn_delpost(){
	var strKey;
	var usercount;
	if(confirm('是否删除职务?')==true)
	{
		strKey=fn_getchecked("id");
		usercount=fn_getchecked("usercount");
		if(strKey !=null && strKey !='' && '0'==usercount)
		{
			fn_delTable('shiro','sc_post_org','id',strKey,
				function(msg){
					if(msg!=null)
					{
						alert('sucess'==msg.msg?"删除成功!":"删除失败!");
						window.location.reload();
					}
				});
		}
		else
		{
			if('0'!=usercount)
			{
				alert('职务用户不为0,无法删除职务!');
			}
		}
	}
}