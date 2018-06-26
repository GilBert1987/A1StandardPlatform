$(document).ready(function(e) {
	var initValue;
	var initData;
	var jsonData;
	var roleData;
	initValue=parent.$("#hinitdata").val();
	if(""!=initValue && null!=initValue)
	{
		initValue=decodeURIComponent(initValue);
		jsonData=JSON.parse(initValue);
		if(null!=jsonData.userInfo && ""!=jsonData.userInfo)
		{
			$('#userInfo').val(JSON.stringify(jsonData.userInfo));
			$('input[name=type]').each(function(i,inputObj) {
				if(jsonData.usertype==$(inputObj).val())
				{
					$(inputObj).attr("checked",true);
				}
				else
				{
					$(inputObj).attr("checked",false);
				}
			});
			$('input[name=check]').each(function(i,inputObj) {
				if(jsonData.usercheck==$(inputObj).val())
				{
					$(inputObj).attr("checked",true);
				}
				else
				{
					$(inputObj).attr("checked",false);
				}
			});
			$('input[name=selother]').each(function(i,inputObj) {
				if(jsonData.userselother==$(inputObj).val())
				{
					$(inputObj).attr("checked",true);
				}
				else
				{
					$(inputObj).attr("checked",false);
				}
			});
		}
		initUsers();
	}
});

function initUsers(){
	var jsonData;
	var userInfo;
	userInfo=$('#userInfo').val();
	if(""!=userInfo)
	{
		jsonData=JSON.parse(userInfo);
		$("#singleUser_userid").val(decodeURIComponent(jsonData.singleUserId==null?"":jsonData.singleUserId));
		$("#singleUser_username").val(decodeURIComponent(jsonData.singleUserName==null?"":jsonData.singleUserName));
		$("#candidateUsers_userid").val(decodeURIComponent(jsonData.candidateUsersId==null?"":jsonData.candidateUsersId));
		$("#candidateUsers_username").val(decodeURIComponent(jsonData.candidateUsersName==null?"":jsonData.candidateUsersName));
		$("#candidateGroups_userid").val(decodeURIComponent(jsonData.candidateGroupsId==null?"":jsonData.candidateGroupsId));
		$("#candidateGroups_username").val(decodeURIComponent(jsonData.candidateGroupsName==null?"":jsonData.candidateGroupsName));
		$("#assigneeUser_userid").val(decodeURIComponent(jsonData.assigneeUserId==null?"":jsonData.assigneeUserId));
		$("#assigneeUser_username").val(decodeURIComponent(jsonData.assigneeUserName==null?"":jsonData.assigneeUserName));
		$("#dept_userid").val(decodeURIComponent(jsonData.deptId==null?"":jsonData.deptId));
		$("#dept_username").val(decodeURIComponent(jsonData.deptName==null?"":jsonData.deptName));
		$("#post_userid").val(decodeURIComponent(jsonData.postId==null?"":jsonData.postId));
		$("#post_username").val(decodeURIComponent(jsonData.postName==null?"":jsonData.postName));
		$("#deptpost_userid").val(decodeURIComponent(jsonData.deptpostId==null?"":jsonData.deptpostId));
		$("#deptpost_username").val(decodeURIComponent(jsonData.deptpostName==null?"":jsonData.deptpostName));
		$("#role_userid").val(decodeURIComponent(jsonData.roleId==null?"":jsonData.roleId));
		$("#role_username").val(decodeURIComponent(jsonData.roleName==null?"":jsonData.roleName));
		$("#txtSql").val(decodeURIComponent(jsonData.custSqlInfo==null?"":jsonData.custSqlInfo));
	}
}

function fn_save(){
	var check;
	var selother;
	var postData;
	var singleUsersId;
	var singleUserName;
	var postId;
	var postName;
	var deptpostId;
	var deptpostName;
	var deptId;
	var deptName;
	var roleId;
	var roleName;
	var custSqlInfo;
	var taskId;
	var sigleType;
	var type;
	sigleType=true;
	type=$("input[name=type]:checked").val();
	check=$('input[name=check]:checked').val();
	selother=$('input[name=selother]:checked').val();
	taskId=$("#taskid").val();
	singleUserId=$("#singleUser_userid").val();
	singleUserName=$("#singleUser_username").val();
	postId=$("#post_userid").val();
	postName=$("#post_username").val();
	deptpostId=$("#deptpost_userid").val();
	deptpostName=$("#deptpost_username").val();
	deptId=$("#dept_userid").val();
	deptName=$("#dept_username").val();
	roleId=$("#role_userid").val();
	roleName=$("#role_username").val();
	custSqlInfo=$("#txtSql").val();
	postData={};
	postData.singleUserId=encodeURIComponent(singleUserId);
	postData.singleUserName=encodeURIComponent(singleUserName);
	postData.deptId=encodeURIComponent(deptId);
	postData.deptName=encodeURIComponent(deptName);
	postData.roleId=encodeURIComponent(roleId);
	postData.roleName=encodeURIComponent(roleName);
	postData.postId=encodeURIComponent(postId);
	postData.postName=encodeURIComponent(postName);
	postData.deptpostId=encodeURIComponent(deptpostId);
	postData.deptpostName=encodeURIComponent(deptpostName);
	postData.custSqlInfo=encodeURIComponent(custSqlInfo);
	if(""!=deptId || ""!=roleId || ""!=postId || ""!=deptpostId || ""!=custSqlInfo)
	{
		sigleType=false;
	}
	if(true==sigleType)
	{
		if(-1!=singleUserId.indexOf(","))
		{
			sigleType=false;
		}
	}
	if('0'==type)
	{
		postData.assigneeUserId='${'+taskId+'_assigneeUserId}';
		postData.assigneeUserName='';
		postData.candidateUsersId='';
		postData.candidateUsersName='';
	}
	if('1'==type)
	{
		postData.candidateUsersId='${'+taskId+'_candidateUsersId}';
		postData.candidateUsersName='';
	}
	postData.candidateGroupsId='';
	postData.candidateGroupsName='';
	parent.window.fn_saveUser({"userInfo":postData,"usertype":type,"usercheck":check,"userselother":selother});
	closeWin();
}