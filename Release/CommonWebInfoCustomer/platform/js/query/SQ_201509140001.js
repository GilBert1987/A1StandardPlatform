function fn_add(){
	var url;
	url='../form/SF_201509140001.form';
	openWin(1100,550,'文件类型',url,null,searchQuery);
}
function fn_edit(){
	var id;
	var url;
	url='../form/SF_201509140001.form';
	id=fn_getchecked("id");
	if(id !=null && id !='')
	{
		url=url+"?id="+id;
		openWin(1100,550,'文件类型',url,null,searchQuery);
	}
}
function searchQuery(){
	$('#searchButton').click();
}