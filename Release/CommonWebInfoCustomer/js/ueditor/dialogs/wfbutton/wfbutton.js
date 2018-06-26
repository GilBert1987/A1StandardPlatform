UE.registerUI('wfbutton',function(editor,uiName){
	var sqlInfo;
	var items;
	var isShow=0;
	var postData;
	var popup = new baidu.editor.ui.Popup( {
        editor:this,
        content: '',
        className: 'edui-bubble',
        _edittext: function () {
			this.hide();
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
    //注册按钮执行时的command命令,用uiName作为command名字，使用命令默认就会带有回退操作
    editor.registerCommand(uiName,{
        execCommand:function(cmdName,value,isadd){
			var list;
			var id;
			var info;
			var jsonValue;
			var jsonData;
			var btnInfo;
			if(uiName==cmdName)
			{
				value=value.replace(/'/g,"\"");
				jsonValue=JSON.parse(value);
				jsonData=fn_getInitData();
				btnInfo=JSON.stringify({"buttons":encodeURIComponent(JSON.stringify(jsonData.btnlist)),"controls":encodeURIComponent(decodeURIComponent(JSON.stringify(jsonData.conlist)))});
				if(""==jsonValue.id)
				{
					openWin(1000,400,'添加按钮','../form/SF_201506080002.form',encodeURIComponent(btnInfo),function(){
						var id;
						var title;
						id=$("#hid").val();
						title=$("#htitle").val();
						if(""!=id && ""!=title)
						{
							fn_insertHtml(editor,id,title);
						}
						$("#hid").val("");
						$("#htitle").val("");
					});
				}
				else
				{
					openWin(1000,400,'编辑按钮','../form/SF_201506080002.form?btnid='+jsonValue.id,encodeURIComponent(btnInfo),null);
					isShow=1;
				}
			}
        },
        queryCommandValue:function(){
			return 1;
        },
		queryCommandState:function(cmdName)
		{
			var iReturn;
			if(isShow==0)
			{
				iReturn=1;
			}
			else{
				iReturn=-1;
				isShow=0;
			}
			return iReturn;
		}
    });


    //创建下拉菜单中的键值对，这里我用字体大小作为例子
    items = [];
	sqlInfo="SELECT CONCAT(sc_control.cn_shorthand,':',sc_control_tld.code) AS cn_code,sc_control_tld.name AS cn_name FROM sc_control JOIN sc_control_tld ON sc_control.id=sc_control_tld.control_id WHERE sc_control_tld.id='SCT_2017101213450003'";
	postData={"type":"sql","dataname":"com","info":sqlInfo};
	$.ajax({
		url: "../../ajax/query/search",
		type: "POST",
		data: postData,
		dataType: "json",
		async: false,
		success: function(list){
			$(list).each(function(index, ele) {
                items.push({label:ele.cn_name,value:"{'id':'','title':'"+ele.cn_code+"'}"});
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
        title:'工作流按钮',
        //当编辑器没有焦点时，combox默认显示的内容
        initValue:'工作流按钮'
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
						//----------------------------
						var html = popup.formatHtml(
                		'<nobr>工作流按钮: <span onclick=$$._edittext() class="edui-clickable">编辑</span>  <span onclick=$$._delete() class="edui-clickable">删除</span></nobr>' );
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
});
function fn_insertHtml(editor,id,title)
{
	var imgHtml;
	imgHtml='<img src="../../img/custcontrol.png" control="1" value="{\'id\':\''+id+'\',\'title\':\''+title+'\'}">';
	editor.execCommand('insertHTML',imgHtml);
}