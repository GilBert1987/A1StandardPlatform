function fn_selClass(){
	var strKey;
	var teacherId;
	teacherId=fn_getchecked("teacherid");
	strKey=fn_getchecked("id");
	if(strKey !=null && strKey !='' && null!=teacherId && teacherId !='')
	{
		parent.window.document.getElementById("framedown").src="../form/SF_201504120003.form?classid="+strKey+"&teachid="+teacherId;
	}
}
function searchQuery(){
	$('#searchButton').click();
}