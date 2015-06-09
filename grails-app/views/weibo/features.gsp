<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>SleepWeibo 0.1</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/js/masonry.pkgd.min.js"></script>
    <link href="/css/nav.css" rel="stylesheet">
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
    <link href="/css/doc.min.css" rel="stylesheet">
    <script src="/js/unslider.js"></script>
    <link href="/css/weibo.css" rel="stylesheet">
    <script src="/js/weibo_features.js"></script>
<style type="text/css">
.banner { position: relative; overflow: auto; }
.banner li { list-style: none; }
.banner ul li { float: left; }
</style>
</head>
<body>
<div class="border" id="home"></div>
<div class="container">
    <div class="row">
        <div class="col-md-12" style="z-index: 99999">
            <div class="wrapper">
                <div id="nav">
                    <ul>
                        <li><a href="home">首页</a></li>
                        <li class="active"><a href="features">收藏</a></li>
                        <li><a href="about">关于</a></li>
                    </ul>
                </div>
                <br>
                <a href="#nav-toggle" id="nav-toggle" aria-hidden="false">Menu</a>
            </div>
        </div>
        <br>
        <div class="col-md-8 col-md-offset-2">
            <div class="progress" id="loadingProgress" style="height: 5px">
                <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="20" aria-valuemin="0"
                     aria-valuemax="100" style="width: 0%">
                    <span class="sr-only">0% Complete</span>
                </div>
            </div>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <div id="list" class="container-fluid" style="display: none">
            </div>
        </div>
        <div class="col-md-8 col-md-offset-2 stickUp" id="page">
            <nav>
                <ul class="pager">
                    <li class="previous disabled"><a href="#"><span aria-hidden="true">&larr;</span> Older</a></li>
                    <li class="next disabled"><a href="#">Newer <span aria-hidden="true">&rarr;</span></a></li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>