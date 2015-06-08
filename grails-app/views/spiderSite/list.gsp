

<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'spiderSite.label', default: 'SpiderSite')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-spiderSite" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-spiderSite" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="siteUrl" title="${message(code: 'spiderSite.siteUrl.label', default: 'Site Url')}" />
					
						<g:sortableColumn property="enabled" title="${message(code: 'spiderSite.enabled.label', default: 'Enabled')}" />
					
						<g:sortableColumn property="siteName" title="${message(code: 'spiderSite.siteName.label', default: 'Site Name')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${spiderSiteInstanceList}" status="i" var="spiderSiteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${spiderSiteInstance.id}">${fieldValue(bean: spiderSiteInstance, field: "siteUrl")}</g:link></td>
					
						<td><g:formatBoolean boolean="${spiderSiteInstance.enabled}" /></td>
					
						<td>${fieldValue(bean: spiderSiteInstance, field: "siteName")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${spiderSiteInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
