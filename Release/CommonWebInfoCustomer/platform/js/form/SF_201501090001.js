function fn_reload(id,pid)
{
	setTimeout(parent.document.Tree.reloadTree(),500);
	window.location.href='../form/SF_201501090001.form?id='+id+'&pid='+pid;
}
function fn_redelete(){
	setTimeout(parent.document.Tree.reloadTree(),500);
	window.location.href='../form/SF_201501090001.form?pid=1';
}
function fn_addchild(){
	var id;
	var blInfo;
	blInfo=false;
	id=$('#id').val();
	if(''!=id)
	{
		blInfo=true;
	}
	else
	{
		alert("请保存后再建子部门!");
	}
	return blInfo;
}
function fn_save(){
	var blInfo;
	blInfo=false;
	if(isNaN(Number($('#org_index').val()))==false && '' !=$('#org_index').val())
	{
		blInfo=true;
	}
	else{
		alert('请输入序号并确认是数字!');
	}
	return blInfo;
}
function fn_del(){
	var childNum;
	var blInfo;
	var orgnum;
	var postnum;
	blInfo=false;
	orgnum=$('#orgnum').children().val();
	postnum=$('#postnum').children().val();
	if('0'==orgnum && '0'==postnum)
	{
		blInfo=true;
	}
	else
	{
		if('0'!=orgnum)
		{
			alert('有子部门无法删除!');
		}
		if('0'!=postnum)
		{
			alert('有职务无法删除!');
		}
	}
	return blInfo;
}