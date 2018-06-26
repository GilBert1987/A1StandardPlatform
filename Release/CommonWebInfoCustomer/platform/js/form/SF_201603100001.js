$(document).ready(function(e) {
	var initValue;
	var initData;
	var jsonData;
	var roleData;
	var conlist;
	var list;
	var readlist;
	var pushlist;
	pushlist=[];
	list=[];
	readlist=[];
	initValue=parent.$("#hinitdata").val();
	if(""!=initValue && null!=initValue)
	{
		initValue=decodeURIComponent(initValue);
		jsonData=JSON.parse(initValue);
		if(""!=initData)
		{
			initData=JSON.parse(jsonData.initData);
			if(null!=initData.forminfo)
			{
				$("#controls").val(JSON.stringify(initData.forminfo.conlist));
			}
		}
		if(null!=jsonData.roleInfo && ""!=jsonData.roleInfo && "[]" != jsonData.roleInfo)
		{
			$('#authoritys').val(jsonData.roleInfo);
		}
		else
		{
			conlist=JSON.parse($("#controls").val());
			$(conlist).each(function(index, conele) {
				var authinfo;
				authinfo='';
				switch(conele.control[0].id)
				{
					case 'btnsubmit':
						authinfo='{"type":0,"expree":"${param.fiid!=\'\'&&null!=param.fiid&&null==param.inid}","sysid":""}';
						authinfo=encodeURIComponent(authinfo);
						break;
					case 'btnnext':
						authinfo='{"type":0,"expree":"${param.inid!=\'\'&&null!=param.inid}","sysid":""}';
						authinfo=encodeURIComponent(authinfo);
						break;
				};
        		list.push({"id":conele.control[0].id,"show":[{"authinfo":authinfo,"showtype":"2"}]})
    		});
			$(conlist).each(function(index, conele) {
				if(conele.control[0] != null && "hidden"==conele.control[0].type)
				{
					readlist.push({"id":conele.control[0].id,"show":[{"authinfo":"","showtype":"2"}]})
				}
				else
				{
        			readlist.push({"id":conele.control[0].id,"show":[{"authinfo":"","showtype":"0"}]})
				}
			});
			pushlist.push({"type":0,"authority":list});
			pushlist.push({"type":1,"authority":readlist});
			$('#authoritys').val(JSON.stringify(pushlist));
		}
	}
});

function fn_checkItem(type){
	var authoritys;
	var jsonAuthoritys;
	var sourceType;
	sourceType=$("#hchecktype").val();
	$("#hchecktype").val(type);
	if(""!=sourceType)
	{
		fn_saveauthority(sourceType);
	}
	authoritys=$('#authoritys').val();
	if(""!=authoritys)
	{
		jsonAuthoritys=JSON.parse(authoritys);
		if(null!=jsonAuthoritys){
			$(jsonAuthoritys).each(function(index, eleAuth) {
                if(type==eleAuth.type)
				{
					$('#authority').val(JSON.stringify(eleAuth.authority));
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
	list=JSON.parse($('#controls').val());
	authobj=JSON.parse($('#authority').val());
	$(list).each(function(i,item){
		if(null!=item.control[0] && null != item.control[0].id && authobj != null)
		{
			controlauthObj=null;
			$(authobj).each(function(ai,aitem){
				if(aitem.id==item.control[0].id)
				{
					controlauthObj=aitem;
					return false;
				}
			});
			if(controlauthObj==null)
			{
				controlauthObj={"show":[{"showtype":"2","authinfo":""}]};
			}
			strTemp="<tr>";
			strTemp+="<td width='20%'>";
			strTemp+="<input name='selauthobj' type='checkbox' value='"+item.control[0].id+"' onclick='clickItem(this);' />";
			strTemp+="</td>";
			strTemp+="<td width='30%'>";
			strTemp+=item.control[0].id;
			strTemp+="</td>";
			strTemp+="<td  width='50%'>";
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
function clickItem(input){
	$('#hauthorityitem').val($(input).val());
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
	var slist=[];
	var authority;
	var authitemid;
	var input;
	input=null;
	authority=$('#hauthority').val();
	if($('input[name="selobj"]:checked').size()!=0)
	{
		jsonobj=$('input[name="selobj"]:checked');
		tabobj=$($(jsonobj.parent()).parent()).parent()[0];
		if(tabobj.rows.length > 1)
		{
			item=JSON.parse($(jsonobj).val());
			authitemid=$($(jsonobj).parent().parent().parent().parent().parent().parent().parent().parent().parent().parent().children().get(1)).text();
			authority=JSON.parse($('#authority').val());
			$(authority).each(function(ci,citem){
				if(authitemid==citem.id)
				{
					sitem=[];
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
			initControls();
		}
		else
		{
			alert("权限项仅有一个不能删除!");
		}
	}
	return false;
}
function addAuthorityItem(item){
	var td;
	var hidden;
	var authority;
	var authitemid;
	var input;
	var bladd;
	input=null;
	bladd=false;
	authitemid=$('#hauthorityitem').val();
	authority=JSON.parse($('#authority').val());
	$(authority).each(function(ci,citem){
		if(authitemid==citem.id)
		{
			bladd=true;
			citem.show.push(item);
			return false;
		}
	});
	if(bladd==false)
	{
		authority.push({'id':authitemid,show:[item]});
	}
	$('#authority').val(JSON.stringify(authority));
	initControls();
}
function fn_saveauthority(type)
{
	var authoritys;
	var jsonauthoritys;
	var descType;
	var conlist;
	var jsonObj;
	var list;
	var finlist;
	list=[];
	finlist=[];
	descType=-1;
	jsonauthoritys=null;
	authoritys=$('#authoritys').val();
	if(""!=authoritys)
	{
		jsonauthoritys=JSON.parse(authoritys);
	}
	$(jsonauthoritys).each(function(index, element) {
        if(element.type==type)
		{
			descType=index;
			return false;
		}
    });
	conlist=JSON.parse($("#controls").val());
	jsonObj=JSON.parse($('#authority').val());
	$(conlist).each(function(index, conele) {
        list.push({"id":conele.control[0].id,"show":[{"authinfo":"","showtype":"2"}]})
    });
	$(list).each(function(index, eleInfo) {
        $(jsonObj).each(function(i, eleCtl) {
            if(eleInfo.id==eleCtl.id)
			{
				eleInfo.show=eleCtl.show;
				return false;
			}
        });
    });
	if(-1!=descType)
	{
		$(jsonauthoritys).each(function(index, ele) {
            if(ele.type!=Number(type))
			{
				finlist.push(ele);
			}
        });
		finlist.push({"type":Number(type),"authority":list});
		$('#authoritys').val(JSON.stringify(finlist));
	}
	$('#authority').val('[]');
}
function fn_save(){
	var type;
	var list;
	type=$("#hchecktype").val();
	fn_saveauthority(type);
	list=JSON.parse($('#authoritys').val());
	parent.window.fn_saveStartRole(list);
	closeWin();
}