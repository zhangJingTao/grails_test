



<div class="fieldcontain ${hasErrors(bean: spiderSiteSelectorConfigInstance, field: 'sort', 'error')} required">
	<label for="sort">
		<g:message code="spiderSiteSelectorConfig.sort.label" default="Sort" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="sort" from="${spiderSiteSelectorConfigInstance.constraints.sort.inList}" required="" value="${fieldValue(bean: spiderSiteSelectorConfigInstance, field: 'sort')}" valueMessagePrefix="spiderSiteSelectorConfig.sort"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spiderSiteSelectorConfigInstance, field: 'selector', 'error')} ">
	<label for="selector">
		<g:message code="spiderSiteSelectorConfig.selector.label" default="Selector" />
		
	</label>
	<g:textField name="selector" value="${spiderSiteSelectorConfigInstance?.selector}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spiderSiteSelectorConfigInstance, field: 'selectorType', 'error')} ">
	<label for="selectorType">
		<g:message code="spiderSiteSelectorConfig.selectorType.label" default="Selector Type" />
		
	</label>
	<g:textField name="selectorType" value="${spiderSiteSelectorConfigInstance?.selectorType}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spiderSiteSelectorConfigInstance, field: 'site', 'error')} required">
	<label for="site">
		<g:message code="spiderSiteSelectorConfig.site.label" default="Site" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="site" name="site.id" from="${SpiderSite.list()}" optionKey="id" optionValue="siteName" required="" value="${spiderSiteSelectorConfigInstance?.site?.id}" class="many-to-one"/>
</div>

