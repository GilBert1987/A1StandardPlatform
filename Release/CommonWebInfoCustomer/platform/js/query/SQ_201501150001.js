function fn_delpostuser(){
	var strKey;
	if(confirm("是否删除职务用户!")==true)
	{
		strKey=fn_getchecked("id");
		if(strKey !=null && strKey !='')
		{
			fn_delTable('shiro','sc_user_post','id',strKey,function(msg){
				if(msg!=null)
				{
					alert('sucess'==msg.msg?"删除成功!":"删除失败!");
					parent.window.location.reload();
				}
			});
		}
		else
		{
			alert("请选择用户!");
		}
	}
}