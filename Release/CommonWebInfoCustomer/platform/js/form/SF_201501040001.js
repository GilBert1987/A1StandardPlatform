$(document).ready(function(e) {
	init_data();
});

function init_data(){
	var list;
	var strTemp;
	var key;
	var nodeItem;
	var sqlData;
	var btnlist;
	key=$('#hselItem').val();
	nodeItem=null;
	strTemp='';
	$('#tblist').empty();
	if(''!=key)
	{
    	list=JSON.parse($('#listNode').val());
		$(list).each(function(i,item){
			if(key==item.level)
			{
				nodeItem=item;
			}
		});
	}
	if(null==nodeItem)
	{
		if(''!=$('#hnewinfo').val())
		{
			nodeItem=JSON.parse($('#hnewinfo').val());
		}
	}
	if(null!=nodeItem)
	{
		sqlData=nodeItem.sqlInfo;
		btnlist=nodeItem.listButton;
		$('#name').val(nodeItem.name);
		$("#level").val(nodeItem.level);
		$("#type").val(nodeItem.type);
		$("#option").val(nodeItem.option);
		$("#nodeid").val(nodeItem.nodeid);
		$("#nodetext").val(nodeItem.nodetext);
		$("#css").val(nodeItem.css);
		$("#target").val(nodeItem.target);
		$("#icon").val(nodeItem.icon);
		$("#selfun").val(decodeURIComponent(nodeItem.selfun));
		$("#iconopen").val(nodeItem.iconOpen);
		$("#iconclose").val(nodeItem.iconClose);
		$("#async").val(nodeItem.async);
		if(null!=sqlData)
		{
			$("#sqldatabase").val(sqlData.database);
			$("#datatype").val(sqlData.type);
			$("#sqlinfo").val(decodeURIComponent(sqlData.sqlInfo));
		}
		if(null!=btnlist){
			$(btnlist).each(function(i,eleitem) {
                strTemp+='<tr>';
				strTemp+='<td width="10%">';
				strTemp+='<input type="radio" name="selbutton" value="'+eleitem.name+'" onclick="fn_click(this);" />';
				strTemp+='</td>';
				strTemp+='<td width="20%">';
				strTemp+=eleitem.name;
				strTemp+='</td>';
				strTemp+='<td width="10%">';
				strTemp+=eleitem.order;
				strTemp+='</td>';
				strTemp+='<td width="60%">';
				strTemp+=decodeURIComponent(eleitem.fun);
				strTemp+='</td>';
				strTemp+='</tr>';
            });
			$('#tblist').append(strTemp);
		}
		$("#url").val(nodeItem.url);
		$("#remark").val(nodeItem.remark);
	}
}
function fn_addbutton(){
	openWin(800,300,'添加按钮','SF_201501040002.form',null,null);
	return false;
}
function fn_delbutton(){
	var key;
	var itemkey;
	var nodeItem;
	var btnlist;
	var newbtnlist;
	newbtnlist=[];
	key=$('#hselItem').val();
	itemkey=$('#hselkey').val();
	if(''!=key)
	{
    	list=JSON.parse($('#listNode').val());
		$(list).each(function(i,item){
			if(key==item.level)
			{
				nodeItem=item;
			}
		});
	}
	else
	{
		if(""!=$('#hnewinfo').val())
		{
			nodeItem=JSON.parse($('#hnewinfo').val());
		}
	}
	if(null!=nodeItem && ''!=itemkey)
	{
		btnlist=nodeItem.listButton;
		if(null!=btnlist){
			$(btnlist).each(function(i,eleitem) {
				if(itemkey!=eleitem.name){
					newbtnlist.push(eleitem);
				}
			});
		}
		nodeItem.listButton=newbtnlist;
		if(''!=key){
			$('#listNode').val(JSON.stringify(list));
		}
		else{
			$('#hnewinfo').val(JSON.stringify(nodeItem));
		}
		init_data();
	}
	return false;
}
function fn_click(input){
	var key;
	key=$(input).val();
	$('#hselkey').val(key);
}
function addbtnInfo(btnInfo){
	var key;
	var itemkey;
	var nodeItem;
	var btnlist;
	var newbtnlist; 
	newbtnlist=[];
	key=$('#hselItem').val();
	itemkey=$('#hselkey').val();
	if(''!=key)
	{
		list=JSON.parse($('#listNode').val());
		$(list).each(function(i,item){
			if(key==item.level)
			{
				nodeItem=item;
			}
		});
	}
	else
	{
		if(""!=$('#hnewinfo').val())
		{
			nodeItem=JSON.parse($('#hnewinfo').val());
		}
		else{
			nodeItem={"listButton":[]};
		}
	}
	if(null!=nodeItem && null !=btnInfo)
	{
		btnlist=nodeItem.listButton;
		btnlist.push(btnInfo);
		if(''!=key){
			$('#listNode').val(JSON.stringify(list));
		}
		else{
			$('#hnewinfo').val(JSON.stringify(nodeItem));
		}
		init_data();
	}
}
function fn_save(){
	var key;
	var list;
	var nodeItem;
	nodeItem=null;
	key=$('#hselItem').val();
	list=JSON.parse($('#listNode').val());
	if(''!=key)
	{
		$(list).each(function(i,item){
			if(key==item.level)
			{
				nodeItem=item;
			}
		});
	}
	else{
		if(""!=$('#hnewinfo').val())
		{
			nodeItem=JSON.parse($('#hnewinfo').val());
		}
		else{
			nodeItem={"listButton":[]};
		}
		list.push(nodeItem);
	}
	if(null!=nodeItem)
	{
		nodeItem.name=$('#name').val();
		nodeItem.level=$("#level").val();
		nodeItem.type=$("#type").val();
		nodeItem.option=$("#option").val();
		nodeItem.nodeid=$("#nodeid").val();
		nodeItem.nodetext=$("#nodetext").val();
		nodeItem.css=$("#css").val();
		nodeItem.target=$("#target").val();
		nodeItem.icon=$("#icon").val();
		nodeItem.selfun=$("#selfun").val();
		nodeItem.iconOpen=$("#iconopen").val();
		nodeItem.iconClose=$("#iconclose").val();
		nodeItem.async=$("#async").val();
		nodeItem.url=$("#url").val();
		nodeItem.remark=$("#remark").val();
		nodeItem.sqlInfo={"database":$("#sqldatabase").val(),"type":$("#datatype").val(),"sqlInfo":encodeURIComponent($("#sqlinfo").val())};
	}
	parent.window.updateNodes(list);
	return false;
}