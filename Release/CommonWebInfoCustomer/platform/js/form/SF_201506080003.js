$(document).ready(function(e) {
	var hidID;
	var hinitdata;
	var hinitObj;
	var controls;
	var elecontrol;
	hinitdata="";
	hidID=$('#hidID').val();
	$('#iID').val(hidID);
	if(null!=parent.document.getElementById("hinitdata"))
	{
		hinitdata=parent.document.getElementById("hinitdata").value;
	}
	if(""!=hinitdata)
	{
		hinitdata=decodeURIComponent(hinitdata);
		hinitObj=JSON.parse(hinitdata);
		$('#hiddens').val(decodeURIComponent(hinitObj.hiddens));
		$('#controls').val(decodeURIComponent(hinitObj.controls));
	}
	else
	{
		$('#hiddens').val('[]');
		$('#controls').val('[]');
	}
	if(""!=hidID){
		controls=JSON.parse(decodeURIComponent(hinitObj.controls));
		$('#iID').attr("readonly","readonly");
		$(controls).each(function(index, ele) {
			elecontrol=ele.control[0];
			if(hidID==elecontrol.id)
			{
            	$("#iBackType").val(elecontrol.backtype==null?"":elecontrol.backtype);
				$("#iDataBind").val(elecontrol.dataBind==null?"":elecontrol.dataBind);
				$("#iValue").val(elecontrol.value==null?"":elecontrol.value);
			}
        });
	}
});

function fn_checkControl(hid){
	var blInfo;
	var consText;
	var consList;
	var hiddenId;
	hiddenId=$('#hidID').val();
	blInfo=true;
	consText=$('#controls').val();
	consList=null;
	if(""==hiddenId)
	{
		if(""!=consText){
			consList=JSON.parse(consText);
		}
		if(null!=consList){
			$(consList).each(function(index, ele) {
				if(hid==ele.control[0].id)
				{
					blInfo=false;
					return false;
				}
			});
		}
	}
	return blInfo;
}

function saveButton(){
	var hId;
	var blInfo;
	var hiddens;
	var controls;
	hId=$('#iID').val();
	blInfo=fn_checkControl(hId);
	if(true==blInfo)
	{
		hiddens=JSON.parse($('#hiddens').val());
		controls=JSON.parse($('#controls').val());
		if(""==$('#hidID').val())
		{
			hiddens.push({"id":hId});
			controls.push({"control":[{"id":hId,"type":"hidden","backtype":$("#iBackType").val(),"dataBind":$("#iDataBind").val(),"value":encodeURIComponent(decodeURIComponent($("#iValue").val())).replace(/"/g, "&quot;").replace(/'/g, "&apos;")}]});
		}
		else
		{
			$(controls).each(function(index, elecontrol) {
                if(elecontrol.control[0].id==hId)
				{
					elecontrol.control[0].type="hidden";
					elecontrol.control[0].backtype=$("#iBackType").val();
					elecontrol.control[0].dataBind=$("#iDataBind").val();
					elecontrol.control[0].value=encodeURIComponent(decodeURIComponent($("#iValue").val())).replace(/"/g, "&quot;").replace(/'/g, "&apos;");
					return false;
				}
            });
		}
		parent.window.fn_savehidden(hiddens,controls);
		closeWin();
	}
	else
	{
		alert("主键不唯一!");
	}
	return false;
}