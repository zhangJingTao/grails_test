<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>知乎·收藏-${content.title}</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>SleepWeibo 0.1</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <link href="/css/nav.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <!-- responsive -->
    <script src="/js/responsive/responsive-nav.min.js"></script>
    <link href="/css/responsive/responsive-nav.css" rel="stylesheet">
    <style type="text/css">
    /* Pill style */
    #scrollUp {
        bottom: 20px;
        right: 20px;
        background: #555;
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
        background: #000;
    }
    </style>

    <script type="text/javascript">
        (function ($) {
            $.scrollUp = function (options) {
                var settings = {
                    scrollName: "scrollUp",
                    topDistance: "300",
                    topSpeed: 300,
                    animation: "fade",
                    animationInSpeed: 200,
                    animationOutSpeed: 200,
                    scrollText: "回到顶部",
                    activeOverlay: false
                };
                if (options)var settings = $.extend(settings, options);
                var sn = "#" + settings.scrollName, an = settings.animation, os = settings.animationOutSpeed, is = settings.animationInSpeed, td = settings.topDistance, st = settings.scrollText, ts = settings.topSpeed, ao = settings.activeOverlay;
                $("<a/>", {
                    id: settings.scrollName,
                    href: "#top", title: st, text: st
                }).appendTo("body");
                $(sn).css({"display": "none", "position": "fixed", "z-index": "2147483647"});
                if (ao) {
                    $("body").append("<div id='" + settings.scrollName + "-active'></div>");
                    $(sn + "-active").css({
                        "position": "absolute",
                        "top": td + "px",
                        "width": "100%",
                        "border-top": "1px dotted " + ao,
                        "z-index": "2147483647"
                    })
                }
                $(window).scroll(function () {
                    if (an === "fade")$($(window).scrollTop() > td ? $(sn).fadeIn(is) : $(sn).fadeOut(os)); else if (an === "slide")$($(window).scrollTop() > td ? $(sn).slideDown(is) : $(sn).slideUp(os));
                    else $($(window).scrollTop() > td ? $(sn).show(0) : $(sn).hide(0))
                });
                $(sn).click(function (event) {
                    $("html, body").animate({scrollTop: 0}, ts);
                    return false
                })
            }
        })(jQuery);
        $(document).ready(function () {
            var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});
            $.scrollUp();
            document.onkeyup = keyUp;
        })
        function keyUp(e) {
            if (navigator.appName == "Microsoft Internet Explorer") {
                var keycode = event.keyCode;
                var realkey = String.fromCharCode(event.keyCode);
            } else {
                var keycode = e.which;
                var realkey = String.fromCharCode(e.which);
            }
            if (keycode == 39) {
                window.location.href = '/zhihu/next?id=${content.id}';
            }
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
                        <li class="active"><a href="/zhihu">首页</a></li>
                    </ul>
                </div>
                <br>
                <a href="#nav-toggle" id="nav-toggle" aria-hidden="false">Menu</a>
            </div>
        </div>
    </div>

    <div class="col-md-8 col-md-offset-2">
        <a type="button" href="/zhihu?baseId=${content.id}" class="btn btn-warning" style="width: 100%">返回列表</a>
    </div>
    <div class="col-md-8 col-md-offset-2" id="content">
        <div class="panel panel-success"><div class="panel-body">${content.title}</div>
            <div class="panel-footer">${content.content}</div>
            <div class="bdsharebuttonbox"><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_mail" data-cmd="mail" title="分享到邮件分享"></a><a href="#" class="bds_copy" data-cmd="copy" title="分享到复制网址"></a></div>
            <script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"1","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"16"},"share":{},"image":{"viewList":["tsina","weixin","qzone","mail","copy"],"viewText":"分享到：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["tsina","weixin","qzone","mail","copy"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
        </div>
    </div>
    <div class="col-md-8 col-md-offset-2" title="‘→’也可以翻页(●'◡'●)’">
        <a type="button" href="/zhihu/next?id=${content.id}" class="btn btn-info" style="width: 100%">下一篇</a>
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