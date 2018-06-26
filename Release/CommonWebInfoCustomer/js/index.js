$(document).ready(function(){
	var title;
	$('#FrmSiteNavigation').css('position','absolute');
	$('#FrmSiteNavigation').css('left','-5px');
	$('#FrmSiteNavigation').css('top','-5px');
	$('#FrmSiteNavigation').css('width','100%');
	$('#FrmSiteNavigation').css('height',document.documentElement.clientHeight);
	if(null!=$('#FrmSiteNavigation')[0])
	{
		$(document).attr("title",$($('#FrmSiteNavigation')[0].contentDocument).attr("title"));
		title=$('#FrmSiteNavigation').contents().attr("title");
	}
});