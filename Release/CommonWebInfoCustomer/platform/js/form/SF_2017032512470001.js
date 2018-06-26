$(document).ready(function(){
	//scroller_time_interval 循环时间 
	var initdata;
	var option;
	initdata=window.parent.document.getElementById("hinitdata").value;
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '400',
		scroller_window_height: '280',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
	initdata=initdata.replace(/'/gm,'"');
	initdata=JSON.parse(initdata);
	if(null!=initdata.uservalue)
	{
		for(var i=0;i<initdata.uservalue.length;i++)
		{
			if(''!=initdata.uservalue[i].id)
			{
				option='<option value="'+initdata.uservalue[i].id+'">'+initdata.uservalue[i].name+'</option>';
				$('#seluser').append(option);
			}
		}
	}
	if(null!=initdata.userid)
	{
		$('#userid').val(initdata.userid);
	}
	if(null!=initdata.username)
	{
		$('#username').val(initdata.username);
	}
	if(null!=initdata.ismultiple)
	{
		$("#ismultiple").val(initdata.ismultiple);
	}
	$('#seluser').children().each(function(index, ele) {
		$(ele).mousedown(function(){fn_select_movedown(ele)});
		$(ele).mouseup(function(){fn_select_moveup(ele)});
	});
});

function fn_select_movedown(ele){
	$('#selvalue').val(ele.selected);
}
function fn_select_moveup(ele){
	var selValue;
	selValue=$('#selvalue').val();
	if("true"==selValue)
	{
		$(ele).removeAttr('selected');
	}
	else
	{
		$(ele).attr('selected','selected');
	}
}

function fn_postuser(postid){
	var postData;
	var sqlInfo;
	postid=(postid==null?"":postid);
	sqlInfo="SELECT DISTINCT sc_post.id,sc_post.name AS postname FROM sc_post WHERE id='"+postid+"'";
	postData={"type":"sql","dataname":"shiro","info":sqlInfo};
	$.ajax({
		url: "../../ajax/query/search",
		type: "POST",
		data: postData,
		dataType: "json",
		success: function(list){
			fn_selList(list);
		}		
	});
}

function fn_selList(list){
	var option;
	$('#userlist').empty();
	$(list).each(function(index, ele) {
         option='<option value="'+ele.id+'">'+ele.postname+'</option>';
		 $('#userlist').append(option);
    });
	$('#userlist').children().each(function(index, ele) {
		$(ele).mousedown(function(){fn_select_movedown(ele)});
		$(ele).mouseup(function(){fn_select_moveup(ele)});
	});
}

function fn_addUser(){
	var option;
	var list=[];
	var finlist=[];
	var listchild;
	var blinfo;
	var ismultiple;
	ismultiple=$("#ismultiple").val();
	$('#userlist').children().each(function(index, ele) {
        if(ele.selected==true)
		{
			list.push(ele);
		}
    });
	for(var i=0;i<list.length;i++)
	{
		blinfo=false;
		$('#seluser').children().each(function(index, ele) {
			if($(ele).val()==list[i].value)
			{
				blinfo=true;
			}
		});
		if(blinfo==false)
		{
			option='<option value="'+$(list[i]).val()+'">'+$(list[i]).html()+'</option>';
			finlist.push(option);
		}
	}
	if("false"==ismultiple)
	{
		$('#seluser').empty();
	}
	$('#seluser').append(finlist);
	$('#seluser').children().each(function(index, ele) {
		$(ele).mousedown(function(){fn_select_movedown(ele)});
		$(ele).mouseup(function(){fn_select_moveup(ele)});
	});
	return false;
}

function fn_delUser(){
	$('#seluser').children().each(function(index, ele) {
        if(ele.selected==true)
		{
			$(ele).remove();
		}
    });
	return false;
}

function fn_save(){
	var userid;
	var username;
	var users;
	users=[];
	userid=$('#userid').val();
	username=$('#username').val();
	$('#seluser').children().each(function(index, ele) {
		users.push({"id":$(ele).val(),"name":$(ele).html()});
	});
	window.parent.fn_platform_selUserBack(users,userid,username);
	closeWin();
	return false;
}