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
    <link href="/css/nav.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <!-- responsive -->
    <script src="/js/responsive/responsive-nav.min.js"></script>
    <link href="/css/responsive/responsive-nav.css" rel="stylesheet">
    <script type="text/javascript">
        var pending = false
        var baseId = ${baseId}
        var count = 0 //超过3次后开始自动删除前10个list-group-item
        var classes = ['panel-primary','panel-success','panel-info','panel-warning','panel-danger']
        $(document).ready(function () {
            var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});
            getNext();
        })

        function getNext(){
            var template = '<a href="/zhihu/detail/{id}" class="list-group-item"><h4 class="list-group-item-heading">{title}</h4><p class="list-group-item-text">{author}{authordesc}</p></a>'
            if(!pending){
                pending = true
                var url = "/zhihu/giveMeFive?max=10&minId="+baseId+"&_="+new Date().getTime()
                var html =""
                $.getJSON(url,function(data){
                    if(data.status == 1){
                        var list = data.list
                        $.each(list,function(index,ele){
                            var author = ele.author
                            if(author.length == 0) author = '匿名用户'
                            html += template.replace("{title}",ele.title)
                                    .replace("{content}",ele.content)
                                    .replace("{author}",author)
                                    .replace("{id}",ele.id)
                                    .replace("{authordesc}",ele.authorDesc==null? "":ele.authorDesc)
                        })
                        baseId = data.nexBaseId
                        $("#content").append(html)
                    }
                    pending = false
                    $("#moreBtn").show()
                    count ++
                    if(count >3){
                        for(var i = 0;i<10;i++){
                            $($(".list-group-item")[0]).remove()
                        }
                    }
                })
            }
        }

        function delThis(obj){
            var url = "/zhihu/del?id="+obj
            $("#div"+obj).css("display","none")
            $.getJSON(url,function(data){
            })
            window.location.href = "#div"+obj
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
        <a href="#moreBtn" type="button" class="btn btn-success" style="width: 100%" id="moreBtn"  style="display: none" onclick="getNext()">查看更多</a>
    </div>
</div>
</body>
</html>