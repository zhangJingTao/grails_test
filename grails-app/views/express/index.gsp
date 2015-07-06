<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>快递查询</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <link href="/css/nav.css" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
    <script src="/js/kuaidi.js"></script>
    <script type="text/javascript">
        function initSelect(obj){
            var text = $(obj).val()
            var url ="/express/queryComp?text="+text+"&_="+new Date().getTime()
            var lis = ""
            $.getJSON(url,function(data){
                $.each(data,function(idx,ele){
                    $.each(express,function(k,v){
                        if(k.trim()==ele.comCode){
                            lis += "<li onclick='hidecode(\""+ele.comCode+"\")'><a href='javascript:void(0)'>"+v+"</a></li>"
                        }
                    })
                })
                $("#list").html(lis)
            })
        }

        function hidecode(code){
            $("#expressCode").val(code)
            $.each(express,function(k,v){
                if(k.trim()==code){
                    $("#dropdownMenu1").html(v+'<span class="caret"></span>')
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
                <input type="text" class="form-control" placeholder="请输入快递单号" aria-describedby="sizing-addon2" onkeyup="initSelect(this)">
                <input type="hidden" id="expressCode" value="">
            </div></p>
            <p>

            <div class="dropdown">
                <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    快递列表
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenu1" id="list">

                </ul>
            </div>

            </p>
            <p>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="输入Email">
                <span class="input-group-btn">
                    <button class="btn btn-default" type="button">订阅!</button>
                </span>
            </div><!-- /input-group -->
        </div>

    </p>
    </div>
</div>
</div>
</body>
</html>