<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Inspiration-v2.0</title>
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
            $("#rssBtn").on('click', function () {
                var email = $("#rssEmail").val();
                var url = "/rssEmail/rssAdd?email="+email+"&_"+new Date().getTime();
                btn = $(this).button('loading')
                $.getJSON(url,function(data){
                    if(data.result==1){
                        $.globalMessenger().post({
                            message: '订阅成功.',
                            showCloseButton: true,
                            id: "Only-one-message"
                        });
                    }else{
                        $.globalMessenger().post({
                            message: '订阅失败.',
                            showCloseButton: true,
                            id: "Only-one-message"
                        });
                    }
                    btn.button('reset')
                })
            })
            $._messengerDefaults = {
                extraClasses: 'messenger-fixed messenger-theme-future messenger-on-top'
            }
            //Git更新历史
            var gitUrl = "/git/commitHistory?limit=4"
            $.getJSON(gitUrl,function(data){
                var htmlTemplate = '<div class="panel panel-danger"><div class="panel-footer" onclick="window.open(\'{respUrl}\')">{resp}</div><div class="panel-body" onclick="window.open(\'{commitUrl}\')">{commit}</div></div>'
                var html = '';
               //<span class="badge"></span>
                $.each(data,function(index,ele){
                    var badge = '<span class="badge">'+new Date(ele.commit_date).format("yyyy/MM/dd hh:mm:ss")+'</span>'
                    html += htmlTemplate.replace("{respUrl}",ele.repository_url).replace("{resp}",ele.repository_name+"@"+badge).replace("{commitUrl}",ele.commit_url).replace("{commit}",ele.commit_msg)
                })
                $("#git-list-group").html(html);
            })
        })


        Date.prototype.format = function(format){
            var o = {
                "M+" : this.getMonth()+1, //month
                "d+" : this.getDate(), //day
                "h+" : this.getHours(), //hour
                "m+" : this.getMinutes(), //minute
                "s+" : this.getSeconds(), //second
                "q+" : Math.floor((this.getMonth()+3)/3), //quarter
                "S" : this.getMilliseconds() //millisecond
            }

            if(/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
            }

            for(var k in o) {
                if(new RegExp("("+ k +")").test(format)) {
                    format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
                }
            }
            return format;
        }
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
            <a class="navbar-brand page-scroll" href="#page-top">
                <i class="fa fa-play-circle"></i>  <span class="light">Start</span> Toy
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
                    <a class="page-scroll" href="#about">About</a>
                </li>
                <li>
                    <a class="page-scroll" href="#download">Source</a>
                </li>
                <li>
                    <a class="page-scroll" href="#wn">What's New</a>
                </li>
                <li>
                    <a class="page-scroll" href="#contact">Contact</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>

<!-- Intro Header -->
<header class="intro">
    <div class="intro-body">
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2">
                    <h1 class="brand-heading">OnlySleep</h1>
                    <p class="intro-text">想你所想,给你所要</p>
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
            <h2>About...</h2>
            <p><s>很忙，根本没想过要写这个地方</s></p>
        </div>
    </div>
</section>

<!-- Download Section -->
<section class="content-section text-center" id="download" style="padding-top: 180px">
    <div class="download-section">
        <div class="container">
            <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Source</h2>
                <p>整个站点的代码都可以在这里下载！</p>
                <a href="https://github.com/zhangJingTao/grails_test" class="btn btn-default btn-lg">Visit Github</a>
            </div>
            </div>
            <br>
            <div class="col-lg-8 col-lg-offset-2">
                <div class="list-group" id="git-list-group" style="color: black;text-align: left">

                </div>
            </div>
        </div>
    </div>
</section>
<!-- What's New -->
<section id="wn" class="container content-section text-center">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>What's New...</h2>
            <p><s></s></p>
            <ul class="list-group" style="color: #000000">
                <li class="list-group-item">
                    <span class="badge">2015-05-19</span>
                    <s>全新的页面上线，<a href="#download">Rss订阅功能</a>,虽然没有什么卵用</s>
                </li>
                <li class="list-group-item">
                    <span class="badge">2015-05-20</span>
                    Chrome插件，匿名统计你的浏览器行为，<a href="/chrome/index">VisitCountPlugin</a>
                </li>
                <li class="list-group-item">
                    <span class="badge">2015-05-26</span>
                    Git Webhooks服务，Git代码更新自动同步到本站！
                </li>
            </ul>
        </div>
    </div>
</section>


<!-- Contact Section -->
<section id="contact" class="container content-section text-center">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2">
            <h2>Contact Me</h2>
            <p></p>
            <p><a href="mailto:zhangjingtaosleep@163.com">zhangjingtaosleep@163.com</a>
            </p>
            <ul class="list-inline banner-social-buttons">
                <!--  <li>
                        <a href="https://twitter.com/SBootstrap" class="btn btn-default btn-lg"><i class="fa fa-twitter fa-fw"></i> <span class="network-name">Twitter</span></a>
                    </li>-->
                <li>
                    <a href="https://github.com/zhangjingtao" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                </li>
                <!--                    <li>
                        <a href="https://plus.google.com/+Startbootstrap/posts" class="btn btn-default btn-lg"><i class="fa fa-google-plus fa-fw"></i> <span class="network-name">Google+</span></a>
                    </li>-->
            </ul>
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
	<div align="center"><a href="http://www.amazingcounters.com"><img border="0" src="http://cc.amazingcounters.com/counter.php?i=3189687&c=9569374" alt="AmazingCounters.com"></a></div>
</footer>

<!-- Bootstrap Core JavaScript -->
<script src="/main/js/bootstrap.min.js"></script>

<!-- Plugin JavaScript -->
<script src="/main/js/jquery.easing.min.js"></script>
<!-- Custom Theme JavaScript -->
<script src="/main/js/grayscale.js"></script>

</body>

</html>
