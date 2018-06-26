function fn_platform_selUser(imgObj)
{
	var userId;
	var username;
	var url;
	var userValue;
	var users;
	var userInfo;
	var postData;
	var userarrId;
	var userarrName;
	var ismultiple;
	users=[];
	url=$(imgObj).attr('url');
	ismultiple=$(imgObj).attr('ismultiple');
	userId=$(imgObj).attr('userid');
	username=$(imgObj).attr('username');
	userValue=$('#'+userId).val();
	userInfo=$('#'+username).val();
	userarrId=userValue.split(',');
	userarrName=userInfo.split(',');
	for(var i=0;i<userarrId.length;i++)
	{
		users.push("{'id':'"+userarrId[i]+"','name':'"+userarrName[i]+"'}");
	}
	if(url!=null)
	{
		postData="{'userid':'"+userId+"','username':'"+username+"','uservalue':["+users.join(',')+"],'ismultiple':'"+ismultiple+"'}";
		openWin(800,430,'人员选择',url,postData,null);
	}
}

function fn_platform_selDeptPost(imgObj)
{
	var userId;
	var username;
	var url;
	var userValue;
	var users;
	var userInfo;
	var postData;
	var userarrId;
	var userarrName;
	var ismultiple;
	users=[];
	url=$(imgObj).attr('url');
	ismultiple=$(imgObj).attr('ismultiple');
	userId=$(imgObj).attr('userid');
	username=$(imgObj).attr('username');
	userValue=$('#'+userId).val();
	userInfo=$('#'+username).val();
	userarrId=userValue.split(',');
	userarrName=userInfo.split(',');
	for(var i=0;i<userarrId.length;i++)
	{
		users.push("{'id':'"+userarrId[i]+"','name':'"+userarrName[i]+"'}");
	}
	if(url!=null)
	{
		postData="{'userid':'"+userId+"','username':'"+username+"','uservalue':["+users.join(',')+"],'ismultiple':'"+ismultiple+"'}";
		openWin(800,430,'部门职务选择',url,postData,null);
	}
}

function fn_platform_selPost(imgObj)
{
	var userId;
	var username;
	var url;
	var userValue;
	var users;
	var userInfo;
	var postData;
	var userarrId;
	var userarrName;
	var ismultiple;
	users=[];
	url=$(imgObj).attr('url');
	ismultiple=$(imgObj).attr('ismultiple');
	userId=$(imgObj).attr('userid');
	username=$(imgObj).attr('username');
	userValue=$('#'+userId).val();
	userInfo=$('#'+username).val();
	userarrId=userValue.split(',');
	userarrName=userInfo.split(',');
	for(var i=0;i<userarrId.length;i++)
	{
		users.push("{'id':'"+userarrId[i]+"','name':'"+userarrName[i]+"'}");
	}
	if(url!=null)
	{
		postData="{'userid':'"+userId+"','username':'"+username+"','uservalue':["+users.join(',')+"],'ismultiple':'"+ismultiple+"'}";
		openWin(800,430,'职务选择',url,postData,null);
	}
}

function fn_platform_selRole(imgObj)
{
	var userId;
	var username;
	var url;
	var userValue;
	var users;
	var userInfo;
	var postData;
	var userarrId;
	var userarrName;
	var ismultiple;
	users=[];
	url=$(imgObj).attr('url');
	ismultiple=$(imgObj).attr('ismultiple');
	userId=$(imgObj).attr('userid');
	username=$(imgObj).attr('username');
	userValue=$('#'+userId).val();
	userInfo=$('#'+username).val();
	userarrId=userValue.split(',');
	userarrName=userInfo.split(',');
	for(var i=0;i<userarrId.length;i++)
	{
		users.push("{'id':'"+userarrId[i]+"','name':'"+userarrName[i]+"'}");
	}
	if(url!=null)
	{
		postData="{'userid':'"+userId+"','username':'"+username+"','uservalue':["+users.join(',')+"],'ismultiple':'"+ismultiple+"'}";
		openWin(800,430,'角色选择',url,postData,null);
	}
}

function fn_platform_selDept(imgObj)
{
	var userId;
	var username;
	var url;
	var userValue;
	var users;
	var userInfo;
	var postData;
	var userarrId;
	var userarrName;
	var ismultiple;
	users=[];
	url=$(imgObj).attr('url');
	ismultiple=$(imgObj).attr('ismultiple');
	userId=$(imgObj).attr('userid');
	username=$(imgObj).attr('username');
	userValue=$('#'+userId).val();
	userInfo=$('#'+username).val();
	userarrId=userValue.split(',');
	userarrName=userInfo.split(',');
	for(var i=0;i<userarrId.length;i++)
	{
		users.push("{'id':'"+userarrId[i]+"','name':'"+userarrName[i]+"'}");
	}
	if(url!=null)
	{
		postData="{'userid':'"+userId+"','username':'"+username+"','uservalue':["+users.join(',')+"],'ismultiple':'"+ismultiple+"'}";
		openWin(800,430,'部门选择',url,postData,null);
	}
}

function fn_platform_selUserBack(msg,userid,username){
	var uid;
	var uname;
	uid='';
	uname='';
	if(null!=msg)
	{
		$(msg).each(function(index, ele) {
            uid=(uid==''?ele.id:uid+','+ele.id);
			uname=(uname==''?ele.name:uname+','+ele.name);
        });
		$('#'+userid).val(uid);
		$('#'+username).val(uname);
	}
}