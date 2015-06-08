

<!DOCTYPE html>
<html>
	<head>
        <meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-spiderSiteSelectorConfig" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-spiderSiteSelectorConfig" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="sort" title="${message(code: 'spiderSiteSelectorConfig.sort.label', default: 'Sort')}" />
					
						<g:sortableColumn property="selector" title="${message(code: 'spiderSiteSelectorConfig.selector.label', default: 'Selector')}" />
					
						<g:sortableColumn property="selectorType" title="${message(code: 'spiderSiteSelectorConfig.selectorType.label', default: 'Selector Type')}" />
					
						<th><g:message code="spiderSiteSelectorConfig.site.label" default="Site" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${spiderSiteSelectorConfigInstanceList}" status="i" var="spiderSiteSelectorConfigInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${spiderSiteSelectorConfigInstance.id}">${fieldValue(bean: spiderSiteSelectorConfigInstance, field: "sort")}</g:link></td>
					
						<td>${fieldValue(bean: spiderSiteSelectorConfigInstance, field: "selector")}</td>
					
						<td>${fieldValue(bean: spiderSiteSelectorConfigInstance, field: "selectorType")}</td>
					
						<td>${fieldValue(bean: spiderSiteSelectorConfigInstance, field: "site.siteName")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${spiderSiteSelectorConfigInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
