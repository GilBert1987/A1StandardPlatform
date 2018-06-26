var ue;
$(document).ready(function() {
	var controlsTxt;
	var controlsObj;
	var controlObj;
	var allcontrols;
	var controlHtml;
	var jsonControlObjs;
	controlObj=null;
	controlHtml='';
	ue = UE.getEditor('dynamicform');
	allcontrols=$("#hAllcontrol").val();
	jsonControlObjs=JSON.parse(allcontrols);
	if(""==$("#jstag").html())
	{
		$("#jstag").html("<link type=\"text/css\" href=\"../../css/custform.css?ver=1.0\" rel=\"stylesheet\" />");
	}
	$(jsonControlObjs).each(function(index, controlEle) {
        controlHtml+='<li class="nav-header"  onclick="fn_openNav(this);">';
		controlHtml+='<em class="icon-plus icon-white"></em>';
		controlHtml+=controlEle.cn_name;
		controlHtml+='<div class="pull-right popover-info">';
		controlHtml+='<em class="icon-question-sign "></em>';
		controlHtml+='<div class="popover fade right">';
		controlHtml+='<div class="arrow"></div>';
		controlHtml+='<h3 class="popover-title">';
		controlHtml+='帮助';
		controlHtml+='</h3>';
		controlHtml+='<div class="popover-content">';
		controlHtml+='拖放组件到布局框内. 拖入后你可以设置。';
		controlHtml+='</div>';
		controlHtml+='</div>';
		controlHtml+='</div>';
		controlHtml+='</li>';
		controlHtml+='<li class="boxes">';
		$(controlEle.tldControl).each(function(tldIndex, tldEle) {
			var jsonObj;
			jsonObj=JSON.parse(tldEle.controlInfo);
            controlHtml+='<div class="box box-element ui-draggable">';
            controlHtml+='<a href="#close" class="remove label label-important" onclick="fn_del(this);">';
            controlHtml+='<em class="icon-remove icon-white"></em>删除';
            controlHtml+='</a>';
            controlHtml+='<span class="drag label"><em class="icon-move"></em>拖动</span>';
            controlHtml+='<span class="configuration">';
            controlHtml+='<button type="button" class="btn btn-mini btnEdit" role="button" data-toggle="modal" onclick="fn_edit(this);">编辑</button>';
            controlHtml+='</span>';
            controlHtml+='<div class="preview">';
			controlHtml+=tldEle.tldName;
            controlHtml+='</div>';
            controlHtml+='<div class="view">';
            controlHtml+='<div class="form-group">';
            controlHtml+='<label class="control-label">'+tldEle.tldName+'：</label>';
            controlHtml+='<img src="'+jsonObj.src+'" class="controlPic" control="1" value="{&#39;id&#39;:&#39;&#39;,&#39;title&#39;:&#39;'+jsonObj.title+'&#39;}"/>';
            controlHtml+='</div>';
            controlHtml+='</div>';
            controlHtml+='</div>';
        });
		controlHtml+='</li>';
    });
	$("#ulControls").append(controlHtml);
	$(".sidebar-nav .boxes").hide();
	$("#wizard").steps({
		bodyTag: "fieldset",
		enableCancelButton:false,
		showFinishButtonAlways: false,
		onStepChanging: function(event, currentIndex, newIndex) {
			var form;
			var imgValue;
			var imgObj;
			var blInfo;
			form=$('#pageForm');
			if(2==currentIndex){
				blInfo=true;
				$("#demo img[control=1]").each(function(i,ele){
					imgValue=$(ele).attr("value").replace(/\'/g,"\"");
					imgObj=JSON.parse(imgValue);
					if(""==imgObj.id)
					{
						blInfo=false;
						return false;
					}
				});
				if(false==blInfo)
				{
					$("#demo").validationEngine('showPrompt', '请设置控件!', 'pass','topLeft',true);
				}
				else
				{
					$("#demo").validationEngine('hide');
				}
				return blInfo;
			}
			if (currentIndex > newIndex) {
				return true;
			}	
			if (currentIndex < newIndex) {
				$(".body:eq(" + newIndex + ") label.error", form).remove();
				$(".body:eq(" + newIndex + ") .error", form).removeClass("error");
			}
			return form.validationEngine('validate');
		},
		onStepChanged: function(event, currentIndex, priorIndex) {
			if (currentIndex === 4) {
				toggleFinish(true);
			}
			else
			{
				toggleFinish(false);
			}
		},
		onFinishing: function(event, currentIndex) {
			var form;
			form=$('#pageForm');
			return form.validationEngine('validate');
		},
		onFinished: function(event, currentIndex) {
			var id;
			id=$("#hid").val();
			if(""==id)
			{
				$("#btnAdd").click();
			}
			else
			{
				$("#btnSave").click();
			}
		}
	});
	toggleFinish(false);
	if(""!=$("#hid").val()){
		controlsTxt=$("#controls").val();
		if(""!=controlsTxt)
		{
			controlsObj=JSON.parse(controlsTxt);
			$(controlsObj).each(function(index, ele) {
                if("dyeditform"==ele.control[0].id)
				{
					controlObj=ele;
					return false;
				}
            });
		}
		if(null!=controlObj)
		{
			$(".demo").html(decodeURIComponent(decodeURIComponent(controlObj.control[0].value)));
		}
		else
		{
			if(""!=$("#dynamicform").val()){
				$(".demo").html(decodeURIComponent($("#dynamicform").val()));
			}
		}
	}
	$(".sidebar-nav .lyrow").draggable({
		connectToSortable: ".demo",
		helper: "clone",
		handle: ".drag",
		start: function(e,t) {
			if (!startdrag) 
			{
				stopsave++;
			}
			startdrag = 1;
		},
		drag: function(e, t) {
			t.helper.width(400);
		},
		stop: function(e, t) {
			var moveObj;
			moveObj=null;
			t.helper.width("100%");
			t.helper.height("");
			if(t.helper[0].parentNode.classList.contains("column")==true)
			{
				t.helper.remove();
			}
			else
			{
				moveObj=t.helper.find(".icon-move");
				moveObj.parent().hide();
				$(".demo .column").sortable({
					opacity: .35,
					connectWith: ".column",
					start: function(e,t) {
						if (!startdrag)
						{
							stopsave--;
						}
						startdrag = 1;
					},
					stop: function(e,t) {
						if(stopsave>0) 
						{
							stopsave--;
						}
						startdrag = 0;
					},
				});
			}
			if(stopsave>0) 
			{
				stopsave--;
			}
			startdrag = 0;
		}
	});
	$(".sidebar-nav .box").draggable({
		connectToSortable: ".column",
		helper: "clone",
		handle: ".drag",
		start: function(e,t) {
			if (!startdrag) 
			{
				stopsave++;
			}
			startdrag = 1;
		},
		drag: function(e, t) {
			t.helper.width(400);
		},
		stop: function(e,t) {
			t.helper.height("");
			if(t.helper[0].parentNode.classList.contains("column")==true)
			{
				$("#hIsDelControl").val("1");
				//配置节点
				fn_configObj(t.helper);
			}
			if(stopsave>0)
			{
				stopsave--;
			}
			startdrag = 0;
		}
	});
	initContainer();
	removeElm();
});
function fn_openNav(obj){
	$(".sidebar-nav .boxes, .sidebar-nav .rows").hide();
	$(obj).next().slideDown(500);
}
function togglePrevious(enable) 
{
	toggleButton("previous", enable);
}
function toggleNext(enable) 
{
	toggleButton("next",enable);
}
function toggleFinish(enable)
{
	toggleButton("finish",enable);
}
function toggleButton(buttonId, enable)
{
	var button
    if (enable)
    {
        // Enable disabled button
		button = $("#wizard").find('a[href="#' + buttonId + '-disabled"]');
		button.attr("href", '#' + buttonId);
		button.parent().removeClass();
    }
    else
    {
        // Disable enabled button
        button = $("#wizard").find('a[href="#' + buttonId + '"]');
		button.attr("href", '#' + buttonId + '-disabled');
		button.parent().addClass("disabled");
    }
}
function handleJsIds() {
	handleModalIds();
	handleAccordionIds();
	handleCarouselIds();
	handleTabsIds()
}
function handleAccordionIds() {
	var e = $(".demo #myAccordion");
	var t = randomNumber();
	var n = "accordion-" + t;
	var r;
	e.attr("id", n);
	e.find(".accordion-group").each(function(e, t) {
		r = "accordion-element-" + randomNumber();
		$(t).find(".accordion-toggle").each(function(e, t) {
			$(t).attr("data-parent", "#" + n);
			$(t).attr("href", "#" + r)
		});
		$(t).find(".accordion-body").each(function(e, t) {
			$(t).attr("id", r)
		})
	})
}
function handleCarouselIds() {
	var e = $(".demo #myCarousel");
	var t = randomNumber();
	var n = "carousel-" + t;
	e.attr("id", n);
	e.find(".carousel-indicators li").each(function(e, t) {
		$(t).attr("data-target", "#" + n)
	});
	e.find(".left").attr("href", "#" + n);
	e.find(".right").attr("href", "#" + n)
}
function handleModalIds() {
	var e = $(".demo #myModalLink");
	var t = randomNumber();
	var n = "modal-container-" + t;
	var r = "modal-" + t;
	e.attr("id", r);
	e.attr("href", "#" + n);
	e.next().attr("id", n)
}
function handleTabsIds() {
	var e = $(".demo #myTabs");
	var t = randomNumber();
	var n = "tabs-" + t;
	e.attr("id", n);
	e.find(".tab-pane").each(function(e, t) {
		var n = $(t).attr("id");
		var r = "panel-" + randomNumber();
		$(t).attr("id", r);
		$(t).parent().parent().find("a[href=#" + n + "]").attr("href", "#" + r)
	})
}
function randomNumber() {
	return randomFromInterval(1, 1e6)
}
function randomFromInterval(e, t) {
	return Math.floor(Math.random() * (t - e + 1) + e)
}
function configurationElm(e, t) {
	$(".demo").delegate(".configuration > a", "click", function(e) {
		e.preventDefault();
		var t = $(this).parent().next().next().children();
		$(this).toggleClass("active");
		t.toggleClass($(this).attr("rel"))
	});
	$(".demo").delegate(".configuration .dropdown-menu a", "click", function(e) {
		e.preventDefault();
		var t = $(this).parent().parent();
		var n = t.parent().parent().next().next().children();
		t.find("li").removeClass("active");
		$(this).parent().addClass("active");
		var r = "";
		t.find("a").each(function() {
			r += $(this).attr("rel") + " "
		});
		t.parent().removeClass("open");
		n.removeClass(r);
		n.addClass($(this).attr("rel"))
	})
}
function removeElm() {
	$(".demo").delegate(".remove", "click", function(e) {
		e.preventDefault();
		$(this).parent().remove();
		if (!$(".demo .lyrow").length > 0) {
			clearDemo();
		}
	})
}
function clearDemo() {
	$(".demo").empty();
}
function changeStructure(e, t) {
	$("#download-layout ." + e).removeClass(e).addClass(t)
}
function cleanHtml(e) {
	$(e).parent().append($(e).children().html())
}
var stopsave = 0;
var startdrag = 0;
function initContainer(){
	$(".demo, .demo .column").sortable({
		connectWith: ".column",
		opacity: .35,
		handle: ".drag",
		start: function(e,t) {
			if (!startdrag) stopsave++;
			startdrag = 1;
		},
		stop: function(e,t) {
			if(stopsave>0) stopsave--;
			startdrag = 0;
		}
	});
	configurationElm();
	$('#hidObjAdd').off('click').on('click',function(){
		var funlist;
		funlist=[];
		funlist.push(savenewHidden);
		ue.options.retFunlist=funlist;
		ue.execCommand('customcontrol',"{'id':'','title':'fm:input'}",0);
	});
	$('#btnParamAdd').off('click').on('click',function(){
		fn_addparam();
	});
	$('#btnParamDel').off('click').on('click',function(){
		fn_delparam();
	});
	$('#btnAuthorityAdd').off('click').on('click',function(){
		fn_addAuthority();
	});
	$('#btnAuthorityDel').off('click').on('click',function(){
		fn_delAuthority();
	});
	$('#btnAuthorityAddItem').off('click').on('click',function(){
		fn_addAuthorityItem();
	});
	$('#btnAuthorityDelItem').off('click').on('click',function(){
		fn_delAuthorityItem();
	});
	$('#btnObjAdd').off('click').on('click',function(){
		var url;
		var hid;
		var initObj;
		hid=$('#hid').val();
		url='../form/SF_201503180001.form?id='+hid;
		initObj={"buttons":encodeURIComponent($("#buttons").val()),"controls":encodeURIComponent($("#controls").val())};
		openWin(1000,400,'添加按钮',url,encodeURIComponent(JSON.stringify(initObj)),null);
	});
	gridSystemGenerator();
	initHidden();
	initButton();
	initparams();
	initAuthoritys();
}
function gridSystemGenerator() {
	$(".lyrow .preview input").bind("keyup", function() {
		var e = 0;
		var t = "";
		var n = $(this).val().split(" ", 12);
		$.each(n, function(n, r) {
			e = e + parseInt(r);
			t += '<div class="span' + r + ' column"></div>'
		});
		if (e == 12) {
			$(this).parent().next().children().html(t);
			$(this).parent().prev().show()
		} else {
			$(this).parent().prev().hide()
		}
	});
}
function initAuthoritys(){
	var list;
	var strTemp;
	$('#athlist').empty();
	list=JSON.parse($('#authoritys').val());
	$(list).each(function(i,item){
		if(item.authority[0]!=null)
		{
			strTemp="<tr>";
			strTemp+="<td width='10%'>";
			strTemp+="<input name='selauthority' type='radio' value='"+item.authority[0].code+"' onclick='fn_checkItem(this.value);'>";
			strTemp+="</td>";
			strTemp+="<td  width='90%'>";
			strTemp+=item.authority[0].code;
			strTemp+="</td>";
			strTemp+="</tr>";
			$('#athlist').append(strTemp);
		}
	});
}
function fn_checkAll(input){
	$("input[name=selauthobj]").each(function(index, element) {
		element.checked=input.checked;
	});
	clickItem();
}
function clickItem(){
	var selValue;
	selValue='';
	$("input[name=selauthobj]").each(function(index, element) {
        if(element.checked==true)
		{
			selValue=(selValue==''?$(element).val():selValue+','+$(element).val());
		}
    });
	$('#hauthorityitem').val(selValue);
}
function addAuthority(name){
	var list;
	var item;
	var show=[];
	var clist;
	var controllist=[];
	list=JSON.parse($('#authoritys').val());
	clist=JSON.parse($('#controls').val());
	show.push({"showtype":"0","authinfo":""});
	$(clist).each(function(i,item){
		controllist.push({"id":item.control[0].id,"show":show});
	});
	item={"authority":[{"code":name,"control":controllist}]};
	list.push(item);
	$('#authoritys').val(JSON.stringify(list));
	$('#athobjlist').empty();
	initAuthoritys();
}
function addAuthorityItem(item){
	var td;
	var hidden;
	var list;
	var authority;
	var authitemid;
	var input;
	var listInfo;
	input=null;
	authority=$('#hauthority').val();
	authitemid=$('#hauthorityitem').val();
	list=JSON.parse($('#authoritys').val());
	listInfo=authitemid.split(',');
	$(list).each(function(ai,authitem){
		if(authority==authitem.authority[0].code)
		{
			$(authitem.authority[0].control).each(function(ci,citem){
				if(authitemid.indexOf(citem.id)!=-1)
				{
					for(var i=0;i<listInfo.length;i++)
					{
						if(listInfo[i]==citem.id)
						{
							listInfo.splice(i,1);
							break;
						}
					}
					citem.show.push(item);
				}
			});
			return false;
		}
	});
	if(0!=listInfo.length)
	{
		for(var i=0;i<listInfo.length;i++)
		{
			listInfo[i]={"id":listInfo[i],show:[item]};
		}
		$(list).each(function(ai,authitem){
			if(authority==authitem.authority[0].code)
			{
				for(var i=0;i<listInfo.length;i++)
				{
					authitem.authority[0].control.push(listInfo[i]);
				}
				return false;
			}
		});
	}
	$("#checkall").removeAttr("checked");
	$('#authoritys').val(JSON.stringify(list));
	fn_checkItem(authority);
}
function fn_addAuthority(){
	openWin(800,300,'添加权限','addauthority.form',null,null);
	return false;
}
function fn_delAuthority(){
	var authority;
	var list;
	var newlist=[];
	authority=$('#hauthority').val();
	if(''!=authority)
	{
		list=JSON.parse($('#authoritys').val());
		$(list).each(function(ai,authitem){
			if(authority!=authitem.authority[0].code)
			{
				newlist.push(authitem);
			}
		});	
	}
	$('#authoritys').val(JSON.stringify(newlist));
	$('#athobjlist').empty();
	initAuthoritys();
	return false;
}
function fn_addAuthorityItem(){
	var authority;
	var item;
	authority=$('#hauthority').val();
	item=$('#hauthorityitem').val();
	if(''!=item)
	{
		openWin(800,300,'添加权限项','SF_201506300002.form',null);
	}
	else
	{
		alert("请选择控件");
	}
	return false;
}
function fn_delAuthorityItem(){
	var jsonobj;
	var tabobj;
	var showtype;
	var authinfo;
	var item;
	var list=[];
	var slist;
	var authority;
	var authitemid;
	var hauthority;
	var input;
	var list;
	input=null;
	hauthority=$('#hauthority').val();
	list=JSON.parse($('#authoritys').val());
	if($('input[name="selobj"]:checked').size()!=0)
	{
		jsonobj=$('input[name="selobj"]:checked');
		for(var i=0;i<jsonobj.size();i++)
		{
			tabobj=$($($(jsonobj.get(i)).parent()).parent()).parent()[0];
			if(tabobj.rows.length > 1)
			{
				item=JSON.parse($(jsonobj.get(i)).val());
				authitemid=$($(jsonobj.get(i)).parent().parent().parent().parent().parent().parent().parent().parent().parent().parent().children().get(1)).text();
				authority=JSON.parse($('#authority').val());
				$(authority.control).each(function(ci,citem){
					if(authitemid==citem.id)
					{
						sitem=[];
						slist=[];
						$(citem.show).each(function(si,sitem){
							if((sitem.showtype==item.showtype && encodeURIComponent(decodeURIComponent(sitem.authinfo))==encodeURIComponent(decodeURIComponent(item.authinfo)))==false)
							{
								slist.push(sitem);
							}
						});
						citem.show=slist;
						return false;
					}
				});
				$('#authority').val(JSON.stringify(authority));
			}
			else
			{
				alert("权限项仅有一个不能删除!");
				break;
			}
		}
		$(list).each(function(ai,authitem){
			if(hauthority==authitem.authority[0].code)
			{
				authitem.authority[0]=authority;
				return false;
			}
		});
		$('#authoritys').val(JSON.stringify(list));
		initControls();
	}
	return false;
}
function fn_checkItem(type){
	var authoritys;
	var jsonAuthoritys;
	var sourceType;
	sourceType=$("#hauthority").val();
	$("#hauthority").val(type);
	if(""!=sourceType)
	{
		$('#authority').val('[]');
	}
	authoritys=$('#authoritys').val();
	if(""!=authoritys)
	{
		jsonAuthoritys=JSON.parse(authoritys);
		if(null!=jsonAuthoritys){
			$(jsonAuthoritys).each(function(index, eleAuth) {
                if(type==eleAuth.authority[0].code)
				{
					$('#authority').val(JSON.stringify(eleAuth.authority[0]));
					return false;
				}
            });
		}
	}
	initControls();
}
function initControls(){
	var list;
	var authobj;
	var controlauthObj;
	var strTemp;
	var strTable;
	$('#athobjlist').empty();
	$('#hauthorityitem').val('');
	$("#checkall").removeAttr("checked");
	list=JSON.parse($('#controls').val());
	authobj=JSON.parse($('#authority').val());
	$(list).each(function(i,item){
		if(null!=item.control[0] && null != item.control[0].id && authobj != null)
		{
			controlauthObj=null;
			$(authobj.control).each(function(ai,aitem){
				if(aitem.id==item.control[0].id)
				{
					controlauthObj=aitem;
					return false;
				}
			});
			if(controlauthObj==null)
			{
				controlauthObj={"show":[{"showtype":"0","authinfo":""}]};
			}
			strTemp="<tr>";
			strTemp+="<td width='10%'>";
			strTemp+="<input name='selauthobj' type='checkbox' value='"+item.control[0].id+"' onclick='clickItem();' />";
			strTemp+="</td>";
			strTemp+="<td width='20%'>";
			strTemp+=item.control[0].id;
			strTemp+="</td>";
			strTemp+="<td  width='70%'>";
			strTemp+="<table border='0' width='100%' cellpadding='0' cellspacing='0' >";
			strTemp+="<tr>";
			strTemp+="<td width='10%' >";
			strTemp+="选择";
			strTemp+="</td>";
			strTemp+="<td width='15%' >";
			strTemp+="读取设置";
			strTemp+="</td>";
			strTemp+="<td width='75%' >";
			strTemp+="参数信息";
			strTemp+="</td>";
			strTemp+="</tr>";
			strTemp+="<tr>";
			strTemp+="<td colspan='3'>";
			if(null != controlauthObj && null!= controlauthObj.show){
				strTemp+="<input type='hidden' value='[";
				$(controlauthObj.show).each(function(si,sitem){
					strTemp+='{"showtype":"'+sitem.showtype+'","authinfo":"'+sitem.authinfo+'"},';
				});
				if($(controlauthObj.show).size()>0)
				{
					strTemp=strTemp.substring(0,strTemp.length-1);
				}
				strTemp+="]' />";
			}
			strTemp+="<table border='1' width='100%' cellpadding='0' cellspacing='0' style=\"border-color:#000000;\" >";
			strTable="";
			if(null != controlauthObj && null!= controlauthObj.show)
			{
				$(controlauthObj.show).each(function(ci,citem){
					strTable+="<tr>";
					strTable+="<td width='10%' >";
					strTable+="<input name='selobj' type='checkbox' value='{\"showtype\":\""+citem.showtype+"\",\"authinfo\":\""+encodeURIComponent(citem.authinfo)+"\"}' />";
					strTable+="</td>";
					strTable+="<td width='15%' key='"+citem.showtype+"' >";
					strTable+= citem.showtype=='2'?'读写':(citem.showtype=='1'?'只读':'不显示');
					strTable+="</td>";
					strTable+="<td width='75%'>";
					strTable+="<textarea readonly='readonly' style='width:90%;height:60px;'>";
					strTable+= decodeURIComponent(citem.authinfo);
					strTable+="</textarea>";
					strTable+="</td>";
					strTable+="</tr>";
				});
			}
			strTemp+=strTable;
			strTemp+="</table>";
			strTemp+="</td>";
			strTemp+="</tr>";
			$('#athobjlist').append(strTemp);
		}
	});
}
function initparams(){
	var list;
	var strTemp;
	$('#paramlist').empty();
	list=JSON.parse($('#dyparm').val());
	$(list).each(function(i,item){
		if(item.key !=null)
		{
			strTemp="<tr>";
			strTemp+="<td width='20%' style='text-align:center;'>";
			strTemp+="<input name='selparam' type='radio' value='"+item.key+"' onclick='clickTable(this);'>";
			strTemp+="</td>";
			strTemp+="<td  width='40%' style='text-align:center;'>";
			strTemp+=item.key;
			strTemp+="</td>";
			strTemp+="<td  width='40%' style='text-align:center;'>";
			strTemp+=item.value;
			strTemp+="</td>";
			strTemp+="</tr>";
			$('#paramlist').append(strTemp);
		}
	});
}
function addparam(param){
	var list;
	list=JSON.parse($('#dyparm').val());
	list.push(param);
	$('#dyparm').val(JSON.stringify(list));
	initparams();
}
function fn_addparam(){
	openWin(800,300,'添加动态参数','addparam.form',null);
	return false;
}
function fn_delparam(){
	var list;
	var newlist=[];
	var paramitem;
	if($('#hparam').val()!='')
	{
		list=JSON.parse($('#dyparm').val());
		paramitem=$('#hparam').val();
		$(list).each(function(i,item){
			if(paramitem != item.key)
			{
				newlist.push(item);
			}
		});
		$('#dyparm').val(JSON.stringify(newlist));
		initparams();
	}
	return false;
}
function clickTable(input){
	$('#hparam').val($(input).val());
}
function fn_del(obj)
{
	var imgsObj;
	var imgObj;
	var imgId;
	var bindId;
	var controlsTxt;
	var controlsObj;
	var controlObj;
	var icontrolId;
	var dataTabTxt;
	var dataTabObj;
	var jsonObj;
	icontrolId=-1;
	imgObj=null;
	imgId="";
	bindId="";
	controlsObj=null;
	imgsObj=$(obj).parent().find("img");
	controlsTxt=$("#controls").val();
	dataTabTxt=$("#hdatabase").val();
	if(0!=imgsObj.size())
	{
		$(imgsObj).each(function(i, ele) {
            if("1"==$(ele).attr("control"))
			{
				imgObj=ele;
				return false;
			}
        });
		if(null!=imgObj)
		{
			jsonObj=JSON.parse($(imgObj).attr("value").replace(/\'/g,"\""));
			imgId=jsonObj.id;
			if(""!=imgId)
			{
				if(""!=controlsTxt)
				{
					controlsObj=JSON.parse(controlsTxt);
					if(null!=controlsObj)
					{
						$(controlsObj).each(function(i,ele){
							if(jsonObj.id==ele.control[0].id)
							{
								icontrolId=i;
								bindId=ele.control[0].dataBind;
								return false;
							}
						});
						if(-1!=icontrolId)
						{
							controlsObj.splice(icontrolId,1);
						}
						$("#controls").val(JSON.stringify(controlsObj));
					}
				}
				if(""!=dataTabTxt)
				{
					dataTabObj=JSON.parse(dataTabTxt);
					if(null!=bindId && "" !=bindId)
					{
						for(var param in dataTabObj)
						{
							if(bindId==param)
							{
								dataTabObj[param].isbind="0";
								break;
							}
						}
						$("#hdatabase").val(JSON.stringify(dataTabObj));
					}
				}
			}
		}
	}
	$(obj).parent().remove();
}
function fn_edit(obj)
{
	$("#hIsDelControl").val("0");
	fn_configObj($(obj).parent().parent());
}
function fn_configObj(obj)
{
	var jsonObj;
	var paramObj;
	var imgObj;
	jsonObj=null;
	imgObj=null;
	obj.find("img").each(function(i,ele){
		if("1"==$(ele).attr("control")){
			imgObj=ele;
			jsonObj=JSON.parse($(ele).attr("value").replace(/'/g,'"'));
			return false;
		}
	});
	if(null!=jsonObj)
	{
		ue.options.retFunlist=[function(retObj){
			var jsonObj;
			if(null!=imgObj && null != retObj){
				jsonObj=JSON.parse($(imgObj).attr("value").replace(/'/g,'"'));
				jsonObj.id=retObj.control.id;
				$(imgObj).attr("value",JSON.stringify(jsonObj).replace(/"/g,'\''));
				currText=$("#currControl").val();
				$($(imgObj).parent().parent().find("label")[0]).html(decodeURIComponent(retObj.control.infoname)+"：");
			}
		}];
		ue.execCommand('customcontrol',JSON.stringify(jsonObj).replace(/"/g,'\''),0);
	}
	else
	{
		obj.remove();
	}
}
function fn_delControl(obj){
	var isDel;
	isDel=$("#hIsDelControl").val();
	if("1"==isDel)
	{
		obj.remove();
	}
}
function fn_saveTableInfo(){
	var list;
	var plist;
	fn_getTabSrc();
	plist=JSON.parse($('#dyparm').val());
	list=JSON.parse($('#authoritys').val());
	$(list).each(function(ai,authitem){
		$(authitem.authority[0].control).each(function(ci,citem){
			$(citem.show).each(function(si,sitem){
				sitem.authinfo=encodeURIComponent(decodeURIComponent(sitem.authinfo));
			});
		});
	});
	$('#authoritys').val(JSON.stringify(list));
	$('#dyparm').val(JSON.stringify(plist));
	return true;
}

function fn_getButton(btnid,btnlist)
{
	var btnObj;
	btnObj=null;
	$(btnlist).each(function(i,ele){
		if(btnid==ele.id)
		{
			btnObj=ele;
			return false;
		}
	});
	return btnObj;
}

function fn_getTabSrc(){
	var t;
	var formatSrc;
	$("#download-layout").children().html($("#demo").html());
	t=$("#download-layout").children();
	t.find(".preview, .configuration, .drag, .remove").remove();
	t.find(".lyrow").addClass("removeClean");
	t.find(".box-element").addClass("removeClean");
	t.find(".lyrow .lyrow .lyrow .lyrow .lyrow .removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".lyrow .lyrow .lyrow .lyrow .removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".lyrow .lyrow .lyrow .removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".lyrow .lyrow .removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".lyrow .removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".removeClean").each(function() {
		cleanHtml(this)
	});
	t.find(".removeClean").remove();
	$("#download-layout .column").removeClass("ui-sortable");
	$("#download-layout .row-fluid").removeClass("clearfix").children().removeClass("column");
	if ($("#download-layout .container").length > 0) {
		changeStructure("row-fluid", "row")
	}
	$("#dynamicform").val($("#download-layout").html());
}
function changeStructure(e, t) {
	$("#download-layout ." + e).removeClass(e).addClass(t)
}
function cleanHtml(e) {
	$(e).parent().append($(e).children().html())
}
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
		$('#btnList').empty();
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
			$('#btnList').append(tabText);
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
	openWin(1000,400,'编辑按钮',url,encodeURIComponent(JSON.stringify(initObj)),null);
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
	var allHtml;
	allHtml="";
	hidVal=$('#hiddenform').val();
	if(""!=hidVal)
	{
		hidList=JSON.parse(hidVal);
		$('#hidList').css('width','100%');
		$('#hidList').empty();
		$(hidList).each(function(index, ele) {
			imgObj=$(decodeURIComponent(ele.hiddenform).replace(/%20/g," "));
			jsonObj=JSON.parse(imgObj.attr('value').replace(/'/g,"\""));
			tableInfo='<tr>';
			tableInfo+='<td width="80%">';
			tableInfo+=decodeURIComponent(ele.hiddenform).replace(/class=\"controlPic\"/g,"").replace(/%20/g," ")+'|'+jsonObj.id;
			tableInfo+='</td>';
			tableInfo+='<td width="10%">';
			tableInfo+='<a href="#" onclick="fn_HidEdit(\''+imgObj.attr('value').replace(/'/g,"\\\'")+'\');">编辑</a>';
			tableInfo+='</td>';
			tableInfo+='<td width="10%">';
			tableInfo+='<a href="#" onclick="fn_HidDel(\''+imgObj.attr('value').replace(/'/g,"\\\'")+'\');">删除</a>';
			tableInfo+='</td>';
			tableInfo+='</tr>';
			allHtml+=tableInfo;
		});
		$('#hidList').append(allHtml);
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