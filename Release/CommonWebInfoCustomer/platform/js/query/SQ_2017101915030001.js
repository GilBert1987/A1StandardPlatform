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
function fn_delInfo(){
	var id;
	var strUrl;
	id=fn_getchecked("id");
	if(null!=id && ""!=id)
	{
		strUrl="../form/SF_2017103009590001.form?authority=SF_2017103009590001_readonly&inid="+id;
		parent.parent.parent.window.openWin(1100,550,'流程实例删除',strUrl,null,searchQuery);
	}
}