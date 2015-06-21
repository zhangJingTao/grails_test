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
    <script type="text/javascript">
        $(document).ready(function () {
            var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});
        })
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
    <g:if test="${content.content.length()>1000}">
        <div class="col-md-8 col-md-offset-2">
            <a type="button" href="/zhihu/next?id=${content.id}" class="btn btn-info" style="width: 100%">下一篇</a>
            <a type="button" href="/zhihu?baseId=${content.id}" class="btn btn-warning" style="width: 100%">返回列表</a>
        </div>
    </g:if>
    <div class="col-md-8 col-md-offset-2" id="content">
        <div class="panel panel-success"><div class="panel-body">${content.title}</div>
            <div class="panel-footer">${content.content}</div>
            <!-- JiaThis Button BEGIN -->
            <div class="jiathis_style">
                <a class="jiathis_button_qzone"></a>
                <a class="jiathis_button_tsina"></a>
                <a class="jiathis_button_weixin"></a>
                <a class="jiathis_button_cqq"></a>
                <a class="jiathis_button_print"></a>
                <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank"></a>
            </div>
            <script type="text/javascript" >
                var author = ${content.author}
                if(author.length==0) author='匿名用户'
                var jiathis_config={
                    url:window.location.href,
                    summary:author+"的回答",
                    title:encodeURI(${content.title}),
                    shortUrl:false,
                    hideMore:false
                }
            </script>
            <script type="text/javascript" src="http://v3.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
            <!-- JiaThis Button END -->
        </div>
    </div>
    <div class="col-md-8 col-md-offset-2">
        <a type="button" href="/zhihu/next?id=${content.id}" class="btn btn-info" style="width: 100%">下一篇</a>
        <a type="button" href="/zhihu?baseId=${content.id}" class="btn btn-warning" style="width: 100%">返回列表</a>
    </div>
</div>
</body>
</html>