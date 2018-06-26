$(document).ready(function(){
	var uploader = new plupload.Uploader({ //实例化一个plupload上传对象
		browse_button : 'browse',
		url : '../../webUploader',
		flash_swf_url : 'js/Moxie.swf',
		silverlight_xap_url : 'js/Moxie.xap',
		file_data_name:"multiFile",
		chunk_size:"200kb",
		filters: {
			/*
			mime_types : [ //只允许上传图片和zip文件
				{ title : "Image files", extensions : "jpg,gif,png" }, 
				{ title : "Zip files", extensions : "zip" }
			],
			max_file_size : '400kb', //最大只能上传400kb的文件
			*/
			prevent_duplicates : true //不允许选取重复文件
		}
	});
	uploader.init(); //初始化

	//绑定文件添加进队列事件
	uploader.bind('FilesAdded',function(uploader,files){
		for(var i = 0, len = files.length; i<len; i++){
			var file_name = files[i].name; //文件名
			//构造html来更新UI
			var html = '<li id="file-' + files[i].id +'"><p class="file-name">' + file_name + '</p><p class="progress"></p></li>';
			$(html).appendTo('#file-list');
		}
	});
	
	//绑定文件添加进队列事件
	uploader.bind('BeforeUpload',function(uploader,files){
		var params;
		var strFileVal;
		var jsonFileArr;
		var jsonFileObj;
		strFileVal="";
		jsonFileArr=[];
		params=uploader.getOption("multipart_params");
		$(files).each(function(i,ele) {
			jsonFileObj={"id":ele.id,"name":ele.name};
			jsonFileArr.push(jsonFileObj);
		});
		params={"files":JSON.stringify(jsonFileArr)};
		uploader.setOption("multipart_params",params);
	});

	//绑定文件上传进度事件
	uploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+'.progress').css('width',file.percent + '%');//控制进度条
	});

	//上传按钮
	$('#upload-btn').click(function(){
		uploader.start(); //开始上传
	});
});