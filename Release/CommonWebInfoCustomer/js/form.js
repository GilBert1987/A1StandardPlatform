var webUpload=[];
$(document).ready(function(){
	document.domain=location.hostname;
	$('#pageForm').validationEngine();
});
function fn_frameHeight(iframe) {
	var iheight;
	iheight=iframe.height;
	iheight=iheight.replace(/px/g,'');
	iheight=iheight.replace(/%/g,'');
	iheight=Number(iheight);
	$(iframe.contentDocument).find('.querytablebody').each(function(i,item){
		$(item).css('height',(iheight-100));
	});
}
function fn_delTabUploadFile(obj){
	if(true==confirm("确认是否删除文件!"))
	{
		$.ajax({
            type: "GET",
            url: "../upload/javaLargeFile",
            data: {"action":"clearFile", "fileId":$(obj).attr("keyid")},
            dataType: "json",
			error:function(msg){
				if(4==msg.readyState && 200==msg.status && "OK"==msg.statusText)
				{
					$(obj).parent().parent().remove();
				}
			}
         });
	}
}
function deleteElementsButEmAndCancel(fileId) {
    var div = $('#' + fileId);
    var em = div.children(":first").detach();
    var cancel = div.children(".cancel").detach();
    div.empty();
    div.append(em);
    div.append(cancel);
}
function fn_createWFId(cid){
	var id;
	var newId;
	var blInfo;
	newId='';
	blInfo=true;
	if($("#"+cid)[0]!=null)
	{
		id=$("#"+cid).val();
		if(id=='')
		{
			newId=createKey("wf","act_busskey","id");
			if(null !=newId && newId!="")
			{
				$("#"+cid).val(newId);
			}
			else
			{
				alert(cid+":主键生成失败!");
				blInfo=false;
			}
		}
	}
	else
	{
		alert(cid+":没有这个上传控件!");
		blInfo=false;
	}
	return blInfo;
}
function fn_clickCheckBox(chboxObj){
	var dataBind;
	var valInfo;
	valInfo="";
	dataBind=$(chboxObj).attr("dataBind");
	if(null!=dataBind && '' !=dataBind)
	{
		$("input[type='checkbox']").each(function(index,ele){
			if(dataBind==$(ele).attr("dataBind") && true==ele.checked){
				valInfo=(valInfo==""?$(ele).val():valInfo+","+$(ele).val());
			}
		});
		$($("input[name='"+dataBind+"']")[0]).val(valInfo);
	}
}

