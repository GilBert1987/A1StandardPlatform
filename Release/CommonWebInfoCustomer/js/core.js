(function ($){
    $.WorkFlow = function () {
		//转换对象
		var divObj=null;
		//初始化数据
		var initData=null;
		//绘画对象
		var paper=null;
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
            "stroke-width":1
		};
		var path_attr={
			"stroke-width":3
		};
		var circle_attr={
			"fill":"#ffffff"
		};
		//箭头圆角
		var arr_r=8;
		//图像数组
		var dataList=[];
		/*
		结构{"id":"","content":"",parentid:""}
		id为-1时为顶点
		*/
		//操作数组
		//var controlList=[];
		//路径数组
		var pathList=[];
		//路径变更列表
		//var circleList=[];
		//图形高度
		//var lineHeight=15;
		//初始化路径文字偏移量
		var pathText_margin_w=0;
		//初始化路径文字偏移量
		var pathText_margin_h=10;
		//使用贝斯尔曲线
		var useBerzierPath=false;
		//鼠标偏移量
		var mouse_x=-30;
		var mouse_y=-30;
		
		//----------------------------------------------------------------------
		//公用方法
		this.init=function(){
			var rects;
			var props;
			rects=null;
			props=null;
			if(null != divObj && null != initData)
			{
				paper=Raphael(divObj, $(divObj).width(), $(divObj).height());
				rects=initData.rects;
				pathList=initData.paths;
				props=initData.props;
				if(null!=rects && null !=pathList && null!=props)
				{
					$(rects).each(function(index, ele) {
                        if(null!=ele.type)
						{
							drawRect(ele);
						}
                    });
					$(pathList).each(function(index, ele) {
                        drawPath(ele);
                    });
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
		this.addRect=function(rectObj){
			if(null!=rectObj)
			{
				drawRect(rectObj);
			}
		};
		this.addPath=function(pathObj){
			if(null!=pathObj)
			{
				pathList.push(pathObj);
				drawPath(pathObj);
			}
		};
		this.createUUID=function(){
			return Raphael.createUUID().replace(/-/g, "");
		};
		this.getPathList=function(){
			return pathList;
		};
		this.getDataList=function(){
			return dataList;
		};
		this.updateRect=function(rectObj){
			var id;
			var txt;
			var properties;
			var txtObj;
			var imgObj;
			id=rectObj.id;
			txt=rectObj.txt;
			properties=rectObj.properties;
			txtObj=null;
			imgObj=null;
			$(dataList).each(function(index, eleObj) {
                if(eleObj.id==id)
				{
					imgObj=eleObj.st[1];
					txtObj=eleObj.st[2];
					return false;
				}
            });
			if(null!=txtObj && null != imgObj)
			{
				txtObj.attr("text",txt);
				imgObj.data("value",rectObj);
			}
		};
		this.delRect=function(id){
			var num;
			var listObj;
			var list;
			list=[];
			$(dataList).each(function(index, eleObj) {
                if(eleObj.id==id)
				{
					num=index;
					listObj=eleObj;
					return false;
				}
            });
			//删除别人连自己的线
			$(pathList).each(function(index, ele){
                if(ele.to==id)
				{
					list.push(ele);
				}
            });
			$(dataList).each(function(index, eleObj) {
                $(list).each(function(i, ele) {
                    if(ele.from==eleObj.id)
					{
						delPath(eleObj,id);
						return false;
					}
                });
            });
			//删除自己的连线
			delPath(listObj,id);
			listObj.st[0].remove();
			listObj.st[1].remove();
			listObj.st[2].remove();
			dataList.splice(num,1);
		};
		this.updatePath=function(pathObj){
			modifyPath(pathObj.from,pathObj.to,pathObj.text,pathObj.properties);
		};
		this.delPath=function(pathObj){
			var from;
			var to;
			var eleObj;
			from=pathObj.from;
			to=pathObj.to;
			$(dataList).each(function(index, ele) {
                if(ele.id==from)
				{
					eleObj=ele;
					return false;
				}
            });
			delPath(eleObj,to);
		};
		this.setWidth=function(width){
			$(divObj).width(width);
		};
		this.setHeight=function(height){
			$(divObj).height(height);
		};
		this.setAnimateRect=function(value)
		{
			animateRect=value;
		};
		//-----------------------------------------------------------------------
		//私有方法
		modifyPath=function(from,to,txt,properties){
			var eleid;
			var eleObj;
			var rectObj;
			var pathObj;
			var pathsList;
			var from;
			var to;
			var dots;
			var fImg;
			var tImg;
			var bbox1;
			var bbox2;
			var backData;
			var newcircle;
			dots=[];
			$(dataList).each(function(index, ele) {
                if(ele.id==from)
				{
					eleObj=ele;
					return false;
				}
            });
			eleid=eleObj.id;
			rectObj=eleObj.st[0];
			pathsList=rectObj.arrPath;
			$(pathsList).each(function(i, ele) {
				pathObj=$(ele).data("pathObj");
				if(null!=pathObj && (pathObj.from==from && pathObj.to==to))
				{
					pathObj.text=txt;
					pathObj.properties=properties;
					if("0"==pathObj.properties.linetype)
					{
						pathObj.dots=dots;
						$(dataList).each(function(index, ele) {
							if(pathObj.from==ele.id)
							{
								from=ele.st;
							}
							if(pathObj.to==ele.id)
							{
								to=ele.st;
							}
						});
						if(null!=from && null!=to)
						{
							fImg=from[1];
							tImg=to[1];
							bbox1=fImg.getBBox();
							bbox2=tImg.getBBox();
							backData=getStartEnd(bbox1,bbox2);
							newcircle=$(ele).data("circles")[0];
							newcircle.attr({"cx":(backData.start.x+backData.end.x)/2,"cy":(backData.start.y+backData.end.y)/2});
						}
					}
					$(ele).data("circles").hide();
					drawPath(pathObj);
				}
            });
			$(pathList).each(function(index, eleObj) {
				if(from==eleObj.from && to==eleObj.to)
				{
					eleObj.text=txt;
					return false;
				}
			});
		};
		//删除园点
		delCircle=function(pathObj){
			$($(pathObj).data("circles")).each(function(index, circle) {
				circle.remove();
			});
		};
		delPath=function(eleObj,id){
			var eleid;
			var rectObj;
			var pathObj;
			var pathsList;
			var list;
			list=[];
			eleid=eleObj.id;
			rectObj=eleObj.st[0];
			pathsList=rectObj.arrPath;
			$(pathsList).each(function(i, ele) {
				pathObj=$(ele).data("pathObj");
				if(null != pathObj && (pathObj.from == id || pathObj.to == id))
				{
					delCircle(ele);
					$(pathList).each(function(index, eleInfo) {
						if(eleObj.id == eleInfo.from && id == eleInfo.to)
						{
							pathList.splice(index,1);
							return false;
						}
					});
					list.push(i);
                	ele.remove();
				}
            });
			$(list).each(function(index, eleObj) {
                pathsList.splice(eleObj,1);
            });
		};
		//得到箭头
		getArr=function(x1, y1, x2, y2, size) {
			var angle;
			var a45;
			var a45m;
			var x2a;
			var y2a;
			var x2b;
			var y2b;
			var result;
            angle = Raphael.angle(x1, y1, x2, y2); //得到两点之间的角度
            a45 = Raphael.rad(angle - 45); //角度转换成弧度
            a45m = Raphael.rad(angle + 45);
            x2a = x2 + Math.cos(a45) * size;
            y2a = y2 + Math.sin(a45) * size;
            x2b = x2 + Math.cos(a45m) * size;
            y2b = y2 + Math.sin(a45m) * size;
            result = ["M", x1, y1, "L", x2, y2, "L", x2a, y2a, "M", x2, y2, "L", x2b, y2b];
            return result;
        };
		//得到路径
		getPathWay=function(x1, y1, x2, y2) {
			var result;
			result = ["M", x1, y1, "L", x2, y2];
            return result;
		};
		//得到2个矩形间的距离
		//3个点的对象 左 中 右
		isLine=function(left, center, right) {
			//斜率
			var slope;
			//基准y的坐标点
			var descslope;
			if ((left.x - right.x) == 0) {
				slope = 1
			} else {
				slope = (left.y - right.y) / (left.x - right.x);
			}
			descslope = (center.x - right.x) * slope + right.y;
			if ((center.y - descslope) < 10 && (center.y - descslope) > -10) {
				center.y = descslope;
				return true
			}
			return false
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
		//拖动节点开始时的事件
		dragger=function(){
			var text;
			var imgObj;
			var rectObj;
			//cleArrPath();
			imgObj=null;
			if("image"==this.node.nodeName)
			{
				imgObj=this;
				rectObj=this.prev;
				text=this.next;
			}
			if("text"==this.node.nodeName)
			{
				text=this;
				imgObj=this.prev;
				rectObj=imgObj.prev;
			}
			if(null!=imgObj)
			{
				imgObj.ox = imgObj.attr("x");
				imgObj.oy = imgObj.attr("y");
				text.ox=text.attr("x");
				text.oy=text.attr("y");
				rectObj.ox=rectObj.attr("x");
				rectObj.oy=rectObj.attr("y");
				rectObj.show();
			}
		};
		//拖动事件
		move=function(dx,dy){
			var att;
			var text;
			var rectObj;
			var rect;
			var imgObj;
			var valObj;
			imgObj=null;
			if("image"==this.node.nodeName)
			{
				imgObj=this;
				rectObj=this.prev;
				text=this.next;
			}
			if("text"==this.node.nodeName)
			{
				text=this;
				imgObj=this.prev;
				rectObj=imgObj.prev;
			}
			if(null!=imgObj)
			{
				if(null==imgObj.ox)
				{
					imgObj.ox=dx;
				}
				if(null==imgObj.oy)
				{
					imgObj.oy=dy;
				}
				att = {"x": imgObj.ox + dx,"y": imgObj.oy + dy };
				if(null==text.ox)
				{
					text.ox=dx;
				}
				if(null==text.oy)
				{
					text.oy=dy;
				}
				if(null==rectObj.ox)
				{
					rectObj.ox=dx;
				}
				if(null==rectObj.oy)
				{
					rectObj.oy=dy;
				}
				if(150<=att.x && 1000>=att.x)
				{
					valObj=imgObj.data("value");
					imgObj.attr(att);
					text.attr({"x":text.ox+ dx,"y": text.oy + dy});
					rectObj.attr({"x":rectObj.ox+ dx,"y": rectObj.oy + dy});
					$(pathList).each(function(index, ele) {
						if(ele.from==valObj.id)
						{
							drawPath(ele);
						}
						if(ele.to==valObj.id)
						{
							drawPath(ele);
						}
					});
				}
			}
		};
		//拖动结束后的事件
		up=function(){
			var rectObj;
			var imgObj;
			if("image"==this.node.nodeName)
			{
				imgObj=this;
				rectObj=this.prev;
			}
			if("text"==this.node.nodeName)
			{
				imgObj=this.prev;
				rectObj=imgObj.prev;
			}
			if(null!=imgObj)
			{
				rectObj.hide();
			}
		};
		drawRect=function(rect){
			var img;
			var rectObj;
			var text;
			var st;
			st=paper.set();
			rectObj=paper.rect(rect.left,rect.top,rect.type.width+rect_margin_w,rect.type.height+rect_margin_h,rect_r);
			img=paper.image(rect.type.src,rect.left+(rect_margin_w/2),rect.top+(rect_margin_h/2),rect.type.width,rect.type.height);
			text=paper.text(rect.left+rect.type.width+(rect_margin_w-rect.type.width)/2,rect.top+(rect.type.height/2)+rect_margin_h,rect.text);
			img.data("value",rect);
			rectObj.attr(rect_attr);
			rectObj.hide();
			st.push(rectObj);
			st.push(img);
			st.push(text);
			st.drag(move,dragger,up);
			if(null!=clickRect)
			{
				st.click(clickRect);
			}
			dataList.push({"id":rect.id,"st":st});
		};
		drawArr = function (obj) {
            var paths;
			var pathObj;
            var pathInfo;
			var blIsnew;
			var currIndex;
			currIndex=0;
			paths=paper.set();
			pathInfo = getArrPath(obj);
			if(pathInfo.length>15 && true==useBerzierPath)
			{
				pathInfo=getBerzierPathWay(pathInfo);
			}
            if (obj.arrPath) {
				blIsnew=true;
				$(obj.arrPath).each(function(index, ele) {
                    if("path"==ele.type && $(ele).data("pathObj").from==obj.pathObj.from && $(ele).data("pathObj").to==obj.pathObj.to){
						blIsnew=false;
						currIndex=index;
						ele.attr({"path":pathInfo});
						return false;
					}
                });
				if(blIsnew==true){
					pathObj=paper.path(pathInfo);
					$(pathObj).data("pathObj",obj.pathObj);
					pathObj.attr(path_attr);
					pathObj.click(function(){
						selArrPath(this);
					});
					point=getStartEnd(obj.obj1,obj.obj2);
					if(null==obj.pathObj.properties.textX && null == obj.pathObj.properties.textY)
					{
						text=paper.text(point.start.x+(point.end.x-point.start.x)/2-pathText_margin_w,point.start.y+(point.end.y-point.start.y)/2-pathText_margin_h,(obj.pathObj.text==null?"":obj.pathObj.text));
					}
					else
					{
						text=paper.text(obj.pathObj.properties.textX,obj.pathObj.properties.textY,(obj.pathObj.text==null?"":obj.pathObj.text));
					}
					obj.arrPath.push(pathObj);
					obj.arrPath.push(text);
					text.drag(function(){movePathText(this,obj.pathObj);},function(){dragPathText(this,obj.pathObj);},function(){updatePathText(this,obj.pathObj);});
				}
				else
				{
					if(null==obj.pathObj.properties.textX && null == obj.pathObj.properties.textY)
					{
						point=getStartEnd(obj.obj1,obj.obj2);
						text=obj.arrPath[currIndex+1];
						text.attr("x",point.start.x+(point.end.x-point.start.x)/2-pathText_margin_w);
						text.attr("y",point.start.y+(point.end.y-point.start.y)/2-pathText_margin_h);
						text.attr("text",obj.pathObj.text);
					}
				}
            } 
			else 
			{
				pathObj=paper.path(pathInfo);
				$(pathObj).data("pathObj",obj.pathObj);
				pathObj.attr(path_attr);
				pathObj.click(function(){
					selArrPath(this);
				});
				paths.push(pathObj);
				point=getStartEnd(obj.obj1,obj.obj2);
				if(null==obj.pathObj.properties.textX && null == obj.pathObj.properties.textY)
				{
					text=paper.text(point.start.x+(point.end.x-point.start.x)/2-pathText_margin_w,point.start.y+(point.end.y-point.start.y)/2-pathText_margin_h,(obj.pathObj.text==null?"":obj.pathObj.text));
				}
				else
				{
					text=paper.text(obj.pathObj.properties.textX,obj.pathObj.properties.textY,(obj.pathObj.text==null?"":obj.pathObj.text));
				}
				text.drag(function(){movePathText(this,obj.pathObj);},function(){dragPathText(this,obj.pathObj);},function(){updatePathText(this,obj.pathObj);});
				obj.arrPath=paths;
				obj.arrPath.push(text);
            }
            return obj;
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
					pathInfo=getArrPath({"obj1":objCenter,"obj2":objD,"arrPath":objS.arrPath,"dots":dlist,"num":numObj,"pathObj":obj.path});
					retList=retList.concat(pathInfo);
					
				}
				else
				{
					retList=retList.concat(pathInfo);
					pathInfo=getArrPath({"obj1":objCenter,"obj2":objD,"arrPath":objS.arrPath,"dots":dlist,"num":numObj,"pathObj":obj.path});
					retList=retList.concat(pathInfo);
				}
			}
			else
			{
				point = getStartEnd(objS,objD);
				pathInfo = getArr(point.start.x, point.start.y, point.end.x, point.end.y, arr_r);
				retList=retList.concat(pathInfo);
			}
			return retList;
		};
		drawPath=function(path){
			var form;
			var to;
			var objS;
			var objD;
			var stObj;
			var imgObj;
			var imgData;
			var len;
			var retObj;
			var pathInfo;
			var sBox;
			var dBox;
			var retInfo;
			objS=null;
			objD=null;
			form=path.from;
			to=path.to;
			$(dataList).each(function(index, ele) {
                stObj=ele.st;
				imgObj=stObj[1];
				imgData=imgObj.data("value");
				if(form==imgData.id){
					objS=stObj[0];
				}
				if(to==imgData.id){
					objD=stObj[0];
				}
            });
			if(null != objS && null != objD){
				sBox=objS.getBBox();
				dBox=objD.getBBox();
				retObj=drawArr({"obj1":sBox,"obj2":dBox,"arrPath":objS.arrPath,"dots":path.dots,"num":1,"pathObj":path});
				objS.arrPath=retObj.arrPath;
			}
		};
		//拖动路径节点开始
		draggerCircle=function(){
			this.ox = this.attr("cx");
			this.oy = this.attr("cy");
		};
		//拖动路径节点移动
		moveCircle=function(dx,dy){
			var x;
			var y;
			var att;
			var pathInfo;
			var prevCircle;
			var nextCircle;
			var centerCircle;
			var backData;
			var startPoint;
			var endPoint;
			var prevPoint;
			var centPoint;
			var nextPoint;
			var dots;
			dots=[];
			prevCircle=this.data("prevElement").getBBox();
			nextCircle=this.data("nextElement").getBBox();
			x=this.ox+dx;
			y=this.oy+dy;
			pathInfo=this.data("pathInfo");
			att=this.attr();
			centerCircle={"x":x,"y":y};
			pathInfo.dots=dots;
			if("1"==pathInfo.properties.linetype)
			{
				dots.push({"x":x,"y":prevCircle.cy});
				dots.push({"x":x,"y":nextCircle.cy});
				att={"cx":x,"cy":this.oy};
			}
			if("2"==pathInfo.properties.linetype)
			{
				dots.push({"x":prevCircle.cx,"y":y});
				dots.push({"x":nextCircle.cx,"y":y});
				att={"cx":this.ox,"cy":y};
			}
			this.attr(att);
			drawPath(pathInfo);
		};
		//拖动路径节点结束path为绘画路径
		upCircle=function(circle,path){
			var circles;
			var pathInfo;
			if(null!=circle.attrs)
			{
				circles=$(path).data("circles");
				pathInfo=circle.data("pathInfo");
				if(2==$(pathInfo.dots).size())
				{
					circle.attr("cx",(pathInfo.dots[0].x+pathInfo.dots[1].x)/2);
					circle.attr("cy",(pathInfo.dots[0].y+pathInfo.dots[1].y)/2);
				}
				circles.hide();
			}
		};
		dragPathText=function(text,pathObj){
		};
		movePathText=function(text,pathObj){
			var evtObj;
			var x;
			var y;
			evtObj=event || window.event;
			x=evtObj.x+mouse_x;
			y=evtObj.y+mouse_y;
			text.attr("x",x);
			text.attr("y",y);
		};
		updatePathText=function(text,pathObj){
			pathObj.properties.textX=text.attr("x");
			pathObj.properties.textY=text.attr("y");
		};
		selArrPath=function(obj){
			var pathInfo;
			var from;
			var to;
			var fImg;
			var tImg;
			var bbox1;
			var bbox2;
			var bbox3;
			var backData;
			var circles;
			var dots;
			dots=[];
			pathInfo=$(obj).data("pathObj");
			circles=$(obj).data("circles");
			$(dataList).each(function(index, ele) {
				if(pathInfo.from==ele.id)
				{
					from=ele.st;
				}
				if(pathInfo.to==ele.id)
				{
					to=ele.st;
				}
            });
			if(null==circles)
			{
				circles=paper.set();
				prevele=from[1];
				nextele=to[1];
				if(null!=from && null!=to)
				{
					fImg=from[1];
					tImg=to[1];
					bbox1=fImg.getBBox();
					bbox2=tImg.getBBox();
					backData=getStartEnd(bbox1,bbox2);
					newcircle = paper.circle((backData.start.x+backData.end.x)/2,(backData.start.y+backData.end.y)/2, arr_r);
					newcircle.attr(circle_attr);
					newcircle.data("pathInfo",pathInfo);
					newcircle.data("prevElement",fImg);
					newcircle.data("nextElement",tImg);
					if(null != pathInfo.dots && 2== $(pathInfo.dots).size()){
						newcircle.attr("cx",(pathInfo.dots[0].x+pathInfo.dots[1].x)/2);
						newcircle.attr("cy",(pathInfo.dots[0].y+pathInfo.dots[1].y)/2);
					}
					circles.push(newcircle);
				}
			}
			if("1"==pathInfo.properties.linetype || "2"==pathInfo.properties.linetype)
			{
				circles.show();	
			}
			else
			{
				circles.hide();
			}
			$(obj).data("circles",circles);
			circles.undrag();
			circles.drag(moveCircle,draggerCircle,function(){upCircle(this,obj);});
			drawPath(pathInfo);
			if(null!=selPath)
			{
				selPath(obj);
			}
		};
	}
})(jQuery);
