<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
        <title>文件上传</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/globle.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/img/ui/sys.css">
        <script type="text/javascript">
            var basePath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/static/js/jquery-1.8.3.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/HoorayLibs/hooraylibs.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/jquery.artDialog.js?skin=simple"></script>
        <script src="${pageContext.request.contextPath}/static/js/artDialog4.1.7/plugins/iframeTools.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/Validform_v5.3.2/Validform_v5.3.2_min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/webuploader-0.1.5/webuploader.min.js"></script>
        <script>
            $(function () {
                //配置artDialog全局默认参数
                (function (config) {
                    config['lock'] = true;
                    config['fixed'] = true;
                    config['resize'] = false;
                    config['background'] = '#000';
                    config['opacity'] = 0.5;
                })($.dialog.defaults);
                //toolTip
                $('[rel="tooltip"]').tooltip();
                //表单提示
                $("[datatype]").focusin(function () {
                    $(this).parent().addClass('info').children('.infomsg').show().siblings('.help-inline').hide();
                }).focusout(function () {
                    $(this).parent().removeClass('info').children('.infomsg').hide().siblings('.help-inline').show();
                });
                //detailIframe
                openDetailIframe = function (url) {
                    ZENG.msgbox.show('正在载入中，请稍后...', 6, 100000);
                    $('#detailIframe iframe').attr('src', url).load(function () {
                        $('body').css('overflow', 'hidden');
                        ZENG.msgbox._hide();
                        $('#detailIframe').animate({
                            'top': 0,
                            'opacity': 'show'
                        }, 500);
                    });
                };
                closeDetailIframe = function (callback) {
                    $('body').css('overflow', 'auto');
                    $('#detailIframe').animate({
                        'top': '-100px',
                        'opacity': 'hide'
                    }, 500, function () {
                        callback && callback();
                    });
                };
            });
        </script>

        <script>
            $(function () {
                var fileCount = 0;
                var percentages = {};

                function setState(mode) {
                    switch (mode) {
                        case 'pedding':
                            $('#btn-upload').prop('disabled', true);
                            uploader.refresh();
                            break;
                        case 'ready':
                            $('#btn-upload').prop('disabled', false);
                            uploader.refresh();
                            break;
                        case 'uploading':
                            $('#btn-upload').prop('disabled', true);
                            $('#btn-paused').prop('disabled', false);
                            $('#btn-goonupload').prop('disabled', true);
                            break;
                        case 'paused':
                            $('#btn-paused').prop('disabled', true);
                            $('#btn-goonupload').prop('disabled', false);
                            break;
                        case 'confirm':
                            stats = uploader.getStats();
                            if (stats.successNum && !stats.uploadFailNum) {
                                setState('finish');
                                return;
                            }
                            break;
                        case 'finish':
                            $('#btn-goonupload').prop('disabled', true);
                            $('#btn-paused').prop('disabled', true);
                            $('#btn-upload').prop('disabled', true);
                            stats = uploader.getStats();
                            if (stats.successNum) {
                                fileCount = 0;
                                $.dialog({
                                    content: "上传成功"
                                });
// 					alert('上传成功');
                            } else {
                                location.reload();
                            }
                            break;
                    }
                }

                var uploader = WebUploader.create({
                    // swf文件路径
                    swf: '${pageContext.request.contextPath}/static/js/webuploader-0.1.5/Uploader.swf',
                    // 文件接收服务端。
                    server: '${pageContext.request.contextPath}/desktopSettingController/uploadfile.do?desk=' + window.parent.HROS.CONFIG.desk,
                    // 选择文件的按钮。可选。
                    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                    pick: '#btn-filePicker',
                    threads: 1,
                    accept: {
                        title: 'Files',
                        extensions: '${filetypesBydouhao}'
                    },
                    fileSingleSizeLimit: ${uploadFileSingleSize*1024*1024},
                    fileSizeLimit: ${uploadFileSize*1024*1024}
                });
                uploader.on('fileQueued', function (file) {
                    fileCount++;
                    var text = '等待上传';
                    if (file.getStatus() === 'invalid') {
                        switch (file.statusText) {
                            case 'exceed_size':
                                text = '文件大小超出';
                                break;
                            case 'interrupt':
                                text = '上传暂停';
                                break;
                            default:
                                text = '上传失败';
                        }
                    }
                    $('.list-table tbody').append(
                        '<tr class="list-bd" id="' + file.id + '">' +
                        '<td>' + file.name + '</td>' +
                        '<td>' + WebUploader.formatSize(file.size) + '</td>' +
                        '<td><span>' + text + '</span><span style="padding-left:10px;display:none"></span></td>' +
                        '<td><a href="javascript:;" class="del">删除</a></td>' +
                        '</tr>'
                    );
                    $('#' + file.id + ' .del').click(function () {
                        uploader.removeFile(file);
                        $('#' + file.id).remove();
                    });
                    setState('ready');
                });
                uploader.on('fileDequeued', function (file) {
                    fileCount--;
                    if (!fileCount) {
                        setState('pedding');
                    }
                });
                uploader.on('error', function (error) {
                    console.log(error);
                    switch (error) {
                        case 'F_EXCEED_SIZE':
                            $.dialog({
                                content: '有文件上传超出大小限制！'
                            });
                            break;
                        case 'Q_EXCEED_SIZE_LIMIT':
                            $.dialog({
                                content: '总文件上传大小超出限制！'
                            });
                            break;
                        case 'F_DUPLICATE':
                            $.dialog({
                                content: '不能重复上传同一文件，系统已帮您去除重复文件！'
                            });
                            break;
                        case 'Q_TYPE_DENIED':
                            $.dialog({
                                content: '上传文件类型不对，请检查！'
                            });
                            break;
                    }
                });
                uploader.on('all', function (type) {
                    switch (type) {
                        case 'uploadFinished':
                            setState('confirm');
                            break;
                        case 'startUpload':
                            setState('uploading');
                            break;
                        case 'stopUpload':
                            setState('paused');
                            break;
                    }
                });
                uploader.on('uploadBeforeSend', function (object, data) {
                    data['desk'] = window.parent.HROS.CONFIG.desk;
                });
                uploader.on('uploadProgress', function (file, percentage) {
                    $('#' + file.id + ' .del').hide();
                    $('#' + file.id + ' td:eq(2) span:eq(0)').text('上传中');
                    $('#' + file.id + ' td:eq(2) span:eq(1)').show().text(Math.ceil(percentage * 100) + '%');
                });
                uploader.on('uploadSuccess', function (file) {
                    $('#' + file.id + ' td:eq(2) span:eq(0)').text('上传完成');
                    $('#' + file.id + ' td:eq(2) span:eq(1)').hide().text('');
                    uploader.removeFile(file);
                    window.parent.HROS.app.get();
                });
                $('#btn-upload').click(function () {
                    uploader.upload();
                });
                $('#btn-paused').click(function () {
                    uploader.stop(true);
                });
                $('#btn-goonupload').click(function () {
                    uploader.upload();
                });
            });
        </script>
    </head>

    <body>
        <div class="creatbox">
            <div class="middle">
                <div class="alert alert-info alert-block" style="margin:10px;">
                    <p><b>注意：</b></p>
                    <p>单个文件最大支持「 ${uploadFileSingleSize}MB 」，总文件大小最大支持「 ${uploadFileSize}MB 」，格式支持「${filetypesBydunhao}」，如果上传的文件为其它格式，建议以压缩包形式上传。</p>
                </div>
                <div style="margin:0 10px">
                    <table class="list-table">
                        <thead>
                            <tr class="col-name">
                                <th>文件名</th>
                                <th style="width:100px">大小</th>
                                <th style="width:120px">状态</th>
                                <th style="width:100px">操作</th>
                            </tr>
                            <tr class="sep-row">
                                <td colspan="100"></td>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="bottom-bar">
            <div class="con">
                <button class="btn fr" disabled id="btn-upload"><i class="icon-upload"></i> 开始上传</button>
                <a class="btn btn-primary fr" id="btn-filePicker" style="margin-right:10px">选择文件</a>
                <!-- <button class="btn fr" disabled id="btn-paused" style="margin-right:10px">暂停上传</button> -->
                <!-- <button class="btn fr" disabled id="btn-goonupload" style="margin-right:10px">继续上传</button> -->
            </div>
        </div>
    </body>
</html>