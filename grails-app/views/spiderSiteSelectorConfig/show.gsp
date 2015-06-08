

<!DOCTYPE html>
<html>
	<head>
        <meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'spiderSiteSelectorConfig.label', default: 'SpiderSiteSelectorConfig')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-spiderSiteSelectorConfig" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-spiderSiteSelectorConfig" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list spiderSiteSelectorConfig">
			
				<g:if test="${spiderSiteSelectorConfigInstance?.sort}">
				<li class="fieldcontain">
					<span id="sort-label" class="property-label"><g:message code="spiderSiteSelectorConfig.sort.label" default="Sort" /></span>
					
						<span class="property-value" aria-labelledby="sort-label"><g:fieldValue bean="${spiderSiteSelectorConfigInstance}" field="sort"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${spiderSiteSelectorConfigInstance?.selector}">
				<li class="fieldcontain">
					<span id="selector-label" class="property-label"><g:message code="spiderSiteSelectorConfig.selector.label" default="Selector" /></span>
					
						<span class="property-value" aria-labelledby="selector-label"><g:fieldValue bean="${spiderSiteSelectorConfigInstance}" field="selector"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${spiderSiteSelectorConfigInstance?.selectorType}">
				<li class="fieldcontain">
					<span id="selectorType-label" class="property-label"><g:message code="spiderSiteSelectorConfig.selectorType.label" default="Selector Type" /></span>
					
						<span class="property-value" aria-labelledby="selectorType-label"><g:fieldValue bean="${spiderSiteSelectorConfigInstance}" field="selectorType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${spiderSiteSelectorConfigInstance?.site}">
				<li class="fieldcontain">
					<span id="site-label" class="property-label"><g:message code="spiderSiteSelectorConfig.site.label" default="Site" /></span>
					
						<span class="property-value" aria-labelledby="site-label"><g:link controller="spiderSite" action="show" id="${spiderSiteSelectorConfigInstance?.site?.id}">${spiderSiteSelectorConfigInstance?.site?.siteName}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${spiderSiteSelectorConfigInstance?.id}" />
					<g:link class="edit" action="edit" id="${spiderSiteSelectorConfigInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
