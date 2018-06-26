(function($){
	//method of operate data
	_setting = {
			async: {
				enableDynamic:true,
				setDynamicUrl:null,
				setDynamicOtherParam:null
			}
	};
	var zt = $.fn.zTree;
	var data = zt._z.data;
	var view = zt._z.view;
	data.exSetting(_setting);
//	Override method in core
	var _dAsyncNode = view.asyncNode;
	view.asyncNode = function(setting, node, isSilent, callback) {
		var url;
		var otherParam;
		if(setting.async.enableDynamic==true)
		{
			 if(isExitsFunction(setting.async.setDynamicUrl)==true)
			 {
				 url=setting.async.setDynamicUrl();
				 setting.async.url=url;
			 }
			 if(isExitsFunction(setting.async.setDynamicOtherParam)==true)
			 {
				 otherParam=setting.async.setDynamicOtherParam();
				 setting.async.otherParam=otherParam;
			 }
			 
		}
		return _dAsyncNode(setting, node, isSilent, callback);
	},
	isExitsFunction=function(funcName) {
	    try {
	        if (typeof(eval(funcName)) == "function") {
	            return true;
	        }
	    } catch(e) {}
	    return false;
	};
})(jQuery);