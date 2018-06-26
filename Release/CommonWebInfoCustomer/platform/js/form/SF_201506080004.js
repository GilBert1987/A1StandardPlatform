function fn_save(){
	parent.window.fn_savefrom($("#iCode").val(),$("#iUrl").val());
	closeWin();
	return false;
}