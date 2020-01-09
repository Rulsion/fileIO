;(function ($) {
    'use strict';
    $.fn.fileUpload = function (options) {
        return new MyFileUpLoad(this, options);
    };
    var MyFileUpLoad = function (element, options) {
        var me = this;
        me.$element = element;
        me.init(options);
    };

    // 初始化
    MyFileUpLoad.prototype.init = function (options) {
        var me = this;
        me.opts = $.extend(true, {}, {
            url: "",
            id: "",
            files: [],
            fileSplitSize: 1048576,//分割文件大小 默认1M
            breakPoints: false,//是否开启断点续传
            status: 0,//标记插件状态，是否还在上传中，1：上传中，0：无任务
        }, options);

        if (me.opts.id !== "") {
            me.opts.files = document.getElementById(me.opts.id);
        }

    };
    MyFileUpLoad.prototype.doUpload = function () {
        var me = this;

        var files = me.opts.files;
        var file;
        for (var i = 0; i < files.length; i++) {
            // 取得一个文件
            file = files.item(i);
            file
            uploadFile(file);
        }
    };

    function uploadFile(file) {
        var me = this;

        var fileName = file.name;
        // 取得一个文件
        let reader = new FileReader();
        let lastModified = file.lastModified;
        var filePath = file
        reader.onload = function (e) {
            me.opts.status = 1;
            let fileByte = e.target.result;
            var fileSplitSize = me.opts.fileSplitSize;
            var fileSize = fileByte.length;
            var fileUploadId = null;//断点续传此处应先后台查询数据 暂时不实现

           if (fileSplitSize > fileSize) {
                fileSplitSize = fileByte.length;
            }

            for (let start = 0, i = 0; start + fileSplitSize < fileSize; start = start + fileSplitSize, i++) {
                let end = start + fileSplitSize;
                if (end > fileByte.length) {
                    end = fileByte.length
                }
                let sendByte = fileByte.slice(start, end);
                let data = {
                    sort: i,
                    fileName: fileName,
                    sendByte: sendByte,
                    fileUploadId: fileUploadId,
                    lastModified: lastModified,
                    fileSplitSize:fileSplitSize,
                    fileSize:fileSize
                };
                $.getMyJSON("/uploadFile", data,function (res) {
                    if(res.returncode !=0){
                        console.log("上传失败:"+res);
                    }
                    fileUploadId =res.data.id
                })
            }

        };
        reader.readAsBinaryString(file);
    }

});
