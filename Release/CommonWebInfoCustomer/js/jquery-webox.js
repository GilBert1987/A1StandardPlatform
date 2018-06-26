/**
 *
 *	Plugin: Jquery.webox
 *	Developer: Blank
 *	Version: 1.0 Beta
 *	Update: 2012.07.08
 *
**/
$.extend({
	webox:function(option){
		if(!option){
			alert('options can\'t be empty');
			return;
		};
		if(!option['html']&&!option['iframe']){
			alert('html attribute and iframe attribute can\'t be both empty');
			return;
		};
		option['parent']='webox';
		option['locked']='locked';
		$(document).ready(function(e){
			$('.webox').remove();
			$('.background').remove();
			var initdata;
			var strHtml;
			var width=option['width']?option['width']:400;
			var height=option['height']?option['height']:240;
			strHtml='';
			initdata=option['initdata']?option['initdata']:'';
			if(true==option['addback'])
			{
				strHtml+='<div class="background" style="display:none;z-index: 40000;opacity: 0.3;width: 100%;height: 100%;position: absolute;top: 0px;left: 0px;background-color: #000;"></div>';
			}
			strHtml+='<div class="webox" style="width:'+width+'px;height:'+height+'px;display:none;z-index:42000;">';
			strHtml+='<div id="inside" class="modal-header">';
			strHtml+='<input id="hinitdata" type="hidden" value="'+initdata+'" />';
			strHtml+='<input id="htop" type="hidden" value="0" />';
			strHtml+='<input id="hleft" type="hidden" value="0" />';
			strHtml+='<input id="hwidth" type="hidden" value="0" />';
			strHtml+='<input id="hheight" type="hidden" value="0" />';
			strHtml+='<input id="hiframetop" type="hidden" value="0" />';
			strHtml+='<input id="hiframeleft" type="hidden" value="0" />';
			strHtml+='<input id="hiframewidth" type="hidden" value="0" />';
			strHtml+='<input id="hiframeheight" type="hidden" value="0" />';
			strHtml+='<button id="wbclose" type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>';
			strHtml+='<h4 class="modal-title" id="myLargeModalLabel">'+(option['title']?option['title']:'webox')+'</h4>';
			strHtml+='</div>';
			strHtml+='<div id="framebody" class="modal-body">';
			strHtml+=option['iframe']?'<iframe class="w_iframe" src="'+option['iframe']+'" frameborder="0" width="100%" scrolling="yes" style="border:none;overflow-x:hidden;height:'+parseInt(height-51)+'px;"></iframe>':option['html']?option['html']:'';
			strHtml+='</div>';
			strHtml+='</div>';
			$('body').append(strHtml);
			if(option['bgvisibel']){
				$('.background').fadeTo('slow',0.3);
			};
			$('.webox').css({display:'block'});
			$('#wbclose').click(function(){
				$('.webox').remove();
				$('.background').remove();
				if($(parent.document).find("#inside").size()!=0)
				{
					parent.$('.webox').css({left:parent.$("#hleft").val(),top:parent.$("#htop").val(),width:parent.$("#hwidth").val(),height:parent.$("#hheight").val()});
					parent.$(".w_iframe").css({left:parent.$("#hiframeleft").val(),top:parent.$("#hiframetop").val(),width:parent.$("#hiframewidth").val(),height:parent.$("#hiframeheight").val()});
					parent.$('.webox').css("background-color","#fff");
					parent.$('.webox').css("box-shadow","0 0 40px rgba(0,0,0,.5)");
					parent.$('#inside').css("display","block");
					parent.$("#framebody").css("background-color","#eee");
					$("body").attr("style","background-color:#fff;");
					$("head").append("<style>body::before{content:\"\";display:block;position:fixed;top:0;bottom:0;left:0;right:0;z-index:-1;background-color:#eee;}</style>");
					$("#pageBody").css("display","block");
				}
				if(null!=option['callback'] && "function"==typeof(option['callback']))
				{
					option['callback']();
				}
			});
			var marginLeft=parseInt(width/2);
			var marginTop=parseInt(height/2);
			var winWidth=parseInt($(window).width()/2);
			var winHeight=parseInt($(window).height()/2);
			var left=winWidth-marginLeft;
			var top=winHeight-marginTop;
			$('.webox').css({"left":left,"top":top,"position":"absolute"});
			$("#htop").val($('.webox').css("top"));
			$("#hleft").val($('.webox').css("left"));
			$("#hwidth").val($('.webox').css("width"));
			$("#hheight").val($('.webox').css("height"));
			$("#hiframetop").val($('.w_iframe').css("top"));
			$("#hiframeleft").val($('.w_iframe').css("left"));
			$("#hiframewidth").val($('.w_iframe').css("width"));
			$("#hiframeheight").val($('.w_iframe').css("height"));
		});
	}
});