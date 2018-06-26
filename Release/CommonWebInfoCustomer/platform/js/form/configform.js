var ue;
$(document).ready(function(e) {
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '1000',
		scroller_window_height: '420',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
	ue = UE.getEditor('dynamicform');
	ue.addListener('ready', function(editor) {
		var item;
		for(var iteminfo in $EDITORUI)
		{
			item=$EDITORUI[iteminfo];
			if(null!=item && true==(item instanceof baidu.editor.ui.Combox))
			{
				if("自定义控件"==item.label){
					if(null!=item.id && null!=item._onArrowClick){
						item._onArrowClick();
					}
				}
			}
		}
		for(var iteminfo in $EDITORUI)
		{
			item=$EDITORUI[iteminfo];
			if(null!=item && true==(item instanceof baidu.editor.ui.Menu))
			{
				if(null!=item.combox)
				{
					if("list"==item.uiName && "自定义控件"==item.combox.label){
						$("#"+item.id+"_body").css("height","210px");
						$("#"+item.id+"_content").css("height","210px");
					}
				}
			}
		}
 	});
	$('#hidObjAdd').click(function(){
		var funlist;
		funlist=[];
		funlist.push(savenewHidden);
		ue.options.retFunlist=funlist;
		ue.execCommand('customcontrol',"{'id':'','title':'fm:input'}",0);
	});
	$('#btnObjAdd').click(function(){
		var url;
		var hid;
		var initObj;
		hid=$('#hid').val();
		url='../form/SF_201503180001.form?id='+hid;
		initObj={"buttons":encodeURIComponent($("#buttons").val()),"controls":encodeURIComponent($("#controls").val())};
		openWin(1000,400,'添加按钮',url,encodeURIComponent(JSON.stringify(initObj)),fn_BtnSave);
	});
	initHidden();
	initButton();
});

