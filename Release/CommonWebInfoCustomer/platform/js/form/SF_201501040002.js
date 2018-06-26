function fn_save(){
	var name;
	name=$('#name').val();
	if(''!=name){
		parent.window.addbtnInfo({"name":name,"ico":$('#ico').val(),"order":$('#order').val(),"type":$('#type').val(),"fun":$('#fun').val()});
		closeWin();
	}
	return false;
}