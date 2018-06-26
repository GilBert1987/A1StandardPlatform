function openmodel(typeid){
	var strKey;
	strKey=fn_getchecked("ID_");
	if(strKey !=null && strKey !='' && null!=typeid && ''!=typeid)
	{
		parent.parent.parent.openWin(1100,550,'流程模型','../form/SF_2017102908550001.form?id='+strKey+'&typeid='+typeid,null,searchQuery);
	}
}
function newmodel(typeid){
	if(null!=typeid && ''!=typeid)
	{
		parent.parent.parent.openWin(1100,550,'流程模型','../form/SF_2017102908550001.form?typeid='+typeid,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}