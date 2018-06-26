$(document).ready(function(){
	document.domain=location.hostname;
});
function initTreeMain(requestId){
	setTimeout(function(){
		fn_autoInitTreeMain(requestId);
	},500);
}
function fn_autoInitTreeMain(requestId)
{
	var iframeleft;
	var iframeMain;
	iframeleft=$("#"+requestId+"_frameleft")[0];
	iframeMain=$("#"+requestId+"_Main")[0];
	dyniframesize(iframeleft,20,0);
	dyniframesize(iframeMain,80,0);
	fn_autoTreeFrameHeight(iframeleft);
	fn_autoTreeFrameHeight(iframeMain);
}
function fn_autoTreeFrameHeight(ifrObj){
	var height;
	height=document.documentElement.clientHeight;
	$(ifrObj).css("height",height);
}