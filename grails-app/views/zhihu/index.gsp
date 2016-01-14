<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>知乎：收藏</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <link href="/css/nav.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <!-- responsive -->
    <script src="/js/responsive/responsive-nav.min.js"></script>
    <link href="/css/responsive/responsive-nav.css" rel="stylesheet">
    <script src="/js/zhihu/scroll.js"></script>
    <style type="text/css">
    /* Pill style */
    #scrollUp {
        bottom: 20px;
        right: 20px;
        background: #0968c7;
        color: #fff;
        font-size: 12px;
        font-family: sans-serif;
        text-decoration: none;
        opacity: .9;
        padding: 10px 20px;
        -webkit-border-radius: 16px;
        -moz-border-radius: 16px;
        border-radius: 16px;
        -webkit-transition: background 200ms linear;
        -moz-transition: background 200ms linear;
        transition: background 200ms linear;
    }

    #scrollUp:hover {
        background: #0965c2;
    }
    </style>
    <script type="text/javascript">
        var pending = false
        var baseId =
        ${baseId}
        var count = 0 //超过3次后开始自动删除前10个list-group-item
        var classes = ['panel-primary', 'panel-success', 'panel-info', 'panel-warning', 'panel-danger']
        $(document).ready(function () {
            var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});
            getNext();
            $.scrollUp();
            $('[data-toggle="tooltip"]').tooltip()
        })

        function getNext() {
            var template = '<a href="/zhihu/detail/{id}" class="list-group-item" data-toggle="tooltip" data-placement="right" title="{commentTimes}"><h4 class="list-group-item-heading">{title}</h4></a>'
            if (!pending) {
                pending = true
                var url = "/zhihu/giveMeFive?max=10&minId=" + baseId + "&_=" + new Date().getTime()
                var html = ""
                $.getJSON(url, function (data) {
                    if (data.status == 1) {
                        var list = data.list
                        $.each(list, function (index, ele) {
                            var author = ele.author
                            if (author.length == 0) author = '匿名用户'
                            html += template.replace("{title}", ele.title)
                                    .replace("{content}", ele.content)
                                    .replace("{id}", ele.id)
                                    .replace("{commentTimes}", ele.commentTimes)
                        })
                        baseId = data.nexBaseId
                        $("#content").append(html)
                        $('[data-toggle="tooltip"]').tooltip()
                    }
                    pending = false
                    $("#moreBtn").show()
                    count++
//                    if(count >3){
//                        for(var i = 0;i<10;i++){
//                            $($(".list-group-item")[0]).remove()
//                        }
//                    }
                })
            }
        }

        function delThis(obj) {
            var url = "/zhihu/del?id=" + obj
            $("#div" + obj).css("display", "none")
            $.getJSON(url, function (data) {
            })
            window.location.href = "#div" + obj
        }
    </script>
</head>

<body>
<div class="border" id="home"></div>

<div class="container">
    <div class="row">
        <div class="col-md-12" style="z-index: 99999">
            <div class="wrapper">
                <div id="nav">
                    <ul>
                        <li class="active"><a href="">首页</a></li>
                    </ul>
                </div>
                <br>
                <a href="#nav-toggle" id="nav-toggle" aria-hidden="false">Menu</a>
            </div>
        </div>
    </div>

    <div class="col-md-8 col-md-offset-2">
        <div class="list-group" id="content"></div>
    </div>

    <div class="col-md-2 col-md-offset-5">
        <a href="#moreBtn" type="button" class="btn btn-block" id="moreBtn"
           style="width: 100%;display: none;font-weight: bold;color: white;" onclick="getNext()">查看更多</a>
    </div>
</div>

<!-- FOOTER -->
<footer class="site-footer">
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-right">
                <p style="text-align: center">Copyright © OnlySleep.net 2015 京ICP备15022349号</p>
            </div> <!-- .col-md-12 -->
        </div> <!-- .row -->
        <div class="row" style="display: none;">
            <div class="col-lg-5"></div>

            <div class="col-md-2">
                <img border="0" src="http://cc.amazingcounters.com/counter.php?i=3189687&c=9569374"
                     alt="AmazingCounters.com"/>
            </div>
        </div> <!-- .row -->
    </div> <!-- .container -->
</footer> <!-- .site-footer -->
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript'
                                  charset='gb2312'></script></div>
</body>
</html>