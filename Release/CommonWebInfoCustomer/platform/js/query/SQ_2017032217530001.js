function fn_sendWF(){
	var strKey;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		parent.parent.parent.openWin(1100,600,'发起流程','../workflow/'+strKey+'.wf',null,searchQuery);
	}
	else
	{
		alert("请选择流程!");
	}
}