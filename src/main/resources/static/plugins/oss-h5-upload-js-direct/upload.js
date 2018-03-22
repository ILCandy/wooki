
var policyText = {
	"expiration": "2025-01-01T12:00:00.000Z", //设置该Policy的失效时间，超过这个失效时间之后，就没有办法通过这个policy上传文件了
	"conditions": [
		["content-length-range", 0, 1048576000] // 设置上传文件的大小限制
	]
};

host = 'http://hiwork.oss-cn-shenzhen.aliyuncs.com';
accessid = 'LTAImw0icrJwu0U7';
accesskey = 'RUnaPsT8m6d5nz0GOAkhUAB4NTmtiz';

var imageSrc ;
// var hasFile = false;

var policyBase64 = Base64.encode(JSON.stringify(policyText))
message = policyBase64
var bytes = Crypto.HMAC(Crypto.SHA1, message, accesskey, { asBytes: true }) ;
var signature = Crypto.util.bytesToBase64(bytes);
var uploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : 'selectfiles',
	max_file_size: '2mb',	//最大2m
	//runtimes : 'flash',
	container: document.getElementById('container'),
	flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
	silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
	url : host,
	multipart_params: {
		'Filename': '${filename}',
		'key' : '${filename}',
		'policy': policyBase64,
		'OSSAccessKeyId': accessid,
		'success_action_status' : '200', //让服务端返回200,不然，默认会返回204
		'signature': signature,
	},

	init: {
		PostInit: function() {
			document.getElementById('ossfile').innerHTML = '';
			document.getElementById('postfiles').onclick = function() {
			// $('#postfiles').onclick = function () {
				var canvas = $("#image").cropper('getCroppedCanvas');

				// base64
				var dataurl = canvas.toDataURL('image/jpeg'); //转成base64
				imageSrc = dataurl;
				// var dataurl=canvas.toDataURL(); //转成base64
				//blob
				var blob = dataURLtoBlob(dataurl);
				var name = $.md5(blob.size + blob.type + blob.data) + '.jpeg';
				uploader.addFile(blob, name);
				// 文件名，key才是存储的文件名
				uploader.settings.multipart_params.Filename = name;
				uploader.settings.multipart_params.key = name;

				// 开始上传
				uploader.start();
				return false;
			};
		},
		FilesAdded: function(up, files) {
            $.each(up.files, function (i, file) {
                if (up.files.length <= 1) {
                    return;
                }

                up.removeFile(file);
            });
			/*	alert(files[0].name+"已经添加到队列");*/
			// 删除原来的文件信息
			// plupload.each(files, function(file) {
			// 	document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
			// 		+'<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
			// 		+'</div>';
			// });
			// hasFile = true;

			// $.each(files || [], function (i, file) {
			// 	alert("123");
			// 	if (!file || !/image\//.analy(file.type)) return; //确保文件是图片
			// 	if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
			// 		alert("gif");
			// 		var fr = new mOxie.FileReader();
			// 		fr.onload = function () {
			// 			file.imgsrc = fr.result;
			// 			fr.destroy();
			// 			fr = null;
			// 		}
			// 		fr.readAsDataURL(file.getSource());
			// 	} else {
			// 		var preloader = new mOxie.Image();
			// 		alert("jpeg");
			// 		preloader.onload = function () {
			// 			//先压缩一下要预览的图片
			// 			preloader.downsize(150, 150);
			// 			//得到图片src,实质为一个base64编码的数据
			// 			var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 150) : preloader.getAsDataURL();
			// 			file.imgsrc = imgsrc;
			// 			preloader.destroy();
			// 			preloader = null;
			// 			// $('#showPic').attr('src', imgsrc);
			// 			$("#up-img-touch img").attr("src",imgsrc);
			// 			$("#indexhear img",window.parent.document).attr("src",imgsrc);
			// 		};
			// 		preloader.load(file.getSource());
			// 	}
			// });
		},
		StateChanged: function (up) {
			// alert("队列改变")
		},
		UploadFile: function(up,file){
			// alert("文件开始上传,文件名:"+file.name)
		},
		UploadProgress: function(up, file) {
			// var d = document.getElementById(file.id);
			// d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			// var prog = d.getElementsByTagName('div')[0];
			// var progBar = prog.getElementsByTagName('div')[0]
			// progBar.style.width= 2*file.percent+'px';
			// progBar.setAttribute('aria-valuenow', file.percent);
		},
		FileUploaded: function(up, file, info) {
			console.info(info)
			if (info.status >= 200 || info.status < 200)
			{
				// 替换本地图片
				$("#up-img-touch img").attr("src",imageSrc);
				$("#indexhear img",window.parent.document).attr("src",imageSrc);

				var imgUrl = host+"/"+file.name;
				// 更新服务器图片数据
				$.ajax({
					url:"/picture/sysUser",
                    data:JSON.stringify({imgUrl: imgUrl}),
					// data:JSON.stringify({type:curWwwPath}),
					type:"post",
					contentType:'application/json;charset=UTF-8',
					dataType:"json",
					success:function(result){
						console.info(result);
						if(result.flag=="false"){
							swal({
								title: "信息提示",
								text: result.msg,
								type: "error"
							});
						}else if(result.flag=="true"){
							swal({
								title: "信息提示",
								text: result.msg,
								type: "success"
							});
							$(message).html("已同意")

						}
						$("#doc-modal-1").modal({});
					}
				})

				// 发送请求更新图片地址
				// 更新服务器的个人头像
				// sendRequest();

				// alert("上传成功 :"+info+"，文件名 ："+file.name);
				// sendRequest("/setPhoto");id
				// var path = uploader.settings.multipart_params.key;
				// $('#uploadPhotoPath').val(file.name);

				// zoom = 1 为不需要缩放，下载原图
				// $("#up-img-touch img").attr("src","picture/"+path+"?flag=true&zoom=1");
                // $("#doc-modal-1").modal({});
                // $("#indexhear img",window.parent.document).attr("src","sysUser/picture/"+path+"?zoom=1");
                // $("#indexhear img",window.parent.document).attr("src","/picture/"+path);

                // swal({
                //     title: "提示信息",
                //     text: "上传成功！",
                //     type: "success",
                //
                // });

				// sendRequest("/setPhoto?photo="+file.name);
				// document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'success';
				// 禁止再次上传
				// document.getElementById('container').hidden = true;
			}
			else
			{
				// document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
			}
		},
		Error: function(up, err) {
            swal({
                title: "上传失败",
                text: err,
                type: "success"
            });
			// document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
		}
	}
});
uploader.init();


