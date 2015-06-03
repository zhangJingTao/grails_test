<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>SleepWeibo:Burn After Reading</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/js/masonry.pkgd.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <!-- messager -->
    <script src="/js/messenger.min.js"></script>
    <script src="/js/messenger-theme-future.js"></script>
    <link href="/css/messenger.css" rel="stylesheet">
    <link href="/css/messenger-theme-future.css" rel="stylesheet">
    <!-- responsive -->
    <script src="/js/responsive/responsive-nav.min.js"></script>
    <link href="/css/responsive/responsive-nav.css" rel="stylesheet">
    <script type="text/javascript">
        $(document).ready(function () {
            var intervalId = window.setInterval(function () {
                var width = parseInt(100*parseInt($(".progress-bar-info").css("width"))/parseInt($("#loadingProgress").css("width")))+16
                $(".progress-bar-info").css("width",width+"%")
                if ( width> 120) {
                    $("#loadingProgress").hide()
                    $("#list").show()
                    window.clearInterval(intervalId)
                }
            }, 1000)

            var navigation = responsiveNav("#nav");

            $._messengerDefaults = {
                extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
            }
            var contentTemplate = ' <div class="content">{content}</div>'
            var url = "/weibo/getWeiboLine";
            $.getJSON(url, function (data) {
                if (data.status == -1) {
                    $.globalMessenger().post({
                        message: '访问微博服务失败.',
                        showCloseButton: true,
                        id: "Only-one-message"
                    });
                } else if (data.status == 0) {
                    $.globalMessenger().post({
                        message: '授权不通过，请<a href="/weibo/oauth">重新授权</a>',
                        showCloseButton: true,
                        id: "Only-one-message"
                    });
                } else {
                    var html = ""
                    $.each(data.weibos, function (index, ele) {
                        html += contentTemplate.replace("{content}", ele.text);
                    })
                    $("#list").html(html)
                }
            });

            var speed = 1000;
            $('#list').masonry({
                itemSelector: '.content',
                columnWidth: 200,
                animate: true,
                animationOptions: {
                    duration: speed,
                    queue: false
                }
            })
        })
    </script>
    <style>
    container-fluid {
        padding: 20px;
    }

    .content {
        margin-bottom: 20px;
        float: left;
        width: 220px;
    }

    .box img {
        max-width: 100%
    }
    </style>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div id="nav">
                <ul>
                    <li><a href="home">Home</a></li>
                    <li><a href="#">About</a></li>
                    <li><a href="logout">Logout</a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-12">
            <div class="progress" id="loadingProgress">
                <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0"
                     aria-valuemax="100" style="width: 0%">
                    <span class="sr-only">0% Complete</span>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <div id="list" class="container-fluid" style="display: none">
            </div>
        </div>
    </div>
</div>
</body>
</html>