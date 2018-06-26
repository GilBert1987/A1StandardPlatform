$(document).ready(function(e) {
	var btnText;
	var btnList;
	var btnId;
	var btnObj;
	var consText;
	var consList;
	var btnInfo;
	var hinitdata;
	var hinitObj;
	hinitdata="";
	btnId=$('#btnID').val();
	$('#id').val(btnId);
	if(null!=parent.document.getElementById("hinitdata"))
	{
		hinitdata=parent.document.getElementById("hinitdata").value;
		hinitdata=decodeURIComponent(hinitdata);
	}
	if(""!=hinitdata)
	{
		hinitObj=JSON.parse(hinitdata);
		$('#buttons').val(decodeURIComponent(hinitObj.buttons));
		$('#controls').val(decodeURIComponent(hinitObj.controls));
	}
	else
	{
		$('#buttons').val('[]');
		$('#controls').val('[]');
	}
	btnText=$('#buttons').val();
	consText=$('#controls').val();
	btnList=JSON.parse(btnText);
	consList=JSON.parse(consText);
	btnObj=null;
	btnInfo=null;
	$('#serverFun').empty();
	$('#clientFun').empty();
	if(""!=btnId)
	{
		$('#id').attr("readonly","readonly");
	}
	$(btnList).each(function(index, ele) {
        if(btnId==ele.id)
		{
			btnObj=ele;
			return false;
		}
    });
	$(consList).each(function(index, ele) {
        if(btnId==ele.control[0].id)
		{
			btnInfo=ele;
			btnInfo.control[0].value=decodeURIComponent(btnInfo.control[0].value);
			if(null!=btnInfo.control[0].icon)
			{
				btnInfo.control[0].icon=decodeURIComponent(btnInfo.control[0].icon.replace(/&nbsp;/g, " ").replace(/%20/g, " "));
			}
			if(null!=btnInfo.control[0].strstyle)
			{
				btnInfo.control[0].strstyle=decodeURIComponent(btnInfo.control[0].strstyle.replace(/&nbsp;/g, " ").replace(/%20/g, " "));
			}
			if(null!=btnInfo.control[0].strclass)
			{
				btnInfo.control[0].strclass=decodeURIComponent(btnInfo.control[0].strclass.replace(/&nbsp;/g, " ").replace(/%20/g, " "));
			}
			$('#name').val(btnInfo.control[0].value);
			$("#icon").val('{"icon":"'+((btnInfo.control[0].icon==null)?"":btnInfo.control[0].icon)+'","style":"'+((btnInfo.control[0].strstyle==null)?"":btnInfo.control[0].strstyle)+'","btnclass":"'+((btnInfo.control[0].strclass==null)?"":btnInfo.control[0].strclass)+'"}');
			return false;
		}
    });
	if(null!=btnObj && null != btnInfo){
		initClientTable(btnObj,btnInfo);
	}
	if(null!=btnObj && null != btnInfo){
		initServerTable(btnObj,btnInfo);
	}
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '920',
		scroller_window_height: '250',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
});
function initServerTable(btnObj,btnInfo){
	var fireTable;
	var tabText;
	tabText="";
	fireTable=$('#serverFun');
	fireTable.empty();
	$('#name').val(btnInfo.control[0].value);
	if(null!=btnObj.firefunction){
		$(btnObj.firefunction).each(function(index, ele) {
			tabText='<tr>';
			tabText+='<td style="width:70%;">';
			tabText+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">'
			tabText+='<tr>';
			tabText+='<td style="width:25%;">';
			tabText+='类名';
			tabText+='</td>';
			tabText+='<td style="width:75%;">';
			tabText+='<input type="text" style="width:98%;" value="'+ele.fireclass+'"/>';
			tabText+='</td>';
			tabText+='</tr>';
			tabText+='<tr>';
			tabText+='<td style="width:25%;">';
			tabText+='方法名';
			tabText+='</td>';
			tabText+='<td style="width:75%;">';
			tabText+='<input type="text" style="width:98%;" value="'+ele.method+'"/>';
			tabText+='</td>';
			tabText+='</tr>';
			tabText+='<tr>';
			tabText+='<td style="width:100%;" colspan="2">';
			tabText+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">';
			if(null!=ele.parm)
			{
				for(var i=0;i<ele.parm.length;i++)
				{
					tabText+='<tr>';
					tabText+='<td style="width:25%;">';
					tabText+='键值';
					tabText+='</td>';
					tabText+='<td style="width:75%;">';
					tabText+='<input type="text" style="width:98%;" value="'+ele.parm[i].key+'" readonly="readonly"/>';
					tabText+='</td>';
					tabText+='</tr>';
					tabText+='<tr>';
					tabText+='<td style="width:25%;">';
					tabText+='值';
					tabText+='</td>';
					tabText+='<td style="width:75%;">';
					tabText+='<input type="text" style="width:98%;" value="'+decodeURIComponent(ele.parm[i].value).replace(/"/g, "&quot;").replace(/'/g, "&apos;").replace(/%20/g, " ")+'"/>';
					tabText+='</td>';
					tabText+='</tr>';
				}
			}
			tabText+='</table>';
			tabText+='</td>';
			tabText+='</tr>';
			tabText+='</table>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrUp(this);">上移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrDown(this);">下移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrDel(this);">删除</a>';
			tabText+='</td>';
			tabText+='</tr>';
			fireTable.append(tabText);
		});
	}
}
function initClientTable(btnObj,btnInfo){
	var clientTable;
	var tabText;
	tabText="";
	clientTable=$('#clientFun');
	clientTable.empty();
	if(null!=btnObj.clientfunction){
		$(btnObj.clientfunction).each(function(index, ele) {
			tabText='<tr>';
			tabText+='<td style="width:70%;">';
			tabText+='<input type="text" style="width:98%;" value="'+decodeURIComponent(ele.function).replace(/"/g, "&quot;").replace(/'/g, "&apos;").replace(/%20/g, " ")+'"/>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrUp(this);">上移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrDown(this);">下移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_ClientTrDel(this);">删除</a>';
			tabText+='</td>';
			tabText+='</tr>';
			clientTable.append(tabText);
		});
	}
}
function fn_ClientTrUp(obj){
	var objParentTR;
	var prevTR;
	objParentTR = $(obj).parent().parent(); 
	prevTR = objParentTR.prev(); 
	if (prevTR.length > 0) { 
		prevTR.insertAfter(objParentTR); 
	}
}
function fn_ClientTrDown(obj){
	var objParentTR;
	var nextTR;
	objParentTR = $(obj).parent().parent();
	nextTR = objParentTR.next();
	if (nextTR.length > 0) {
		nextTR.insertBefore(objParentTR); 
	} 
}
function fn_ClientTrDel(aObj){
	if(confirm("确定删除?")==true)
	{
		$(aObj).parent().parent().remove();
	}
}
function addClientFun(){
	var tabText;
	tabText='<tr>';
	tabText+='<td style="width:70%;">';
	tabText+='<input type="text" style="width:98%;"  value=""/>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrUp(this);">上移</a>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrDown(this);">下移</a>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrDel(this);">删除</a>';
	tabText+='</td>';
	tabText+='</tr>';
	$('#clientFun').append(tabText);
	return false;
}
function clickFireButton(){
	openWin(1000,400,'方法设置','../tree/SE_2016050915180001.treelist',null,null);
	return false;
}
function addFireFun(ele){
	var tabText;
	tabText='<tr>';
	tabText+='<td style="width:70%;">';
	tabText+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">'
	tabText+='<tr>';
	tabText+='<td style="width:25%;">';
	tabText+='类名';
	tabText+='</td>';
	tabText+='<td style="width:75%;">';
	tabText+='<input type="text"  style="width:98%;"  value="'+ele.fireclass+'"/>';
	tabText+='</td>';
	tabText+='</tr>';
	tabText+='<tr>';
	tabText+='<td style="width:25%;">';
	tabText+='方法名';
	tabText+='</td>';
	tabText+='<td style="width:75%;">';
	tabText+='<input type="text"  style="width:98%;"  value="'+ele.method+'"/>';
	tabText+='</td>';
	tabText+='</tr>';
	tabText+='<tr>';
	tabText+='<td style="width:100%;" colspan="2">';
	tabText+='<table cellspacing="0" style="border-color:#000000;width:100%;" border="1">';
	if(null!=ele.parm)
	{
		for(var i=0;i<ele.parm.length;i++)
		{
			tabText+='<tr>';
			tabText+='<td style="width:25%;">';
			tabText+='键值';
			tabText+='</td>';
			tabText+='<td style="width:75%;">';
			tabText+='<input type="text"  style="width:98%;"  value="'+ele.parm[i].key+'" readonly="readonly"/>';
			tabText+='</td>';
			tabText+='</tr>';
			tabText+='<tr>';
			tabText+='<td style="width:25%;">';
			tabText+='值';
			tabText+='</td>';
			tabText+='<td style="width:75%;">';
			tabText+='<input type="text"  style="width:98%;"  value="'+ele.parm[i].value+'"/>';
			tabText+='</td>';
			tabText+='</tr>';
		}
	}
	tabText+='</table>';
	tabText+='</td>';
	tabText+='</tr>';
	tabText+='</table>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrUp(this);">上移</a>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrDown(this);">下移</a>';
	tabText+='</td>';
	tabText+='<td style="width:10%;">';
	tabText+='<a href="#" onclick="fn_ClientTrDel(this);">删除</a>';
	tabText+='</td>';
	tabText+='</tr>';
	$('#serverFun').append(tabText);
	return false;
}
function fn_save(){
	var btnid;
	var btnname;
	var consText;
	var blconAdd;
	var blbtnAdd;
	var consList;
	var btnText;
	var btnList;
	var btnObj;
	var clientFun;
	var fireFun;
	var fireObj;
	var clientText;
	var fireTab;
	var paramTab;
	var params;
	var paramText;
	var ele;
	var iconInfo;
	var iconStyle;
	var iconClass;
	var iconText;
	var iconObject;
	iconObject=null;
	btnid=$('#id').val();
	btnname=$('#name').val();
	iconText=$("#icon").val();
	blconAdd=false;
	blbtnAdd=false;
	iconInfo="";
	iconStyle="";
	iconClass="";
	if(""!=iconText)
	{
		iconObject=JSON.parse(iconText);
		iconInfo=iconObject.icon;
		iconStyle=iconObject.style;
		iconClass=iconObject.btnclass;
	}
	if(""!=btnid && ""!=btnname){
		if(true==fn_checkControl(btnid))
		{
			consText=$('#controls').val();
			btnText=$('#buttons').val();
			consList=null;
			btnList=null;
			if(""!=consText){
				consList=JSON.parse(consText);
			}
			if(""!=btnText){
				btnList=JSON.parse(btnText);
			}
			if(null!=consList){
				$(consList).each(function(index, ele) {
                    if(btnid==ele.control[0].id)
					{
						consList[index]={"control":[{"id":btnid,"value":btnname,"type":"button","icon":iconInfo,"strstyle":iconStyle,"strclass":iconClass}]};
						blconAdd=true;
						return false;
					}
                });
				if(blconAdd==false)
				{
					consList.push({"control":[{"id":btnid,"value":btnname,"type":"button","icon":iconInfo,"strstyle":iconStyle,"strclass":iconClass}]});
				}
			}
			if(null!=btnList){
				clientFun=[];
				fireFun=[];
				ele=null;
				for(var i=0;i<$('#clientFun')[0].rows.length;i++)
				{
					ele=$('#clientFun')[0].rows[i];
					clientText=$($(ele.cells[0]).children()[0]).val();
					if(""!=clientText){
						clientText=encodeURIComponent(decodeURIComponent(clientText)).replace(/"/g, "&quot;").replace(/'/g, "&apos;");
						clientFun.push({"function":clientText});
					}
				}
				ele=null;
				for(var j=0;j<$('#serverFun')[0].rows.length;j++)
				{
					ele=$('#serverFun')[0].rows[j];
					params=null;
					fireTab=$(ele.cells[0]).children()[0];
					if(null!=fireTab)
					{
						paramTab=$(fireTab.rows[2].cells[0]).children()[0];
						paramText='[';
						for(var i=0;i<paramTab.rows.length;i++)
						{
							if(i%2==0)
							{
								paramText+='{"key":"'+$($(paramTab.rows[i].cells[1]).children()[0]).val()+'",';
							}
							else
							{
								paramText+='"value":"'+encodeURIComponent(decodeURIComponent($($(paramTab.rows[i].cells[1]).children()[0]).val())).replace(/"/g, "&quot;").replace(/'/g, "&apos;")+'"},';
							}
						}
						if(paramTab.rows.length>0)
						{
							paramText=paramText.substring(0,paramText.length-1);
						}
						paramText+=']';
						params=JSON.parse(paramText);
					}
					if(null==params)
					{
						params=[];
					}
					fireObj={"fireclass":$($(fireTab.rows[0].cells[1]).children()[0]).val(),"method":$($(fireTab.rows[1].cells[1]).children()[0]).val(),"parm":params};
					fireFun.push(fireObj);
                }
				btnObj={"id":btnid,"tag":"fm:button","clientfunction":clientFun,"firefunction":fireFun};
				$(btnList).each(function(index, ele) {
                    if(btnid==ele.id)
					{
						btnList[index]=btnObj;
						blbtnAdd=true;
						return false;
					}
                });
				if(false==blbtnAdd)
				{
					btnList.push(btnObj);
				}
			}
			if(null!=consList && null!=btnList){
				parent.fn_saveButton(btnList,consList);
				closeWin();
			}
		}
		else{
			alert('控件不能重复!');
		}
	}
	else
	{
		alert("主键或名称为空!");
	}
	return false;
}
function fn_checkControl(btnid){
	var blInfo;
	var consText;
	var consList;
	var btnId;
	btnId=$('#btnID').val();
	blInfo=true;
	consText=$('#controls').val();
	consList=null;
	if(""==btnId)
	{
		if(""!=consText){
			consList=JSON.parse(consText);
		}
		if(null!=consList){
			$(consList).each(function(index, ele) {
				if(btnid==ele.control[0].id)
				{
					blInfo=false;
					return false;
				}
			});
		}
	}
	return blInfo;
}