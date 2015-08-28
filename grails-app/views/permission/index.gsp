<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>权限增加</title>
    <g:include view="/include/all.gsp"/>
</head>

<body>
<form action="save">
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="bs-callout bs-callout-warning" id="callout-navbar-overflow">
                <h4>输入权限菜单</h4>
                <p>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="权限名称" name="name"
                           aria-describedby="sizing-addon2">
                    <input type="text" class="form-control" placeholder="权限URL：/home/index" name="pUrl"
                           aria-describedby="sizing-addon2">
                </div></p>
                <p>
                </p>
                <p>
                <div class="input-group">
                    <button class="btn btn-default" type="submit">增加!</button>
                </div><!-- /input-group -->
            </div>
        </p>
        </div>
    </div>
</form>
<form action="addRole">
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <div class="bs-callout bs-callout-warning" id="callout-navbar-overflow">
                <h4>添加角色与权限关系</h4>
                <p>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="权限名称" id="expressNo"
                           aria-describedby="sizing-addon2">
                    <input type="text" class="form-control" placeholder="权限URL：/home/index" id="uri"
                           aria-describedby="sizing-addon2">
                </div></p>
                <p>
                </p>
                <p>
                <div class="input-group">
                    <button class="btn btn-default" type="submit">增加!</button>
                </div><!-- /input-group -->
            </div>
        </p>
        </div>
    </div>
</form>
</body>
</html>