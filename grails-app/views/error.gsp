<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Expires" content="Thu, 01 Jan 1970 00:00:01 GMT"/>
    <meta http-equiv="Expires" content="0"/>
    <title>服务器提了一个问题:塔防游戏</title>
    <link href="/css/tower/c.css" rel="stylesheet" type="text/css" media="screen"/>
</head>
<body id="tower-defense">
<g:if env="development">
    <g:renderException exception="${exception}"/>
</g:if>
<g:else>
    <div id="td-wrapper">
        <h1>服务器提了一个问题....</h1>

        <div id="td-loading">加载中...</div>
        <div id="td-board">
            <canvas id="td-canvas">抱歉，您的浏览器不支持 HTML 5 Canvas 标签，请使用 IE9 / Chrome / Opera 等浏览器浏览本页以获得最佳效果。</canvas>
        </div>
    </div>
    <div id="about">
        <a href="https://github.com/oldj/html5-tower-defense" target="_blank">源码</a>
    </div>
    </div>

    <script type="text/javascript" src="/js/tower/td.js"></script>
    <script type="text/javascript" src="/js/tower/td-lang.js"></script>
    <script type="text/javascript" src="/js/tower/td-event.js"></script>
    <script type="text/javascript" src="/js/tower/td-stage.js"></script>
    <script type="text/javascript" src="/js/tower/td-element.js"></script>
    <script type="text/javascript" src="/js/tower/td-obj-map.js"></script>
    <script type="text/javascript" src="/js/tower/td-obj-grid.js"></script>
    <script type="text/javascript" src="/js/tower/td-obj-building.js"></script>
    <script type="text/javascript" src="/js/tower/td-obj-monster.js"></script>
    <script type="text/javascript" src="/js/tower/td-obj-panel.js"></script>
    <script type="text/javascript" src="/js/tower/td-data-stage-1.js"></script>
    <script type="text/javascript" src="/js/tower/td-cfg-buildings.js"></script>
    <script type="text/javascript" src="/js/tower/td-cfg-monsters.js"></script>
    <script type="text/javascript" src="/js/tower/td-render-buildings.js"></script>
    <script type="text/javascript" src="/js/tower/td-msg.js"></script>
    <script type="text/javascript" src="/js/tower/td-walk.js"></script>

    <!--<script type="text/javascript" src="td-pkg-min.js?fmts=1"></script>-->
    <script type="text/javascript">
        window.onload = function () {
            _TD.init("td-board", true);
            document.getElementById("td-loading").style.display = "none";
            document.getElementById("td-board").style.display = "block";
        };
    </script>

</g:else>
</body>
</html>
