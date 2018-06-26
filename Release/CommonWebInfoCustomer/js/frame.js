var userAgent = navigator.userAgent.toLowerCase();
// Figure out what browser is being used 
jQuery.browser = {
	version: (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1],
	safari: /webkit/.test(userAgent),
	opera: /opera/.test(userAgent),
	msie: /msie/.test(userAgent) && !/opera/.test(userAgent),
	mozilla: /mozilla/.test(userAgent) && !/(compatible|webkit)/.test(userAgent)
};
$(document).ready(function(){
	document.domain=location.hostname;
	_init_data();
});
function _init_data(){
	var type;
	var lefturl;
	var righturl;
	var rightupurl;
	var rightdownurl;
	var upurl;
	var downurl;
	type=$('#hframetype').val();
	lefturl=$('#hframeleft').val();
	righturl=$('#hframeright').val();
	rightupurl=$('#hframerightup').val();
	rightdownurl=$('#hframerightdown').val();
	upurl=$('#hframeup').val();
	downurl=$('#hframedown').val();
	if('0'==type)
	{
		$('#frameleftinfo').attr('src',lefturl);
		setTimeout(
			function(){
				$('#framerightup').attr('src',rightupurl);
			},100);
		setTimeout(
			function(){
				$('#framerightdown').attr('src',rightdownurl);
				$('#framerightdown').slimScroll({
					height: '100%',
					railOpacity: 0.4,
					wheelStep: 10
				});
			},200);
	}
	if('1'==type)
	{
		$('#frameleft').attr('src',lefturl);
		setTimeout(
			function(){
				$('#frameright').attr('src',righturl);
			},500);
	}
	if('2'==type)
	{
		$('#frameup').attr('src',upurl);
		setTimeout(
			function(){
				$('#framedown').attr('src',downurl);
			},400);
	}
}
function dyniframesize(down,number,type) {
	var FFextraHeight = 0;
	var pTar = down;
	var yScroll;
	var wScroll;
	setTimeout(function(){
		if(true==$.browser.msie)
		{
			if(null!=pTar.Document.body.scrollWidth)
			{
				wScroll=pTar.Document.body.scrollWidth;
			}
			if(null!=pTar.Document.body.scrollWidth)
			{
				wScroll=document.body.scrollWidth;
			}
			if(null!=pTar.Document.body.scrollHeight)
			{
				yScroll=pTar.Document.body.scrollHeight;
			}
			if(document.body.scrollHeight>yScroll)
			{
				yScroll=document.body.scrollHeight;
			}
		}
		else
		{
			yScroll = (document.documentElement.scrollHeight >document.documentElement.clientHeight) ? document.documentElement.scrollHeight : document.documentElement.clientHeight;
			wScroll = (document.documentElement.scrollWidth <document.documentElement.clientWidth) ? document.documentElement.scrollWidth : document.documentElement.clientWidth; 
		}
		if (document.getElementById==null) {
			eval('pTar = ' + down + ';');
		}
		if(window.navigator.userAgent.indexOf("Firefox")>=1)
		{
			FFextraHeight = 16;
		}
		if(pTar && !window.opera)
		{
			if(type==1)
			{
				if(false==isNaN(yScroll*number/100-15))
				{
					$(pTar).css("height",yScroll*number/100-15);
					if(true==$.browser.msie)
					{
						$(pTar).css("height",yScroll*number/100+15);
						$(down).css('height',$(pTar).css("height"));
					}
				}
			}
			else
			{
				if(false==isNaN(wScroll*number/100-15))
				{
					$(pTar).css("width",wScroll*number/100-15);
					if(true==$.browser.msie)
					{
						$(down).css('width',$(pTar).css("width"));
					}
				}
			}
		}
		$(pTar).css("display","block");
		$(pTar).css('position','relative');
		$(down).css('position','relative');
		$(down).css('display','block');
	   /*
	   //右侧边栏使用slimscroll
	   $(down).slimScroll({
			height:yScroll+'px',
			railOpacity: 0.4,
			wheelStep: 10
	   });
	   */
   },500);
   $(down).css('position','relative');
}
function fun_ChangeFrameUrl(frame,url){
	$('#'+frame).attr('src',url);
}
function reloadFrame(){
	window.location.reload();
}