<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
    <meta name="keywords" content=""/>
    <meta name="description" content="">

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="format-detection" content="telephone=no">
    <script src="/static/js/jquery.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/static/plugins/webUploader/webuploader.css"/>


</head>
<body>
<section class="content">
    <div class="container" style="margin-top: 20px">
        <div class="alert alert-info">可以一次上传多个大文件</div>
        <input type="radio" name="docType" class="docType" checked value="1">文件
        <input type="radio" name="docType" class="docType" value="2">文件夹
    </div>

    <div class="container" style="margin-top: 50px">
        <div id="uploader" class="container">
            <div class="container">
                <div id="fileList" class="uploader-list"></div>
                <!--存放文件的容器-->
            </div>
            <div class="btns container">
                <div id="picker" class="webuploader-container" style="float: left; margin-right: 10px"> 选择文件</div>
                <div id="uploadBtn" class="webuploader-pick" style="float: left; margin-right: 10px">开始上传 </div>
                <div id="stopBtn" class="webuploader-pick" style="float: left; margin-right: 10px" status="suspend">暂停上传</div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
<script type="text/javascript" src="/static/plugins/webUploader/webuploader.js"></script>
<script type="text/javascript" src="/static/js/demo.js"></script>

