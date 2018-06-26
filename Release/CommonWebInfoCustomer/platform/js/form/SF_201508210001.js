$(document).ready(function(e) {
	var initdata;
	var initObj;
	var collist;
	collist=null;
	initdata=parent.$("#hinitdata").val();
	if(""!=initdata)
	{
		initdata=decodeURIComponent(initdata);
		initObj=JSON.parse(initdata);
		$("#backid").val(initObj.id);
		if(null!=initObj.iconObj)
		{
			$("#icon").val(initObj.iconObj.icon);
			$("#style").val(initObj.iconObj.style);
			$("#btnclass").val(initObj.iconObj.btnclass);
			$("#swatch").css("background-color",initObj.iconObj.style);
			$("#icon_default").attr("class",initObj.iconObj.icon);
			$("#icon_default").attr("style",initObj.iconObj.style);
			$("#icon_primary").attr("class",initObj.iconObj.icon);
			$("#icon_primary").attr("style",initObj.iconObj.style);
			$("#icon_success").attr("class",initObj.iconObj.icon);
			$("#icon_success").attr("style",initObj.iconObj.style);
			$("#icon_info").attr("class",initObj.iconObj.icon);
			$("#icon_info").attr("style",initObj.iconObj.style);
			$("#icon_warning").attr("class",initObj.iconObj.icon);
			$("#icon_warning").attr("style",initObj.iconObj.style);
			$("#icon_danger").attr("class",initObj.iconObj.icon);
			$("#icon_danger").attr("style",initObj.iconObj.style);
		}
	}
	$(".webwidget_scroller_tab").webwidget_scroller_tab({
		scroller_time_interval: '-1',
		scroller_window_padding: '10',
		scroller_window_width: '920',
		scroller_window_height: '250',
		scroller_head_text_color: '#0099FF',
		scroller_head_current_text_color: '#666',
		directory: 'img'
	});
	$("#red,#green,#blue").slider({
		orientation: "horizontal",
		range: "min",
		max: 255,
		value: 127,
		slide: refreshSwatch,
		change: refreshSwatch
	});
	collist=colorRgb(colorHex($("#swatch").css("background-color")));
	$("#red").slider("value",0);
	$("#green").slider("value",0);
	$("#blue").slider("value",0);
	if(null!=collist && 0!=collist.length)
	{
		$("#red").slider("value",collist[0]);
		$("#green").slider("value",collist[1]);
		$("#blue").slider("value",collist[2]);
	}
});
function colorRgb(colrgb){
    var sColor;
	var sColorChange;
	var reg;
	reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
	//处理六位的颜色值
	sColorChange = [];
	sColor = colrgb.toLowerCase();
    if(sColor && reg.test(sColor)){
        if(sColor.length === 4){
            var sColorNew = "#";
                for(var i=1; i<4; i+=1){
                    sColorNew += sColor.slice(i,i+1).concat(sColor.slice(i,i+1));        
                }
                sColor = sColorNew;
        }        
        for(var i=1; i<7; i+=2){
            sColorChange.push(parseInt("0x"+sColor.slice(i,i+2)));        
        }
	}
	return sColorChange;
}
function colorHex(color){
    var that;
	var reg;
	reg = /^#([0-9a-fA-f]{3}|[0-9a-fA-f]{6})$/;
	that = color;
    if(/^(rgb|RGB)/.test(that)){
        var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g,"").split(",");
        var strHex = "#";
        for(var i=0; i<aColor.length; i++){
            var hex = Number(aColor[i]).toString(16);
            if(hex === "0"){
                hex += hex;        
            }
            strHex += hex;
        }
        if(strHex.length !== 7){
            strHex = that;        
        }
        return strHex;
    }else if(reg.test(that)){
        var aNum = that.replace(/#/,"").split("");
        if(aNum.length === 6){
            return that;        
        }else if(aNum.length === 3){
            var numHex = "#";
            for(var i=0; i<aNum.length; i+=1){
                numHex += (aNum+aNum);
            }
            return numHex;
        }
    }else{
        return that;        
    }
}

function hexFromRGB(r,g,b) {
	var hex;
	hex = [
	  r.toString(16),
	  g.toString(16),
	  b.toString(16)
	];
	$.each( hex, function( nr, val ) {
	  if ( val.length === 1 ) {
		hex[ nr ] = "0" + val;
	  }
	});
	return hex.join( "" ).toUpperCase();
}

function refreshSwatch() {
	var red;
	var green;
	var blue;
	var hex;
	red = $("#red").slider( "value" );
	green = $("#green").slider( "value" );
	blue = $("#blue").slider( "value" );
	hex = hexFromRGB(red,green,blue);
	$("#swatch").css("background-color", "#" + hex );
	$("#style").val("color:"+$("#swatch").css("background-color"));
	$("#icon_default").css("color", "#" + hex );
	$("#icon_primary").css("color", "#" + hex );
	$("#icon_success").css("color", "#" + hex );
	$("#icon_info").css("color", "#" + hex );
	$("#icon_warning").css("color", "#" + hex );
	$("#icon_danger").css("color", "#" + hex );
}
function fn_selectIco(obj){
	var em;
	em=$(obj).find("em");
	if(em.size()>0)
	{
		$("#icon").val($(em.get(0)).attr("class"));
		$("#icon_default").attr("class", $(em.get(0)).attr("class"));
		$("#icon_primary").attr("class", $(em.get(0)).attr("class"));
		$("#icon_success").attr("class", $(em.get(0)).attr("class"));
		$("#icon_info").attr("class", $(em.get(0)).attr("class"));
		$("#icon_warning").attr("class", $(em.get(0)).attr("class"));
		$("#icon_danger").attr("class", $(em.get(0)).attr("class"));
	}
}
function fn_saveIco(){
	var id;
	var jsObj;
	id=$("#backid").val();
	jsObj={"icon":$("#icon").val(),"style":$("#style").val(),"btnclass":$("#btnclass").val()};
	parent.$("#"+id).val(JSON.stringify(jsObj));
	closeWin();
	return false;
}
function fn_setBtnClass(btnObj){
	$("#btnclass").val($(btnObj).attr("class"));
}