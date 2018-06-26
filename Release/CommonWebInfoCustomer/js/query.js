$(document).ready(function(){
	var jsonobj;
	var selectValue;
	var iValue;
	document.domain=location.hostname;
	fn_initTableWidth("dataTable");
	$('#searchName').find('option').each(function(i,item){
		selectValue=$(item).val().replace(/\\/g,'');
		jsonobj=parseJSON(selectValue);
		if(jsonobj!=null)
		{
			if(jsonobj.key==$('#querySearchName').val())
			{
				$(item).attr('selected','selected');
				return;
			}
		}
	});
	if($('#searchName').val()!='')
	{
		changeSearchName(document.getElementById("searchName"));
	}
	if($('#selvalue').attr('type')!=null)
	{
		$('#selvalue').val($('#querySearchValue').val());
	}
	else{
		$('#selvalue').find('option').each(function(i,item){
			if($(item).val()==$('#querySearchValue').val())
			{
				$(item).attr('selected','selected');
				return;
			}
		});
	}
	if($('#querySearchExpree').val()!='')
	{
		$('#selexperee').val($('#querySearchExpree').val());
	}
	$(".fht-cell").remove();
});
function changePage(pageNum){
	$('#pageNum').val(pageNum);
	$('#pageForm').submit();
}

function getPage(pageNum){
	//处理手动输入页码时错误问题。
	$('#pageNum').val(pageNum);
	$('#pageForm').submit();
}

function submitForm(){
	$('#querySearchExpree').val($('#selexperee').val());
	$('#querySearchValue').val($('#selvalue').val());
	$('#pageNum').val(1);
	$('#pageForm').submit();
}

function changeSearchName(select){
	var jsonData;
	var strexpree;
	var selectValue;
	if(select.value!='')
	{
		selectValue=select.value.replace(/\\/g,'');
		jsonData=parseJSON(selectValue);
		$('#querySearchName').val(jsonData.key);
		strexpree="";
		if(jsonData.type=='string')
		{
			strexpree= '<option value="=">等于</option>';
			strexpree+='<option value="like">包含</option>';
			strexpree+='<option value="in">分组</option>';
		}
		if(jsonData.type=='number')
		{
			strexpree= '<option value="=">等于</option>';
			strexpree+='<option value=">">大于</option>';
			strexpree+='<option value="<">小于</option>';
			strexpree+='<option value=">=">大于等于</option>';
			strexpree+='<option value="<=">小于等于</option>';
			strexpree+='<option value="!=">不等于</option>';
		}
		if(jsonData.type=='datetime')
		{
			strexpree= '<option value="=">等于</option>';
			strexpree+='<option value=">">大于</option>';
			strexpree+='<option value="<">小于</option>';
			strexpree+='<option value=">=">大于等于</option>';
			strexpree+='<option value="<=">小于等于</option>';
			strexpree+='<option value="!=">不等于</option>';
		}
		$('#selexperee').empty();
		$('#selexperee').append(strexpree);
		$('#tdvalue').empty();
		switch(jsonData.searchType)
		{
			case 'input':
				$('#tdvalue').append('<input id="selvalue" class="form-control" type="input" value="" />');
				break;
			case 'datetime':
				$('#tdvalue').append('<input id="selvalue" type="text" class="form-control" onClick="WdatePicker()" style="background: #fff url(../../js/My97DatePicker/skin/datePicker.gif) no-repeat right;border: #999 1px solid;"/>');
				break;
			case 'select':
				createSelect(jsonData);
				break;
			default:$('#tdvalue').append('<input id="selvalue" type="input" value="" />');
		}
	}
	else
	{
		$('#selexperee').empty();
		strexpree= '<option value="=">等于</option>';
		strexpree+='<option value="like">包含</option>';
		$('#selexperee').append(strexpree);
		$('#tdvalue').empty();
		$('#tdvalue').append('<input id="selvalue" type="input" value="" class="form-control" />');
		$('#querySearchName').val('');
		$('#querySearchExpree').val('');
		$('#querySearchValue').val('');
	}
}

