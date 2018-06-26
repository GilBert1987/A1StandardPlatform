function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}
$(document).ready(function(){
	var setting;
	document.domain=location.hostname;
	setting= {
            view: {  
                selectedMulti: false,//禁止多点选中
                dblClickExpand: dblClickExpand
            },  
            data: {  
                simpleData: {  
                    enable:true,  
                    idKey: "id",  
                    pIdKey: "pid",  
                    rootPId: ""  
                }  
            },
			async: { 
				enable: true,
				enableDynamic:true,
				setDynamicUrl:function(){return "../../ajax/tree/data";},
				setDynamicOtherParam:function(){return getOtherparam();},
				autoParam:["id"], 
				dataFilter: filter //异步返回后经过Filter 
			},
            callback: {  
            	onClick: zTreeOnClick
            }  
	};
	$.fn.zTree.init($("#dataTree"), setting, parseJSON($('#treeData').html()));
});
function zTreeOnClick(event, treeId, treeNode) {
	var jsonData;
	var jsonNode;
	var jsoncurrData;
	var jsoncurrNode;
	var button;
	var strico;
	var jsonButton;
	var jsonurl;
	jsoncurrData=null;
	jsoncurrNode=null;
	jsonData=parseJSON($('#treeData').html());
	jsonNode=parseJSON($('#treeObj').html());
	jsonurl=parseJSON($('#treeUrl').html());
    if(treeNode.id==jsonurl.rootid)
    {
    	$('#toolButton').empty();
    }
    else{
    	for(var i=0;i<jsonData.length;i++)
    	{
    		if(jsonData[i].id==treeNode.id){
    			jsoncurrData=jsonData[i];
    			break;
    		}
    	}
    	if(jsoncurrData!=null)
    	{
    		for(var i=0;i<jsonNode.length;i++)
        	{
        		if(jsoncurrData.level==jsonNode[i].level){
        			jsoncurrNode=jsonNode[i];
        			break;
        		}
        	}
    		if(jsoncurrNode!=null){
    			$('#toolButton').empty();
    			for(var i=0;i<jsoncurrNode.listButton.length;i++)
            	{
    				jsonButton=jsoncurrNode.listButton[i];
    				if('menu'==jsonButton.type)
    				{
    					strico=jsonButton.ico==null?"":jsonButton.ico;
	    				button=$('<input type="button" />');
	    				button.attr('class','treebutton');
	    				button.attr('value',jsonButton.name);
	    				button.attr('onclick',jsonButton.fun);
	    				if(strico!='')
	    				{
	    					button.css('background-image','url('+strico+')');
	    				}
	    				$('#toolButton').append(button);
    				}
            	}
    		}
    	}
    }
    
}
function getOtherparam(){
    return {"node":$('#treeObj').html(),"data":$('#treeData').html(),"url":$('#treeUrl').html()};
}
function filter(treeId, parentNode, childNodes) {
	var jsonData;
	var zTree;
	zTree = $.fn.zTree.getZTreeObj("dataTree");
	jsonData=parseJSON($('#treeData').html());
	for(var i=0;i<childNodes.length;i++)
	{
		jsonData.push(childNodes[i]);
	}
	zTree.setting.async.otherParam=getOtherparam();
	$('#treeData').html(JSON.stringify(jsonData));
	return childNodes;
}

function getData(){
	$.ajax({
	    type: "POST",
	    url: "../../ajax/tree/data",
	    data: "node="+$('#treeObj').html()+"&id=1",
	    dataType: "json",
	    success: function(jsonData) {
	    	alert(jsonData);
	    }
	});
}
function reloadTree(){
	window.location.reload();
}