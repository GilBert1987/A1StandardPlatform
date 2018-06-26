function viewWF(url){
	var strKey;
	strKey=fn_getchecked("url");
	if(strKey !=null && strKey !='')
	{
		strKey=strKey.replace(/&amp;/g,"&");
		parent.parent.window.openWin(1100,550,'办结的流程',url+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}