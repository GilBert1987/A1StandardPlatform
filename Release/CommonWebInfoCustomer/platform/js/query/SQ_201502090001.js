function fn_selrole(){
	var strKey;
	strKey=fn_getchecked("id");
	if($(parent.window.document).find('#framedown').size()==1 && ""!=strKey)
	{
		$(parent.window.document).find('#framedown').attr('src','/CommonWebInfo/platform/query/SQ_201502090002.query?roleid='+strKey);
	}
}
