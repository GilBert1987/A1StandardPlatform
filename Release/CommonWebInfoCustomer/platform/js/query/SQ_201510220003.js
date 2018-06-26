function viewWF(url){
	var strKey;
	strKey=fn_getchecked("url");
	if(strKey !=null && strKey !='')
	{
		strKey=strKey.replace(/&amp;/g,"&");
		parent.parent.parent.window.openWin(1100,550,'树形信息',url+strKey,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}
function fn_wfclaim(taskId,userId)
{
	$.ajax({ 
		url: "../../ajax/common/wfclaim", 
		type: "POST",
		data: "taskId="+taskId+"&userId="+userId,
		dataType: "json",
		async:true,
		success: function(msg){
					searchQuery();
		}
	});
}