function fn_postadd(){
	openWin(1100,250,'添加职务','../form/SF_201501160001.form',null,searchQuery);
}
function fn_postedit(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		openWin(1100,250,'编辑职务','../form/SF_201501160001.form?id='+strKey,null,searchQuery);
	}
	else
	{
		alert("请选择职务!");
	}
}
function fn_postdel(){
	var strKey;
	if(confirm("是否删除职务!")==true)
	{
		strKey=fn_getchecked("id");
		if(strKey !=null && strKey !='')
		{
			fn_delTable('shiro','sc_post','id',strKey,function(msg){
				if(msg!=null)
				{
					alert('sucess'==msg.msg?"删除成功!":"删除失败!");
					searchQuery();
				}
			});
		}
		else
		{
			alert("请选择职务!");
		}
	}
}
function searchQuery(){
	$('#searchButton').click();
}