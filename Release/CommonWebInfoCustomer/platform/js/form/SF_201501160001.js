function fn_save(){
	var blInfo;
	var name;
	var level;
	var ps_index;
	blInfo=false;
	name=$('#name').val();
	level=$('#level').val();
	ps_index=$('#ps_index').val();
	if(''!=name && ''!=level && isNaN(Number(ps_index))==false)
	{
		blInfo=true;
	}
	else
	{
		if(''==name)
		{
			alert('职务名称不能为空!');
		}
		if(''==ps_index)
		{
			alert('职务排序不能为空且必须为数字!');
		}
	}
	return blInfo;
}
function fn_close(){
	closeWin();
}