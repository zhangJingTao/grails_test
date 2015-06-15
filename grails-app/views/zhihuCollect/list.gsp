

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zhihuCollect.label', default: 'ZhihuCollect')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-zhihuCollect" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-zhihuCollect" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="collectId" title="${message(code: 'zhihuCollect.collectId.label', default: 'Collect Id')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'zhihuCollect.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="enabled" title="${message(code: 'zhihuCollect.enabled.label', default: 'Enabled')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${zhihuCollectInstanceList}" status="i" var="zhihuCollectInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${zhihuCollectInstance.id}">${fieldValue(bean: zhihuCollectInstance, field: "collectId")}</g:link></td>
					
						<td><g:formatDate date="${zhihuCollectInstance.dateCreated}" /></td>
					
						<td><g:formatBoolean boolean="${zhihuCollectInstance.enabled}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${zhihuCollectInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
