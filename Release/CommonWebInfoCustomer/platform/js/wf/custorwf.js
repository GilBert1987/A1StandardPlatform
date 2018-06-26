function setFormdata(list){
	var i;
	var code;
	var inputValue;
	var inputType;
	var inputName;
	i=0;
	if(null==list)
	{
		list=[];
	}
	else
	{
		$(list).each(function(i,ele){
			if("code"==$(ele).attr("key"))
			{
				$("#code").val($(ele).attr("value"));
			}
			if("name"==$(ele).attr("key"))
			{
				$("#name").val($(ele).attr("value"));
			}
		});
	}
	for(i=0;i<document.forms[0].elements.length;i++){
		inputValue=$(document.forms[0].elements[i]).val();
		inputType=$(document.forms[0].elements[i]).attr('coltype');
		inputName=$(document.forms[0].elements[i]).attr('name');
		inputType=(inputType==null?'S':inputType);
		if(inputName!=null && '' != inputName && 'undefined' != inputName)
		{
			list.push({"key":inputName,"type":inputType,"value":inputValue});
		}
	}
	$("#wfUserList_table tbody tr").each(function(trindex, trinfo) {
		var userhidid;
		var userValue;
		userhidid=$(trinfo).attr("userhidid");
		userValue=$("#"+userhidid).val();
		list.push({"key":userhidid,"type":"S","value":userValue});
    	});
	if(""==$("#code").val()){
		code=createKey("wf","act_re_url","code");
		$("#code").val(code);
	}
	$('#formdata').val(JSON.stringify(list));
	return true;
}

function viewImage(){
	var strKey;
	var wfid;
	var tkid;
	wfid=$("#processkey").val();
	tkid=$("#tkform").val();
	strKey=$("#inid").val();
	window.openWin(1100,570,'工作流浏览','../frame/SR_201510170001.frame?inid='+strKey+"&wfid="+wfid+"&tkid="+tkid,null,null);
	return false;
}

function saveSubPage(){
	var newId;
	var state;
	var blInfo;
	var businesskey;
	state=$("#subpagestate").val();
	if(state=='begin')
	{
		businesskey=$("#businesskey").val();
		if(""==businesskey)
		{
			newId=createKey("wf","act_busskey","id");
			$("#businesskey").val(newId);
		}
		document.getElementById("Iframework").contentWindow.btnSubmit("btnsave");
		if($("#subpagestate").val()=='begin')
		{
			$("#subpagestate").val('run');
		}
	}
	return true;
}

function fn_stopSave(){
	$("#subpagestate").val('begin');
	return false;
}

function setWFStart(btnId){
	var key;
	var blInfo;
	var isSubmit;
	blInfo=false;
	isSubmit=$("#_btnSubmit").val();
	if("0"==isSubmit)
	{
		key=createKey("com","sc_redis","id");
		$("#_btnKey").val(key);
		$("#_btnId").val(btnId);
		if(true==fn_saveBtnStatus(key,btnId,"0")){
			fn_checkBtnStatus(key);
		}
		else
		{
			$("#_btnId").val("");
			$("#_btnKey").val("");
			alert("创建按钮动作更新失败!");
		}
	}
	else
	{
		$("#_btnId").val("");
		$("#_btnKey").val("");
		$("#_btnState").val("0");
		blInfo=true;
	}
	return blInfo;
}
function fn_Permit(valPer){
	$("#_submitInfo").val(valPer);
	return true;
}
function checkWFUser(){
	var blInfo;
	blInfo=true;
	$("#wfUserList_table tbody tr").each(function(trindex, trinfo) {
		var isCheck;
		var userhidid;
		var type;
		var userValue;
		var usertype;
		var taskname;
        isCheck=$(trinfo).attr("ischeck");
		userhidid=$(trinfo).attr("userhidid");
		type=$(trinfo).attr("type");
		taskname=$(trinfo).attr("taskname");
		usertype=$(trinfo).attr("usertype");
		if("1"==isCheck){
			userValue=$("#"+userhidid).val();
			if(""==userValue)
			{
				alert(taskname+"节点人员不能为空!")
				blInfo=false;
				return false;
			}
			if("1"==type && "1"==usertype)
			{
				if(-1!=userValue.indexOf(","))
				{
					alert(taskname+"节点人员不能为多人!")
					blInfo=false;
					return false;
				}
			}
		}
    });
	return blInfo;
}