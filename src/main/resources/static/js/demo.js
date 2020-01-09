var demo = {
    exts:['xls','xlsx','doc' ,'docx','ppt','pptx','pdf'],
    init: function () {

        $(".docType").change(function (e) {
            if (this.value == 1) {
                $("#picker input").removeAttr('webkitdirectory')
            } else if (this.value == 2) {
                $("#picker input").attr('webkitdirectory', '');
            }
        });

        var $list = $('#fileList');
        var flie_count = 0;
        var uploader = WebUploader.create({
            pick: {
                id: "#picker", //启用上传的按钮
                multiple: true //是否支持多文件上传
            },
            fileSizeLimit: 2048 * 1024 * 1024, //最大2GB
            fileSingleSizeLimit: 100 * 1024 * 1024, //单个文件最大100M
            resize: false, //是否压缩
            swf: '/static/plugins/webUploader/Uploader.swf', //flash插件目录地址
            server: "/testUploadFile", //服务器接收地址
            chunked: true, //是否分片
            chunkSize: 5242880, //5M分片
            accept: [{
                title: 'document',
                extensions: 'xls,xlsx,doc,docx,ppt,pptx,pdf',
                mimeTypes: 'application/vnd.ms-excel,' +
                'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,' +
                'application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,' +
                'application/vnd.openxmlformats-officedocument.presentationml.presentation,' +
                'application/vnd.ms-powerpoint,' +
                'application/pdf'
            }] //默认支持文件格式
        });
        uploader.on('fileQueued', function (file) {
            var ext = file.source.ext;
            if(demo.exts.indexOf(ext)===-1) {//如果文件不属于列表中的后缀类型，则不上传
               return;
            }
            $list.append('<div id="' + file.id + '" class="item">' +

                '<h4 class="info">' + file.name + '<button type="button" fileId="' + file.id + '" class="btn btn-danger btn-delete"><span class="glyphicon glyphicon-trash"></span></button></h4>' +

                '<p class="state">正在计算文件MD5...请等待计算完毕后再点击上传！</p>' +

                '</div>');

            flie_count++;

            //删除要上传的文件

            //每次添加文件都给btn-delete绑定删除方法

            $(".btn-delete").click(function () {

                //console.log($(this).attr("fileId")); //拿到文件id

                uploader.removeFile(uploader.getFile($(this).attr("fileId"), true));

                $(this).parent().parent().fadeOut(); //视觉上消失了

                $(this).parent().parent().remove(); //DOM上删除了

            });

            //uploader.options.formData.guid = WebUploader.guid(); //每个文件都附带一个guid，以在服务端确定哪些文件块本来是一个

            //console.info("guid= "+WebUploader.guid());

            //md5计算

            uploader.md5File(file) .progress(function (percentage) {

                    console.log('Percentage:', percentage);

                }).then(function (fileMd5) {  // 完成

                    var end = +new Date();

                    console.log("before-send-file preupload: file.size=" + file.size + " file.md5=" + fileMd5);

                    file.wholeMd5 = fileMd5; //获取到了md5

                    //uploader.options.formData.md5value = file.wholeMd5; //每个文件都附带一个md5，便于实现秒传

                    $('#' + file.id).find('p.state').text('MD5计算完毕，可以点击上传了');

                    console.info("MD5=" + fileMd5);

                });

        });

        // 文件上传过程中创建进度条实时显示。

        uploader.on('uploadProgress', function (file, percentage) {

            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $li.append('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0% "></div>' +
                    '</div>')
                $percent = $li.find('.progress-bar');


            }
            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');

        });

        //发送前填充数据

        uploader.on('uploadBeforeSend', function (block, data) {

            // block为分块数据。
            // file为分块对应的file对象。
            var file = block.file;

            // 修改data可以控制发送哪些携带数据。
            // 将存在file对象中的md5数据携带发送过去。
            data.md5value = file.wholeMd5; //md5
            var pathName =  file.source.source.webkitRelativePath;
            data.pathName =pathName.slice(0,pathName.lastIndexOf("/"));//获取目录结构

            // 删除其他数据

            if (block.chunks > 1) {  //文件大于chunksize 分片上传

                data.isChunked = true;

            } else {
                data.isChunked = false;
            }
        });

        uploader.on('uploadSuccess', function (file) {

            $('#' + file.id).find('p.state').text('已上传');

            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-success");

            $('#' + file.id).find(".info").find('.btn').fadeOut('slow'); //上传完后删除"删除"按钮

            $('#stopBtn').fadeOut('slow');

        });

        uploader.on('uploadError', function (file) {

            $('#' + file.id).find('p.state').text('上传出错');

            //上传出错后进度条变红

            $('#' + file.id).find(".progress").find(".progress-bar").attr("class", "progress-bar progress-bar-danger");

            //添加重试按钮

            //为了防止重复添加重试按钮，做一个判断
            if ($('#' + file.id).find(".btn-retry").length < 1) {

                var btn = $('<button type="button" fileid="' + file.id + '" class="btn btn-success btn-retry"><span class="glyphicon glyphicon-refresh"></span></button>');

                $('#' + file.id).find(".info").append(btn); //.find(".btn-danger")

            }

            $(".btn-retry").click(function () {


                uploader.retry(uploader.getFile($(this).attr("fileId")));

            });

        });

        uploader.on('uploadComplete', function (file) { //上传完成后回调

            //$('#' + file.id).find('.progress').fadeOut(); //上传完删除进度条

            //$('#' + file.id + 'btn').fadeOut('slow') //上传完后删除"删除"按钮

        });

        uploader.on('uploadFinished', function () {
         var f = new FileList();
         f.prototype.prototype
            //上传完后的回调方法

            //alert("所有文件上传完毕");

            //提交表单

        });

        $("#uploadBtn").click(function () {

            uploader.upload(); //上传

        });

        $("#stopBtn").click(function () {


            var status = $('#stopBtn').attr("status");

            if (status == "suspend") {


                $("#stopBtn").html("继续上传");

                $("#stopBtn").attr("status", "continuous");

                uploader.stop(true);



            } else {

                $("#stopBtn").html("暂停上传");

                $("#stopBtn").attr("status", "suspend");


                uploader.upload(uploader.getFiles("interrupt"));

            }

        });

        uploader.on('uploadAccept', function (file, response) {

            if (response._raw === '{"error":true}') {

                return false;

            }

        });

    }

};

$(function () {
    demo.init()
});