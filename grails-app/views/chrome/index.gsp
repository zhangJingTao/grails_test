<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Visit Count Plugin</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="/main/css/grayscale.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="/main/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="http://fonts.useso.com/css?family=Lora:400,700,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="http://fonts.useso.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- messager -->
    <script src="/js/messenger.min.js"></script>
    <script src="/js/messenger-theme-future.js"></script>
    <link href="/css/messenger.css" rel="stylesheet">
    <link href="/css/messenger-theme-future.css" rel="stylesheet">

    <script type="text/javascript">
        var btn
        $(document).ready(function(){
            $._messengerDefaults = {
                extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
            }
        })
    </script>
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

<!-- Navigation -->
<nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand page-scroll" href="/download/VisitCounter_chrome_extensions.crx">
                <i class="fa fa-play-circle"></i>  <span class="light">Download It!</span>
            </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
            <ul class="nav navbar-nav">
                <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                <li class="hidden">
                    <a href="#page-top"></a>
                </li>
                <li>
                    <a class="page-scroll" href="/">首页</a>
                </li>
                <li>
                    <a class="page-scroll" href="#about">Description</a>
                </li>
                <li>
                    <a class="page-scroll" href="#ht">How To...</a>
                </li>
                <li>
                    <a class="page-scroll" href="#qa">Q&A</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Intro Header -->
<header class="intro" style="background: url(/chrome/bg.jpg)">
    <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <h1 class="brand-heading">VisitCounter</h1>
                    <p class="intro-text">匿名统计Chrome插件</p>
                    <a href="#about" class="btn btn-circle page-scroll">
                        <i class="fa fa-angle-double-down animated"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- About Section -->
<section id="about" class="container content-section text-center">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>Description...</h2>
            <p>一个匿名统计的plugin，当使用随机字符串绑定后，可匿名分类Chrome浏览器行为，插件安装后，不会感受到它的存在</p>
            <p>整个项目的源码<a href="https://github.com/zhangJingTao/VisitCounter_chrome_extensions/">在这里</a></p>

        </div>
    </div>
</section>

<!-- Download Section -->
<section class="content-section text-center" id="ht" style="padding-top: 180px;">
    <div class="download-section" style="background: url(/chrome/bg2.jpg)">
        <div class="container">
            <div class="col-lg-6 col-lg-offset-3">
                <div class="input-group">
                    <span class="input-group-btn">
                        <h3>How To Install</h3>
                        <h5>1.点击左上角 <span class="label label-success">Download It！</span></h5>
                        <h5>2.下载完毕后，在Chrome浏览器中输入 <span class="label label-info">chrome://extensions/</span></h5>
                        <h5>3.将下载到的<span class="label label-danger">VisitCounter_chrome_extensions.crx</span>拖放到该页面安装</h5>
                        <h3>Option</h3>
                        <h5>访问<span class="label label-info">chrome://extensions/</span>点击<span class="label label-info">VisitCounter 的选项</span></h5>
                        <h5>可输入<span class="label label-danger">任意字符串</span>，谨记，这个字符串是唯一有效的</h5>
                        <h5>或者可以点击 <span class="label label-success">Clear Login Data！</span>这样，你的行为将不被分类！</h5>
                    </span>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="qa" class="container content-section text-center">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>.</h2>
            <h2>.</h2>
            <h2>.</h2>
            <h2>.</h2>
            <h2>.</h2>
            <h4>源码都开放了，还有什么可说的</h4>
        </div>
    </div>
</section>

<!-- Map Section -->
<div id="map"></div>

<!-- Footer -->
<footer>
    <div class="container text-center">
        <p>Copyright &copy; OnlySleep.net 2015 京ICP备15022349号</p>
    </div>
</footer>

<!-- Bootstrap Core JavaScript -->
<script src="/main/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="/main/js/jquery.easing.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="/main/js/grayscale.js"></script>

</body>

</html>
