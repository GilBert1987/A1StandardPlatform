$(document).ready(function(e) {
    init_data();
});

function init_data(){
	var list;
	var listitem;
	var strTemp;
	$('#tblist').empty();
	$('#itemlist').empty();
	$('#code').val('');
	$('#name').val('');
	$('#remark').val('');
	$('#hselitem').val('');
	$('#hselgroup').val('');
	$('#selshow').val('true');
	$('#selselect').val('false');
	$('#selcode').empty();
	$('#selcode').val('');
	listitem=JSON.parse($('#listitem').val());
	$(listitem).each(function(i,item){
		$('#selcode').append('<option value="'+item.code+'">'+item.name+'</option>')
	});
	list=JSON.parse($('#liststyle').val());
	$(list).each(function(i,item){
		if(""!=item.code)
		{
			strTemp="<tr>";
			strTemp+="<td width='10%'>";
			strTemp+="<input name='selgroup' type='radio' value='"+item.code+"' onclick='clickTable(this);'>";
			strTemp+="</td>";
			strTemp+="<td  width='20%'>";
			strTemp+=item.code;
			strTemp+="</td>";
			strTemp+="<td  width='70%'>";
			strTemp+=item.name;
			strTemp+="</td>";
		 	strTemp+="</tr>";
		 	$('#tblist').append(strTemp);
		}
	});
}
function clickTable(input){
	var list;
	var key;
	var listshow;
	var listitem;
	var blInfo;
	$('#hselitem').val('');
	$('#selshow').val('true');
	$('#selselect').val('false');
	$('#selcode').empty();
	$('#selcode').val('');
	listitem=JSON.parse($('#listitem').val());
	key=$(input).val();
	listshow=null;
	list=JSON.parse($('#liststyle').val());
	$('#hselgroup').val(key);
	$(list).each(function(i,item){
		if(key==item.code)
		{
			$('#code').val(item.code);
			$('#name').val(item.name);
			$('#remark').val(item.remark);
			listshow=item.listshow;
			$('#itemlist').empty();
			return false;
		}
	});
	if(null!=listshow)
	{
		$(listshow).each(function(index, item) {
            strTemp="<tr>";
			strTemp+="<td width='10%'>";
			strTemp+="<input name='selitem' type='radio' value='"+item.code+"' onclick='clickItem(this);'>";
			strTemp+="</td>";
			strTemp+="<td  width='20%'>";
			strTemp+=item.code;
			strTemp+="</td>";
			strTemp+="<td  width='35%'>";
			strTemp+=(item.isshow=='false'?'否':'是');
			strTemp+="</td>";
			strTemp+="<td  width='35%'>";
			strTemp+=(item.isselect=='false'?'否':'是');
			strTemp+="</td>";
		 	strTemp+="</tr>";
		 	$('#itemlist').append(strTemp);
        });
		$(listitem).each(function(i,codeitem){
				blInfo=false;
				$(listshow).each(function(index, item) {
					if(item.code==codeitem.code)
					{
						blInfo=true;
						return false;
					}
				});
				if(blInfo==false)
				{
					$('#selcode').append('<option value="'+codeitem.code+'">'+codeitem.name+'</option>');
				}
		});
	}
}
function clickItem(input){
	var key;
	var groupkey;
	key=$(input).val();
	$('#hselitem').val(key);
}
function fn_add(){
	$('#code').val('');
	$('#name').val('');
	$('#remark').val('');
	$('#hselitem').val('');
	$('#hselgroup').val('');
	return false;
}
function fn_del(){
	var list;
	var key;
	var plist;
	key=$('#hselgroup').val();
	plist=[];
	if(''!=key)
	{
		list=JSON.parse($('#liststyle').val());
		$(list).each(function(i,item){
			if(key!=item.code)
			{
				plist.push(item);
			}
		});
		$('#liststyle').val(JSON.stringify(plist));
		init_data();
	}
	return false;
}
function fn_additem(){
	var list;
	var key;
	var listshow;
	var code;
	var isselect;
	var isshow;
	code=$('#selcode').val();
	isselect=$('#selselect').val();
	isshow=$('#selshow').val();
	listshow=null;
	key=$('#hselgroup').val();
	if(''!=key)
	{
		list=JSON.parse($('#liststyle').val());
		$(list).each(function(i,item){
			if(key==item.code)
			{
				listshow=item.listshow;
				return false;
			}
		});
		if(''!=code)
		{
			listshow.push({"code":code,"isshow":isshow,"isselect":isselect});
		}
		$('#liststyle').val(JSON.stringify(list));
		init_data();
	}
	return false;
}
function fn_delitem(){
	var list;
	var key;
	var itemkey;
	var plist;
	var itemInfo;
	itemInfo=null;
	plist=[];
	key=$('#hselgroup').val();
	itemkey=$('#hselitem').val();
	if(''!=key && ''!=itemkey)
	{
		list=JSON.parse($('#liststyle').val());
		$(list).each(function(i,item){
			if(key==item.code)
			{
				itemInfo=item;
				return false;
			}
		});
		if(null!=itemInfo && null!= itemInfo.listshow)
		{
			$(itemInfo.listshow).each(function(i,item){
				if(itemkey!=item.code)
				{
					plist.push(item);
				}
			});
			itemInfo.listshow=plist;
		}
		$('#liststyle').val(JSON.stringify(list));
		init_data();
	}
	return false;
}
function fn_save(){
	var key;
	var list;
	key=$('#hselgroup').val();
	list=JSON.parse($('#liststyle').val());
	if(''!=key){
		$(list).each(function(i,item){
			if(key==item.code)
			{
				item.code=$('#code').val();
				item.name=$('#name').val();
				item.remark=$('#remark').val();
				return false;
			}
		});
	}
	else
	{
		list.push({"code":$('#code').val(),"name":$('#name').val(),"remark":$('#remark').val(),"listshow":[]})
	}
	parent.window.updateListStyle(list);
	return false;
}