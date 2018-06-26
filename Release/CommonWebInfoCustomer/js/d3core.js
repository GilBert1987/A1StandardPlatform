(function ($){
    $.WorkFlow = function () {
		//转换对象
		var divObj=null;
		//初始化数据
		var initData=null;
		//绘画对象
		var svg=null;
		//矩形圆角
		var rect_r=6;
		//矩形宽差
		var rect_margin_w=12;
		//矩形高差
		var rect_margin_h=5;
		var clickRect=null;
		var selPath=null;
		//矩形填充属性
		var rect_attr={
            "stroke": "#000000",
            "stroke-width":1,
			"fill":"none",
			"style":"display:none;"
		};
		var path_attr={
			"stroke-width":3,
			"stroke-linecap":"",
			"stroke-dasharray":""
		};
		var path_dash_attr={
			"stroke-width":3,
			"stroke-linecap":"round",
			"stroke-dasharray":"1,4"
		};
		var circle_attr={
			"fill":"#ffffff",
			"r":6,
			"stroke":"#000"
		};
		//初始化路径文字偏移量
		var pathText_margin_w=0;
		//初始化路径文字偏移量
		var pathText_margin_h=10;
		//使用贝斯尔曲线
		var useBerzierPath=false;
		var arrow_attr={
			"id":"arrow",
			"markerUntis":"strokeWidth",
			"markerWidth":"10",
			"markerHeight":"10",
			"viewBox":"0 0 10 10",
			"refX":"6",
			"refY":"6",
			"orient":"auto"
		};
		var arrow_path={
			"d":"M3,4 L7,6 L3,8 L5,6 L3,4",
			"fill":"#f00",
			"style":"z-index:10;"
		};
		var img_drag_fun=null;
		var circle_drag_fun=null;
		var pathTxt_drag_fun=null;
		var rectList=null;
		var pathList=null;
		var propList=null;
		var fn_AnimateRect=null;
		//----------------------------------------------------------------------
		//公用方法
		this.init=function(){
			var imgArr;
			var txtArr;
			var rectArr;
			var txtSpan;
			var imgUpdate;
			var txtUpdate;
			var rectUpdate;
			rectList=null;
			img_drag_fun=d3.drag()
				.on("start", imgdragstart)
				.on("drag", imgdrag)
				.on("end", imgdragend);
			circle_drag_fun=d3.drag()
				.on("drag", circledrag)
				.on("end", circledragend);
			pathTxt_drag_fun=d3.drag()
				.on("drag", pathTxtdrag);
			if(null != divObj && null != initData)
			{
				svg=d3.select("#"+$(divObj).attr("id")).append("svg");	
				svg.attr("width",$(divObj).width());
				svg.attr("height",$(divObj).height());
				svg.attr("xmlns","http://www.w3.org/2000/svg");
				svg.attr("xmlns:xlink","http://www.w3.org/1999/xlink");
				svg.attr("style","overflow: hidden; position: relative;")
				rectList=initData.rects;
				pathList=initData.paths;
				propList=initData.props;
				if(null!=rectList && null !=pathList && null!=propList)
				{
					initdraw(rectList,pathList,propList);
					if(null!=fn_AnimateRect)
					{
						rectList=d3.selectAll("rect").nodes();
						$(rectList).each(function(i,ele) {
                            fn_AnimateRect(ele);
                        });
					}
				}
			}
		};
		this.setDivObj=function(value)
		{
			divObj=value;
		};
		this.setInitData=function(value)
		{
			initData=value;
		};
		this.setClickRect=function(value)
		{
			clickRect=value;	
		};
		this.setSelPath=function(value)
		{
			selPath=value;	
		};
		this.setAnimateRect=function(value)
		{
			fn_AnimateRect=value;
		};
		this.addRect=function(rectObj){
			var txtSpan;
			var imgUpdate;
			var txtUpdate;
			var rectUpdate;
			if(null!=rectObj)
			{
				rectUpdate=svg.append("rect");
				imgUpdate=svg.append("image");
				txtUpdate=svg.append("text");
				rectUpdate.attr("class",rectObj.id);
				rectUpdate.attr("x",rectObj.left);
				rectUpdate.attr("y",rectObj.top);
				rectUpdate.attr("width",rectObj.type.width+rect_margin_w);
				rectUpdate.attr("height",rectObj.type.height+rect_margin_h);
				rectUpdate.attr("rx",rect_r);
				rectUpdate.attr("ry",rect_r);
				for (var key in rect_attr){
					rectUpdate.attr(key,rect_attr[key]);
				}
				rectUpdate.datum(rectObj);
				imgUpdate.attr("class",rectObj.id);
				imgUpdate.attr("href",rectObj.type.src);
				imgUpdate.attr("x",rectObj.left+(rect_margin_w/2));
				imgUpdate.attr("y",rectObj.top+(rect_margin_h/2));
				imgUpdate.attr("width",rectObj.type.width);
				imgUpdate.attr("height",rectObj.type.height);
				imgUpdate.attr("preserveAspectRatio","none");
				imgUpdate.call(img_drag_fun);
				imgUpdate.datum(rectObj);
				txtUpdate.attr("class",rectObj.id);
				txtUpdate.attr("x",rectObj.left+rectObj.type.width+(rect_margin_w-rectObj.type.width)/2);
				txtUpdate.attr("y",rectObj.top+(rectObj.type.height/2)+rect_margin_h);
				txtUpdate.attr("text-anchor","middle");
				txtUpdate.attr("font-family","Arial");
				txtUpdate.attr("font-size","10px");
				txtUpdate.attr("stroke","none");
				txtUpdate.attr("fill","#000000");
				txtUpdate.datum(rectObj);
				txtSpan=txtUpdate.append("tspan");
				txtSpan.text(rectObj.text);
				rectList.push(rectObj);
			}
		};
		this.addPath=function(pathObj){
			var txtSpan;
			var pathUpdate;
			var pathTxtUpdate;
			if(null!=pathObj)
			{
				pathList.push(pathObj);
				pathUpdate=svg.append("path");
				pathTxtUpdate=svg.append("text");
				pathUpdate.attr("fill","none");
				pathUpdate.attr("stroke","#000000"); 
				pathUpdate.attr("class",pathObj.from+" "+pathObj.to);
				pathUpdate.attr("marker-end","url(#arrow)");
				pathUpdate.datum(pathObj);
				pathUpdate.attr("d",pathWay);
				if("0"==pathObj.properties.linetype || "1"==pathObj.properties.linetype || "2"==pathObj.properties.linetype)
				{
					for(var obj in path_attr)
					{
						pathUpdate.attr(obj,path_attr[obj]);
					}
				}
				if("3"==pathObj.properties.linetype || "4"==pathObj.properties.linetype || "5"==pathObj.properties.linetype)
				{
					for(var obj in path_dash_attr)
					{
						pathUpdate.attr(obj,path_dash_attr[obj]);
					}
				}
				pathUpdate.on("click",pathClick);
				pathTxtUpdate.datum(pathObj);
				pathTxtUpdate.attr("class",pathObj.from+" "+pathObj.to+" pathText");
				pathTxtUpdate.attr("x",pathTxtX);
				pathTxtUpdate.attr("y",pathTxtY);
				pathTxtUpdate.attr("text-anchor","middle");
				pathTxtUpdate.attr("font-family","Arial");
				pathTxtUpdate.attr("font-size","10px");
				pathTxtUpdate.attr("stroke","none");
				pathTxtUpdate.attr("fill","#000000");
				pathTxtUpdate.call(pathTxt_drag_fun);
				txtSpan=pathTxtUpdate.append("tspan");
				txtSpan.text(pathObj.text);
				pathList.push(pathObj);
			}
		};
		this.getDataList=function(){
			var eleList;
			var pathAllList;
			eleList=[];
			pathAllList=[];
			$(rectList).each(function(i,ele) {
				var rect;
				rect=d3.select("rect[class='"+ele.id+"']").node();
                eleList.push({"rect":rect,"data":ele});
            });
			$(pathList).each(function(i,ele) {
				var path;
				path=d3.select("path[class='"+ele.from+" "+ele.to+"']").node();
                pathAllList.push({"path":path,"data":ele});
            });
			return {"rectList":eleList,"pathList":pathAllList};
		};
		this.getRectList=function(){
			return rectList;
		};
		this.getPathList=function(){
			return pathList;
		};
		this.updateRect=function(rectObj){
			var id;
			var txt;
			var properties;
			id=rectObj.id;
			txt=rectObj.txt;
			properties=rectObj.properties;
			$(rectList).each(function(i,ele) {
				if(ele.id==id)
				{
					ele.properties=properties;
					ele.text=txt;
					return false;
				}
			});
			d3.selectAll("text."+id).text(function(u,i){
				var text;
				if(-1==$(this).attr("class").indexOf("pathText"))
				{
					text=txt;
				}
				else
				{
					text=$(this).text();
				}
				return text;
			});
		};
		this.delRect=function(id){
			var num;
			var pList;
			pList=[];
			$(rectList).each(function(index, eleObj) {
                if(eleObj.id==id)
				{
					num=index;
					return false;
				}
            });
			$(pathList).each(function(index, eleObj) {
                if(eleObj.from==id)
				{
					pList.push(index);
				}
				if(eleObj.to==id)
				{
					pList.push(index);
				}
            });
			rectList.splice(num,1);
			$(pList).each(function(i, ele) {
                pathList.splice(ele-i,1);
            });
			d3.selectAll("."+id).remove();
		};
		this.updatePath=function(pathObj){
			var textX;
			var textY;
			var pathTxt;
			var pathInfo;
			textX=0;
			textY=0;
			$(pathList).each(function(i,ele) {
                if(pathObj.from==ele.from && pathObj.to==ele.to)
				{
					ele.text=pathObj.text;
					if(null!=ele.properties && null!=ele.properties.textX)
					{
						textX=ele.properties.textX;
					}
					if(null!=ele.properties && null!=ele.properties.textY)
					{
						textY=ele.properties.textY;
					}
					ele.properties=pathObj.properties;
					ele.properties.textX=textX;
					ele.properties.textY=textY;
					return false;
				}
            });
			pathTxt=d3.selectAll("text[class='"+pathObj.from+" "+pathObj.to+" pathText']");
			pathTxt.text(pathObj.text);
			pathInfo=d3.selectAll("path[class='"+pathObj.from+" "+pathObj.to+"']");
			if("0"==pathObj.properties.linetype || "1"==pathObj.properties.linetype || "2"==pathObj.properties.linetype)
			{
				for(var obj in path_attr)
				{
					pathInfo.attr(obj,path_attr[obj]);
				}
			}
			if("3"==pathObj.properties.linetype || "4"==pathObj.properties.linetype || "5"==pathObj.properties.linetype)
			{
				for(var obj in path_dash_attr)
				{
					pathInfo.attr(obj,path_dash_attr[obj]);
				}
			}
			if("0"==pathObj.properties.linetype || "3"==pathObj.properties.linetype)
			{
				if(2==$(pathObj.dots).size())
				{
					pathObj.dots.splice(0,1);
					pathObj.dots.splice(0,1);
				}
			}
			pathInfo.attr("d",function(d,i){
				var list;
				var pathInfo;
				pathInfo="";
				list=drawPath(d);
				for(var i=0;i<list.length;i++)
				{
					pathInfo+=list[i];
					if((i+2)%3==0){
						pathInfo+=",";
					}
				}
				return pathInfo;
			});
		};
		this.delPath=function(pathObj){
			var from;
			var to;
			var eleIndex;
			from=pathObj.from;
			to=pathObj.to;
			eleIndex=-1;
			$(pathList).each(function(index, ele) {
                if(ele.from==from && ele.to==to)
				{
					eleIndex=index;
					return false;
				}
            });
			if(-1!=eleIndex)
			{
				pathList.splice(eleIndex,1);
				d3.select("path[class='"+from+" "+to+"']").remove();
				d3.select("text[class='"+from+" "+to+" pathText']").remove();
				d3.select("circle[class='"+from+" "+to+"']").remove();
			}
		};
		this.setWidth=function(width){
			$(divObj).width(width);
		};
		this.setHeight=function(height){
			$(divObj).height(height);
		};
		//-----------------------------------------------------------------------
		//私有方法
		initdraw=function(rects,paths,proper){
			var defs;
			var marker;
			var imgArr;
			var txtArr;
			var rectArr;
			var pathArr;
			var markPath;
			var imgUpdate;
			var txtUpdate;
			var rectUpdate;
			var pathUpdate;
			var pathTxtUpdate;
			rectArr = svg.selectAll("rect");
			imgArr = svg.selectAll("image");
			txtArr = svg.selectAll("text");
			pathArr = svg.selectAll("path");
			rectUpdate=rectArr.data(rects).enter().append("rect");
			imgUpdate=imgArr.data(rects).enter().append("image");
			txtUpdate=txtArr.data(rects).enter().append("text");
			pathUpdate=pathArr.data(paths).enter().append("path");
			pathTxtUpdate=txtArr.data(paths).enter().append("text");
			defs=svg.append("defs");
			marker=defs.append("marker");
			markPath=marker.append("path");
			for(var obj in arrow_attr)
			{
				marker.attr(obj,arrow_attr[obj]);
			}
			for(var obj in arrow_path){
				markPath.attr(obj,arrow_path[obj]);
			}
			rectUpdate.attr("class",function(d,i){
				return d.id;
			});
			rectUpdate.attr("x",function(d,i){
				return d.left;
			});
			rectUpdate.attr("y",function(d,i){
				return d.top;
			});
			rectUpdate.attr("width",function(d,i){
				return d.type.width+rect_margin_w;
			});
			rectUpdate.attr("height",function(d,i){
				return d.type.height+rect_margin_h;
			});
			rectUpdate.attr("rx",function(d,i){
				return rect_r;
			});
			rectUpdate.attr("ry",function(d,i){
				return rect_r;
			});
			for (var key in rect_attr){
				rectUpdate.attr(key,rect_attr[key]);
			}
			imgUpdate.attr("class",function(d,i){
				return d.id;
			});
			imgUpdate.attr("href",function(d,i){
				return d.type.src;
			});
			imgUpdate.attr("x",function(d,i){
				return d.left+(rect_margin_w/2);
			});
			imgUpdate.attr("y",function(d,i){
				return d.top+(rect_margin_h/2);
			});
			imgUpdate.attr("width",function(d,i){
				return d.type.width;
			});
			imgUpdate.attr("height",function(d,i){
				return d.type.height;
			});
			imgUpdate.attr("preserveAspectRatio","none");
			if(true==proper.candrag)
			{
				imgUpdate.call(img_drag_fun);
			}
			txtUpdate.attr("class",function(d,i){
				return d.id;
			});
			txtUpdate.attr("x",function(d,i){
				return d.left+d.type.width+(rect_margin_w-d.type.width)/2;
			});
			txtUpdate.attr("y",function(d,i){
				return d.top+(d.type.height/2)+rect_margin_h;
			});
			txtUpdate.attr("text-anchor","middle");
			txtUpdate.attr("font-family","Arial");
			txtUpdate.attr("font-size","10px");
			txtUpdate.attr("stroke","none");
			txtUpdate.attr("fill","#000000");
			txtSpan=txtUpdate.append("tspan");
			txtSpan.text(function(d,i){
				return d.text;
			});
			pathUpdate.attr("fill","none");
			pathUpdate.attr("stroke","#000000"); 
			pathUpdate.attr("class",function(d,i){
				return d.from+" "+d.to;
			});
			pathUpdate.attr("marker-end","url(#arrow)");
			pathUpdate.attr("d",pathWay);
			for(var obj in path_attr)
			{
				pathUpdate.filter(function(d,i){
					var blInfo;
					blInfo=false;
					if(null==d.properties)
					{
						blInfo=true;
					}
					if(null==d.properties.linetype)
					{
						blInfo=true;
					}
					if("0"==d.properties.linetype)
					{
						blInfo=true;
					}
					if("1"==d.properties.linetype)
					{
						blInfo=true;
					}
					if("2"==d.properties.linetype)
					{
						blInfo=true;
					}
					return blInfo;
				}).attr(obj,path_attr[obj]);
			}
			for(var obj in path_dash_attr)
			{
				pathUpdate.filter(function(d,i){
					var blInfo;
					blInfo=false;
					if(null==d.properties)
					{
						blInfo=false;
					}
					if(null==d.properties.linetype)
					{
						blInfo=false;
					}
					if("3"==d.properties.linetype)
					{
						blInfo=true;
					}
					if("4"==d.properties.linetype)
					{
						blInfo=true;
					}
					if("5"==d.properties.linetype)
					{
						blInfo=true;
					}
					return blInfo;
				}).attr(obj,path_dash_attr[obj]);
			}
			if(true==proper.candrag)
			{
				pathUpdate.on("click",pathClick);
			}
			pathTxtUpdate.attr("class",function(d,i){
				return d.from+" "+d.to+" pathText";
			});
			pathTxtUpdate.attr("x",pathTxtX);
			pathTxtUpdate.attr("y",pathTxtY);
			pathTxtUpdate.attr("text-anchor","middle");
			pathTxtUpdate.attr("font-family","Arial");
			pathTxtUpdate.attr("font-size","10px");
			pathTxtUpdate.attr("stroke","none");
			pathTxtUpdate.attr("fill","#000000");
			if(true==proper.candrag)
			{
				pathTxtUpdate.call(pathTxt_drag_fun);
			}
			txtSpan=pathTxtUpdate.append("tspan");
			txtSpan.text(function(d,i){
				return d.text;
			});
		},
		getSvgObj=function(ObjList,type){
			var objInfo;
			objInfo=null;
			if(null!=ObjList && null!=ObjList._groups && 0!=$(ObjList._groups).size())
			{
				if(null!=ObjList._groups[0])
				{
					for(var obj in ObjList._groups[0])
					{
						if("image"==ObjList._groups[0][obj].tagName)
						{
							objInfo=ObjList._groups[0][obj];
							break;
						}
					}
				}
			}
			return objInfo;
		};
		pathTxtX=function(d,i){
			var txtX;
			var sBox;
			var dBox;
			var toObj;
			var toList;
			var result;
			var fromObj;
			var fromList;
			toObj=null;
			fromObj=null;
			if(0!=d.textX)
			{
				txtX=d.textX;
			}
			else
			{
				fromList=d3.selectAll("."+d.from);
				toList=d3.selectAll("."+d.to);
				fromObj=getSvgObj(fromList,"image");
				toObj=getSvgObj(toList,"image");
				if(null!=fromObj && null!=toObj)
				{
					sBox=fromObj.getBBox();
					dBox=toObj.getBBox();
					result=getStartEnd(sBox,dBox);
					txtX=(result.start.x+result.end.x)/2;
				}
			}
			return txtX;
		};
		pathTxtY=function(d,i){
			var txtY;
			var sBox;
			var dBox;
			var toObj;
			var toList;
			var result;
			var fromObj;
			var fromList;
			toObj=null;
			fromObj=null;
			if(0!=d.textY)
			{
				txtY=d.textY;
			}
			else
			{
				fromList=d3.selectAll("."+d.from);
				toList=d3.selectAll("."+d.to);
				fromObj=getSvgObj(fromList,"image");
				toObj=getSvgObj(toList,"image");
				if(null!=fromObj && null!=toObj)
				{
					sBox=fromObj.getBBox();
					dBox=toObj.getBBox();
					result=getStartEnd(sBox,dBox);
					txtY=(result.start.y+result.end.y)/2;
				}
			}
			return txtY;
		};
		pathClick=function(d){
			var sBox;
			var dBox;
			var txtX;
			var txtY;
			var toObj;
			var result;
			var toList;
			var fromObj;
			var fromList;
			var hasCircle;
			var circleObj;
			var circleSize;
			circleObj=null;
			hasCircle=false;
			circleSize=0;
			circleSize=d3.selectAll("circle[class='"+d.from+" "+d.to+"']").size();
			circleObj=d3.selectAll("circle[class='"+d.from+" "+d.to+"']");
			if(null != d.properties.linetype && "0"!=d.properties.linetype && "3"!=d.properties.linetype)
			{
				if(0!=circleSize)
				{
					circleObj.style("display","inline");
					txtX=(d.dots[0].x+d.dots[1].x)/2;
					txtY=(d.dots[0].y+d.dots[1].y)/2;
					circleObj.attr("cx",txtX);
					circleObj.attr("cy",txtY);
				}
				else
				{
					if(0==$(d.dots).size())
					{
						fromList=d3.selectAll("."+d.from);
						toList=d3.selectAll("."+d.to);
						fromObj=getSvgObj(fromList,"image");
						toObj=getSvgObj(toList,"image");
						if(null!=fromObj && null!=toObj)
						{
							sBox=fromObj.getBBox();
							dBox=toObj.getBBox();
							result=getStartEnd(sBox,dBox);
							txtX=(result.start.x+result.end.x)/2;
							txtY=(result.start.y+result.end.y)/2;
						}
					}
					else{
						txtX=(d.dots[0].x+d.dots[1].x)/2;
						txtY=(d.dots[0].y+d.dots[1].y)/2;
					}
					circleObj=svg.append("circle");
					circleObj.attr("class",d.from+" "+d.to);
					circleObj.attr("cx",txtX);
					circleObj.attr("cy",txtY);
					circleObj.datum(d);
					for(var obj in circle_attr)
					{
						circleObj.attr(obj,circle_attr[obj]);
					}
					circleObj.call(circle_drag_fun);
				}
			}
			else
			{
				if(null!=circleObj)
				{
					$(circleObj).remove();
				}
			}
			if(null!=selPath)
			{
				selPath(d);
			}
		};
		pathWay=function(d){
			var list;
			var pathInfo;
			pathInfo="";
			list=drawPath(d);
			for(var i=0;i<list.length;i++)
			{
				pathInfo+=list[i];
				if((i+2)%3==0){
					pathInfo+=",";
				}
			}
			return pathInfo;
		};
		drawPath=function(path){
			var sBox;
			var dBox;
			var toObj;
			var result;
			var toList;
			var fromObj;
			var pointArr;
			var linePath;
			var fromList;
			var pointList;
			toObj=null;
			fromObj=null;
			pointList=[];
			linePath="";
			if(null!=path && null!=path.from && null!=path.to)
			{
				fromList=d3.selectAll("."+path.from);
				toList=d3.selectAll("."+path.to);
				fromObj=getSvgObj(fromList,"image");
				toObj=getSvgObj(toList,"image");
				if(null!=fromObj && null!=toObj)
				{
					sBox=fromObj.getBBox();
					dBox=toObj.getBBox();
					linePath=getArrPath({"obj1":sBox,"obj2":dBox,"dots":path.dots,"num":1});
				}
			}
			return linePath;
		};
		pathTxtdrag=function(d){
			var pathTxt;
			pathTxt=d3.selectAll("text[class='"+d.from+" "+d.to+" pathText']");
			pathTxt.attr("x",d3.event.x);
			pathTxt.attr("y",d3.event.y);
			d.properties.textX=d3.event.x;
			d.properties.textY=d3.event.y;
		};
		imgdragstart=function(d){
			d3.selectAll("rect[class='"+d.id+"']").style("display","inline");
		};
		imgdrag=function(d){
			var imgWidth;
			var imgHeight;
			var pathUpdate;
			imgWidth=0;
			imgHeight=0;
			imgWidth=d.type.width;
			imgHeight=d.type.height;
			d3.selectAll("rect."+d.id).attr("x",function(u,i){
				var x;
				x=d3.event.x;
				return x;
			});
			d3.selectAll("image."+d.id).attr("x",function(u,i){
				var x;
				x=d3.event.x+(rect_margin_w/2);
				return x;
			});
			d3.selectAll("text."+d.id).attr("x",function(u,i){
				var x;
				if(-1==$(this).attr("class").indexOf("pathText"))
				{
					x=d3.event.x+imgWidth+(rect_margin_w-imgWidth)/2;
				}
				else
				{
					x=$(this).attr("x");
				}
				return x;
			});
			d3.selectAll("rect."+d.id).attr("y",function(u,i){
				var y;
				y=d3.event.y;
				return y;
			});
			d3.selectAll("image."+d.id).attr("y",function(u,i){
				var y;
				y=d3.event.y+(rect_margin_h/2);
				return y;
			});
			d3.selectAll("text."+d.id).attr("y",function(u,i){
				var y;
				if(-1==$(this).attr("class").indexOf("pathText"))
				{
					y=d3.event.y+imgHeight/2+rect_margin_h;
				}
				else
				{
					y=$(this).attr("y");
				}
				return y;
			});
			pathUpdate=d3.selectAll("path."+d.id);
			pathUpdate.attr("d",function(d,i){
				var list;
				var pathInfo;
				pathInfo="";
				list=drawPath(d);
				for(var i=0;i<list.length;i++)
				{
					pathInfo+=list[i];
					if((i+2)%3==0){
						pathInfo+=",";
					}
				}
				return pathInfo;
			});
		};
		imgdragend=function(d){
			d3.selectAll("rect[class='"+d.id+"']").style("display","none");
			if(null!=rectList)
			{
				$(rectList).each(function(i,ele) {
                    if(ele.id==d.id)
					{
						ele.x=d3.event.x;
						ele.y=d3.event.y;
						return false;
					}
                });
			}
			if(null!=clickRect)
			{
				clickRect({"id":d.id,"x":d3.event.x,"y":d3.event.y,"text":d.text,"properties":d.properties,"type":d.type});
			}
		};
		circledrag=function(d){
			var sBox;
			var dBox;
			var toObj;
			var result;
			var toList;
			var fromObj;
			var pathArr;
			var fromList;
			fromList=d3.selectAll("."+d.from);
			toList=d3.selectAll("."+d.to);
			fromObj=getSvgObj(fromList,"image");
			toObj=getSvgObj(toList,"image");
			if(null!=fromObj && null!=toObj)
			{
				sBox=fromObj.getBBox();
				dBox=toObj.getBBox();
			}
			if("1"==d.properties.linetype || "4"==d.properties.linetype)
			{
				d3.selectAll("circle[class='"+d.from+" "+d.to+"']").attr("cx",d3.event.x);
				if(0==$(d.dots).size())
				{
					d.dots.push({"x":d3.event.x,"y":(sBox.y+sBox.height/2)});
					d.dots.push({"x":d3.event.x,"y":(dBox.y+dBox.height/2)});
				}
				else
				{
					d.dots[0].x=d3.event.x;
					d.dots[0].y=(sBox.y+sBox.height/2);
					d.dots[1].x=d3.event.x;
					d.dots[1].y=(dBox.y+dBox.height/2);
				}
			}
			if("2"==d.properties.linetype || "5"==d.properties.linetype)
			{
				d3.selectAll("circle[class='"+d.from+" "+d.to+"']").attr("cy",d3.event.y);
				if(0==$(d.dots).size())
				{
					d.dots.push({"x":(sBox.x+sBox.width/2),"y":d3.event.y});
					d.dots.push({"x":(dBox.x+dBox.width/2),"y":d3.event.y});
				}
				else
				{
					d.dots[0].x=(sBox.x+sBox.width/2);
					d.dots[0].y=d3.event.y;
					d.dots[1].y=d3.event.y;
					d.dots[1].x=(dBox.x+dBox.width/2);
				}
			}
			pathArr = svg.selectAll("path[class='"+d.from+" "+d.to+"']");
			pathArr.attr("d",function(u,i){
				var list;
				var pathInfo;
				pathInfo="";
				list=drawPath(d);
				for(var i=0;i<list.length;i++)
				{
					pathInfo+=list[i];
					if((i+2)%3==0){
						pathInfo+=",";
					}
				}
				return pathInfo;
			});
		};
		circledragend=function(d){
			$(this).css("display","none");
		};
		getArrPath=function(obj){
			var dots;
			var dot;
			var dlist;
			var objS;
			var objD;
			var objCenter;
			var numObj;
			var pathInfo;
			var retList;
			var blInfo;
			dlist=[];
			pathInfo=[];
			dot=null;
			dots=obj.dots;
			pathInfo='';
			numObj=obj.num;
			retList=[];
			//objS和objD为对象轮廓边界
			objS=obj.obj1;
			objD=obj.obj2;
			//objCenter中间点
			objCenter=null;
			blInfo=false;
			for(var i=0;i<dots.length;i++)
			{
				if(i==0)
				{
					dot=dots[i];
				}
				else
				{
					dlist.push(dots[i]);
				}
			}
			if(1==numObj)
			{
				blInfo=true;
				numObj++;
			}
			if(0==dots.length)
			{
				numObj=0;
			}
			if(0!=numObj)
			{
				objCenter={"x":dot.x-1,"width":2,"y":dot.y-1,"height":2};
				point = getStartEnd(objS,objCenter);
				pathInfo = getPathWay(point.start.x, point.start.y, point.end.x, point.end.y);
				if(dlist.length>=1)
				{
					retList=retList.concat(pathInfo);
					pathInfo=getArrPath({"obj1":objCenter,"obj2":objD,"dots":dlist,"num":numObj});
					retList=retList.concat(pathInfo);
					
				}
				else
				{
					retList=retList.concat(pathInfo);
					pathInfo=getArrPath({"obj1":objCenter,"obj2":objD,"dots":dlist,"num":numObj});
					retList=retList.concat(pathInfo);
				}
			}
			else
			{
				point = getStartEnd(objS,objD);
				pathInfo = getPathWay(point.start.x, point.start.y, point.end.x, point.end.y);
				retList=retList.concat(pathInfo);
			}
			return retList;
		};
		//得到路径
		getPathWay=function(x1, y1, x2, y2) {
			var result;
			result = ["M", x1, y1, "L", x2, y2];
            return result;
		};
		getStartEnd=function(bb1, bb2) {
			var p;
			var d;
			var dis;
			var dx;
			var dy;
			var res;
			var result;
			d = {};
			dis = [];
            p = [
                    { x: bb1.x + bb1.width / 2, y: bb1.y - 1 },
                    { x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1 },
                    { x: bb1.x - 1, y: bb1.y + bb1.height / 2 },
                    { x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2 },
                    { x: bb2.x + bb2.width / 2, y: bb2.y - 1 },
                    { x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1 },
                    { x: bb2.x - 1, y: bb2.y + bb2.height / 2 },
                    { x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2 }
			];
            for (var i = 0; i < 4; i++) {
                for (var j = 4; j < 8; j++) {
                    dx = Math.abs(p[i].x - p[j].x);
                    dy = Math.abs(p[i].y - p[j].y);
                    if (
                         (i == j - 4) ||
                         (((i != 3 && j != 6) || p[i].x < p[j].x) &&
                         ((i != 2 && j != 7) || p[i].x > p[j].x) &&
                         ((i != 0 && j != 5) || p[i].y > p[j].y) &&
                         ((i != 1 && j != 4) || p[i].y < p[j].y))
                       ) {
                        dis.push(dx + dy);
                        d[dis[dis.length - 1]] = [i, j];
                    }
                }
            }
            if (dis.length == 0) {
                res = [0, 4];
            } else {
                res = d[Math.min.apply(Math, dis)];
            }
            result = {};
            result.start = {};
            result.end = {};
            result.start.x = p[res[0]].x;
            result.start.y = p[res[0]].y;
            result.end.x = p[res[1]].x;
            result.end.y = p[res[1]].y;
            return result;
        };
	}
})(jQuery);
