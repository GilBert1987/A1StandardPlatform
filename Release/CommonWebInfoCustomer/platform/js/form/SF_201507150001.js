var wf;
$(document).ready(function(e) {
	var initData;
	var initWFData;
	var delCount;
	var paths;
	var rects;
	var props;
	var blInfo;
	var jsonObj;
	var forminfo;
	var width;
	var height;
	var htaskuserlist;
	var jsonTaskuserlist;
	rects=[];
	paths=[];
	props={};
	delCount=0;
	jsonObj=null;
	forminfo=null;
	jsonTaskuserlist=null;
	if(""!=$("#hprops").val())
	{
		props=JSON.parse($("#hprops").val());
	}
	if(''!=$("#hInitWFData").val())
	{
		try
		{
			initWFData=JSON.parse($("#hInitWFData").val());
		}
		catch (e)
		{
			console.log(e);
		}
		if(null!=initWFData)
		{
			if(null!=initWFData.bounds)
			{
				if(null!=initWFData.bounds.lowerRight)
				{
					width=(null==initWFData.bounds.lowerRight.x?$('#holder').width():initWFData.bounds.lowerRight.x);
					height=(null==initWFData.bounds.lowerRight.y?$('#holder').height():initWFData.bounds.lowerRight.y);
				}
			}
			jsonObj={
				"process_id": initWFData.properties.process_id,
				"name":initWFData.properties.name,
				"documentation":initWFData.properties.documentation,
				"process_author": "",
				"process_version": "",
				"process_namespace": $("#hCategory").val(),
				"executionlisteners":initWFData.properties.executionlisteners,
				"eventlisteners":"",
				"signaldefinitions": "",
        		"messagedefinitions": "",
        		"process_potentialstarteruser": "",
        		"process_potentialstartergroup": "",
				"width":width,
				"height":height
			};
			$("#hmaxCount").val(initWFData.maxCount);
			$("#hKey").val(initWFData.properties.process_id);
			$("#hinitData").val(JSON.stringify(jsonObj));
			htaskuserlist=$("#htaskuserlist").val();
			if(null!=htaskuserlist && ""!=htaskuserlist)
			{
				jsonTaskuserlist=JSON.parse(htaskuserlist);
			}
			$(initWFData.childShapes).each(function(i,eleObj) {
				blInfo=false;
                if("StartNoneEvent"==eleObj.stencil.id)
				{
					rects.push({"id":eleObj.resourceId,"type":"start","left":eleObj.bounds.upperLeft.x,"top":eleObj.bounds.upperLeft.y,"text":eleObj.properties.name,"properties":eleObj.properties});
					blInfo=true;
				}
				if("UserTask"==eleObj.stencil.id)
				{
					if(null!=jsonTaskuserlist)
					{
						$(jsonTaskuserlist).each(function(taskUserI, taskUserEle) {
                            if(eleObj.resourceId==taskUserEle.id)
							{
								eleObj.properties.usertaskassignment.items=taskUserEle.userlist;
								eleObj.properties.usercheck=taskUserEle.usercheck;
								eleObj.properties.usertype=taskUserEle.usertype;
								eleObj.properties.userselother=taskUserEle.userselother;
								return false;
							}
                        });
					}
					rects.push({"id":eleObj.resourceId,"type":"userTask","left":eleObj.bounds.upperLeft.x,"top":eleObj.bounds.upperLeft.y,"text":eleObj.properties.name,"properties":eleObj.properties});
					blInfo=true;
				}
				if("ExclusiveGateway"==eleObj.stencil.id)
				{
					rects.push({"id":eleObj.resourceId,"type":"fork","left":eleObj.bounds.upperLeft.x,"top":eleObj.bounds.upperLeft.y,"text":eleObj.properties.name,"properties":eleObj.properties});
					blInfo=true;
				}
				if("ParallelGateway"==eleObj.stencil.id)
				{
					rects.push({"id":eleObj.resourceId,"type":"join","left":eleObj.bounds.upperLeft.x,"top":eleObj.bounds.upperLeft.y,"text":eleObj.properties.name,"properties":eleObj.properties});
					blInfo=true;
				}
				if("EndNoneEvent"==eleObj.stencil.id)
				{
					rects.push({"id":eleObj.resourceId,"type":"end","left":eleObj.bounds.upperLeft.x,"top":eleObj.bounds.upperLeft.y,"text":eleObj.properties.name,"properties":eleObj.properties});
					blInfo=true;
				}
				if(blInfo==true)
				{
					$(eleObj.outgoing).each(function(index, element) {
						paths.push({"from":eleObj.resourceId,"to":element.resourceId});
					});
				}
            });
			$(initWFData.childShapes).each(function(i,eleObj) {
				if("SequenceFlow"==eleObj.stencil.id)
				{
					$(paths).each(function(index, element) {
                        if(eleObj.resourceId==element.from || eleObj.resourceId==element.to)
						{
							if(eleObj.resourceId==element.from)
							{
								element.from=eleObj.outgoing[0].resourceId;
							}
							if(eleObj.resourceId==element.to)
							{
								element.to=eleObj.outgoing[0].resourceId;
							}
							element.dots=eleObj.dots;
							element.text=eleObj.properties.name;
							element.properties=eleObj.properties;
						}
                    });
				}
			});
		}
	}
	initData={"rects":rects,"paths":paths,"props":props};
	initData=getDataInfo(initData);
	if(null==width)
	{
		width=$('#holder').width();
	}
	if(null==height)
	{
		height=$('#holder').height();
	}
	$('#holder').width(width);
	$('#holder').height(height);
	wf=new $.WorkFlow();
	wf.setDivObj($('#holder')[0]);
	wf.setWidth(width);
	wf.setHeight(height);
	wf.setInitData(initData);
	wf.setClickRect(clickRect);
	wf.setSelPath(selPath);
	wf.init();
	init();
});
function init(){
	var initData;
	$("#pointer").click(function(){
		var initInfo;
		$("#pointer").css("font-weight","bold");
		$("#pointer").css("background","#cccccc 50% 50% repeat-x");
		$("#pointer").css("border","1px solid #aaaaaa");
		$("#path").css("font-weight","");
		$("#path").css("background","");
		$("#path").css("border","");
		$("#hseltype").val("0");
		$("#myflow_props").empty();
		createTableIntoDiv('baseInfo');
		initInfo=$("#hinitData").val();
		modifyInitData(JSON.parse(initInfo));
	});
	$("#path").click(function(){
		$("#path").css("font-weight","bold");
		$("#path").css("background","#cccccc 50% 50% repeat-x");
		$("#path").css("border","1px solid #aaaaaa");
		$("#pointer").css("font-weight","");
		$("#pointer").css("background","");
		$("#pointer").css("border","");
		$("#hseltype").val("1");
		$("#hselobj").val('{"from":"","to":""}');
	});
	$("#myflow_save").click(fn_saveWfData);
	$("#myflow_del").click(fn_delWfData);
	$("#myflow_forcedel").click(fn_forcedelWfData);
	$("#pointer").trigger("click");
	$(".node").hover(
		function () {
			$(this).addClass("mover"); //移入
		},
		function () {
			$(this).removeClass("mover");//移除
	});
	$(".state").draggable({"revert":true,"helper": "clone","opacity":0.35,"stop":stopDrag});
	$("#myflow_props").empty();
	createTableIntoDiv('baseInfo');
	initInfo=$("#hinitData").val();
	modifyInitData(JSON.parse(initInfo));
}
function fn_delMsg(msg){
	var jsonData;
	if(msg!='')
	{
		jsonData=JSON.parse(msg);
		if(jsonData.isDel==true)
		{
			closeWin();
		}
		else
		{
			alert(jsonData.msg);
		}
	}
}
function fn_delWfData(){
	if(confirm("是否删除部署?")==true){
		$("#btnDel").click();
	}
}
function fn_forcedelWfData(){
	if(confirm("确认强制删除，强制删除会删除运行数据和历史数据?")==true)
	{
		$("#hforceDel").val('true');
		$("#btnForceDel").click();
	}
}
function fn_checkId(){
	var id;
	var blInfo;
	blInfo=false;
	id=$("#hId").val();
	if(""!=id)
	{
		blInfo=true;
	}
	else
	{
		alert("请先保存工作流!");
	}
	return blInfo;
}
function fn_formBase(){
	var temp;
	var initData;
	var btnlist;
	var conlist;
	var formlist;
	var hidlist;
	var jsinfo;
	var taginfo;
	var dynamicform;
	initData={};
	temp=$("#htemp").val();
	btnlist=$("#hbtnlist").val();
	conlist=$("#hconlist").val();
	formlist=$("#hformlist").val();
	hidlist=$("#hhidlist").val();
	jsinfo=$("#hjsinfo").val();
	taginfo=$("#htaginfo").val();
	dynamicform=$("#hdynamicform").val();
	if(""!=temp)
	{
		initData.temp=temp;
		if(""!=btnlist)
		{
			initData.btnlist=JSON.parse(btnlist);
		}
		if(""!=conlist)
		{
			initData.conlist=JSON.parse(conlist);
		}
		if(""!=formlist)
		{
			initData.formlist=JSON.parse(formlist);
		}
		if(""!=formlist)
		{
			initData.hidlist=JSON.parse(hidlist);
		}
		initData.jsinfo=jsinfo;
		if(""!=taginfo)
		{
			initData.taginfo=JSON.parse(taginfo);
		}
		initData.dynamicform=dynamicform;
		initData=encodeURIComponent(JSON.stringify(initData));
	}
	else
	{
		initData="{}";
	}
	openWin(1100,450,'表单信息列表','../form/SF_201505300002.form',initData,null);
}
function fn_saveInitData(jsonData)
{
	$("#hinitData").val(JSON.stringify(jsonData));
}
function fn_setBaseSet(){
	var baseInfo;
	var initData;
	var postData;
	initData=$("#hformlist").val();
	baseInfo=$("#hUserTaskBaseSet").val();
	postData=JSON.stringify({"initData":initData,"baseInfo":baseInfo});
	postData=encodeURIComponent(postData);
	openWin(1000,500,'基础信息设置','../form/SF_201506100004.form?authority=SF_201506100004_deployment',postData,null);
}
function fn_setUserSet(){
	var userInfo;
	var taskId;
	taskId=$("#overrideid").val();
	userInfo=encodeURIComponent($("#hUserTaskUserSet").val());
	openWin(1000,500,'用户信息设置','../form/SF_201506100002.form?taskid='+taskId,userInfo,null);
}
function fn_setRoleSet(){
	var roleInfo;
	var initData;
	var postData;
	initData=$("#hconlist").val();
	roleInfo=$("#hUserTaskRoleSet").val();
	postData=JSON.stringify({"initData":initData,"roleInfo":roleInfo});
	postData=encodeURIComponent(postData);
	openWin(1000,400,'权限信息设置','../form/SF_201506100003.form',postData,null);
}
function fn_saveBase(baseObj){
	$("#hUserTaskBaseSet").val(JSON.stringify(baseObj));
}
function fn_saveRole(roleObj){
	$("#hUserTaskRoleSet").val(JSON.stringify(roleObj));
}
function fn_saveUser(userObj){
	$("#hUserTaskUserSet").val(JSON.stringify(userObj));
}
function selPath(pathObj){
	var pathInfo;
	var tabInfo;
	pathInfo=$(pathObj).data("pathObj");
	if(null==pathInfo.properties)
	{
		pathInfo.properties={"overrideid":"","conditionsequenceflow":"","defaultflow":"","conditionalflow":""};
	}
	tabInfo='';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="40%">';
	tabInfo+='Id';
	tabInfo+='</td>';
	tabInfo+='<td width="60%">';
	tabInfo+='<input id="id" type="input" value="'+pathInfo.properties.overrideid+'" readonly="readonly" style="width:100px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="40%">';
	tabInfo+='原节点';
	tabInfo+='</td>';
	tabInfo+='<td width="60%">';
	tabInfo+='<input id="from" type="input" value="'+pathInfo.from+'" readonly="readonly" style="width:100px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="40%">';
	tabInfo+='目标节点';
	tabInfo+='</td>';
	tabInfo+='<td width="60%">';
	tabInfo+='<input id="to" type="input" value="'+pathInfo.to+'" readonly="readonly" style="width:100px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="40%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="60%">';
	tabInfo+='<input id="txt" type="input" value="'+pathInfo.text+'" style="width:100px;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='属性设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='流程线类型';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='<select id="linetype" style="width:100%;">';
	tabInfo+='<option value="0" '+("0"==pathInfo.properties.linetype?"selected='selected'":"")+'>直线</option>';
	tabInfo+='<option value="1" '+("1"==pathInfo.properties.linetype?"selected='selected'":"")+'>可调纵向折线</option>';
	tabInfo+='<option value="2" '+("2"==pathInfo.properties.linetype?"selected='selected'":"")+'>可调横向折线</option>';
	tabInfo+='</select>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='条件设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="conditionsequenceflow" type="input" value="'+decodeURIComponent(pathInfo.properties.conditionsequenceflow)+'" style="width:100%;" readonly="readonly"/>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='默认连线';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="defaultflow" type="input" value="'+pathInfo.properties.defaultflow+'" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='条件连线';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="conditionalflow" type="input" value="'+pathInfo.properties.conditionalflow+'" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	$("#myflow_props").empty();
	$("#myflow_props").append(tabInfo);
}

