<%@ page import="hello.Catalog" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'catalog.label', default: 'Catalog')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
    <script type="text/javascript">
        function queryTest() {
            var url = "queryTest";
            $.getJSON(url, function (data) {
                console.log(data);
            })
        }
        function download(){
            var fileName = $("#fileName").val();
            window.open("fileDownload?fileName="+fileName,"newwindow");
        }

    </script>
</head>

<body>
<a href="#list-catalog" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
        <li>
            <a href="javascript:queryTest()">测试查询</a></li>
        <li>
            <g:uploadForm useToken="true" action="fileUpload" method="POST">
                <g:field type="file" name="uploadFile"></g:field>
                <g:submitButton value="上传" name="uploadButton"></g:submitButton>
            </g:uploadForm>
            <input type="text" id="fileName"/>
            <input type="button" onclick="download()">
        </li>
    </ul>
</div>

<div id="list-catalog" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="name" title="${message(code: 'catalog.name.label', default: 'Name')}"/>

            <g:sortableColumn property="amount" title="${message(code: 'catalog.amount.label', default: 'Amount')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${catalogInstanceList}" status="i" var="catalogInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${catalogInstance.id}">${fieldValue(bean: catalogInstance, field: "name")}</g:link></td>

                <td>${fieldValue(bean: catalogInstance, field: "amount")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${catalogInstanceTotal}"/>
    </div>
</div>
</body>
</html>
