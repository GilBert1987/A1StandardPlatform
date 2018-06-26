
function fn_dataFormat(dateinfo,fmt) {
    var o = {
        "M+": dateinfo.getMonth() + 1,
        "d+": dateinfo.getDate(),
        "h+": dateinfo.getHours(),
        "m+": dateinfo.getMinutes(), 
        "s+": dateinfo.getSeconds(),  
        "q+": Math.floor((dateinfo.getMonth() + 3) / 3),
        "S": dateinfo.getMilliseconds()  
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (dateinfo.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function parseJSON(data) {
	if (typeof data !== "string" || !data ) { 
		return null; 
	} 
	data = jQuery.trim( data ); 
	if ( /^[\],:{}\s]*$/.test(data.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@") 
	.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]") 
	.replace(/(?:^|:|,)(?:\s*\[)+/g, "")) )
	{ 
		return window.JSON && window.JSON.parse ? window.JSON.parse( data ) : (new Function("return " + data))(); 
	} else { 
		jQuery.error( "Invalid JSON: " + data ); 
	} 
}

$(document).ready(function(){
	document.domain=document.domain;
	//document.domain=location.hostname;
});
function closeWin(){
	parent.window.clickCloseButton();
}
function resizeWin(){
	if(0!=parent.$(".webox").size())
	{
		parent.$('.webox').css({left:parent.$("#hleft").val(),top:parent.$("#htop").val(),width:parent.$("#hwidth").val(),height:parent.$("#hheight").val(),position:"absolute"});
		parent.$(".w_iframe").css({left:parent.$("#hiframeleft").val(),top:parent.$("#hiframetop").val(),width:parent.$("#hiframewidth").val(),height:parent.$("#hiframeheight").val()});
		parent.$('.webox').css("background-color","#fff");
		parent.$('.webox').css("box-shadow","0 0 40px rgba(0,0,0,.5)");
		parent.$('#inside').css("display","block");
		parent.$("#framebody").css("background-color","#eee");
		$("body").attr("style","background-color:#fff;");
		$("head").append("<style>body::before{content:\"\";display:block;position:fixed;top:0;bottom:0;left:0;right:0;z-index:-1;background-color:#eee;}</style>");
		$("#pageBody").css("display","block");
	}
}

function clickCloseButton(){
	$('#wbclose').trigger('click');
}

function existKey(sysId,table,key,value,perkey,id)
{
	var blInfo;
	blInfo=false;
	$.ajax({
		url:"../../ajax/common/existKey",
		type: "POST",
		data: {"sysName":sysId,"table":table,"key":key,"value":value,"perKey":perkey,"perValue":id},
		dataType: "json",
		async: false,
		success: function(blValue){
			blInfo=blValue;
		}
	});
	return blInfo;
}

function createKey(sysId,table,colInfo)
{
	var strId;
	strId="";
	$.ajax({
		url:"../../ajax/common/createKey",
		type: "POST",
		data: {"sysName":sysId,"table":table,"colInfo":colInfo},
		dataType: "json",
		async: false,
		success: function(obj){
			strId=obj.msg;
		}
	});
	return strId;
}
function fn_getPath(){
	var index;
	var result;
	var pathName; 
	pathName = document.location.pathname;
	index = pathName.lastIndexOf("/");
	result = pathName.substr(0,index+1);
	return result;
}
function fn_autoIframeHeight(iframeId,blMaxHeight){
	var ifm;
	var subWeb;
	var height;
	var mainHeight;
	height=0;
	ifm=$("#"+iframeId);
	subWeb = document.frames ? document.frames[iframeId].document: ifm[0].contentDocument;
	if(ifm != null && subWeb != null) {
		height=subWeb.body.scrollHeight;
		if(true==blMaxHeight && null!=document.body.scrollHeight && document.body.scrollHeight>height)
		{
			height=document.body.scrollHeight;
		}
		ifm.css("height",height);
		mainHeight=document.body.scrollHeight;
		if(mainHeight<height)
		{
			ifm.attr("scrolling","auto");
		}
    }
}
function openWin(widthInfo,heightInfo,titleInfo,url,initInfo,callbackInfo){
	var blHasParent;
	var parentDiv;
	var backDiv;
	var parentIframe;
	blHasParent=false;
	//判断是否上层还有弹出层
	if($(parent.document).find("#inside").size()!=0)
	{
		parentDiv=$(parent.document).find(".webox").get(0);
		backDiv=$(parent.document).find(".background").get(0);
		parentIframe=$(parent.document).find(".w_iframe").get(0);
		if(null!=backDiv)
		{
			$(parentDiv).css({top:"0px",left:"0px",width:"100%",height:$(backDiv).height()});
			$(parentIframe).css({top:"0px",left:"0px",width:"100%",height:$(backDiv).height()});
		}
		else
		{
			$(parentDiv).css({top:"0px",left:"0px",width:"100%",height:$($(parent.parent.document).find(".w_iframe").get(0)).css("height")});
			$(parentIframe).css({top:"0px",left:"0px",width:"100%",height:$($(parent.parent.document).find(".w_iframe").get(0)).css("height")});
		}
		$(parentDiv).css("background-color","transparent");
		$(parentDiv).css("box-shadow","none");
		$($(parentDiv).find("#inside").get(0)).css("display","none");
		$("body").css("background-color","transparent");
		$('head').append("<style>body::before{background-color:transparent;}</style>");
		$($(parentDiv).find("#framebody").get(0)).css("background-color","transparent");
		$("#pageBody").css("display","none");
		$.webox({
			height:heightInfo,
			width:widthInfo,
			bgvisibel:true,
			title:titleInfo,
			iframe:url,
			initdata:initInfo,
			callback:callbackInfo,
			addback:false
		});
	}
	else
	{
		$.webox({
			height:heightInfo,
			width:widthInfo,
			bgvisibel:true,
			title:titleInfo,
			iframe:url,
			initdata:initInfo,
			callback:callbackInfo,
			addback:true
		});
	}
}
function fn_saveBtnStatus(key,btnId,status)
{
	var blInfo;
	blInfo=false;
	$.ajax({
		url:"../../ajax/common/saveBtnStatus",
		type: "POST",
		data: {"key":key,"btnId":btnId,"status":status},
		dataType: "json",
		async: false,
		success: function(obj){
			if("success"==obj.msg)
			{
blInfo=true;
			}
		}
	});
	return blInfo;
}
function fn_initTableWidth(id){
	var sumWidth;
	var tabWidth;
	var finWidth;
	sumWidth=0;
	tabWidth=$("#"+id).parent().width();
	$("#" + id + " thead tr:last th").each(function (index, ele) {
        if("none"!=$(ele).css("display") && (""!=$(ele).attr("w") && null != $(ele).attr("w")))
		{
			sumWidth+=parseInt($(ele).attr("w"));
		}
    });
	if(sumWidth<tabWidth)
	{
		finWidth=tabWidth-sumWidth-16;
		$("#"+id+" thead tr").append("<th class='ui-th-column ui-th-ltr rightcol' w='"+finWidth+"' data-width='"+finWidth+"px' width='"+finWidth+"px'></th>");
		$("#"+id+" tbody tr").each(function(i,ele){
			$(ele).append("<td></td>");
		});
	}
	else
	{
		$("#"+id).width(sumWidth);
	}
}
function fn_checkBtnStatus(key){
	$.ajax({
		url:"../../ajax/common/getBtnStatus",
		type: "POST",
		data: {"key":key},
		dataType: "json",
		success: function(obj){
			if("success"==obj.msg)
			{
				if("0"==obj.status)
				{
					setTimeout(function(){
						fn_checkBtnStatus(key);	
					},3000);
				}
				if("1"==obj.status)
				{
					$("#_btnSubmit").val("1");
					btnSubmit(obj.btnId);
				}
			}
			if("error"==obj.msg)
			{
				alert("按钮更新检测失败!");
			}
		}
	});
}
function fn_saveRedisKey(taskIds){
	var key;
	key="";
	$.ajax({
			url:"../../ajax/common/tempSave",
			type: "POST",
			data: {"info":taskIds},
			dataType: "json",
			async: false,
			success: function(msg){
				if(null!=msg && null!=msg.id)
				{
					key=msg.id;
				}
			}
		});
	return key;
	
}
function fn_initTableReport(id){
	var g;
	var y;
	var z;
	var x0;
	var x1;
	var svg;
	var yBar;
	var keys;
	var type;
	var data;
	var yAxis;
	var width;
	var height;
	var margin;
	var legend;
	var colKey;
	var blInfo;
	var rowkeys;
	var jsonStr;
	var jsonArr;
	var colTitle;
	var rowentries;
	var colentries;
	var canvasWidth;
	var canvasHeight;
	colKey=[];
	rowkeys=[];
	jsonStr=$("#"+id).val();
	canvasWidth=$("#"+id+"_width").val();
	canvasHeight=$("#"+id+"_height").val();
	colTitle=$("#"+id+"_title").val();
	type=$("#"+id+"_type").val();
	jsonArr=JSON.parse(jsonStr);
	if("SC_2017120315430001"==type)
	{
		keys=[];
		if(""!=canvasWidth)
		{
			canvasWidth=Number(canvasWidth);
		}
		if(""!=canvasHeight)
		{
			canvasHeight=Number(canvasHeight);
		}
		rowentries= d3.nest()
				.key(function(d) { return d.rowid; })
				.entries(jsonArr);
		svg = d3.select("#div_"+id)
				.append("svg")
				.attr("width", canvasWidth)
				.attr("height", canvasHeight);
		margin = {top: 20, right: 20, bottom: 30, left: 60};
		width  = canvasWidth - margin.left - margin.right;
		height = canvasHeight- margin.top - margin.bottom;
		g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");
		x0 = d3.scaleBand().rangeRound([0, width]).paddingInner(0.1);
		x1 = d3.scaleBand().padding(0.05);
		y = d3.scaleLinear().rangeRound([height, 0]);
		z = d3.scaleOrdinal().range(["#98abc5", "#8a89a6", "#7b6888", "#6b486b", "#a05d56", "#d0743c", "#ff8c00"]);
		$(rowentries).each(function(i,item){
			if(null!=item && null!=item.values)
			{
				$(item.values).each(function(i,item){
					for(p in item)
					{
						if("rowid"!=p)
						{
							blInfo=false;
							$(keys).each(function(ikey,keyitem){
								if(p==keyitem)
								{
									blInfo=true;
									return false;
								}
							});
							if(false==blInfo)
							{
								keys.push(p);
							}
						}
					}
				});
			}
		});
		$(rowentries).each(function(i,item){
			rowkeys.push(item.key);
		});
		x0.domain(rowkeys,function(d) { 
			return d;
		});
		x1.domain(keys).rangeRound([0, x0.bandwidth()]);
		y.domain(
			[0, 
			d3.max(jsonArr, function(d) { 
				return d3.max(keys, function(key) { 
					return Number(null==d[key]?0:d[key]); });
				})]
		).nice();
		g.append("g")
			.selectAll("g")
			.data(jsonArr)
			.enter().append("g")
			  .attr("transform", function(d) { return "translate(" + x0(d.rowid) + ",0)"; })
			.selectAll("rect")
			.data(function(d) { return keys.map(function(key) { return {"key": key, "value": null==d[key]?0:Number(d[key])}; }); })
			.enter()
			.append("rect")
			  .attr("x", function(d) { return x1(d.key); })
			  .attr("y", function(d) { return y(d.value); })
			  .attr("width", x1.bandwidth())
			  .attr("height", function(d) { return height - y(d.value); })
			  .attr("fill", function(d) { return z(d.key); });

		  g.append("g")
			  .attr("class", "axis")
			  .attr("transform", "translate(0," + height + ")")
			  .call(d3.axisBottom(x0));

		  g.append("g")
			  .attr("class", "axis")
			  .call(d3.axisLeft(y).ticks(null, "s"))
			.append("text")
			  .attr("x", 2)
			  .attr("y", y(y.ticks().pop()) + 0.5)
			  .attr("dy", "0.32em")
			  .attr("fill", "#000")
			  .attr("font-weight", "bold")
			  .attr("text-anchor", "start");

		  legend = g.append("g")
			  .attr("font-family", "sans-serif")
			  .attr("font-size", 10)
			  .attr("text-anchor", "end")
			.selectAll("g")
			.data(keys.slice().reverse())
			.enter().append("g")
			  .attr("transform", function(d, i) { return "translate(0," + i * 20 + ")"; });
		g.append("g")
			.selectAll("g")
			.data(jsonArr)
			.enter().append("g")
			  .attr("transform", function(d) { return "translate(" + x0(d.rowid) + ",0)"; })
			.selectAll("rect")
			.data(function(d) { return keys.map(function(key) { return {"key": key, "value": null==d[key]?0:Number(d[key])}; }); })
			.enter()
			.append("text")
			  .attr("dy", "0.32em")
			  .attr("fill", "#ff0000")
			  .attr("font-weight", "bold")
			  .attr("text-anchor", "start")
			  .attr("x", function(d) { return x1(d.key); })
			  .attr("y", function(d) { return y(d.value)-4; })
			  .text(function(d) { return d.value; });
		  legend.append("rect")
			  .attr("x", width - 19)
			  .attr("width", 19)
			  .attr("height", 19)
			  .attr("fill", z);

		  legend.append("text")
			  .attr("x", width - 24)
			  .attr("y", 9.5)
			  .attr("dy", "0.32em")
			  .text(function(d) { return d; });
		  yBar=svg.append("g")
                    .attr("transform", "translate(" + margin.top + ",0)")
                    .append("text")
                    .attr("transform", "rotate(-90)")
			  		.attr("fill", "#000000")
					.attr("font-weight", "bold")
					.attr("text-anchor", "start")
                    .attr("y", 0 - 20)
                    .attr("x", 0 - (canvasHeight / 2))
                    .attr("dy", "1em")
                    .text(colTitle);
	}
}