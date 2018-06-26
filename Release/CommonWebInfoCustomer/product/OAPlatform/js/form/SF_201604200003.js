function fn_save(proid){
	var blInfo;
	var userid;
	blInfo=false;
	userid=$('#user_post')[0].contentWindow.fn_getchecked("id");
	$('#user_id').val(userid);
	$('#por_id').val(proid);
	if(''!=$('#user_id').val() && ''!= $('#por_id').val())
	{
		blInfo=true;
	}
	else
	{
		if(''==$('#user_id').val())
		{
			alert("请选择用户!");
		}
	}
	return blInfo;
}
function fn_reload(){
	setTimeout(parent.window.location.reload(),500);
}