function createSelect(jsonData){
	var strSelect;
	var strInfo;
	var jsonObj;
	strInfo=jsonData.searchInfo;
	strInfo=strInfo.replace(/\+/g,' ');
	strInfo=decodeURIComponent(strInfo);
	jsonObj=null;
	$.ajax({
		async:false,
		url: "../../ajax/query/search",
		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		type:"POST",
		datatype:'json',
		data:{"info":strInfo,"type":jsonData.searchDataType,"dataname":jsonData.sysid},
		success: function(result){
			strSelect = "<select id='selvalue' class='form-control'>";
			strSelect +="<option value=''>请选择</option>";
			jsonObj=parseJSON(result);
			if(jsonObj!=null)
			{
				for(var i=0;i<jsonObj.length;i++){
					strSelect +="<option value='"+jsonObj[i].value+"'>"+jsonObj[i].code+"</option>";
				}
			}
			strSelect +="</select>";
			$('#tdvalue').append(strSelect);
		}
	});
	
}

function fn_editcol(span,id){
	$('#'+id).show();
	$(span).hide();
}

function fn_mergeTd(colname,istart,iend,isedit,colNum,rowNum,nextrowNum){
	var rowspan;
	var strSource;
	var strNow;
	var tdSouce;
	var tdNow;
	if(rowNum>=istart && nextrowNum<iend)
	{
		tdSouce=$($('#dataTable').find('tr')[rowNum]).find('td')[colNum];
		tdNow=$($('#dataTable').find('tr')[nextrowNum]).find('td')[colNum];
		if("0"==isedit)
		{
			strSource=$(tdSouce).html();
			strNow=$(tdNow).html();
		}
		else
		{
			strSource=$('#'+colname+rowNum+'editsqpan').html();
			strNow=$('#'+colname+nextrowNum+'editsqpan').html();
		}
		if(strSource==strNow)
		{
			rowspan=Number($(tdSouce).attr('rowspan')==null?1:$(tdSouce).attr('rowspan'));
			$(tdSouce).attr('rowspan',rowspan+1);
			$(tdNow).hide();
			fn_mergeTd(colname,istart,iend,isedit,colNum,rowNum,nextrowNum+1);
		}
		else
		{
			fn_mergeTd(colname,istart,iend,isedit,colNum,nextrowNum,nextrowNum+1);
		}
	}
}