function dataURLtoBlob(dataurl) {
	var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
		bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
	while(n--){
		u8arr[n] = bstr.charCodeAt(n);
	}
	return new Blob([u8arr], {type:mime});
}

// 成功后保存文件
function sendRequest(url) {

	//先声明一个异步请求对象
	var xmlHttpReg = null;
	if (window.ActiveXObject) {//如果是IE
		xmlHttpReg = new ActiveXObject("Microsoft.XMLHTTP");
	} else if (window.XMLHttpRequest) {
		xmlHttpReg = new XMLHttpRequest(); //实例化一个xmlHttpReg
	}

	//如果实例化成功,就调用open()方法,就开始准备向服务器发送请求
	if (xmlHttpReg != null) {
		xmlHttpReg.open("GET", url, true);
		xmlHttpReg.send();
		xmlHttpReg.onreadystatechange = doResult; //设置回调函数
	}

	//回调函数
	//一旦readyState的值改变,将会调用这个函数,readyState=4表示完成相应

	//设定函数doResult()
	function doResult() {
		//4代表执行完成,发送请求有几种状态
		if (xmlHttpReg.readyState == 4) {
			if (xmlHttpReg.status == 200) {//200代表执行成功
				var json = eval('(' + xmlHttpReg.responseText + ')');

				filename = json.filename;
				submitFlag = true;
				// policyText.expiration = json.expiration.toString();
				// policyBase64 = Base64.encode(JSON.stringify(policyText));
				// uploader.settings.multipart_params.policy = policyBase64;
				// console.info("过期时间 = "+policyText.expiration);
			}
		}
	}
}