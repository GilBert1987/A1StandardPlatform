function saveButton(){
	parent.window.fn_saveTag($('#taginfo').val());
	closeWin();
	return false;
}