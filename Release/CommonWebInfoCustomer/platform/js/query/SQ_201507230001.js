function fn_editQuartz(groupid){
	var strKey;
	var strGroup;
	var strName;
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='')
	{
		strGroup=fn_getchecked("JOB_GROUP");
		strName=fn_getchecked("JOB_NAME");
		if(strGroup!=''&&strName!='')
		{
			parent.parent.parent.window.openWin(1100,550,'计划调度','../form/SF_201507240001.form?authority=SF_201507240001_edit&group='+strGroup+'&name='+strName,null,searchQuery);
		}
	}
	else
	{
		parent.parent.parent.window.openWin(1100,550,'计划调度','../form/SF_201507240001.form?authority=SF_201507240001_add&group='+groupid,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}