function fn_delControl(){
	var numInfo;
	var blInfo;
	blInfo=true;
	numInfo=($("#cn_count").val()==''?0:Number($("#cn_count").val()));
	if(0!=numInfo)
	{
		alert("控件不为0不能删除!");
		blInfo=false;
	}
	return blInfo;
}