function saveButton(){
	var showtype;
	var authinfo;
	var jsonObj;
	showtype=$('input[name="showtype"]:checked').val();
	authinfo=$('#authinfo').val();
	authinfo=authinfo.replace(/'/g, "%26apos;");
	parent.window.addAuthorityItem({"showtype":showtype,"authinfo":encodeURIComponent(authinfo)});
	closeWin();
	return false;
}