<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>测试git Webhooks</title>
    <!-- jQuery -->
    <script src="/main/js/jquery.js"></script>
</head>
<body>
<h3>测试git Webhooks</h3>

<form method="POST" action="/git/pushEvent">
    <textarea cols="20" rows="5" name="params">


    </textarea>
    <input type="submit" value="提交">
</form>
</body>
</html>