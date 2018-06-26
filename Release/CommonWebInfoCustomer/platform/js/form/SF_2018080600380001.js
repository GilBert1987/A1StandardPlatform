$(document).ready(function(){
	var id;
	id=$("#id").val();
	if(""==id)
	{
		$("[name=changepwd]").each(function(index, ele) {
			$(ele).prop("checked",false);
        });
		$("[name=changepwd]").each(function(index, ele) {
			if("1"==$(ele).val())
			{
				$(ele).prop("checked",true);
				return false;
			}
        });
	}
});
function fn_save(){
	var hasUser;
	var retVal;
	var changepwd;
	var username;
	var realname;
	username="";
	realname="";
	retVal=false;
	hasUser=existKey("shiro","sc_user","username",$("#username").val(),"id",$("#id").val());
	if(true==hasUser)
	{
		retVal=true;
	}
	if(false==hasUser)
	{
		alert("用户名重复!");
	}
	username=$("#username").val().trim();
	realname=$("#realname").val().trim();
	if(""==username)
	{
		alert("用户名不能为空!");
		retVal=false;
	}
	if(""==realname)
	{
		alert("真实名不能为空!");
		retVal=false;
	}
	return retVal;
}