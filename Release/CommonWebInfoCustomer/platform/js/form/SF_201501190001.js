function fn_addcuts(){
	var trinfo;
	for(var i=0;i<document.getElementById('datacut').rows.length;i++)
	{
		trinfo=document.getElementById('datacut').rows[i];
		if(trinfo.cells[0].children[0].checked==true)
		{
			$(trinfo.cells[3].children[0]).val('1');
		}
	}
	return false;
}
function fn_delcuts(){
	var trinfo;
	for(var i=0;i<document.getElementById('datacut').rows.length;i++)
	{
		trinfo=document.getElementById('datacut').rows[i];
		if(trinfo.cells[0].children[0].checked==true)
		{
			$(trinfo.cells[3].children[0]).val('0');
		}
	}
	return false;
}
function fn_addmenus(){
	var trinfo;
	for(var i=0;i<document.getElementById('datamenu').rows.length;i++)
	{
		trinfo=document.getElementById('datamenu').rows[i];
		if(trinfo.cells[0].children[0].checked==true)
		{
			$(trinfo.cells[4].children[0]).val('1');
		}
	}
	return false;
}
function fn_delmenus(){
	var trinfo;
	for(var i=0;i<document.getElementById('datamenu').rows.length;i++)
	{
		trinfo=document.getElementById('datamenu').rows[i];
		if(trinfo.cells[0].children[0].checked==true)
		{
			$(trinfo.cells[4].children[0]).val('0');
		}
	}
	return false;
}
function fn_save(){
	return true;
}
function fn_ckallcut(input){
	var checked;
	var tdinfo;
	checked=input.checked;
	for(var i=0;i<document.getElementById('datacut').rows.length;i++)
	{
		tdinfo=document.getElementById('datacut').rows[i].cells[0];
		tdinfo.children[0].checked=checked;
	}
}
function fn_ckallmenu(input){
	var checked;
	var tdinfo;
	checked=input.checked;
	for(var i=0;i<document.getElementById('datamenu').rows.length;i++)
	{
		tdinfo=document.getElementById('datamenu').rows[i].cells[0];
		tdinfo.children[0].checked=checked;
	}
}