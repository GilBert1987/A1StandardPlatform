$(document).ready(function(e) {
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '1000',
		scroller_window_height: '380',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
});
function setPrikey(id){
	openWin(1000,400,'主键相关设置','SF_201412220005.form?id='+id,null,null);
	return false;
}
function setPagestyle(id){
	openWin(1000,400,'功能显示设置','SF_201412220006.form?id='+id,null,null);
	return false;
}
function setColumn(id){
	 openWin(1000,600,'列信息设置','SF_201412240001.form?id='+id,null,null);
	 return false;
}
function setButton(id){
	 openWin(1000,400,'按钮信息设置','SF_201412240002.form?id='+id,null,null);
	 return false;
}
function fn_save(){
	$('#head').val($('#title').val());
	return true;
}
function updatePrikey(priitem,inititem){
	$('#prikey').val(JSON.stringify(priitem));
	$('#initsql').val(JSON.stringify(inititem));
	resizeWin();
	$('#btnsave').click();
}
function updatePageStyle(pageitem,timeitem){
	$('#pagestyle').val(JSON.stringify(pageitem));
	$('#choicetime').val(JSON.stringify(timeitem));
	resizeWin();
	$('#btnsave').click();
}
function updateButton(items){
	$('#buttons').val(JSON.stringify(items));
	resizeWin();
	$('#btnsave').click();
}
function updateColumn(items){
	$('#columns').val(JSON.stringify(items));
	resizeWin();
	$('#btnsave').click();
}
