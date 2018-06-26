function fn_view(){
	var url;
	var strUrl;
	url=fn_getchecked("url");
	if(url !=null && url !='')
	{
		strUrl="../.."+url;
		parent.parent.parent.window.openWin(1100,550,'流程表单',strUrl,null,searchQuery);
	}
}
