<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>知乎：收藏</title>
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
        var pending = false
        var baseId = 99999999999
        var classes = ['panel-primary','panel-success','panel-info','panel-warning','panel-danger']
        $(document).ready(function () {
            var navigation = responsiveNav("#nav", {customToggle: "#nav-toggle"});
            getNext();
        })

        function getNext(){
            var template = '<div id="div{id}" class="panel {class}"><div class="panel-body">{title}</div><div class="panel-footer">{content}<span class="label label-info" onclick="{method}">x</span></div></div>'
            if(!pending){
                pending = true
                var url = "/zhihu/giveMeFive?minId="+baseId+"&_="+new Date().getTime()
                var html =""
                $.getJSON(url,function(data){
                    if(data.status == 1){
                        var list = data.list
                        $.each(list,function(index,ele){
                            html += template.replace("{title}",ele.title)
                                    .replace("{content}",ele.content)
                                    .replace("{class}",classes[index%5])
                                    .replace("{id}",ele.id)
                                    .replace("{method}","delThis("+ele.id+")")
                        })
                        baseId = data.nexBaseId
                        $("#content").append(html)
                    }
                    pending = false
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
    <div class="col-md-8 col-md-offset-2" id="content">

    </div>
    <div class="col-md-2 col-md-offset-5">
        <button type="button" class="btn btn-warning" style="width: 100%" onclick="getNext()">查看更多</button>
    </div>
</div>
</body>
</html>