function fn_reloadTree(){
	setTimeout(parent.document.Tree.reloadTree(),500);
	window.location.href=window.location.href;
	return false;
}