function setWFFormdata(){
	var iframes;
	iframes=document.getElementById('Iframework').contentWindow.document;
	if(iframes.forms[0].elements.length!=0)
	{
		//$("#code").val($(iframes).find("select[id=teacherId]").find("option:selected").val());
		//$("#code").val($("#businesskey").val());
		$("#code").val($(iframes).find("input[id=FI_ID]").val());
		$("#name").val($(iframes).find("select[id=teacherId]").find("option:selected").text());
	}
	return true;
}


