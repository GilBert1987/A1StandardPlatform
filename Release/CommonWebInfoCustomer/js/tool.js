$(document).ready(function(){
	var u="";
	$("button").each(function(i,ele){
		u=$(ele).attr("u");
		if(null!=u && ""!=u)
		{
			fun_ChangeUrl(u);
			return false;
		}
	});
});

function fun_ChangeUrl(url){
	$('#Iframework').attr('src',url);
}

function fn_autoFrameHeight(ifrObj){
	setTimeout(function(){
		var height;
		height=document.documentElement.clientHeight;
		height=height-50;
		$(ifrObj).css("height",height);
	},500);
}