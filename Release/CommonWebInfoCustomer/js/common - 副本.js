
function parseJSON( data ) { 
	if (typeof data !== "string" || !data ) { 
		return null; 
	} 
	data = jQuery.trim( data ); 
	if ( /^[\],:{}\s]*$/.test(data.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@") 
	.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]") 
	.replace(/(?:^|:|,)(?:\s*\[)+/g, "")) )
	{ 
		return window.JSON && window.JSON.parse ? window.JSON.parse( data ) : (new Function("return " + data))(); 
	} else { 
		jQuery.error( "Invalid JSON: " + data ); 
	} 
}

$(document).ready(function(){
	document.domain=document.domain;
	//document.domain=location.hostname;
});
function closeWin(){
	parent.window.clickCloseButton();
}
function resizeWin(){
	if(0!=parent.$(".webox").size())
	{
		parent.$('.webox').css({left:parent.$("#hleft").val(),top:parent.$("#htop").val(),width:parent.$("#hwidth").val(),height:parent.$("#hheight").val(),position:"absolute"});
		parent.$(".w_iframe").css({left:parent.$("#hiframeleft").val(),top:parent.$("#hiframetop").val(),width:parent.$("#hiframewidth").val(),height:parent.$("#hiframeheight").val()});
		parent.$('.webox').css("background-color","#fff");
		parent.$('.webox').css("box-shadow","0 0 40px rgba(0,0,0,.5)");
		parent.$('#inside').css("display","block");
		parent.$("#framebody").css("background-color","#eee");
		$("body").attr("style","background-color:#fff;");
		$("head").append("<style>body::before{content:\"\";display:block;position:fixed;top:0;bottom:0;left:0;right:0;z-index:-1;background-color:#eee;}</style>");
		$("#pageBody").css("display","block");
	}
}

function clickCloseButton(){
	$('#wbclose').trigger('click');
}

function existKey(sysId,table,key,value,perkey,id)
{
	var blInfo;
	blInfo=false;
	$.ajax({
		url:"../../ajax/common/existKey",
		type: "POST",
		data: {"sysName":sysId,"table":table,"key":key,"value":value,"perKey":perkey,"perValue":id},
		dataType: "json",
		async: false,
		success: function(blValue){
			blInfo=blValue;
		}
	});
	return blInfo;
}

function createKey(sysId,table,colInfo)
{
	var strId;
	strId="";
	$.ajax({
		url:"../../ajax/common/createKey",
		type: "POST",
		data: {"sysName":sysId,"table":table,"colInfo":colInfo},
		dataType: "json",
		async: false,
		success: function(obj){
			strId=obj.msg;
		}
	});
	return strId;
}
function fn_getPath(){
	var index;
	var result;
	var pathName; 
	pathName = document.location.pathname;
	index = pathName.lastIndexOf("/");
	result = pathName.substr(0,index+1);
	return result;
}
function fn_autoIframeHeight(iframeId,blMaxHeight){
	var ifm;
	var subWeb;
	var height;
	height=0;
	ifm=$("#"+iframeId);
	subWeb = document.frames ? document.frames[iframeId].document: ifm[0].contentDocument;
	if(ifm != null && subWeb != null) {
		height=subWeb.body.scrollHeight;
		if(true==blMaxHeight && null!=document.body.scrollHeight && document.body.scrollHeight>height)
		{
			height=document.body.scrollHeight;
		}
		ifm.css("height",height);
    }
}
function openWin(widthInfo,heightInfo,titleInfo,url,initInfo,callbackInfo){
	var blHasParent;
	var parentDiv;
	var backDiv;
	var parentIframe;
	blHasParent=false;
	//判断是否上层还有弹出层
	if($(parent.document).find("#inside").size()!=0)
	{
		parentDiv=$(parent.document).find(".webox").get(0);
		backDiv=$(parent.document).find(".background").get(0);
		parentIframe=$(parent.document).find(".w_iframe").get(0);
		if(null!=backDiv)
		{
			$(parentDiv).css({top:"0px",left:"0px",width:"100%",height:$(backDiv).height()});
			$(parentIframe).css({top:"0px",left:"0px",width:"100%",height:$(backDiv).height()});
		}
		else
		{
			$(parentDiv).css({top:"0px",left:"0px",width:"100%",height:$($(parent.parent.document).find(".w_iframe").get(0)).css("height")});
			$(parentIframe).css({top:"0px",left:"0px",width:"100%",height:$($(parent.parent.document).find(".w_iframe").get(0)).css("height")});
		}
		$(parentDiv).css("background-color","transparent");
		$(parentDiv).css("box-shadow","none");
		$($(parentDiv).find("#inside").get(0)).css("display","none");
		$("body").css("background-color","transparent");
		$('head').append("<style>body::before{background-color:transparent;}</style>");
		$($(parentDiv).find("#framebody").get(0)).css("background-color","transparent");
		$("#pageBody").css("display","none");
		$.webox({
			height:heightInfo,
			width:widthInfo,
			bgvisibel:true,
			title:titleInfo,
			iframe:url,
			initdata:initInfo,
			callback:callbackInfo,
			addback:false
		});
	}
	else
	{
		$.webox({
			height:heightInfo,
			width:widthInfo,
			bgvisibel:true,
			title:titleInfo,
			iframe:url,
			initdata:initInfo,
			callback:callbackInfo,
			addback:true
		});
	}
}
function fn_saveBtnStatus(key,btnId,status)
{
	var blInfo;
	blInfo=false;
	$.ajax({
		url:"../../ajax/common/saveBtnStatus",
		type: "POST",
		data: {"key":key,"btnId":btnId,"status":status},
		dataType: "json",
		async: false,
		success: function(obj){
			if("success"==obj.msg)
			{
				blInfo=true;
			}
		}
	});
	return blInfo;
}
function fn_checkBtnStatus(key){
	$.ajax({
		url:"../../ajax/common/getBtnStatus",
		type: "POST",
		data: {"key":key},
		dataType: "json",
		success: function(obj){
			if("success"==obj.msg)
			{
				if("0"==obj.status)
				{
					setTimeout(function(){
						fn_checkBtnStatus(key);	
					},3000);
				}
				if("1"==obj.status)
				{
					$("#_btnSubmit").val("1");
					btnSubmit(obj.btnId);
				}
			}
			if("error"==obj.msg)
			{
				alert("按钮更新检测失败!");
			}
		}
	});
}

