function fn_save(){
	var key;
	var name;
	var grouptype;
	var type;
	var jsonObj;
	key=$('#key').val();
	name=$('#name').val();
	grouptype=$('#grouptype').val();
	type=$('#type').val();
	if(''!=name && ''!=key)
	{
		jsonObj={"key":key,"name":name,"grouptype":grouptype,"type":type};
		jsonObj.quick={"isshow":"0","showname":"0","showoperation":"0"};
		jsonObj.title={"width":100,"order":1,"isshow":"1","isedit":"0"};
		jsonObj.search={"type":"input","order":1,"isshow":"1","datatype":"","searchinfo":""};
		jsonObj.unit={"merge":"0","mergerefer":"0","mergecolumn":"","align":"left"};
		parent.window.addColumn(jsonObj);
		closeWin();
	}
	else
	{
		alert('请输入名称!');
	}
	return false;
}
