$(document).ready(function(){
	init_data();
});
function fn_add(id){
	openWin(800,300,'添加列信息','SF_201412250002.form?id='+id,null,null);
	return false;
}
function fn_addExtend(id){
	openWin(1200,500,'添加扩展列信息','SF_2017022109430001.form?authority=SF_2017022109430001_add&id='+id,null,null);
	return false;
}
function fn_editExtend(id){
	var colId;
	var colInfo;
	var colList;
	var blInfo;
	var colObj;
	blInfo=false;
	colObj=null;
	colId=$('#hcolumn').val();
	if(""!=colId)
	{
		colInfo=$("#columns").val();
		colList=JSON.parse(colInfo);
		$(colList).each(function(i,item){
			if('1'==item.typeid && colId==item.key)
			{
				colObj=item;
				blInfo=true;
				return false;
			}
		});
		if(true==blInfo)
		{
			openWin(1200,500,'编辑扩展列信息','SF_2017022109430001.form?authority=SF_2017022109430001_add&colid='+colId+'&id='+id,encodeURIComponent(JSON.stringify(colObj)),null);
		}
		else
		{
			alert("当前列不是扩展列!");
		}
	}
	else
	{
		alert("请选择扩展列!");
	}
	return false;
}
function fn_save(){
     var list;
	 list=JSON.parse($('#columns').val());
     parent.window.updateColumn(list);
     return false;
}
function addColumn(colObj){
	var list;
	list=JSON.parse($('#columns').val());
	list.push(colObj);
	$('#columns').val(JSON.stringify(list));
	init_data();
}
function editColumn(colObj){
	var list;
	list=JSON.parse($('#columns').val());
	$(list).each(function(i, ele) {
        if(ele.key==colObj.key)
		{
			ele.dataSysId=colObj.dataSysId;
			ele.titleSysId=colObj.titleSysId;
			ele.dataSqlInfo=colObj.dataSqlInfo;
			ele.titleSqlInfo=colObj.titleSqlInfo;
			ele.titColId=colObj.titColId;
			ele.currColId=colObj.currColId;
			ele.currValId=colObj.currValId;
			ele.relColId = colObj.relColId;
			return false;
		}
    });
	$('#columns').val(JSON.stringify(list));
	init_data();
}
function fn_del(){
	var list;
	var plist;
	var key;
	plist=[];
	if(confirm("确定删除列信息?")==true)
	{
		key=$('#hcolumn').val();
		if(''!=key)
		{
			list=JSON.parse($('#columns').val());
			$(list).each(function(i,item){
				if(key != item.key)
				{
					plist.push(item);
				}
			});
			$('#columns').val(JSON.stringify(plist));
			init_data();
		}
	}
  return false;
}
function init_data(){
	var collist;
	var strTemp;
	$('#collist').empty();
	$('#colitemlist').empty();
	$('#editInfo').empty();
	collist=JSON.parse($('#columns').val());
	$(collist).each(function(i,item){
		if(""!=item.key)
		{
			if("0"==item.typeid)
			{
				strTemp="<tr>";
				strTemp+="<td width='20%'>";
				strTemp+="<input name='selcol' type='radio' value='"+item.key+"' onclick='clickTable(this);'>";
				strTemp+="</td>";
				strTemp+="<td  width='80%'>";
				strTemp+=item.name;
				strTemp+="</td>";
				strTemp+="</tr>";
			}
			if("1"==item.typeid)
			{
				strTemp="<tr>";
				strTemp+="<td width='20%'>";
				strTemp+="<input name='selcol' type='radio' value='"+item.key+"' onclick='clickExtendTable(this);'>";
				strTemp+="</td>";
				strTemp+="<td  width='80%'>";
				strTemp+=item.name;
				strTemp+="</td>";
				strTemp+="</tr>";
			}
		 	$('#collist').append(strTemp);
		}
	});
}
function clickExtendTable(input){
	var key;
	var strColTable;
	strColTable='';
	key=$(input).val();
	$('#hcolumn').val(key);
	$('#colitemlist').empty();
	$('#editInfo').empty();
	strColTable+='<tr>';
	strColTable+='<td>';
	strColTable+="<input name='selitem' type='radio' value='base' onclick='clickExtendItem(this);'>";
	strColTable+='</td>';
	strColTable+='<td>';
	strColTable+="基本设置";
	strColTable+='</td>';
	strColTable+='</tr>';
	strColTable+='<tr>';
	strColTable+='<td>';
	strColTable+="<input name='selitem' type='radio' value='title' onclick='clickExtendItem(this);'>";
	strColTable+='</td>';
	strColTable+='<td>';
	strColTable+="标题";
	strColTable+='</td>';
	strColTable+='</tr>';
	$('#colitemlist').append(strColTable); 
}
function clickTable(input){
  var key;
  var strColTable;
  strColTable='';
  key=$(input).val();
  $('#hcolumn').val(key);
  $('#colitemlist').empty();
  $('#editInfo').empty();
  strColTable+='<tr>';
  strColTable+='<td>';
  strColTable+="<input name='selitem' type='radio' value='base' onclick='clickItem(this);'>";
  strColTable+='</td>';
  strColTable+='<td>';
  strColTable+="基本设置";
  strColTable+='</td>';
  strColTable+='</tr>';
  strColTable+='<tr>';
  strColTable+='<td>';
  strColTable+="<input name='selitem' type='radio' value='search' onclick='clickItem(this);'>";
  strColTable+='</td>';
  strColTable+='<td>';
  strColTable+="搜索";
  strColTable+='</td>';
  strColTable+='</tr>';
  strColTable+='<tr>';
  strColTable+='<td>';
  strColTable+="<input name='selitem' type='radio' value='quick' onclick='clickItem(this);'>";
  strColTable+='</td>';
  strColTable+='<td>';
  strColTable+="快速查询";
  strColTable+='</td>';
  strColTable+='</tr>';
  strColTable+='<tr>';
  strColTable+='<td>';
  strColTable+="<input name='selitem' type='radio' value='title' onclick='clickItem(this);'>";
  strColTable+='</td>';
  strColTable+='<td>';
  strColTable+="标题";
  strColTable+='</td>';
  strColTable+='</tr>';
  strColTable+='<tr>';
  strColTable+='<td>';
  strColTable+="<input name='selitem' type='radio' value='unit' onclick='clickItem(this);'>";
  strColTable+='</td>';
  strColTable+='<td>';
  strColTable+="合并";
  strColTable+='</td>';
  strColTable+='</tr>';
  $('#colitemlist').append(strColTable); 
}
function clickExtendItem(input){
	var key;
	var list;
	var itemkey;
	var colitem;
	var strTable;
	colitem=null;
	itemkey=$(input).val();
	key=$('#hcolumn').val();
	$('#hcolumnitem').val(itemkey);
	list=JSON.parse($('#columns').val());
	$(list).each(function(i,item){
		if(key==item.key)
		{
			colitem=item;
			return false;
		}
	});
	$('#editInfo').empty();
	if(null !=colitem){
		switch(itemkey)
		{
			case "base":clickExtendbase(key,colitem);break;
			case "title":clickExtendtitle(key,colitem.title);break;
		};
	}
	strTable="<table width='100%'><tr><td><input type='button' id='btncolitem' value='保存' onclick='fn_colItemSave(\""+key+"\",\""+itemkey+"\");' /></td></tr></table>"
	$('#editInfo').append(strTable);
}
function clickItem(input){
	var key;
	var list;
	var itemkey;
	var colitem;
	var strTable;
	colitem=null;
	itemkey=$(input).val();
	key=$('#hcolumn').val();
	$('#hcolumnitem').val(itemkey);
	list=JSON.parse($('#columns').val());
	$(list).each(function(i,item){
		if(key==item.key)
		{
			colitem=item;
			return false;
		}
	});
	$('#editInfo').empty();
	if(null !=colitem){
		switch(itemkey)
		{
			case "base":clickbase(key,colitem);break;
			case "quick":clickquick(key,colitem.quick);break;
			case "title":clicktitle(key,colitem.title);break;
			case "search":clicksearch(key,colitem.search);break;
			case "unit":clickunit(key,colitem.unit);break;
		};
	}
	strTable="<table width='100%'><tr><td><input type='button' id='btncolitem' value='保存' onclick='fn_colItemSave(\""+key+"\",\""+itemkey+"\");' /></td></tr></table>"
	$('#editInfo').append(strTable);
}
function clickExtendbase(key,colitem){
	var strTable;
	strTable='<table width="100%">';
	strTable+='<tr>';
	strTable+='<td width="25%">';
	strTable+='列字段';
	strTable+='</td>';
	strTable+='<td width="75%">';
	strTable+='<input id="colkey" value="'+colitem.key+'"/>';
	strTable+='</td>';
	strTable+='</tr>';
	strTable+='<tr>';
	strTable+='<td width="25%">';
	strTable+='列名称';
	strTable+='</td>';
	strTable+='<td width="75%">';
	strTable+='<input id="colname" value="'+colitem.name+'"/>';
	strTable+='</td>';
	strTable+='</tr>';
	strTable += '<tr>';
	strTable += '<td width="25%">';
	strTable += '数据汇总';
	strTable += '</td>';
	strTable += '<td width="75%">';
	strTable += '<select id="selgroup">';
	strTable += '<option value="0" ' + (colitem.grouptype == '0' ? 'selected="selected"' : '') + '>默认</option>';
	strTable += '<option value="1" ' + (colitem.grouptype == '1' ? 'selected="selected"' : '') + '>SUM</option>';
	strTable += '<option value="2" ' + (colitem.grouptype == '2' ? 'selected="selected"' : '') + '>MAX</option>';
	strTable += '<option value="3" ' + (colitem.grouptype == '3' ? 'selected="selected"' : '') + '>MIN</option>';
	strTable += '<option value="4" ' + (colitem.grouptype == '4' ? 'selected="selected"' : '') + '>AVG</option>';
	strTable += '<option value="5" ' + (colitem.grouptype == '5' ? 'selected="selected"' : '') + '>Count</option>';
	strTable += '<option value="6" ' + (colitem.grouptype == '6' ? 'selected="selected"' : '') + '>DisCount</option>';
	strTable += '</select>';
	strTable += '</td>';
	strTable += '</tr>';
	strTable+='</table>';
	$('#editInfo').append(strTable);
}
function clickbase(key,colitem){
	var strTable;
	strTable='<table width="100%">';
	strTable += '<tr>';
	strTable += '<td width="25%">';
	strTable += '列字段';
	strTable += '</td>';
	strTable += '<td width="75%">';
	strTable += '<input id="colkey" value="'+colitem.key+'"/>';
	strTable += '</td>';
	strTable += '</tr>';
	strTable += '<tr>';
	strTable += '<td width="25%">';
	strTable += '列名称';
	strTable += '</td>';
	strTable += '<td width="75%">';
	strTable += '<input id="colname" value="'+colitem.name+'"/>';
	strTable += '</td>';
	strTable += '</tr>';
	strTable += '<tr>';
	strTable += '<td width="25%">';
	strTable += '数据汇总';
	strTable += '</td>';
	strTable += '<td width="75%">';
	strTable += '<select id="selgroup">';
	strTable += '<option value="0" ' + (colitem.grouptype == '0' ? 'selected="selected"' : '') + '>默认</option>';
	strTable += '<option value="1" ' + (colitem.grouptype == '1' ? 'selected="selected"' : '') + '>SUM</option>';
	strTable += '<option value="2" ' + (colitem.grouptype == '2' ? 'selected="selected"' : '') + '>MAX</option>';
	strTable += '<option value="3" ' + (colitem.grouptype == '3' ? 'selected="selected"' : '') + '>MIN</option>';
	strTable += '<option value="4" ' + (colitem.grouptype == '4' ? 'selected="selected"' : '') + '>AVG</option>';
	strTable += '<option value="5" ' + (colitem.grouptype == '5' ? 'selected="selected"' : '') + '>Count</option>';
	strTable += '<option value="6" ' + (colitem.grouptype == '6' ? 'selected="selected"' : '') + '>DisCount</option>';
	strTable += '</select>';
	strTable += '</td>';
	strTable += '</tr>';
	strTable += '<tr>';
	strTable += '<td width="25%">';
	strTable += '数据类型';
	strTable += '</td>';
	strTable += '<td width="75%">';
	strTable += '<select id="selcoltype">';
	strTable += '<option value="string" '+(colitem.type=='string'?'selected="selected"':'')+'>字符串</option>';
	strTable += '<option value="number" '+(colitem.type=='number'?'selected="selected"':'')+'>数字</option>';
	strTable += '<option value="datetime" '+(colitem.type=='datetime'?'selected="selected"':'')+'>日期</option>';
	strTable += '</select>'
	strTable += '</td>';
	strTable += '</tr>';
	strTable += '</table>';
	$('#editInfo').append(strTable);
}
function clickquick(key,quick){
	var strTable;
	if(null!=quick)
	{
		strTable='<table width="100%">';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='显示';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selshow">';
		strTable+='<option value="0" '+(quick.isshow=='0'?'selected="selected"':'')+'>否</option>';
		strTable+='<option value="1" '+(quick.isshow=='1'?'selected="selected"':'')+'>是</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='显示名称';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selname">';
		strTable+='<option value="0" '+(quick.showname=='0'?'selected="selected"':'')+'>否</option>';
		strTable+='<option value="1" '+(quick.showname=='1'?'selected="selected"':'')+'>是</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='显示表达式';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="seloperation">';
		strTable+='<option value="0" '+(quick.showoperation=='0'?'selected="selected"':'')+'>否</option>';
		strTable+='<option value="1" '+(quick.showoperation=='1'?'selected="selected"':'')+'>是</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='</table>';
		$('#editInfo').append(strTable);
	}
}
function clickExtendtitle(key,title){
	var strTable;
	if(null!=title)
	{
		strTable='<table width="100%">';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='宽度';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<input id="colwidth" value="'+title.width+'"/>';
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='排序';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<input id="colorder" value="'+title.order+'"/>';
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='对齐方式';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selalign">';
		strTable+='<option value="left" '+(title.align=='left'?'selected="selected"':'')+'>左</option>';
		strTable+='<option value="center" '+(title.align=='center'?'selected="selected"':'')+'>中</option>';
		strTable+='<option value="right" '+(title.align=='right'?'selected="selected"':'')+'>右</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '二级表头';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<input type="text" id="sehead" value="' + (null == title.sehead ? "" : title.sehead) + '" />';
		strTable += '</td>';
		strTable += '</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='显示';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selcolshow">';
		strTable+='<option value="1" '+(title.isshow=='1'?'selected="selected"':'')+'>显示</option>';
		strTable+='<option value="0" '+(title.isshow=='0'?'selected="selected"':'')+'>不显示</option>';
		strTable += '</select>';
		strTable+='</td>';
		strTable += '</tr>';
		strTable+='</table>';
		$('#editInfo').append(strTable);
	}
}
function clicktitle(key,title){
	var strTable;
	if(null!=title)
	{
		strTable  = '<table width="100%">';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '宽度';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<input id="colwidth" value="'+title.width+'"/>';
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '排序';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<input id="colorder" value="'+title.order+'"/>';
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '对齐方式';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<select id="selalign">';
		strTable += '<option value="left" '+(title.align=='left'?'selected="selected"':'')+'>左</option>';
		strTable += '<option value="center" '+(title.align=='center'?'selected="selected"':'')+'>中</option>';
		strTable += '<option value="right" '+(title.align=='right'?'selected="selected"':'')+'>右</option>';
		strTable += '</select>'
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '显示';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<select id="selcolshow">';
		strTable += '<option value="1" '+(title.isshow=='1'?'selected="selected"':'')+'>显示</option>';
		strTable += '<option value="0" '+(title.isshow=='0'?'selected="selected"':'')+'>不显示</option>';
		strTable += '</select>'
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '二级表头';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<input type="text" id="sehead" value="' + (null==title.sehead ? "" : title.sehead ) + '" />';
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '<tr>';
		strTable += '<td width="25%">';
		strTable += '编辑';
		strTable += '</td>';
		strTable += '<td width="75%">';
		strTable += '<select id="selcoledit">';
		strTable += '<option value="0" '+(title.isedit=='0'?'selected="selected"':'')+'>否</option>';
		strTable += '<option value="1" '+(title.isedit=='1'?'selected="selected"':'')+'>是</option>';
		strTable += '</select>'
		strTable += '</td>';
		strTable += '</tr>';
		strTable += '</table>';
		$('#editInfo').append(strTable);
	}
}
function clicksearch(key,search){
	var strTable;
	if(null!=search)
	{
		strTable='<table width="100%">';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='类型';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selseartype">';
		strTable+='<option value="input" '+(search.type=='input'?'selected="selected"':'')+'>输入框</option>';
		strTable+='<option value="select" '+(search.type=='select'?'selected="selected"':'')+'>下拉框</option>';
		strTable+='<option value="datetime" '+(search.type=='datetime'?'selected="selected"':'')+'>日期框</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='排序';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<input id="colorder" value="'+search.order+'"/>';
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='显示';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selcolshow">';
		strTable+='<option value="1" '+(search.isshow=='1'?'selected="selected"':'')+'>显示</option>';
		strTable+='<option value="0" '+(search.isshow=='0'?'selected="selected"':'')+'>不显示</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='数据类型';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selcoledit">';
		strTable+='<option value="" '+(search.datatype==''?'selected="selected"':'')+'>无</option>';
		strTable+='<option value="sql" '+(search.datatype=='sql'?'selected="selected"':'')+'>sql类型</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='Sql信息';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<textarea id="txtSqlInfo" style="width:90%;height:175px;">';
		strTable+= decodeURIComponent(search.searchinfo);
		strTable+='</textarea>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='</table>';
		$('#editInfo').append(strTable);
	}
}
function clickunit(key,unit){
	var strTable;
	var list;
	var parmlist;
	parmlist=[];
	list=JSON.parse($('#columns').val());
	$(list).each(function(i,item){
		if(key!=item.key)
		{
			parmlist.push({"key":item.key,"name":item.name});
		}
	});
	if(null!=unit)
	{
		strTable='<table width="100%">';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='是否合并';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selmerge">';
		strTable+='<option value="0" '+(unit.merge=='0'?'selected="selected"':'')+'>否</option>';
		strTable+='<option value="1" '+(unit.merge=='1'?'selected="selected"':'')+'>是</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='是否依赖';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selmergerefer">';
		strTable+='<option value="0" '+(unit.mergerefer=='0'?'selected="selected"':'')+'>否</option>';
		strTable+='<option value="1" '+(unit.mergerefer=='1'?'selected="selected"':'')+'>是</option>';
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='<tr>';
		strTable+='<td width="25%">';
		strTable+='依赖列信息';
		strTable+='</td>';
		strTable+='<td width="75%">';
		strTable+='<select id="selmergecolumn">';
		for(var i=0;i<parmlist.length;i++)
		{
			strTable+='<option value="'+parmlist[i].key+'" '+(unit.mergecolumn==parmlist[i].key?'selected="selected"':'')+'>'+parmlist[i].name+'</option>';
		}
		strTable+='</select>'
		strTable+='</td>';
		strTable+='</tr>';
		strTable+='</table>';
		$('#editInfo').append(strTable);
	}
}
function fn_colItemSave(colkey,colinfo){
	var list;
	var parmlist;
	parmlist=[];
	list=JSON.parse($('#columns').val());
	$(list).each(function(i,item){
		if(colkey!=item.key)
		{
			parmlist.push(item);
		}
	});
	switch(colinfo){
		case "base":parmlist=savebase(colkey,parmlist);break;
		case "quick":parmlist=savequick(colkey,parmlist);break;
		case "title":parmlist=savetitle(colkey,parmlist);break;
		case "search":parmlist=savesearch(colkey,parmlist);break;
		case "unit":parmlist=saveunit(colkey,parmlist);break;
	};
	$('#columns').val(JSON.stringify(parmlist));
}
function savebase(key,list){
	var list;
	var colObj;
	list=JSON.parse($('#columns').val());
	colObj=null;
	$(list).each(function(i,item){
		if(key ==item.key)
		{
			colObj=item;
			return false;
		}
	});
	if(colObj!=null)
	{
		if("0"==colObj.typeid || null==colObj.typeid)
		{
			colObj.key=$('#colkey').val();
			colObj.name=$('#colname').val();
			colObj.type=$('#selcoltype').val();
			colObj.grouptype=$('#selgroup').val();
		}
		if("1"==colObj.typeid)
		{
			colObj.key=$('#colkey').val();
			colObj.name=$('#colname').val();
			colObj.grouptype=$('#selgroup').val();
		}
	}
	else
	{
		colObj={"key":$('#colkey').val(),"name":$('#colname').val(),"type":$('#selcoltype').val(),"grouptype":$('#selgroup').val()};
		list.push(colObj);
	}
	return list;
}
function savequick(key,list){
	var list;
	var colObj;
	list=JSON.parse($('#columns').val());
	colObj=null;
	$(list).each(function(i,item){
		if(key ==item.key)
		{
			colObj=item;
			return false;
		}
	});
	if(colObj!=null)
	{
		colObj.quick={"showoperation":$('#seloperation').val(),"showname":$('#selname').val(),"isshow":$('#selshow').val()};
	}
	return list;
}
function savetitle(key,list){
	var list;
	var colObj;
	list=JSON.parse($('#columns').val());
	colObj=null;
	$(list).each(function(i,item){
		if(key ==item.key)
		{
			colObj=item;
			return false;
		}
	});
	if(colObj!=null)
	{
		if("0"==colObj.typeid || null==colObj.typeid)
		{
		    colObj.title = { "width": $('#colwidth').val(), "isshow": $('#selcolshow').val(), "order": $('#colorder').val(), "isedit": $('#selcoledit').val(), "align": $('#selalign').val(), "sehead": $("#sehead").val()};
		}
		if("1"==colObj.typeid)
		{
		    colObj.title = { "width": $('#colwidth').val(), "isshow": $('#selcolshow').val(), "order": $('#colorder').val(), "align": $('#selalign').val(),"sehead":$("#sehead").val() };
		}
	}
	return list;
}
function saveunit(key,list){
	var list;
	var colObj;
	list=JSON.parse($('#columns').val());
	colObj=null;
	$(list).each(function(i,item){
		if(key ==item.key)
		{
			colObj=item;
			return false;
		}
	});
	if(colObj!=null)
	{
		colObj.unit={"merge":$('#selmerge').val(),"mergerefer":$('#selmergerefer').val(),"mergecolumn":$('#selmergecolumn').val()};
	}
	return list;
}
function savesearch(key,list){
	var list;
	var colObj;
	list=JSON.parse($('#columns').val());
	colObj=null;
	$(list).each(function(i,item){
		if(key ==item.key)
		{
			colObj=item;
			return false;
		}
	});
	if(colObj!=null)
	{
		colObj.search={"type":$('#selseartype').val(),"isshow":$('#selcolshow').val(),"order":$('#colorder').val(),"datatype":$('#selcoledit').val(),"searchinfo":encodeURIComponent(decodeURIComponent($('#txtSqlInfo').val()))};
	}
	return list;
}