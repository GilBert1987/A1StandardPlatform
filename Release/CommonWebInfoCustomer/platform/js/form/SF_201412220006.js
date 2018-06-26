$(document).ready(function(){
	var pstyle;
	var trinfo;
	var tdlist;
	pstyle=JSON.parse($('#pagestyle').val());
	if(null != pstyle && null != pstyle.showlist)
	{
		for(var i=0;i<pstyle.showlist.length;i++)
		{
			trinfo=$('#datastyle').find('tr').get(i);
			tdlist=$(trinfo).find('td');
			$($(tdlist.get(1)).children()).val(pstyle.showlist[i].isshow);
		}      
	}  
});  
function fn_save(){
	var list=[];
	var tdlist;
	$('#datastyle').find('tr').each(function(i, item){
		tdlist=$(item).find('td');
		list.push({"type":(i+1),"isshow":$($(tdlist.get(1)).children()).val()});
	});       
	parent.window.updatePageStyle(
		{
			"showlist":list,
			"type":$('#pagetype').val()
		},
		{
			"pagenum":$('#pagenum').val(),
			"type":$('#timetype').val()
		}
	);
	return false;
}  