function fn_savePath(){
	var from;
	var to;
	var txt;
	var properties;
	properties={};
	properties.overrideid=$("#id").val();
	properties.name=$("#txt").val();
	properties.documentation="";
	properties.conditionsequenceflow=$("#conditionsequenceflow").val().replace(/"/g, "&quot;").replace(/'/g, "&apos;");;
	properties.defaultflow=$("#defaultflow").val();
	properties.conditionalflow=$("#conditionalflow").val();
	properties.linetype=$("#linetype").val();
	from=$("#from").val();
	to=$("#to").val();
	txt=$("#txt").val();
	wf.updatePath({"from":from,"to":to,"text":txt,"properties":properties});
}
function fn_delPath(){
	var from;
	var to;
	from=$("#from").val();
	to=$("#to").val();
	if(confirm("确定删除?")==true)
	{
		wf.delPath({"from":from,"to":to});
	}
}
function fn_saveWfData(){
	retPostData(postWFData);
}
function retPostData(callFun){
	var dataList;
	var eleList;
	var eleInfo;
	var phList;
	var phInfo;
	var initData;
	var postData;
	eleList=[];
	phList=[];
	dataList=wf.getDataList();
	initData=JSON.parse($("#hinitData").val());
	$(dataList).each(function(i,eleObj) {
		var rect;
		rect=eleObj.st[1].data("value");
		eleInfo={"rect":eleObj.st[0],"data":rect};
		eleList.push(eleInfo);
		if(null!=eleInfo.rect.arrPath)
		{
			$(eleInfo.rect.arrPath).each(function(ipath, elepath) {
				if(null!=$(elepath).data("pathObj"))
				{
					phInfo={"path":elepath,"data":$(elepath).data("pathObj")};
					phList.push(phInfo);
				}
            });
		}
	});
	if(""!=initData.name)
	{
		if(null==initData.process_id || ""==initData.process_id)
		{
			$.ajax({ 
				url: "../../ajax/common/createKey", 
				type: "POST",
				data: "sysName=wf&table=act_re_model&colInfo=id_",
				dataType: "json",
				async:false,
				success: function(msg){
					if(null!=msg && null!=msg.msg)
					{
						initData.process_id=msg.msg;
					}
				}
			});
		}
		if(true==fn_checkeleList(eleList) && "" != initData.process_id)
		{
			if(""==$("#hdynamicform").val())
			{
				alert("请配置表单信息");
				return;
			}
			postData=setPostData(initData,eleList,phList);
			if(null!=callFun && 'function'==typeof(callFun))
			{
				callFun({"svg_xml":"","type":"BPMN 2.0","parent":"","glossary_xml":"[]","namespace":"","views":"[]","json_xml":JSON.stringify(postData),"id":initData.id,"name":initData.name,"description":initData.documentation});
			}
		}
	}
	else
	{
		alert("请输入工作流名称或关键字生成失败!");
	}
}
function fn_checkeleList(eleList){
	var blInfo;
	var taskObj;
	taskObj=null;
	blInfo=true;
	$(eleList).each(function(index, eleObj) {
        if(null != eleObj.data && null != eleObj.data.type)
		{
			if("userTask"==eleObj.data.type.id)
			{
				if(null==eleObj.data.properties)
				{
					roleList=fn_getRoleList(eleObj.data.id);
					if(null==roleList || 0==$(roleList).size())
					{
						taskObj=eleObj;
						blInfo=false;
						return false;
					}
				}
			}
			if("start"==eleObj.data.type.id)
			{
				roleList=fn_getRoleList(eleObj.data.id);
				if(null==eleObj.data.properties || null==roleList || 0==$(roleList).size() || null==eleObj.data.properties.formkeydefinition || ""==eleObj.data.properties.formkeydefinition)
				{
					startObj=eleObj;
					blInfo=false;
					return false;
				}
			}
		}
	});
	if(false==blInfo && null != taskObj)
	{
		alert("请检查用户任务:{id:"+taskObj.data.id+",name:"+taskObj.data.text+"}相关设置!");
	}
	return blInfo;
}
function postWFData(wfData){
	$("#hName").val(wfData.name);
	$("#hDescription").val(wfData.description);
	$("#hJsonXml").val(wfData.json_xml);
	$("#btnSave").click();
}

function setPostData(initData,eleList,pathList){
	var postData;
	var childShapes;
	childShapes=[];
	postData={};
	postData.resourceId="canvas";
	postData.properties=initData;
	postData.stencil={"id":"BPMNDiagram"};
	postData.childShapes=childShapes;
	postData.bounds={"lowerRight": {"x": 1485,"y": 1050},"upperLeft": {"x": 0,"y": 0}};
    postData.stencilset={"url": "./stencilsets/bpmn2.0/bpmn2.0.json","namespace": "http://b3mn.org/stencilset/bpmn2.0#"};
	postData.ssextensions=[];
	postData.maxCount=Number($("#hmaxCount").val());
	postData.initTime=$("#hlastModifyTime").val();
	$(eleList).each(function(i,ele) {
        var childData;
		var eleData;
		var eleRect;
		var shareObj;
		var outgoing;
		outgoing=[];
		eleRect=ele.rect;
		eleData=ele.data;
		childData={};
		childData.resourceId=eleData.id;
		childData.properties=(eleData.properties==null?{}:eleData.properties);
		childData.properties.name=eleData.text;
		childData.childShapes=[];
		childData.dockers=[];
		shareObj=eleRect.getBBox();
		childData.bounds={"lowerRight":{"x":Math.round(shareObj.x2),"y":Math.round(shareObj.y2)},"upperLeft": {"x":Math.round(shareObj.x),"y":Math.round(shareObj.y)}};
		if("start"==eleData.type.id)
		{
			childData.stencil={"id":"StartNoneEvent"};
		}
		if("userTask"==eleData.type.id)
		{
			childData.stencil={"id":"UserTask"};
		}
		if("fork"==eleData.type.id)
		{
			childData.stencil={"id":"ExclusiveGateway"};
		}
		if("join"==eleData.type.id)
		{
			childData.stencil={"id":"ParallelGateway"};
		}
		if("end"==eleData.type.id)
		{
			childData.stencil={"id":"EndNoneEvent"};
		}
		childData.outgoing=outgoing;
		$(pathList).each(function(ipath,elepath) {
            if(elepath.data.from==eleData.id)
			{
				outgoing.push({"resourceId":elepath.data.properties.overrideid});
			}
        });
		childShapes.push(childData);
    });
	$(pathList).each(function(i,ele) {
        var childData;
		var eleData;
		var elePath;
		var shareObj;
		var outgoing;
		var dockers;
		outgoing=[];
		elePath=ele.path;
		eleData=ele.data;
		shareObj=elePath.getBBox();
		dockers=fn_comptyPoint(eleData,eleList,shareObj);
		childData={};
		childData.resourceId=eleData.properties.overrideid;
		childData.properties=(eleData.properties==null?{}:eleData.properties);
		childData.properties.conditionsequenceflow=encodeURIComponent(decodeURIComponent(eleData.properties.conditionsequenceflow));
		childData.stencil={"id":"SequenceFlow"};
		childData.childShapes=[];
		childData.outgoing=outgoing;
		childData.bounds={"lowerRight":{"x":Math.round(shareObj.x2),"y":Math.round(shareObj.y2)},"upperLeft": {"x":Math.round(shareObj.x),"y":Math.round(shareObj.y)}};
		childData.dots=eleData.dots;
		childData.dockers=dockers;
		outgoing.push({"resourceId":eleData.to});
		childData.target={"resourceId":eleData.to};
		childShapes.push(childData);
    });
	return postData;
}
//计算dockers的点
function fn_comptyPoint(eleData,eleList,pathObj)
{
	var dockers;
	var fromObj;
	var toObj;
	dockers=[];
	fromObj=null;
	toObj=null;
	$(eleList).each(function(i,ele) {
		if(ele.data.id==eleData.from)
		{
			fromObj=ele.data.type;
		}
		if(ele.data.id==eleData.to)
		{
			toObj=ele.data.type;
		}
	});
	if(null!=fromObj && null!=toObj)
	{
		if(fromObj.id=="start")
		{
			dockers.push({"x":15,"y":15});
		}
		if(fromObj.id=="end")
		{
			dockers.push({"x":14,"y":14});
		}
		if(fromObj.id=="join")
		{
			dockers.push({"x":21,"y":21});
		}
		if(fromObj.id=="fork")
		{
			dockers.push({"x":21,"y":21});
		}
		if(fromObj.id=="userTask")
		{
			dockers.push({"x":50,"y":40});
		}
		if(toObj.id=="start")
		{
			dockers.push({"x":15,"y":15});
		}
		if(toObj.id=="end")
		{
			dockers.push({"x":14,"y":14});
		}
		if(toObj.id=="join")
		{
			dockers.push({"x":21,"y":21});
		}
		if(toObj.id=="fork")
		{
			dockers.push({"x":21,"y":21});
		}
		if(toObj.id=="userTask")
		{
			dockers.push({"x":50,"y":40});
		}
	}
	return dockers;
}
//拖拽结束创建对象
function stopDrag(event, ui){
	var xInfo;
	var yInfo;
	var type;
	var text;
	var typeObj;
	var typeData;
	var jsonObj;
	var maxCount;
	var properties;
	if(''!=$('#htype').val())
	{
		typeData=JSON.parse($('#htype').val());
	}
	xInfo=event.pageX;
	yInfo=event.pageY;
	typeObj=null;
	if(xInfo>=180 && xInfo<=1000)
	{
		xInfo=xInfo-180;
		type=$(this).attr("type");
		text=$(this).attr("text");
		$(typeData).each(function(index, ele) {
			if(ele.id==type)
			{
           		typeObj=ele;
				return false;
			}
        });
		if(null!=typeObj)
		{
			maxCount=Number($("#hmaxCount").val());
			maxCount=maxCount+1;
			properties={};
			if("start"==typeObj.id){
				properties.overrideid="ST_"+maxCount;
				properties.name=text;
				properties.documentation="";
				properties.formproperties="";
				properties.initiator="";
				properties.formkeydefinition="";
				properties.executionlisteners="";
			}
			if("end"==typeObj.id){
				properties.overrideid="ED_"+maxCount;
				properties.name=text;
				properties.documentation="";
				properties.executionlisteners="";
			}
			if("join"==typeObj.id){
				properties.overrideid="JI_"+maxCount;
				properties.name=text;
				properties.documentation="";
			}
			if("fork"==typeObj.id){
				properties.overrideid="FK_"+maxCount;
				properties.name=text;
				properties.documentation="";
			}
			if("userTask"==typeObj.id){
				properties.overrideid="UT_"+maxCount;
				properties.name=text;
				properties.formkeydefinition="";
				properties.asynchronousdefinition="No";
				properties.exclusivedefinition="Yes";
				properties.looptype="None";
				properties.multiinstance_sequential="Yes";
				properties.isforcompensation="false";
				properties.documentation="";
				properties.duedatedefinition="";
				properties.prioritydefinition="";
				properties.usertype="0";
				properties.usercheck="1";
				properties.userselother="0";
				properties.usertaskassignment={
                    "totalCount": 3,
                    "items": [
                        {
                            "assignment_type": "assignee",
                            "resourceassignmentexpr": "",
                            "name": ""
                        },
                        {
                            "assignment_type": "candidateUsers",
                            "resourceassignmentexpr": "",
                            "name": ""
                        },
                        {
                            "assignment_type": "candidateGroups",
                            "resourceassignmentexpr": "${candidateGroupsId}",
                            "name": ""
                        }
                    ]
                };
				properties.formproperties="";
				properties.tasklisteners="";
				properties.multiinstance_cardinality="";
				properties.multiinstance_collection="";
				properties.multiinstance_variable="";
				properties.multiinstance_condition="";
			}
			jsonObj={"id":properties.overrideid,"type":typeObj,"left":xInfo,"top":yInfo,"text":text};
			jsonObj.properties=properties;
			wf.addRect(jsonObj);
			$("#hmaxCount").val(maxCount);
		}
	}
}
function clickRect(){
	var imgObj;
	var txtObj;
	var rectObj;
	var selType;
	var selObj;
	var pathList;
	var blPath;
	var maxCount;
	if("image"==this.type)
	{
		imgObj=this;
		txtObj=this.next;
	}
	if("text"==this.type)
	{
		imgObj=this.prev;
		txtObj=this;
	}
	rectObj=imgObj.data("value");
	selType=$("#hseltype").val();
	if("0"==selType)
	{
		$("#myflow_props").empty();
		createTableIntoDiv(rectObj.type.id);
		modifyTable(rectObj,txtObj);
	}
	if("1"==selType)
	{
		selObj=JSON.parse($("#hselobj").val());
		if(""==selObj.from)
		{
			selObj.from=rectObj.id;
			if("end"!=rectObj.type.id){
				$("#hselobj").val(JSON.stringify(selObj));
			}
			else{
				$("#pointer").trigger("click");
			}
		}
		else
		{
			blPath=true;
			if(selObj.from!=rectObj.id)
			{
				selObj.to=rectObj.id;
				pathList=wf.getPathList();
				$(pathList).each(function(index, ele) {
					if(selObj.to==ele.to && selObj.from==ele.from)
					{
						blPath=false;
						return false;
					}
				});
				if(true==blPath && "start"!=rectObj.type.id)
				{
					maxCount=Number($("#hmaxCount").val());
					selObj.dots=[];
					selObj.text="";
					selObj.properties={"name":"","overrideid":"SF_"+(maxCount+1),"conditionsequenceflow":"","defaultflow":"None","conditionalflow":"None"};
					wf.addPath(selObj);
					$("#hmaxCount").val(maxCount+1);
				}
				$("#hselobj").val('{"from":"","to":""}');
				$("#pointer").trigger("click");
			}
		}
		
	}
}
function createTableIntoDiv(typeid){
	var tabInfo;
	tabInfo='';
	switch(typeid)
	{
		case 'start':
			tabInfo=createStart();
			break;
		case 'userTask':
			tabInfo=createUserTask();
			break;
		case 'fork':
			tabInfo=createFork();
			break;
		case 'join':
			tabInfo=createJoin();
			break;
		case 'end':
			tabInfo=createEnd();
			break;
		case 'baseInfo':
			tabInfo=createBaseInfo();
			break;
	};
	$("#myflow_props").append(tabInfo);
}
function createStart(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="overrideid" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='<input id="hStartRoleSet" type="hidden" value="" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="txt" type="input" value="" style="width:150px;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='属性设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='表单发起人';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="initiator" type="input" value="" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<table width="100%">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='表单设置';
	tabInfo+='</td>';
	tabInfo+='<td>';
	tabInfo+='表单权限';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<table width="100%">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='开始表单';
	tabInfo+='</td>';
	tabInfo+='<td rowspan="4">';
	tabInfo+='<input type="button" value="表单权限" onclick="fn_setStartRoleSet();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<select coltype="" id="startFormkey">';
	tabInfo+='</select>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='结束表单';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<select coltype="" id="endFormkey">';
	tabInfo+='</select>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='表单发起监听';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="executionlisteners" type="input" value="" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%" style="text-align:center;">';
	tabInfo+='<input type="button" id="btnSave" value="保存" onclick="fn_saveStart();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function fn_setStartRoleSet(){
	var roleInfo;
	var initData;
	var postData;
	initData=$("#hconlist").val();
	roleInfo=$("#hStartRoleSet").val();
	postData=JSON.stringify({"initData":initData,"roleInfo":roleInfo});
	postData=encodeURIComponent(postData);
	openWin(1000,400,'权限信息设置','../form/SF_201603100002.form',postData,null);
}
function fn_saveStartRole(roleObj){
	$("#hStartRoleSet").val(JSON.stringify(roleObj));
}
function createFork(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="overrideid" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="txt" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='属性设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='描述';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="documentation" type="input" value="" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function createJoin(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="overrideid" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="txt" type="input" value="" style="width:150px;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='描述';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="documentation" type="input" value="" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function createBaseInfo(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<input id="hcontrols" type="hidden" value="" />';
	tabInfo+='<input id="hjsinfo" type="hidden" value="" />';
	tabInfo+='<input id="hfiledir" type="hidden" value="" />';
	tabInfo+='<input id="hinclude" type="hidden" value="" />';
	tabInfo+='<input id="hhiddenform" type="hidden" value="" />';
	tabInfo+='<input id="hdynamicform" type="hidden" value="" />';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="key" type="input" value="" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='名称';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="name" type="input" value=""  readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='描述';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="documentation" type="input" value=""  readonly="readonly" style="width:150px;" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td colspan="2">';
	tabInfo+='全局表单监控';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td colspan="2">';
	tabInfo+='<textarea id="executionlisteners" type="input" style="width:100%;height:30px"></textarea>';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='宽度';
	tabInfo+='</td>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='<input id="width" type="input" value="" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='高度';
	tabInfo+='</td>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='<input id="height" type="input" value="" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='表单绑定';
	tabInfo+='</td>';
	tabInfo+='<td style="width:50%;">';
	tabInfo+='<input id="formButton" type="button" value="表单绑定" onclick="fn_formBase();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%" style="text-align:center;">';
	tabInfo+='<input type="button" id="btnSave" value="保存" onclick="fn_saveBaseInfo();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function createEnd(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="overrideid" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="txt" type="input" value="" style="width:150px;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='流程结束监听';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td>';
	tabInfo+='<input id="executionlisteners" type="input" value="" style="width:100%;" readonly="readonly">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function fn_baseSave(){
	var id;
	var txt;
	var jsonObj;
	id=$("#id").val();
	txt=$("#txt").val();
	jsonObj={"id":id,"text":txt};
	return jsonObj;
}
function fn_delete(){
	var id;
	id=$("#id").val();
	if(confirm("确定是否删除?")==true)
	{
		wf.delRect(id);
	}
}
function fn_saveStart(){
	var jsonObj;
	var properties;
	var roleInfo;
	properties={};
	roleInfo=$("#hStartRoleSet").val();
	if(""!=$("#startFormkey").val() && ""!=$("#endFormkey").val() && ""!=roleInfo && "[]"!=roleInfo)
	{
		jsonObj=fn_baseSave();
		roleData=JSON.parse(roleInfo);
		properties.overrideid=$("#overrideid").val();
		properties.name=jsonObj.text;
		properties.documentation="";
		properties.formproperties="";
		properties.initiator=$("#initiator").val();
		properties.formkeydefinition=encodeURIComponent("{\"startcode\":\""+$("#startFormkey").val()+"\",\"endcode\":\""+$("#endFormkey").val()+"\"}");
		properties.executionlisteners=$("#executionlisteners").val();
		properties.controlrole=roleData;
		jsonObj.properties=properties;
		jsonObj.type={"id":"start"};
		jsonObj.txt=jsonObj.text;
		$("#hstartcode").val(jsonObj.id);
		fn_saveNodeRole(jsonObj.id,roleData);
		fn_saveNodeFormList(jsonObj.id,{"startcode":$("#startFormkey").val(),"endcode":$("#endFormkey").val()});
		wf.updateRect(jsonObj);
	}
	else
	{
		alert("请检查表单和权限!");
	}
}
function fn_saveFork(){
	var jsonObj;
	var properties;
	properties={};
	jsonObj=fn_baseSave();
	properties.overrideid=$("#overrideid").val();
	properties.name=jsonObj.text;
	properties.documentation=$("#documentation").val();
	jsonObj.properties=properties;
	jsonObj.type={"id":"fork"};
	jsonObj.txt=jsonObj.text;
	wf.updateRect(jsonObj);
}
function fn_saveJoin(){
	var jsonObj;
	var properties;
	properties={};
	jsonObj=fn_baseSave();
	properties.overrideid=$("#overrideid").val();
	properties.name=jsonObj.text;
	properties.documentation=$("#documentation").val();
	jsonObj.properties=properties;
	jsonObj.type={"id":"join"};
	jsonObj.txt=jsonObj.text;
	wf.updateRect(jsonObj);
}
function fn_saveEnd(){
	var jsonObj;
	var properties;
	properties={};
	jsonObj=fn_baseSave();
	jsonObj.type={"id":"end"};
	properties.overrideid=$("#overrideid").val();
	properties.name=jsonObj.text;
	properties.documentation="";
	properties.executionlisteners=$("#executionlisteners").val();
	jsonObj.properties=properties;
	jsonObj.txt=jsonObj.text;
	wf.updateRect(jsonObj);
}
function modifyTable(rectObj,txtObj){
	var properties;
	var baseData;
	var roleData;
	var userData;
	var userInfo;
	var initData;
	var selOption;
	var jsonFormkeydefinition;
	baseData={};
	roleData={};
	userData={};
	userInfo={};
	selOption='';
	$("#id").val(rectObj.id);
	$("#txt").val(txtObj.attr("text"));
	properties=rectObj.properties;
	if(null!=properties)
	{
		if("start"==rectObj.type.id)
		{
			if(""!=$("#hformlist").val())
			{
				initData=JSON.parse($("#hformlist").val());
				if(null!=initData)
				{
					$(initData).each(function(index, eleform) {
						selOption+=("<option value=\""+eleform.code+"\">"+eleform.code+"</option>");
                	});
					$("#startFormkey").append(selOption);
					$("#endFormkey").append(selOption);
				}
			}
			$("#initiator").val((null == properties.initiator ? "" : properties.initiator));
			$("#executionlisteners").val((null == properties.executionlisteners ? "" : properties.executionlisteners));
			if(null!=properties.formkeydefinition && ""!= properties.formkeydefinition)
			{
				if(null!=properties.formkeydefinition && ""!= properties.formkeydefinition)
				{
					jsonFormkeydefinition=JSON.parse(decodeURIComponent(properties.formkeydefinition));
					if(null!=jsonFormkeydefinition.startcode && ""!= jsonFormkeydefinition.startcode)
					{
						$("#startFormkey").val(jsonFormkeydefinition.startcode);
					}
					if(null!=jsonFormkeydefinition.endcode && ""!= jsonFormkeydefinition.endcode)
					{
						$("#endFormkey").val(jsonFormkeydefinition.endcode);
					}
				}
			}
			roleData=fn_getRoleList(rectObj.id);
			if(null==roleData)
			{
				roleData=[];
			}
			$("#hStartRoleSet").val(JSON.stringify(roleData));
		}
		if("userTask"==rectObj.type.id)
		{
			$("#overrideid").val((null == properties.overrideid ? "" : properties.overrideid));
			baseData.asynchronousdefinition=properties.asynchronousdefinition;
			baseData.duedatedefinition=properties.duedatedefinition;
			baseData.exclusivedefinition=properties.exclusivedefinition;
			baseData.formkeydefinition=properties.formkeydefinition;
			baseData.formproperties=properties.formproperties;
			baseData.isforcompensation=properties.isforcompensation;
			baseData.looptype=properties.looptype;
			baseData.multiinstance_type=properties.multiinstance_type;
			baseData.multiinstance_cardinality=properties.multiinstance_cardinality;
			baseData.multiinstance_collection=properties.multiinstance_collection;
			baseData.multiinstance_condition=properties.multiinstance_condition;
			baseData.multiinstance_sequential=properties.multiinstance_sequential;
			baseData.multiinstance_variable=properties.multiinstance_variable;
			baseData.prioritydefinition=properties.prioritydefinition;
			baseData.tasklisteners=properties.tasklisteners;
			roleData=fn_getRoleList(rectObj.id);
			if(null==roleData)
			{
				roleData=[];
			}
			if(null!=properties.usertaskassignment && null!=properties.usertaskassignment.items)
			{
				$(properties.usertaskassignment.items).each(function(index, eleObj) {
					if("assignee"==eleObj.assignment_type)
					{
						userInfo.assigneeUserId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.assigneeUserName=encodeURIComponent(eleObj.name);
					}
					if("candidateUsers"==eleObj.assignment_type)
					{
						userInfo.candidateUsersId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.candidateUsersName=encodeURIComponent(eleObj.name);
					}
					if("candidateGroups"==eleObj.assignment_type)
					{
						userInfo.candidateGroupsId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.candidateGroupsName=encodeURIComponent(eleObj.name);
					}
					if("singleUser"==eleObj.assignment_type)
					{
						userInfo.singleUserId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.singleUserName=encodeURIComponent(eleObj.name);
					}
					if("postGroups"==eleObj.assignment_type)
					{
						userInfo.postId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.postName=encodeURIComponent(eleObj.name);
					}
					if("deptPostGroups"==eleObj.assignment_type)
					{
						userInfo.deptpostId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.deptpostName=encodeURIComponent(eleObj.name);
					}
					if("deptGroups"==eleObj.assignment_type)
					{
						userInfo.deptId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.deptName=encodeURIComponent(eleObj.name);
					}
					if("roleGroups"==eleObj.assignment_type)
					{
						userInfo.roleId=encodeURIComponent(eleObj.resourceassignmentexpr);
						userInfo.roleName=encodeURIComponent(eleObj.name);
					}
					if("custSql"==eleObj.assignment_type)
					{
						userInfo.custSqlInfo=encodeURIComponent(eleObj.resourceassignmentexpr);
					}
				});
				userData={"userInfo":userInfo};
				if(null!=properties.usertype&&""!=properties.usertype){
					userData.usertype=properties.usertype;
				}
				else
				{
					userData.usertype="0";
				}
				if(null!=properties.usercheck && ""!=properties.usercheck){
					userData.usercheck=properties.usercheck;
				}
				else
				{
					userData.usercheck="1";
				}
				if(null!=properties.userselother && ""!=properties.userselother){
					userData.userselother=properties.userselother;
				}
				else
				{
					userData.userselother="0";
				}
				$("#hUserTaskBaseSet").val(JSON.stringify(baseData));
				$("#hUserTaskRoleSet").val(JSON.stringify(roleData));
				$("#hUserTaskUserSet").val(JSON.stringify(userData));
			}
		}
		if("fork"==rectObj.type.id)
		{
			$("#documentation").val((null == properties.documentation ? "" : properties.documentation));
		}
		if("join"==rectObj.type.id)
		{
			$("#documentation").val((null == properties.documentation ? "" : properties.documentation));
		}
		if("end"==rectObj.type.id)
		{
			$("#executionlisteners").val((null == properties.executionlisteners ? "" : properties.executionlisteners));
		}
	}
}
function getDataInfo(initData){
	var retData;
	var type;
	var typeInfo;
	var blInfo;
	var typeData;
	if(''!=$('#htype').val())
	{
		typeData=JSON.parse($('#htype').val());
	}
	retData=null;
	if(null!=initData.rects)
	{
		$(initData.rects).each(function(index, ele) {
			blInfo=false;
			type=ele.type;
			if(null!=type)
			{
				for(var i=0;i<typeData.length;i++)
				{
					if(type==typeData[i].id)
					{
						blInfo=true;
						typeInfo=typeData[i];
						break;
					}
				}
				if(blInfo==true)
				{
					ele.type=typeInfo;
				}
				else
				{
					ele.type=null;
				}
			}
		});
		retData=initData;
	}
	return retData;
}
function modifyInitData(jsonData){
	$("#id").val(jsonData.id);
	$("#key").val(jsonData.process_id);
	$("#name").val(jsonData.name);
	$("#width").val(jsonData.width);
	$("#height").val(jsonData.height);
	$("#documentation").val(jsonData.documentation);
	$("#executionlisteners").val(jsonData.executionlisteners);
	$("#key").attr("readonly","readonly");
}
function fn_saveBaseInfo(){
	var jsonData;
	var jsonObj;
	var initValue;
	jsonObj=null;
	initValue=$("#hinitData").val();
	if(""!=initValue)
	{
		jsonObj=JSON.parse(initValue);
	}
	jsonData={};
	jsonData.id=$("#id").val();
	jsonData.process_id=$("#key").val();
	jsonData.name=$("#name").val();
	jsonData.documentation=$("#documentation").val();
	jsonData.executionlisteners=$("#executionlisteners").val();
	jsonData.process_author="";
	jsonData.process_executable="Yes";
	jsonData.process_version="";
	jsonData.process_namespace=$("#hCategory").val();
	jsonData.width=$("#width").val();
	jsonData.height=$("#height").val();
	if(true==isNaN(jsonData.width))
	{
		alert("宽度请填写数字!");
		return;
	}
	if(true==isNaN(jsonData.height))
	{
		alert("高度请填写数字!");
		return;
	}
	if(null!=jsonObj)
	{
		jsonData.forminfo=jsonObj.forminfo;
	}
	if(null!=jsonData)
	{
		$("#hKey").val(jsonData.process_id);
		$("#hinitData").val(JSON.stringify(jsonData));
		$("#myflow_props").empty();
		createTableIntoDiv('baseInfo');
		modifyInitData(jsonData);
		$("#holder").width(jsonData.width);
		$("#holder").height(jsonData.height);
		$("#holder").children().width(jsonData.width);
		$("#holder").children().height(jsonData.height);
	}
}
function createUserTask(){
	var tabInfo;
	tabInfo='';
	tabInfo+='<input id="hUserTaskBaseSet" type="hidden" value="" />';
	tabInfo+='<input id="hUserTaskUserSet" type="hidden" value="" />';
	tabInfo+='<input id="hUserTaskRoleSet" type="hidden" value="" />';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr style="display:none;">';
	tabInfo+='<td width="25%">';
	tabInfo+='关键字';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="overrideid" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='主键';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="id" type="input" value="" readonly="readonly" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="25%">';
	tabInfo+='文本';
	tabInfo+='</td>';
	tabInfo+='<td width="75%">';
	tabInfo+='<input id="txt" type="input" value="" style="width:150px;">';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%">';
	tabInfo+='属性设置';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="50%">';
	tabInfo+='基础设置';
	tabInfo+='</td>';
	tabInfo+='<td width="50%">';
	tabInfo+='<input type="button" value="基础设置" onclick="fn_setBaseSet();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="50%">';
	tabInfo+='人员配置';
	tabInfo+='</td>';
	tabInfo+='<td width="50%">';
	tabInfo+='<input type="button" value="人员配置" onclick="fn_setUserSet();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='<tr>';
	tabInfo+='<td width="50%">';
	tabInfo+='权限配置';
	tabInfo+='</td>';
	tabInfo+='<td width="50%">';
	tabInfo+='<input type="button" value="权限配置" onclick="fn_setRoleSet();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	tabInfo+='<hr>';
	tabInfo+='<table width="100%" border="0">';
	tabInfo+='<tr>';
	tabInfo+='<td width="100%" style="text-align:center;">';
	tabInfo+='<input type="button" id="btnSave" value="保存" onclick="fn_saveUserTask();" />';
	tabInfo+='</td>';
	tabInfo+='</tr>';
	tabInfo+='</table>';
	return tabInfo;
}
function fn_saveUserTask(){
	var jsonObj;
	var properties;
	var baseInfo;
	var userInfo;
	var roleInfo;
	var baseData;
	var userData;
	var roleData;
	var userList;
	var candidateUsers;
	var candidateGroups;
	var userAssignmentList;
	userList=[];
	candidateUsers=[];
	candidateGroups=[];
	userAssignmentList={};
	jsonObj=fn_baseSave();
	baseInfo=$("#hUserTaskBaseSet").val();
	userInfo=$("#hUserTaskUserSet").val();
	roleInfo=$("#hUserTaskRoleSet").val();
	if(""!=baseInfo && ""!=userInfo && ""!=roleInfo)
	{
		baseData=JSON.parse(baseInfo);
		userData=JSON.parse(userInfo);
		roleData=JSON.parse(roleInfo);
		properties={};
		properties.overrideid=$("#overrideid").val();
		properties.name=jsonObj.text;
		if(null!=userData.usertype && ""!=userData.usertype){
			properties.usertype=userData.usertype;
		}
		if(null!=userData.usercheck && ""!=userData.usercheck){
			properties.usercheck=userData.usercheck;
		}
		if(null!=userData.userselother && ""!=userData.userselother){
			properties.userselother=userData.userselother;
		}
		if(""!=userData.userInfo.assigneeUserId && null!=userData.userInfo.assigneeUserId && "undefined"!=userData.userInfo.assigneeUserId){
			userList.push({"assignment_type":"assignee","resourceassignmentexpr":decodeURIComponent(userData.userInfo.assigneeUserId),"name":decodeURIComponent(userData.userInfo.assigneeUserName)});
			userAssignmentList.assignee=decodeURIComponent(userData.userInfo.assigneeUserId);
		}
		else
		{
			userList.push({"assignment_type":"assignee","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.candidateUsersId && null!=userData.userInfo.candidateUsersId && "undefined"!=userData.userInfo.candidateUsersId){
			userList.push({"assignment_type":"candidateUsers","resourceassignmentexpr":decodeURIComponent(userData.userInfo.candidateUsersId),"name":decodeURIComponent(userData.userInfo.candidateUsersName)});
			candidateUsers.push({"value":decodeURIComponent(userData.userInfo.candidateUsersId)});
			userAssignmentList.candidateUsers=candidateUsers;
		}
		else
		{
			userList.push({"assignment_type":"candidateUsers","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.candidateGroupsId && null!=userData.userInfo.candidateGroupsId && "undefined"!=userData.userInfo.candidateGroupsId){
			userList.push({"assignment_type":"candidateGroups","resourceassignmentexpr":decodeURIComponent(userData.userInfo.candidateGroupsId),"name":decodeURIComponent(userData.userInfo.candidateGroupsName)});
			candidateGroups.push({"value":decodeURIComponent(userData.userInfo.candidateGroupsId)});
			userAssignmentList.candidateGroups=candidateGroups;
		}
		else
		{
			userList.push({"assignment_type":"candidateGroups","resourceassignmentexpr":"","name":""});
		} 
		if(""!=userData.userInfo.singleUserId && null!=userData.userInfo.singleUserId && "undefined"!=userData.userInfo.singleUserId){
			userList.push({"assignment_type":"singleUser","resourceassignmentexpr":decodeURIComponent(userData.userInfo.singleUserId),"name":decodeURIComponent(userData.userInfo.singleUserName)});
		}
		else
		{
			userList.push({"assignment_type":"singleUser","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.deptId && null!=userData.userInfo.deptId && "undefined"!=userData.userInfo.deptId){
			userList.push({"assignment_type":"deptGroups","resourceassignmentexpr":decodeURIComponent(userData.userInfo.deptId),"name":decodeURIComponent(userData.userInfo.deptName)});
		}
		else
		{
			userList.push({"assignment_type":"deptGroups","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.roleId && null!=userData.userInfo.roleId && "undefined"!=userData.userInfo.roleId){
			userList.push({"assignment_type":"roleGroups","resourceassignmentexpr":decodeURIComponent(userData.userInfo.roleId),"name":decodeURIComponent(userData.userInfo.roleName)});
		}
		else
		{
			userList.push({"assignment_type":"roleGroups","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.postId && null!=userData.userInfo.postId && "undefined"!=userData.userInfo.postId){
			userList.push({"assignment_type":"postGroups","resourceassignmentexpr":decodeURIComponent(userData.userInfo.postId),"name":decodeURIComponent(userData.userInfo.postName)});
		}
		else
		{
			userList.push({"assignment_type":"postGroups","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.deptpostId && null!=userData.userInfo.deptpostId && "undefined"!=userData.userInfo.deptpostId){
			userList.push({"assignment_type":"deptPostGroups","resourceassignmentexpr":decodeURIComponent(userData.userInfo.deptpostId),"name":decodeURIComponent(userData.userInfo.deptpostName)});
		}
		else
		{
			userList.push({"assignment_type":"deptPostGroups","resourceassignmentexpr":"","name":""});
		}
		if(""!=userData.userInfo.custSqlInfo && null!=userData.userInfo.custSqlInfo && "undefined"!=userData.userInfo.custSqlInfo){
			userList.push({"assignment_type":"custSql","resourceassignmentexpr":decodeURIComponent(userData.userInfo.custSqlInfo),"name":decodeURIComponent(userData.userInfo.custSqlInfo)});
		}
		else
		{
			userList.push({"assignment_type":"custSql","resourceassignmentexpr":"","name":""});
		}
		if(0!=$(userList).size())
		{
			properties.asynchronousdefinition=baseData.asynchronousdefinition;
			properties.duedatedefinition=baseData.duedatedefinition;
			properties.exclusivedefinition=baseData.exclusivedefinition;
			properties.formkeydefinition=baseData.formkeydefinition;
			properties.formproperties=baseData.formproperties;
			properties.isforcompensation=baseData.isforcompensation;
			properties.looptype=baseData.looptype;
			properties.multiinstance_type=baseData.multiinstance_type;
			properties.multiinstance_cardinality=baseData.multiinstance_cardinality;
			properties.multiinstance_collection=baseData.multiinstance_collection;
			properties.multiinstance_condition=baseData.multiinstance_condition;
			properties.multiinstance_sequential=baseData.multiinstance_sequential;
			properties.multiinstance_variable=baseData.multiinstance_variable;
			properties.prioritydefinition=baseData.prioritydefinition;
			properties.tasklisteners=baseData.tasklisteners;
			properties.documentation=jsonObj.text;
			properties.usertaskassignment={"totalCount":$(userList).size(),"items":userList};
			jsonObj.type={"id":"userTask"};
			jsonObj.txt=jsonObj.text;
			jsonObj.properties=properties;
			wf.updateRect(jsonObj);
			fn_saveNodeRole(jsonObj.id,roleData);
			fn_saveNodeFormList(jsonObj.id,JSON.parse(decodeURIComponent(baseData.formkeydefinition)));
			if(null != properties.usertype)
			{
				fn_saveNodeUserList(jsonObj.id,{"usertype":properties.usertype,"usercheck":properties.usercheck,"userselother":properties.userselother,"userlist":userList});
			}
		}
		else
		{
			alert("必须配置人员信息!");
		}
	}
	else
	{
		alert("基本信息 人员信息 权限信息为必设项!");
	}
}
function fn_saveNodeRole(nodeid,roleList){
	var strRoleList;
	var jsonList;
	var blInfo;
	blInfo=false;
	strRoleList=$("#hroleList").val();
	jsonList=JSON.parse(strRoleList);
	$(jsonList).each(function(i,ele) {
        if(nodeid==ele.id)
		{
			blInfo=true;
			ele.roleList=roleList;
			return false;
		}
    });
	if(false==blInfo)
	{
		jsonList.push({"id":nodeid,"roleList":roleList});
	}
	$("#hroleList").val(JSON.stringify(jsonList));
}
function fn_getRoleList(nodeid)
{
	var retList;
	var jsonList;
	var strRoleList;
	retList=[];
	strRoleList=$("#hroleList").val();
	jsonList=JSON.parse(strRoleList);
	$(jsonList).each(function(i,ele) {
        if(nodeid==ele.id)
		{
			retList=ele.roleList;
			return false;
		}
    });
	return retList;
}
function fn_saveNodeFormList(nodeid,formKeyInfo){
	var strFormKeyList;
	var jsonList;
	var blInfo;
	blInfo=false;
	strFormKeyList=$("#htaskformlist").val();
	jsonList=JSON.parse(strFormKeyList);
	$(jsonList).each(function(i,ele) {
        if(nodeid==ele.id)
		{
			blInfo=true;
			ele.startcode=formKeyInfo.startcode;
			ele.endcode=formKeyInfo.endcode;
			return false;
		}
    });
	if(false==blInfo)
	{
		jsonList.push({"id":nodeid,"startcode":formKeyInfo.startcode,"endcode":formKeyInfo.endcode});
	}
	$("#htaskformlist").val(JSON.stringify(jsonList));
}
function fn_saveNodeUserList(nodeid,userInfo){
	var strUserList;
	var jsonList;
	var blInfo;
	blInfo=false;
	strUserList=$("#htaskuserlist").val();
	jsonList=JSON.parse(strUserList);
	$(jsonList).each(function(i,ele) {
        if(nodeid==ele.id)
		{
			blInfo=true;
			ele.usertype=userInfo.usertype;
			ele.usercheck=userInfo.usercheck;
			ele.userselother=userInfo.userselother;
			ele.userlist=userInfo.userlist;
			return false;
		}
    });
	if(false==blInfo)
	{
		jsonList.push({"id":nodeid,"usertype":userInfo.usertype,"usercheck":userInfo.usercheck,"userselother":userInfo.userselother,"userlist":userInfo.userlist});
	}
	$("#htaskuserlist").val(JSON.stringify(jsonList));
}
function fn_saveFormData(jsonObj)
{
	if(null!=jsonObj.temp)
	{
		$("#htemp").val(jsonObj.temp);
	}
	if(null!=jsonObj.btnlist)
	{
		$("#hbtnlist").val(JSON.stringify(jsonObj.btnlist));
	}
	if(null!=jsonObj.conlist)
	{
		$("#hconlist").val(JSON.stringify(jsonObj.conlist));
	}
	if(null!=jsonObj.formlist)
	{
		$("#hformlist").val(JSON.stringify(jsonObj.formlist));
	}
	if(null!=jsonObj.hidlist)
	{
		$("#hhidlist").val(JSON.stringify(jsonObj.hidlist));
	}
	if(null!=jsonObj.jsinfo)
	{
		$("#hjsinfo").val(encodeURIComponent(decodeURIComponent(jsonObj.jsinfo)));
	}
	if(null!=jsonObj.taginfo)
	{
		$("#htaginfo").val(JSON.stringify(jsonObj.taginfo));
	}
	if(null!=jsonObj.dynamicform)
	{
		$("#hdynamicform").val(encodeURIComponent(decodeURIComponent(jsonObj.dynamicform)));
	}
}
