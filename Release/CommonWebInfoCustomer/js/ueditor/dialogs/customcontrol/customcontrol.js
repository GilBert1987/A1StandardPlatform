UE.registerUI('customcontrol',function(editor,uiName){
	var sqlInfo;
	var items;
	var postData;
	var popup = new baidu.editor.ui.Popup( {
        editor:this,
        content: '',
        className: 'edui-bubble',
        _edittext: function () {
			this.hide();
			//baidu.editor.plugins[uiName].editdom = popup.anchorEl;
			editor.options.retFunlist=null;
			editor.execCommand(uiName,$(popup.anchorEl).attr('value'),1);
        },
        _delete:function(){
            if( window.confirm('确认删除该控件吗？') ) {
                baidu.editor.dom.domUtils.remove(this.anchorEl,false);
            }
            this.hide();
        }
    } );
    popup.render();
	 //创建dialog
    var dialog = new UE.ui.Dialog({
        //指定弹出层中页面的路径，这里只能支持页面,因为跟addCustomizeDialog.js相同目录，所以无需加路径
        iframeUrl:'../../js/ueditor/dialogs/customcontrol/customcontrol.html',
		//iframeUrl:'customcontrol.html',
        //需要指定当前的编辑器实例
        editor:editor,
        //指定dialog的名字
        name:uiName,
        //dialog的标题
        title:"自定义控件",

        //指定dialog的外围样式
        cssRules:"width:600px;height:300px;",

        //如果给出了buttons就代表dialog有确定和取消
        buttons:[
            {
                className:'edui-okbutton',
                label:'确定',
                onclick:function () {
                    dialog.close(true);
					fn_saveControl(editor.options.retObj);
					if(null!=editor.options.retFunlist)
					{
						$(editor.options.retFunlist).each(function(index, ele) {
                            if('function'==typeof(ele)){
								ele(editor.options.retObj);
							}
                        });
					}
                }
            },
            {
                className:'edui-cancelbutton',
                label:'取消',
                onclick:function () {
                    dialog.close(false);
                }
            }
        ]});
    //注册按钮执行时的command命令,用uiName作为command名字，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName,value,isadd){
			var list;
			var id;
			var info;
			var jsonValue;
			if(uiName==cmdName)
			{
				value=value.replace(/'/g,"\"");
				jsonValue=JSON.parse(value);
				list=JSON.parse($('#controls').val());
				for(var i=0;i<list.length;i++)
				{
					if(jsonValue.id==list[i].control[0].id){
						info=list[i].control[0];
					}
				}
				editor.options.parmObj=jsonValue;
				editor.options.parmInfo=info;
				editor.options.parmIsadd=isadd;
				dialog.render();
				dialog.open();
			}
            //这里借用fontsize的命令
            //this.execCommand('fontsize',value + 'px')
        },
        queryCommandValue:function(){
            //这里借用fontsize的查询命令
            //return this.queryCommandValue('fontsize')
			//return this.queryCommandValue(uiName);
			return 1;
        },
		queryCommandState:function(cmdName)
		{
			var iReturn;
			if(uiName==cmdName){
				if(false==dialog.isHidden()){
					iReturn=-1;
				}
				else{
					iReturn=1;
				}
			}
			return iReturn;
		}
    });


    //创建下拉菜单中的键值对，这里我用字体大小作为例子
    items = [];
	sqlInfo="SELECT CONCAT(sc_control.cn_shorthand,':',sc_control_tld.code) AS cn_code,sc_control_tld.name AS cn_name,sc_control.id AS parentid FROM sc_control JOIN sc_control_tld ON sc_control.id=sc_control_tld.control_id";
	postData={"type":"sql","dataname":"com","info":sqlInfo};
	$.ajax({
		url: "../../ajax/query/search",
		type: "POST",
		data: postData,
		dataType: "json",
		async: false,
		success: function(list){
			$(list).each(function(index, ele) {
                		items.push({
					"label":ele.cn_name,
					"value":"{'id':'','title':'"+ele.cn_code+"'}",
            				//针对每个条目进行特殊的渲染
            				"renderLabelHtml":function () {
                				//这个是希望每个条目的字体是不同的
                				return '<div key="'+ele.parentid+'" class="control-tld edui-label %%-label">' + (this.label || '') + '</div>';
            				}
            			});
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
            //拿到选中条目的值
			editor.options.retFunlist=null;
            editor.execCommand(uiName,this.items[index].value,1);
			//渲染dialog
			return false;
        },
        //提示
        title:'自定义控件',
        //当编辑器没有焦点时，combox默认显示的内容
        initValue:'自定义控件'
    });
	
    editor.addListener('selectionchange', function (type, causeByUi, uiReady) {
        if (!uiReady) {
			var img;
			var range;
			var value;
            var state = editor.queryCommandState(uiName);
			if(state!=-1)
			{
				range=editor.selection.getRange();
				img=range.getClosedNode();
				if(null!=img && 'IMG'==img.tagName && '1'==$(img).attr('control'))
				{
					value=$(img).attr('value');
					if(null!=value)
					{
						/*
						editor.options.parmObj=value;
						dialog.render();
           				dialog.open();
						*/
						//editor.setContent('')
						//----------------------------
						var html = popup.formatHtml(
                		'<nobr>自定义控件: <span onclick=$$._edittext() class="edui-clickable">编辑</span>  <span onclick=$$._delete() class="edui-clickable">删除</span></nobr>' );
            			popup.getDom( 'content' ).innerHTML = html;
                		popup.anchorEl = img;
                		popup.showAnchor(popup.anchorEl);
					}
					else
					{
						popup.hide();
					}
				}
				else
				{
					popup.hide();
				}
			}
			else
			{
				popup.hide();
			}
        }

    });
    return combox;
}/*,2/*index 指定添加到工具栏上的那个位置，默认时追加到最后,editorId 指定这个UI是那个编辑器实例上的，默认是页面上所有的编辑器都会添加这个按钮*/);
function fn_saveControl(retObj){
	var controls;
	var tags;
	var tagObjlist;
	var conObjlist;
	var taglist;
	var listInfo;
	var listObj;
	listInfo=[];
	taglist=[];
	controls=$('#controls').val();
	tags=$('#tags').val();
	if(''!=controls && null != retObj)
	{
		conObjlist=JSON.parse(controls);
		if(null != retObj.control && null != retObj.control.id){
			listObj=[];
			for(var p in retObj.control)
			{
				if(typeof(p)!="function" && "id"!=p)
				{
					retObj.control[p]=encodeURIComponent(retObj.control[p]);
				}
			}
			$(conObjlist).each(function(index, ele) {
				if(ele.control[0].id!=retObj.control.id){
            		listInfo.push(ele);
				}
        	});
			listObj.push(retObj.control);
			listInfo.push({"control":listObj});
		}
	}
	if(''!=tags && null != retObj)
	{
		tagObjlist=JSON.parse(tags);
		if(null != retObj.label){
			$(tagObjlist).each(function(index, ele) {
				if(retObj.label != decodeURIComponent(ele.tag)){
            		taglist.push(ele);
				}
        	});
			taglist.push({"tag":encodeURIComponent(retObj.label)});
		}
	}
	$('#controls').val(JSON.stringify(listInfo));
	$('#tags').val(JSON.stringify(taglist));
}