function fn_mergeTable(value){
	var colname;
	var mergerefer;
	var mergecolumn;
	var isedit;
	var i;
	var istart;
	var iend;
	var colNum;
	var colNumber;
	var irow;
	colNum=0;
	colNumber=0;
	if(value.length>0)
	{
		for(i=0;i<value.length;i++)
		{
			colname=value[i].colname;
			mergerefer=value[i].mergerefer;
			mergecolumn=value[i].mergecolumn;
			isedit=value[i].isedit;
			if("1"==mergerefer)
			{
				$($('#dataTable').find('tr')[0]).find('td').each(function(tdindex,tditem){
					if($(tditem).attr('key')==mergecolumn)
					{
						colNumber=tdindex;
						return;
					}
				});
			}
			if($('#dataTable').find('tr').size()!=0)
			{
				$($('#dataTable').find('tr')[0]).find('td').each(function(tdindex,tditem){
					if($(tditem).attr('key')==colname)
					{
						colNum=tdindex;
						return;
					}
				});
				if("1"!=mergerefer)
				{
					fn_mergeTd(colname,0,$('#dataTable').find('tr').size(),isedit,colNum,0,1);
				}
				else
				{
					irow=$('#dataTable').find('tr').size();
					i=0;
					while(i<irow)
					{
						istart=i;
						iend=$($('#dataTable')[0].rows[istart].cells[colNumber]).attr('rowspan')==null?1:Number($($('#dataTable')[0].rows[istart].cells[colNumber]).attr('rowspan'));
						fn_mergeTd(colname,istart,(istart+iend),isedit,colNum,istart,(istart+1));
						i+=iend;
					}
				}
			}
		}
	}
}
function fn_getchecked(colname){
	var key;
	var value;
	var optionsInfo;
	var row;
	var tdInfo;
	var tdValue;
	key='';
	tdValue="";
	if(null!=$('#dataTable').data()['bootstrap.table'] && null !=$('#dataTable').data()['bootstrap.table'].data)
	{
		$('input[name=querycheckBox]').each(function(i,item){
			if(item.checked==true)
			{
				row=$($(item).parent()).parent().attr("data-index");
				if(null!=row && null!=$('#dataTable').data()['bootstrap.table'].data[row][colname])
				{
					tdInfo=$('#dataTable').data()['bootstrap.table'].data[row][colname].trim();
					if(-1!=tdInfo.indexOf("<span"))
					{
						tdValue=$(tdInfo).text();
					}
					else
					{
						tdValue=tdInfo;
					}
					if(''==key)
					{
						key=tdValue;
					}
					else
					{
						key=key+','+tdValue;
					}
				}
			}
		});
	}
	return key;
}
function fn_delTable(dataName,tableName,key,value,callback){
	var list;
	var blInfo;
	blInfo=false;
	if(value.indexOf(',')!=-1)
	{
		$.ajax({
			url: "../../ajax/query/delete",
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			type:"POST",
			datatype:'json',
			data:{"dataname":dataName,"table":tableName,"key":key,"value":value},
			success: function(result){
				callback(result);
			}
		});
	}
	else
	{
		list=value.split(',');
		$(list).each(function(i,eleitem){
				$.ajax({
					async:false,
					url: "../../ajax/query/delete",
					contentType:"application/x-www-form-urlencoded;charset=UTF-8",
					type:"POST",
					datatype:'json',
					data:{"dataname":dataName,"table":tableName,"key":key,"value":value},
					success: function(result){
						if(false==result.msg){
							blInfo=true;
							return false;
						}
					}
				});
		});
		if(false==blInfo)
		{
			callback({"msg":"sucess"});
		}
		else
		{
			callback({"msg":"error"});
		}
	}
}
function fn_selAll(checkObj){
	var hSelAll;
	hSelAll=$(checkObj).prop("checked");
	if(true==hSelAll)
	{
		$("input[type=checkbox][name=querycheckBox]").each(function(i,ele){
			$(ele).prop("checked",true);
		});
	}
	else
	{
		$("input[type=checkbox][name=querycheckBox]").each(function(i,ele){
			$(ele).prop("checked",false);
		});
	}
}
function searchQuery(){
	$('#searchButton').click();
}
function _exportExcel(){
	var key;
	key=createKey("com","sc_fileinfo","id");
	_exportExcelSub(key,0);
}
function _exportExcelSub(key,type){
	var jsonObj;
	var ajaxUrl;
	var jsonResult;
	var inputValue;
	var inputName;
	var strValue;
	var queryString;
	queryString=$("#queryString").val();
	jsonObj={};
	strValue="<input type='hidden' name='_exportExcel' value='"+type+"'/>";
	$("#pageExcelForm").html("");
	for(i=0;i<document.forms[0].elements.length;i++){
		inputValue=$(document.forms[0].elements[i]).val();
		inputName=$(document.forms[0].elements[i]).attr('name');
		if(inputName!=null && '' != inputName && 'undefined' != inputName)
		{
			jsonObj[inputName]=inputValue;
		}
	}
	if(0==type)
	{
		strValue+="<input type='hidden' name='_exportExcelKey' value='"+key+"'/>";
		for(i=0;i<document.forms[0].elements.length;i++){
			inputValue=$(document.forms[0].elements[i]).val();
			inputName=$(document.forms[0].elements[i]).attr('name');
			if(inputName!=null && '' != inputName && 'undefined' != inputName)
			{
				strValue+="<input type='hidden' name='"+inputName+"' value='"+inputValue+"'/>";
			}
		}
		$("#pageExcelForm").html(strValue);
		$("#pageExcelForm").submit();
		$("#pageExcelForm").html("");
		setTimeout(function(){_exportExcelSub(key,1);},1500);
	}
	if(1==type)
	{
		jsonObj.exportExcelKey=key;
		ajaxUrl="../../ajax/query/exportExcel"+(""!=queryString?"?"+queryString:"");
		$.ajax({
			url: ajaxUrl,
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			type:"POST",
			datatype:'json',
			data:jsonObj,
			success: function(result){
				if(""!=result){
					jsonResult=JSON.parse(result);
					console.log(jsonResult);
					if(5==jsonResult.type && 1==jsonResult.status)
					{
						strValue+="<input type='hidden' name='_exportExcelKey' value='"+key+"'/>";
						$("#pageExcelForm").html(strValue);
						$("#pageExcelForm").submit();
						$("#pageExcelForm").html("");
					}
					else
					{
						if(0!=jsonResult.type && -1!=jsonResult.status){
							setTimeout(function(){_exportExcelSub(key,1);},1500);
						}
					}
				}
			}
		});
	}
}