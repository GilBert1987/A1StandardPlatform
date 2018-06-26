function fn_editWFInfo(typeid){
	var strKey;
	var strWFDeployMentId;
	var strJsonName;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='' && null != typeid && '' !=typeid)
	{
		strWFDeployMentId=fn_getchecked("DEPLOYMENT_ID_");
		strJsonName=fn_getchecked("jsonname");
		if(strWFDeployMentId!=''&&strJsonName!='')
		{
			parent.parent.parent.window.openWin(1100,550,'流程信息','../form/SF_2017110610590001.form?typeid='+typeid+'&id='+strWFDeployMentId+'&jsonname='+strJsonName,null,searchQuery);
		}
	}
	else
	{
		alert("请选择版本!");
	}
}
function searchQuery(){
	$('#searchButton').click();
}