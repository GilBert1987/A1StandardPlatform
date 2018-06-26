UE.registerUI('customselect',function(editor,uiName){
	var sqlInfo;
	var items;
    //创建下拉菜单中的键值对，这里我用字体大小作为例子
    items = [];
	sqlInfo="SELECT id AS cn_code,cn_name FROM sc_control";
	postData={"type":"sql","dataname":"com","info":sqlInfo};
	$.ajax({
		url: "../../ajax/query/search",
		type: "POST",
		data: postData,
		dataType: "json",
		async: false,
		success: function(list){
			$(list).each(function(index, ele) {
                items.push({"label":ele.cn_name,"value":ele.cn_code});
            });
		}
	});
    //创建下来框
    var combox = new UE.ui.Combox({
        //需要指定当前的编辑器实例
        editor:editor,
        //添加条目
        items:items,
        //当选中时要做的事情
        onselect:function (t, index) {
			var selValue;
			selValue=this.items[index].value;
			if(null!=selValue)
			{
				$("#edui185_body").css("height","210px");
				$("#edui185_content").css("height","200px");
				$(".control-tld").each(function(i,item){
					if(selValue==$(item).attr("key")){
						$(item).parent().parent().css("display","");
					}
					else
					{
						$(item).parent().parent().css("display","none");
					}
				});
			}
			return true;
        },
        //提示
        title:'控件分类',
        //当编辑器没有焦点时，combox默认显示的内容
        initValue:'控件分类'
    });
	return combox;
});
   