function savenewHidden(retObj){
	var jsonObj;
	var hidlist;
	var hidFormValue;
	if(""!=retObj.control.id && "" != retObj.imgHtml)
	{
		jsonObj={"hiddenform":encodeURIComponent(retObj.imgHtml)};
		hidFormValue=$("#hiddenform").val();
		hidlist=JSON.parse(hidFormValue);
		hidlist.push(jsonObj);
		$("#hiddenform").val(JSON.stringify(hidlist));
		ue.options.retFunlist=null;
		initHidden();
	}
}
function initButton(){
	var hidButton;
	var btnList;
	var tag;
	var imgHtml;
	var tabText;
	hidButton=$('#buttons').val();
	if(""!=hidButton)
	{
		btnList=JSON.parse(hidButton);
		$('#btntbList').empty();
		$(btnList).each(function(index, ele) {
			tabText='<tr>';
			tag="{'id':'"+ele.id+"','title':'"+ele.tag+"'}";
			imgHtml='<img src="../../img/custcontrol.png" control="1" value="'+tag+'"/>';
			tabText+='<td style="width:40%;">';
			tabText+=imgHtml+'|'+ele.id;
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_MoveUp(this);">上移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_MoveDown(this);">下移</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_BtnEdit(\''+tag.replace(/'/g,"\\\'")+'\');">编辑</a>';
			tabText+='</td>';
			tabText+='<td style="width:10%;">';
			tabText+='<a href="#" onclick="fn_BtnDel(this);">删除</a>';
			tabText+='</td>';
			tabText+='</tr>';
			$('#btntbList').append(tabText);
		});
	}
}
function fn_MoveUp(obj){
	var objParentTR;
	var prevTR;
	objParentTR = $(obj).parent().parent(); 
	prevTR = objParentTR.prev(); 
	if (prevTR.length > 0) { 
		prevTR.insertAfter(objParentTR); 
	}
	fn_BtnSave();
}
function fn_MoveDown(obj){
	var objParentTR;
	var nextTR;
	objParentTR = $(obj).parent().parent();
	nextTR = objParentTR.next();
	if (nextTR.length > 0) {
		nextTR.insertBefore(objParentTR); 
	}
	fn_BtnSave();
}
function fn_BtnEdit(jsonObj){
	var url;
	var btnId;
	var btnObj;
	var hid;
	var jsonObj;
	var initObj;
	hid=$('#hid').val();
	jsonObj=jsonObj.replace(/'/g,"\"");
	btnObj=JSON.parse(jsonObj);
	btnId=btnObj.id;
	initObj={"buttons":encodeURIComponent($("#buttons").val()),"controls":encodeURIComponent($("#controls").val())};
	url='../form/SF_201503180001.form?id='+hid+'&btnid='+btnId;
	openWin(1000,400,'编辑按钮',url,encodeURIComponent(JSON.stringify(initObj)),fn_BtnSave);
}
function fn_BtnDel(jsonObj){
	var tr;
	tr=$(jsonObj).parent().parent();
	if(confirm("确定删除?")==true)
	{
		tr.remove();
	}
	fn_BtnSave();
}
function fn_BtnSave(){
	var value;
	var jsonObj;
	var btnlist;
	var conlist;
	var taglist;
	var contsText;
	var contsList;
	var butsText;
	var butsList;
	var tagsText;
	var tagsList;
	var blInfo;
	var blHasList;
	btnlist=[];
	conlist=[];
	taglist=[];
	tagsList=null;
	butsList=null;
	contsList=null;
	contsText=$('#controls').val();
	butsText=$('#buttons').val();
	tagsText=$('#tags').val();
	if(""!=butsText){
		butsList=JSON.parse(butsText);
	}
	if(""!=contsText){
		contsList=JSON.parse(contsText);
	}
	if(""!=tagsText)
	{
		tagsList=JSON.parse(tagsText);
	}
	if(null!=butsList && null!=contsList)
	{
		$(contsList).each(function(i,item) {
			blInfo=false;
			$(butsList).each(function(index, ele) {
                if(item.control[0].id==ele.id)
				{
					blInfo=true;
					return false;
				}
            });
			if(false==blInfo)
			{
				conlist.push(item);
			}
		});
		$('#btntbList tr').each(function(index, ele) {
			value=$($(ele.cells[0]).children()[0]).attr('value');
			value=value.replace(/'/g,"\"");
			jsonObj=JSON.parse(value);
			if(""!=jsonObj.id)
			{
				$(butsList).each(function(i, item) {
					if(item.id==jsonObj.id)
					{
						btnlist.push(item);
						return false;
					}
                });
				$(contsList).each(function(i, item) {
					if(item.control[0].id==jsonObj.id)
					{
						conlist.push(item);
						return false;
					}
                });
			}
		});
		$('#buttons').val(JSON.stringify(btnlist));
		$('#controls').val(JSON.stringify(conlist));
		if(null!=tagsList){
			$(tagsList).each(function(index, ele) {
                if(ele.tag!='%3C%25%40%20taglib%20prefix%3D%22fm%22%20uri%3D%22%2Fform%22%20%25%3E')
				{
					taglist.push(ele);
				}
            });
			blHasList=false;
			$(taglist).each(function(index, ele) {
                if(ele.tag=='%3C%25%40%20taglib%20prefix%3D%22fm%22%20uri%3D%22%2Fform%22%20%25%3E')
				{
					blHasList=true;
					return false;
				}
            });
			if(blHasList==false)
			{
				taglist.push({"tag":"%3C%25%40%20taglib%20prefix%3D%22fm%22%20uri%3D%22%2Fform%22%20%25%3E"});
			}
			$('#tags').val(JSON.stringify(taglist));
		}
	}
	initButton();
}
function initHidden(){
	var hidVal;
	var hidList;
	var tableInfo;
	var imgObj;
	var jsonObj;
	hidVal=$('#hiddenform').val();
	if(""!=hidVal)
	{
		hidList=JSON.parse(hidVal);
		$('#hidtbList').css('width','100%');
		$('#hidtbList').empty();
		$(hidList).each(function(index, ele) {
			imgObj=$(decodeURIComponent(ele.hiddenform).replace(/%20/g," "));
			jsonObj=JSON.parse(imgObj.attr('value').replace(/'/g,"\""));
			tableInfo='<tr>';
			tableInfo+='<td width="80%">';
			tableInfo+=decodeURIComponent(ele.hiddenform).replace(/%20/g," ")+'|'+jsonObj.id;
			tableInfo+='</td>';
			tableInfo+='<td width="10%">';
			tableInfo+='<a href="#" onclick="fn_HidEdit(\''+imgObj.attr('value').replace(/'/g,"\\\'")+'\');">编辑</a>';
			tableInfo+='</td>';
			tableInfo+='<td width="10%">';
			tableInfo+='<a href="#" onclick="fn_HidDel(\''+imgObj.attr('value').replace(/'/g,"\\\'")+'\');">删除</a>';
			tableInfo+='</td>';
			tableInfo+='</tr>';
			$('#hidtbList').append(tableInfo);
		});
	}
}
function fn_HidEdit(value){
	ue.execCommand('customcontrol',value,0);
}
function fn_HidDel(value){
	var hidVal;
	var hidList;
	var imgObj;
	var list;
	if(confirm("确定删除?")==true)
	{
		list=[];
		hidVal=$('#hiddenform').val();
		hidList=JSON.parse(hidVal);
		$(hidList).each(function(index, ele) {
			imgObj=$(decodeURIComponent(ele.hiddenform).replace(/%20/g,' '));
			if(value!=imgObj.attr('value'))
			{
				list.push(ele);
			}
    	});
		$('#hiddenform').val(JSON.stringify(list));
		initHidden();
	}
}
function fn_saveButton(btnlist,clist){
	$('#buttons').val(JSON.stringify(btnlist));
	$('#controls').val(JSON.stringify(clist));
	initButton();
}
function setButtonList(id){
	openWin(1000,400,'按钮设置','buttonlist.form?id='+id,null);
	return false;
}
function setAuthorityList(id){
	openWin(1000,400,'权限设置','SF_201506300003.form?id='+id,null);
	return false;
}
function setTaglist(id){
	openWin(1000,400,'权限设置','taglist.form?id='+id,null);
	return false;
}
function setControlList(id){
	openWin(1000,400,'控件设置','controllist.form?id='+id,null);
	return false;
}
function setHiddenList(id){
	openWin(1000,400,'隐藏域设置','hiddenlist.form?id='+id,null);
	return false;
}
function setDparamList(id){
	openWin(1000,400,'动态参数设置','paramlist.form?id='+id,null);
	return false;
}
function setButtons(btnlist,controls){
	var list;
	var clist=[];
	list=JSON.parse($('#controls').val());
	$(list).each(function(i,item){
		if('button'!=item.control[0].type)
		{
			clist.push(item);
		}
	});
	$(controls).each(function(i,item){
		clist.push(item);
	});
	$(btnlist).each(function(i,item){
		$(item.firefunction).each(function(fi,fitem){
			$(fitem.parm).each(function(pi,pitem){
				pitem.value=encodeURIComponent(pitem.value);
			});
		});
	});
	$('#buttons').val(JSON.stringify(btnlist));
	$('#controls').val(JSON.stringify(clist));
	$('#btnsave').click();
}
function updateAuthority(items){
	$('#authoritys').val(JSON.stringify(items));
	$('#btnsave').click();
}
function updateTag(items){
	$('#tags').val(JSON.stringify(items));
	$('#btnsave').click();
}
function updateControl(items){
	$('#controls').val(JSON.stringify(items));
	$('#btnsave').click();
}
function updateHidden(items){
	$('#hiddenform').val(JSON.stringify(items));
	$('#btnsave').click();
}
function updateParam(items){
	$('#dyparm').val(JSON.stringify(items));
	$('#btnsave').click();
}
function fn_saveDynamicForm(){
	/*
	var js_source;
	var tabchar;
	js_source=$("textarea[name='dynamicform']").val().replace(/^\s+/, '').replace(/^\&nbsp;/,'');
	tabchar = '\t';
	
	if (js_source && js_source.charAt(0) === '<') {
		$("textarea[name='dynamicform']").val(style_html(js_source, 1, tabchar, 80));
	} else {
		$("textarea[name='dynamicform']").val(js_beautify(js_source, 1, tabchar));
	}
	*/
	return true;
}