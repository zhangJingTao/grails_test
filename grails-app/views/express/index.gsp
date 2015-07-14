<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>快递查询</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <link href="/css/nav.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/kuaidi.js"></script>
    <!-- messager -->
    <script src="/js/messenger.min.js"></script>
    <script src="/js/messenger-theme-future.js"></script>
    <link href="/css/messenger.css" rel="stylesheet">
    <link href="/css/messenger-theme-future.css" rel="stylesheet">

    <script type="text/javascript">
        $(document).ready(function(){
            $._messengerDefaults = {
                extraClasses: 'messenger-fixed messenger-theme-future messenger-on-bottom'
            }
        })


        function initSelect(obj) {
            var text = $(obj).val()
            var url = "/express/queryComp?text=" + text + "&_=" + new Date().getTime()
            var lis = ""
            $.getJSON(url, function (data) {
                $.each(data, function (idx, ele) {
                    $.each(express, function (k, v) {
                        if (k.trim() == ele.comCode) {
                            lis += "<li onclick='hidecode(\"" + ele.comCode + "\")'><a href='javascript:void(0)'>" + v + "</a></li>"
                        }
                    })
                })
                $("#list").html(lis)
            })
        }

        function hidecode(code) {
            $("#expressCode").val(code)
            $.each(express, function (k, v) {
                if (k.trim() == code) {
                    $("#dropdownMenu1").html(v + '<span class="caret"></span>')
                }
            })
        }
        function joinIt() {
            var listTemplast = '<li class="list-group-item"><span class="badge">{date}</span>{content}</li>'
            var activelistTemplast = '<li class="list-group-item active"><span class="badge">{date}</span>{content}</li>'
            var url = "/express/joinIt?company=" + $("#expressCode").val() + "&text=" + $("#expressNo").val() + "&email=" + $("#email").val() + "&_=" + new Date().getTime()
            $.getJSON(url, function (data) {
                if (data == 'error') {
                    alert("请检查输入")
                } else if(data.status=='200') {
                    var html =''
                    var isFirst = true
                    $.each(data.data, function (inx, ele) {
                        var line = ''
                        if(isFirst){
                            line = activelistTemplast.replace("{date}",ele.time).replace("{content}",ele.context)
                            isFirst = false
                        }else {
                            line = listTemplast.replace("{date}",ele.time).replace("{content}",ele.context)
                        }
                        html += line
                    })
                    $("#resultList").html(html)
                    $("#result").show()
                }else{
                    alert(data.message);
                }
            })
        }
    </script>
</head>

<body>

<div class="container">
    <div class="col-md-8 col-md-offset-2">
        <div class="bs-callout bs-callout-warning" id="callout-navbar-overflow">
            <h4>输入快递单号和订阅邮箱，快递有更新会立即提醒</h4>

            <p>

            <div class="input-group">
                <span class="input-group-addon" id="sizing-addon2">No.</span>
                <input type="text" class="form-control" placeholder="请输入快递单号" id="expressNo"
                       aria-describedby="sizing-addon2" onkeyup="initSelect(this)">
                <input type="hidden" id="expressCode" value="">
            </div></p>
            <p>

            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="true">
                    快递列表
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="list">

                </ul>
            </div>

        </p>
            <p>

            <div class="input-group">
                <input type="text" class="form-control" placeholder="输入Email" id="email">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button" onclick="joinIt()">订阅!</button>
                </span>
            </div><!-- /input-group -->
        </div>

    </p>
    </div>
    <div class="col-md-8 col-md-offset-2" id="result" style="display: none">
        <div class="panel panel-default">
            <div class="panel-heading">当前结果:</div>
            <div class="panel-body">
                <ul class="list-group" id="resultList">

                </ul>
            </div>
        </div>
    </div>
</div>
</body>
</html>