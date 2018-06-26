function fn_changeUsers(){
	var strUrl;
	var taskIds;
	var taskObj;
	taskIds=fn_getchecked("id");
	if(taskIds !=null && taskIds !='')
	{
		strUrl="../form/SF_2017101015350001.form";
		$.ajax({
			url:"../../ajax/common/tempSave",
			type: "POST",
			data: {"info":taskIds},
			dataType: "json",
			success: function(msg){
				strUrl=strUrl+"?authority=SF_2017101015350001_edit&tempid="+msg.id;
				parent.parent.parent.window.openWin(1100,550,'流程代理人替换',strUrl,null,searchQuery)
			}
		});
	}
}