function fn_saveWFInfo(){
	var btnId;
	var blInfo;
	var btnKey;
	var btnState;
	var businesskey;
	var formdatalist;
	blInfo=false;
	btnState=$("#_btnState").val();
	formdatalist=fn_wfsubPagedata();
	businesskey=$("#FI_ID").val();
	parent.setFormdata(formdatalist);
	if("0"==btnState)
	{
		parent.$('#businesskey').val(businesskey);
		btnKey=parent.$("#_btnKey").val();
		btnId=parent.$("#_btnId").val();
		$("#FI_btnKey").val(btnKey);
		$("#FI_btnId").val(btnId);
		blInfo=true;
	}
	return blInfo;
}
function fn_getwfFiid()
{
	var fiid;
	fiid=parent.$('#businesskey').val();
	if(null != fiid && "" != fiid)
	{
		$("#FI_ID").val(fiid);
	}
	return true;
}
function fn_wfsubPagedata(){
	var i;
	var strValue;
	var inputValue;
	var inputType;
	var inputName;
	var list;
	i=0;
	strValue='';
	list=[];
	for(i=0;i<document.forms[0].elements.length;i++){
		inputValue=$(document.forms[0].elements[i]).val();
		inputType=$(document.forms[0].elements[i]).attr('coltype');
		inputName=$(document.forms[0].elements[i]).attr('name');
		inputType=(inputType==null?'S':inputType);
		inputType=(inputType==''?'S':inputType);
		if(null!=inputName && inputName!='')
		{
			list.push({"key":inputName,"type":inputType,"value":inputValue});	
		}
	}
	return list;
}
function fn_addWebUpdate(groupId){
	var $list;
	var filelist;
	var uploader;
	var grouopObj;
	var grouopAttr;
	filelist=[];
	grouopAttr=$("#_"+groupId+"_attribute").val();
	grouopAttr=decodeURIComponent(grouopAttr);
	grouopObj=JSON.parse(grouopAttr);
	$list = $("#"+groupId+"_table_filelist"); 
    uploader = WebUploader.create({ 
		auto:false, //是否自动上传
		pick: {
			id: '#'+groupId+'_upload',
			name:"multiFile", //这个地方 name 没什么用，和fileVal 配合使用。
			label: '点击选择文件',
			multiple:grouopObj.multiple//默认为true，true表示可以多选文件，HTML5的属性
		},
		swf: '../../js/webuploader/Uploader.swf',  //在这里必需要引入swf文件，webuploader初始化要用
		fileVal:grouopObj.groupId+'|multiFile',  //提交到到后台，是要用"multiFile"这个名称属性来取文件的
		server: "../../webUploader",
		duplicate:false,//是否可重复选择同一文件
		resize: false,
		chunked: true,  //分片处理
		chunkSize: 20 * 1024 * 1024, //每片20M
		chunkRetry:2,//如果失败，则不重试
		threads:2,//上传并发数。允许同时最大上传进程数。
		fileNumLimit:(true==grouopObj.multiple?0:1),//上传的文件总数
		// 禁掉全局的拖拽功能。
		disableGlobalDnd: true
	});  
	// 当有文件添加进来的时候
	uploader.on("fileQueued",function(file){
		var trObj;
		var trInfo;
		var tdInfo;
		var trSize;
		var fileKey;
		console.log("fileQueued:"+file);
		trSize=$list.find("tr").size();
		trInfo='<tr class="jqgrow ui-row-ltr" key="'+file.id+'" status="begin">';
		tdInfo='<td class="ui-td-column ui-td-ltr" width="80%">';
		tdInfo+=file.name;
		tdInfo+="&nbsp;<span class=\""+file.id+"_status\">待开始</span>";
		tdInfo+='</td>';
		if(0==trSize)
		{
			tdInfo+='<td class="ui-th-column ui-th-ltr" width="20%">';
			tdInfo+='<a href="#" onclick="fn_delUpdateFile(\''+groupId+'\');">清除重选</a>';
			tdInfo+='</td>';
		}
		trInfo+=tdInfo;
		trInfo+='</tr>';
		$list.append($(trInfo));
		if(trSize!=0)
		{
			trObj=$($list.find("tr")[0]);
			$(trObj.find("td")[1]).attr("rowspan",trSize+1);
			$(trObj.find("td")[1]).css("text-align","center");
			$(trObj.find("td")[1]).css("vertical-align","middle");
		}
		fileKey=createKey("com","sc_file","id");
		filelist.push({"groupId":grouopObj.groupId,"fileId":file.id,"fileKey":fileKey,"pathId":grouopObj.pathId,"multiple":(true==grouopObj.multiple?"1":"0")});
	});

       //当所有文件上传结束时触发
       uploader.on("uploadFinished",function(){
			var that;
			var btnId;
			var btnKey;
			var blInfo;
			var uploadObj;
			blInfo=true;
			if(null!=webUpload)
			{
				that=this;
				$(webUpload).each(function(i,ele) {
					uploadObj=ele.uploadObj;
					if(that.options.pick.id==uploadObj.options.pick.id)
					{
						ele.status="finish";
					}
				});
				$(webUpload).each(function(i,ele) {
					if(ele.status!="finish")
					{
						blInfo=false;
						return false;
					}
				});
				if(true==blInfo)
				{
					btnId=$("#_btnId").val();
					btnKey=$("#_btnKey").val();
					fn_saveBtnStatus(btnKey,btnId,"1");
				}
			}
       });
	   
	   //上传前
	   uploader.on("uploadBeforeSend",function(fileObj){
		   var file;
           console.log("uploadBeforeSend:"+fileObj);
		   file=fileObj.file;
		   $("."+file.id+"_status").html("上传中");
       });
       uploader.on("startUpload", function(){
		   var formData;
		   console.log("startUpload");
		   formData=this.option("formData");
		   formData.files=JSON.stringify(filelist);
		   this.option("formData",formData);
		});
       //当文件上传成功时触发。
       uploader.on("uploadSuccess", function( file ,response) {
		   console.log("uploadSuccess:"+file);
		   $("#"+groupId+"_table_filelist tr").each(function(i,ele) {
                if($(ele).attr("key")==file.id){
					$(ele).attr("status","end");
					$("."+file.id+"_status").html("已上传");
				}
            });
       });

       uploader.on("uploadError", function( file ) {
           $( "#"+file.id ).find("p.state").text("上传出错");
           uploader.cancelFile(file);
           uploader.removeFile(file,true);
           uploader.reset();
		   $("#_uplodeSubmit").val("1");
       });
	   webUpload.push({"groupId":groupId,"uploadObj":uploader,"status":"begin"});
}

function fn_delUpdateFile(groupId){
	var uploadObj;
	uploadObj=null;
	if(null!=webUpload)
	{
		$(webUpload).each(function(i, ele) {
            if(groupId==ele.groupId)
			{
				uploadObj=ele.uploadObj;
				return false;
			}
        });
		if(null!=uploadObj)
		{
			uploadObj.reset();
			$("#"+groupId+"_table_filelist tr").each(function(i,ele) {
                $(ele).remove();
            });
		}
	}
}
function fn_webUpLoadFile(btnId){
	var key;
	var blInfo;
	var isSubmit;
	var uploadObj;
	var submitBtn;
	var upfilelist;
	blInfo=false;
	upfilelist=[];
	isSubmit=$("#_btnSubmit").val();
	if("0"==isSubmit)
	{
		if(null != webUpload && 0 != $(webUpload).size())
		{
			key=createKey("com","sc_redis","id");
			$("#_btnKey").val(key);
			$("#_btnId").val(btnId);
			$(webUpload).each(function(i, ele) {
                uploadObj=ele.uploadObj;
				uploadObj.upload();
            });
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
			blInfo=true;
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
function fn_filedown(fileId){
	$("#_fileId").val(fileId);
	$("#fileDownLoad").submit();
}