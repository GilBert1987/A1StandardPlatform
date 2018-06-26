function fn_save(){
	var blInfo;
	var postid;
	blInfo=false;
	postid=$('#org_post')[0].contentWindow.fn_getchecked("id");
	$('#post_id').val(postid);
	if(''!=$('#org_id').val() && ''!= $('#post_id').val())
	{
		blInfo=true;
	}
	return blInfo;
}
function fn_close(){
	setTimeout(closeWin(),500);
}