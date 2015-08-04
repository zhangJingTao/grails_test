<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>贴吧内容抓取</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
    <script src="/main/js/bootstrap.min.js"></script>
    <!-- Bootstrap Core CSS -->
    <link href="/main/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<form action="/tieba/getIt" method="post">
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">起始ID</span>
                <input type="text" class="form-control" placeholder="起始ID" name="start"
                       aria-describedby="basic-addon1">
            </div>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">终止ID</span>
                <input type="text" class="form-control" placeholder="终止ID" name="end"
                       aria-describedby="basic-addon1">
            </div>
        </div>
        <div class="col-md-8 col-md-offset-2">
            <input type="submit" class="btn btn-primary" value="提交任务"/>
        </div>
    </div>
</form>
</body>
</html>