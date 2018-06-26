$(document).ready(function(e) {
    init_data();
});
function init_data(){
	var sysid;
	var property;
	var valInfo;
	var treeObj;
	var setting;
	var sysid;
	var finlist;
	var rootlist;
	rootlist=[];
	finlist=[];
	setting={
		view: {
			selectedMulti: false
		}
	};
	sysid=$("#db").val();
	property=$("#property").val();
	$.ajax({
		url: "../../ajax/common/getsystemproperty", 
		type: "POST",
		data:"sysid="+sysid+"&property="+property,
	    dataType: "json",
		success: function(msg){
			if(null!=msg)
			{
				$(msg).each(function(i,eleObj) {
					if(""==eleObj.pid)
					{
						rootlist.push(eleObj);
					}
				});
				if(0!=$(rootlist).size())
				{
					finlist=fn_getFinList(rootlist,msg,sysid,property);
				}
				treeObj=$.fn.zTree.getZTreeObj("tree");
				if(null==treeObj)
				{
					$.fn.zTree.init($("#tree"), setting, [{"code":"root","name":"根目录","level":-1,"urlInfo":"","order":1,"open":true,"children":finlist,"click":"fn_clickSysProperty('','"+sysid+"','"+property+"');"}]);
				}
				else
				{
					treeObj.destroy();
					$.fn.zTree.init($("#tree"), setting, [{"code":"root","name":"根目录","level":-1,"urlInfo":"","order":1,"open":true,"children":finlist,"click":"fn_clickSysProperty('','"+sysid+"','"+property+"');"}]);
				}
			}
		}
	});
}
function fn_getFinList(rootList,allList,sysid,property){
	var childlist;
	if($(rootList).size()!=0)
	{
		$(rootList).each(function(rootIndex, rootEle) {
			rootEle.click="fn_clickSysProperty('{\\\'id\\\':\\\'"+rootEle.id+"\\\',\\\'pid\\\':\\\'"+rootEle.pid+"\\\'}','"+sysid+"','"+property+"');";
			rootEle.open=true;
			if(rootEle.children==null)
			{
				rootEle.children=[];
			}
			childlist=rootEle.children;
            $(allList).each(function(allIndex, allEle) {
				if(allEle.pid==rootEle.id)
				{
					childlist.push(allEle);
				}
			});
			fn_getFinList(childlist,allList,sysid,property);
        });
	}
	return rootList;
}
function fn_clickSysProperty(obj,sysid,type){
	var url;
	var jsonObj;
	url="../form/SF_2016050611470001.form?sysid="+sysid+"&type="+type;
	if(''==obj)
	{
		window.parent.fun_ChangeFrameUrl("frameright",url);
	}
	else
	{
		jsonObj=JSON.parse(obj.replace(/'/g,"\""));
		url+="&id="+jsonObj.id+"&pid="+jsonObj.pid;
		window.parent.fun_ChangeFrameUrl("frameright",url);
	}
}
