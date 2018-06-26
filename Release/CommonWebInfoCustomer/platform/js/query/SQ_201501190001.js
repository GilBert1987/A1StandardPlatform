function fn_selplatform(roleid){
	var strKey;
	strKey=fn_getchecked("id");
	if($(parent.window.document).find('#framedown').size()==1 && ""!=strKey)
	{
		$(parent.window.document).find('#framedown').attr('src','../form/SF_201501190001.form?authority=SF_201501190001_edit&roleid='+roleid+'&menuid='+strKey);
	}
}
