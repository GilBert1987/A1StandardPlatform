$(document).ready(function(){
	initAuthoritys();
});
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
							if((sitem.showtype==item.showtype && encodeURIComponent(decodeURIComponent(sitem.authinfo))==encodeURIComponent(decodeURIComponent(decodeURIComponent(item.authinfo))))==false)
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
function saveButton(){
	var list;
	var slist=[];
	list=JSON.parse($('#authoritys').val());
	$(list).each(function(ai,authitem){
		$(authitem.authority[0].control).each(function(ci,citem){
			$(citem.show).each(function(si,sitem){
				sitem.authinfo=encodeURIComponent(decodeURIComponent(sitem.authinfo));
			});
		});
	});
	parent.window.updateAuthority(list);
	closeWin();
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