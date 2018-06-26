function fn_selIcon(obj){
	var text;
	var value;
	text=$(obj).attr("txt");
	value=$("#"+text).val();
	openWin(1100,400,'选择图标','SF_201508210001.form',encodeURIComponent('{"id":"'+text+'","iconObj":'+((""==value)?"null":value)+'}'),null);
}
