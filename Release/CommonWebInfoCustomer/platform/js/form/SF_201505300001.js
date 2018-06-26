var ue;
$(document).ready(function(e) {
	var initData;
	var jsonObj;
	var dfrom;
	var btnlist;
	var hidlist;
	var conlist;
	jsonObj=null;
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '1010',
		scroller_window_height: '280',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
	initData=parent.$("#hinitdata").val();
	if(null!=initData)
	{
		initData=decodeURIComponent(initData);
		jsonObj=JSON.parse(initData);
		if(null==jsonObj || true==$.isEmptyObject(jsonObj)){
			btnlist=[];
			hidlist=[];
			conlist=[];
			conlist.push({
					"control":[
							{
								"id":"btnsave",
								"value":"保存",
								"type": "button",
								"icon": "",
								"strstyle": "color:rgb(255,%20255,%20255)",
								"strclass": "btn%20btn-default"
							}
						]
						},
						{
					"control":[
							{
								"id":"btnsubmit",
								"value":"提交",
								"type": "button",
								"icon": "",
								"strstyle": "color:rgb(255,%20255,%20255)",
								"strclass": "btn%20btn-default"
							}
						]
						},
						{
					"control":[
							{
								"id":"btnnext",
								"value":"提交",
								"type": "button",
								"icon": "glyphicon glyphicon-ok-sign",
								"strstyle": "color:rgb(255,%20255,%20255)",
								"strclass": "btn%20btn-default"
							}
						]
						},
						{
					"control":[
							{
								"id":"btnimage",
								"value":"流程图片",
								"type": "button",
								"icon": "",
								"strstyle": "color:rgb(0,%20153,%20255)",
								"strclass": "btn%20btn-default"
							}
						]
						},
						{
					"control":[
							{
								"id":"btnclose",
								"value":"关闭",
								"type": "button",
								"icon": "",
								"strstyle": "color:rgb(255,%20255,%20255)",
								"strclass": "btn%20btn-default"
							}
						]
						},
						{
					"control":[
							{
								"id":"formdata",
								"type":"hidden",
								"backtype":"s",
								"value": "",
							}
						]
						},
						{
					"control":[
							{
								"id":"businesskey",
								"type":"hidden",
								"dataBind": "businesskey",
								"backtype":"s"
							}
						]
						},
						{
					"control":[
							{
								"id":"urldata",
								"type":"hidden",
								"backtype":"s",
								"value":""
							}
						]
						},
						{
					"control":[
							{
								"id":"tkid",
								"type":"hidden",
								"backtype":"s",
								"value":"${requestScope._wfcurrtkid}"
							}
						]
						},
						{
					"control":[
							{
								"id":"processkey",
								"type":"hidden",
								"backtype":"s",
								"dataBind": "processkey",
								"value":"${requestScope._viewpath}"
							}
						]
						},
						{
					"control":[
							{
								"id":"defkey",
								"type":"hidden",
								"backtype":"s",
								"value":"${requestScope._wfdefkey}"
							}
						]
						},
						{
					"control":[
							{
								"id":"inid",
								"type":"hidden",
								"backtype":"s",
								"value":"${param.inid}"
							}
						]
						},
                {
                    "control": [
                        {
                            "id": "querystring",
                            "type": "hidden",
                            "backtype": "s",
                            "dataBind": "querystring",
                            "value": "%24%7Bparam.fiid%3D%3Dnull%3F(pageContext.request.queryString%3D%3Dnull%3F&apos;%3Ffiid%3D%24%7Bparam.businesskey%7D&apos;%3A&apos;%3F&apos;.concat(pageContext.request.queryString).concat(&apos;%26fiid%3D%24%7Bparam.businesskey%7D&apos;))%3A&apos;%3F&apos;.concat(pageContext.request.queryString)%7D"
                        }
                    ]
                },
				{
					"control":[
						{
							"id":"assigneeUserId",
							"type": "hidden",
                            "backtype": "s",
                            "dataBind": "assigneeUserId",
                            "value": ""
						}
					]
				},
				{
					"control":[
						{
							"id":"candidateUsersId",
							"type": "hidden",
                            "backtype": "s",
                            "dataBind": "candidateUsersId",
                            "value": ""
						}
					]
				},
				{
					"control":[
						{
							"id":"candidateGroupsId",
							"type": "hidden",
                            "backtype": "s",
                            "dataBind": "candidateGroupsId",
                            "value": ""
						}
					]
				},
				{
                    "control": [
                        {
                            "id": "subpagestate",
                            "type": "hidden",
                            "backtype": "s",
                            "dataBind": "",
                            "value": "begin"
                        }
                    ]
                },
				{
                    "control": [
                        {
                            "id": "tkform",
                            "type": "hidden",
                            "backtype": "s",
                            "dataBind": "",
                            "value": "${requestScope._wfcurrform}"
                        }
                    ]
                });
			btnlist.push( {
                    "id": "btnsave",
                    "tag": "fm:button",
                    "clientfunction": [
                        {
                            "function": "saveSubPage()"
                        },
                        {
                            "function": "setWFStart(id)"
                        }
                    ],
                    "firefunction": [
                        {
                            "fireclass": "com.common.service.WorkflowService",
                            "method": "saveWFUrlInfo",
                            "parm": [
                                {
                                    "key": "java.lang.String",
                                    "value": "${pageContext.request.contextPath}"
                                },
								{
                                    "key": "java.lang.String",
                                    "value": "${sessionScope.user}"
                                },
								{
                                    "key": "java.lang.String",
                                    "value": "%5B%7B%22inid%22%3A%22%22%2C%22taskid%22%3A%22%22%2C%22businesskey%22%3A%22%24%7Bmap.businesskey%7D%22%2C%22formkey%22%3A%22%22%2C%22taskdefinitionKey%22%3A%22%22%7D%5D"
                                },
								{
                                    "key": "java.util.Map",
                                    "value": "map"
                                }
                            ]
                        },
                        {
                            "fireclass": "com.common.service.LocalCommonService",
                            "method": "redirecturl",
                            "parm": [
                                {
                                    "key": "java.lang.String",
                                    "value": "%24%7BrequestScope._urlInfo%7D%24%7Bfn%3AindexOf(requestScope._urlInfo%2C%22fiid%22)!%3D-1%3F''%3A(fn%3Acontains('%3F'%2CrequestScope._urlInfo)%3D%3Dtrue%3F'%26'%3A'%3F')%7D%24%7Bfn%3AindexOf(requestScope._urlInfo%2C%22fiid%22)!%3D-1%3F''%3A'fiid%3D'%7D%24%7Bfn%3AindexOf(requestScope._urlInfo%2C%22fiid%22)!%3D-1%3F''%3Aparam.businesskey%7D"
                                },
                                {
                                    "key": "javax.servlet.http.HttpServletRequest",
                                    "value": "request"
                                },
                                {
                                    "key": "javax.servlet.http.HttpServletResponse",
                                    "value": "response"
                                }
                            ]
                        }
                    ]
                });
			btnlist.push({
							"id":"btnimage",
							"tag":"fm:button",
							"clientfunction":[
								{"function":"viewImage()"}
							],
							"firefunction":[
							]
						});
			btnlist.push({
							"id":"btnsubmit",
							"tag":"fm:button",
							"clientfunction":[
								{
									"function": "saveSubPage()"
								},
								{
									"function": "setWFStart(id)"
								}
							],
							"firefunction":[
								{
									"fireclass":"com.common.service.LocalWorkflowService",
									"method":"startProcess",
									"parm":[
										{"key":"java.lang.String","value":"${param.processkey}"},
										{"key":"java.lang.String","value":"${param.businesskey}"},
										{"key":"javax.servlet.http.HttpServletRequest","value":"request"},
										{"key":"javax.servlet.http.HttpServletResponse","value":"response"}
									]
								},
								{
									"fireclass":"com.common.service.WorkflowService",
									"method":"saveWFUrlInfo",
									"parm":[
										{
											"key":"java.lang.String",
											"value":"${pageContext.request.contextPath}"
										},
										{
											"key":"java.lang.String",
											"value":"${sessionScope.user}"
										},
										{
											"key":"java.lang.String",
											"value":"${requestScope._wfcurrtaskid}"
										},
										{
											"key":"java.util.Map",
											"value":"map"
										}
									]
								},
								{
									"fireclass":"com.common.service.LocalWorkflowService",
									"method":"redirectWF",
									"parm":[
										{"key":"javax.servlet.http.HttpServletRequest","value":"request"},
										{"key":"javax.servlet.http.HttpServletResponse","value":"response"}
									]
								}
							]
						});
			btnlist.push({
							"id":"btnnext",
							"tag":"fm:button",
							"clientfunction":[
								{
									"function": "saveSubPage()"
								},
								{
									"function": "setWFStart(id)"
								}
							],
							"firefunction":[
								{
									"fireclass":"com.common.service.LocalWorkflowService",
									"method":"completeTaskWithCommentCmd",
									"parm":[
										{"key":"java.lang.String","value":"${param.tkid}"},
										{"key":"javax.servlet.http.HttpServletRequest","value":"request"},
										{"key":"javax.servlet.http.HttpServletResponse","value":"response"}
									]
								},
								{
									"fireclass":"com.common.service.LocalWorkflowService",
									"method":"redirectWF",
									"parm":[
										{"key":"javax.servlet.http.HttpServletRequest","value":"request"},
										{"key":"javax.servlet.http.HttpServletResponse","value":"response"}
									]
								}
							]
						});
			btnlist.push({
							"id":"btnclose",
							"tag":"fm:button",
							"clientfunction":[
								{"function":"closeWin()"}
							],
							"firefunction":[
							]
						});
			/*--------------------------------------------------------------------------------*/
			hidlist.push({"id":"formdata"});
			hidlist.push({"id":"businesskey"});
			hidlist.push({"id":"processkey"});
			hidlist.push({"id":"urldata"});
			hidlist.push({"id":"tkid"});
			hidlist.push({"id":"defkey"});
			hidlist.push({"id":"inid"});
			hidlist.push({"id":"querystring"});
			hidlist.push({"id":"assigneeUserId"});
			hidlist.push({"id":"candidateGroupsId"});
			hidlist.push({"id":"candidateUsersId"});
			hidlist.push({"id":"subpagestate"});
			hidlist.push({"id":"tkform"});
			/*--------------------------------------------------------------------------------*/
			dfrom='<h4 id="titleName" class="widget-caption" style="color:#fff;">{titlename}</h4>';
			dfrom+='<div class="btn-group">';
			dfrom+='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\'btnsave\',\'title\':\'fm:button\'}"/>';
			dfrom+='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\'btnimage\',\'title\':\'fm:button\'}"/>';
			dfrom+='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\'btnsubmit\',\'title\':\'fm:button\'}"/>';
			dfrom+='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\'btnnext\',\'title\':\'fm:button\'}"/>';
			dfrom+='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\'btnclose\',\'title\':\'fm:button\'}"/>';
			dfrom+='</div>';
			jsonObj.forminfo={};
			jsonObj.forminfo.taginfo=[{"tag":"%3C%25%40%20taglib%20prefix%3D%22fm%22%20uri%3D%22%2Fform%22%20%25%3E"}];
			jsonObj.forminfo.jsinfo='%3Cscript%20type%3D%22text%2Fjavascript%22%20src%3D%22..%2F..%2Fplatform%2Fjs%2Fwf%2Fcustorwf.js%22%3E%3C%2Fscript%3E';
			jsonObj.forminfo.dynamicform=dfrom;
			jsonObj.forminfo.temp='page';
			jsonObj.forminfo.btnlist=btnlist;
			jsonObj.forminfo.hidlist=hidlist;
			jsonObj.forminfo.conlist=conlist;
			jsonObj.forminfo.formlist=[];
		}
	}
	if(null!=jsonObj)
	{
		$("#hJsonData").val(JSON.stringify(jsonObj));
		fn_initData(jsonObj);
	}
	$("#dynamicform").attr("class","");
	ue = UE.getEditor('dynamicform');
});
function fn_initData(jsonObj){
	var tagTable;
	var formTable;
	var btnTable;
	var hidTable;
	tagTable="";
	formTable="";
	btnTable="";
	hidTable="";
	$("#dynamicform").val(decodeURIComponent(jsonObj.forminfo.dynamicform));
	$("#jsInfo").val(decodeURIComponent(jsonObj.forminfo.jsinfo));
	$("#filedir").val(jsonObj.forminfo.temp);
	$("#taglist").empty();
	$(jsonObj.forminfo.taginfo).each(function(index, element) {
        tagTable+="<tr>";
		tagTable+="<td align=\"left\" width=\"90%\">";
		tagTable+=decodeURIComponent(element.tag);
		tagTable+="</td>";
		tagTable+="<td width=\"10%\">";
		tagTable+="<a onclick=\"fn_delTag('"+encodeURIComponent(decodeURIComponent(element.tag)).replace(/"/g, "&quot;").replace(/'/g, "&apos;")+"');\" style=\"text-decoration:none;color: blue;border-bottom: 1px #0099CC dotted;cursor:pointer;\">删除</a>";
		tagTable+="</td>";
		tagTable+="</tr>";
    });
	$("#taglist").append(tagTable);
	$("#formlist").empty();
	$(jsonObj.forminfo.formlist).each(function(index, element) {
        formTable+="<tr>";
		formTable+="<td align=\"left\" width=\"25%\">";
		formTable+=element.code;
		formTable+="</td>";
		formTable+="<td align=\"left\" width=\"65%\">";
		formTable+=decodeURIComponent(element.url);
		formTable+="</td>";
		formTable+="<td width=\"10%\">";
		formTable+="<a onclick=\"fn_delUrl('"+element.code+"');\" style=\"text-decoration:none;color: blue;border-bottom: 1px #0099CC dotted;cursor:pointer;\">删除</a>";
		formTable+="</td>";
		formTable+="</tr>";
    });
	$("#formlist").append(formTable);
	$("#hidlist").empty();
	$(jsonObj.forminfo.hidlist).each(function(index, element) {
        hidTable+="<tr>";
		hidTable+="<td align=\"left\" width=\"80%\">";
		hidTable+=element.id;
		hidTable+="</td>";
		hidTable+="<td align=\"left\" width=\"10%\">";
		hidTable+="<a onclick=\"fn_editHid('"+element.id+"');\" style=\"text-decoration:none;color: blue;border-bottom: 1px #0099CC dotted;cursor:pointer;\">编辑</a>";
		hidTable+="</td>";
		hidTable+="<td width=\"10%\">";
		hidTable+="<a onclick=\"fn_delHid('"+element.id+"');\" style=\"text-decoration:none;color: blue;border-bottom: 1px #0099CC dotted;cursor:pointer;\">删除</a>";
		hidTable+="</td>";
		hidTable+="</tr>";
    });
	$("#hidlist").append(hidTable);
}

function fn_getInitData()
{
	var initData;
	var jsonData;
	jsonData=null;
	initData=$("#hJsonData").val();
	if(""!=initData)
	{
		jsonData=JSON.parse(initData);
	}
	return jsonData;
}

function fn_saveInitData(jsonData){
	$("#hJsonData").val(JSON.stringify(jsonData));
}

function fn_delTag(tag){
	var jsonData;
	var delId;
	delId=-1;
	jsonData=fn_getInitData();
	if(confirm("确认删除么?")==true && null!=jsonData){
		$(jsonData.forminfo.taginfo).each(function(index, eletag) {
            if(encodeURIComponent(decodeURIComponent(eletag.tag)).replace(/"/g, "&quot;").replace(/'/g, "&apos;")==tag)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.taginfo.splice(delId,1);
		}
		fn_saveInitData(jsonData);
		fn_initData(jsonData);
	}
}

function fn_delBtn(btnId){
	var jsonData;
	var delId;
	delId=-1;
	jsonData=fn_getInitData();
	if(confirm("确认删除么?")==true && null!=jsonData){
		$(jsonData.forminfo.btnlist).each(function(index, eletag) {
            if(eletag.id==btnId)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.btnlist.splice(delId,1);
		}
		delId=-1;
		$(jsonData.forminfo.conlist).each(function(index, eletag) {
            if(eletag.control[0].id==btnId)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.conlist.splice(delId,1);
		}
		fn_saveInitData(jsonData);
		fn_initData(jsonData);
	}
}

function fn_delHid(hidId){
	var jsonData;
	var delId;
	delId=-1;
	jsonData=fn_getInitData();
	if(confirm("确认删除么?")==true && null!=jsonData){
		$(jsonData.forminfo.hidlist).each(function(index, eletag) {
            if(eletag.id==hidId)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.hidlist.splice(delId,1);
		}
		delId=-1;
		$(jsonData.forminfo.conlist).each(function(index, eletag) {
            if(eletag.control[0].id==hidId)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.conlist.splice(delId,1);
		}
		fn_saveInitData(jsonData);
		fn_initData(jsonData);
	}
}

function fn_delUrl(urlCode){
	var jsonData;
	var delId;
	delId=-1;
	jsonData=fn_getInitData();
	if(confirm("确认删除么?")==true && null!=jsonData){
		$(jsonData.forminfo.formlist).each(function(index, eleForm) {
            if(eleForm.code==urlCode)
			{
				delId=index;
				return false;
			}
        });
		if(-1!=delId)
		{
			jsonData.forminfo.formlist.splice(delId,1);
		}
		fn_saveInitData(jsonData);
		fn_initData(jsonData);
	}
}

function fn_addTag(){
	openWin(1000,400,'添加标签','../form/SF_201506080001.form',null,null);
	return false;
}
function fn_addBtn(){
	var jsonData;
	var btnInfo;
	jsonData=fn_getInitData();
	btnInfo=JSON.stringify({"buttons":encodeURIComponent(JSON.stringify(jsonData.forminfo.btnlist)),"controls":encodeURIComponent(JSON.stringify(jsonData.forminfo.conlist))}).replace(/"/g,"'");
	openWin(1000,400,'添加按钮','../form/SF_201506080002.form',btnInfo,null);
	return false;
}
function fn_addHid(){
	var jsonData;
	var hidInfo;
	jsonData=fn_getInitData();
	hidInfo=JSON.stringify({"hiddens":encodeURIComponent(JSON.stringify(jsonData.forminfo.hidlist)),"controls":encodeURIComponent(decodeURIComponent(JSON.stringify(jsonData.forminfo.conlist)))});
	openWin(1000,400,'添加隐藏域','../form/SF_201506080003.form',encodeURIComponent(hidInfo),null);
	return false;
}
function fn_editHid(hid){
	var jsonData;
	var hidInfo;
	jsonData=fn_getInitData();
	hidInfo=JSON.stringify({"hiddens":encodeURIComponent(JSON.stringify(jsonData.forminfo.hidlist)),"controls":encodeURIComponent(decodeURIComponent(JSON.stringify(jsonData.forminfo.conlist)))});
	openWin(1000,400,'添加隐藏域','../form/SF_201506080003.form?hid='+hid,encodeURIComponent(hidInfo),null);
	return false;
}
function fn_addUrlInfo(){
	openWin(1000,400,'添加表单信息','../form/SF_201506080004.form',null,null);
	return false;
}
function fn_editBtn(btnId){
	var jsonData;
	var btnInfo;
	jsonData=fn_getInitData();
	btnInfo=JSON.stringify({"buttons":encodeURIComponent(JSON.stringify(jsonData.forminfo.btnlist)),"controls":encodeURIComponent(JSON.stringify(jsonData.forminfo.conlist))});
	openWin(1000,400,'编辑按钮','../form/SF_201506080002.form?btnid='+btnId,btnInfo,null);
	return false;
}
function fn_saveButton(btnlist,conlist){
	var jsonData;
	jsonData=fn_getInitData();
	jsonData.forminfo.conlist=conlist;
	jsonData.forminfo.btnlist=btnlist;
	fn_saveInitData(jsonData);
	fn_initData(jsonData);
}

function fn_savehidden(hidlist,conlist)
{
	var jsonData;
	jsonData=fn_getInitData();
	jsonData.forminfo.conlist=conlist;
	jsonData.forminfo.hidlist=hidlist;
	fn_saveInitData(jsonData);
	fn_initData(jsonData);
}

function fn_saveTag(tagInfo){
	var jsonData;
	jsonData=fn_getInitData();
	jsonData.forminfo.taginfo.push({"tag":encodeURIComponent(tagInfo)});
	fn_saveInitData(jsonData);
	fn_initData(jsonData);
}

function fn_savefrom(code,url){
	var jsonData;
	var blInfo;
	blInfo=true;
	jsonData=fn_getInitData();
	if(""!=code && ""!=url)
	{
		$(jsonData.forminfo.formlist).each(function(index, eleform) {
            if(eleform.code==code)
			{
				blInfo=false;
				return false;
			}
        });
		if(blInfo==true)
		{
			jsonData.forminfo.formlist.push({"code":code,"url":encodeURIComponent(url)});
			fn_saveInitData(jsonData);
			fn_initData(jsonData);
		}
		else
		{
			alert("编码不能重复!");
		}
	}
	else
	{
		alert("编码和链接不能为空!");
	}
}

function fn_saveConfig(){
	var jsonData;
	jsonData=fn_getInitData();
	if(null!=jsonData)
	{
		jsonData.dynamicform=ue.getContent();
		jsonData.jsinfo=$("#jsInfo").val();
		jsonData.temp=$("#filedir").val();
	}
	parent.window.fn_saveInitData(jsonData);
	closeWin();
	return false;
}