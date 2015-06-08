



<div class="fieldcontain ${hasErrors(bean: spiderSiteInstance, field: 'siteUrl', 'error')} ">
	<label for="siteUrl">
		<g:message code="spiderSite.siteUrl.label" default="Site Url" />
		
	</label>
	<g:textField name="siteUrl" value="${spiderSiteInstance?.siteUrl}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: spiderSiteInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="spiderSite.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${spiderSiteInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: spiderSiteInstance, field: 'siteName', 'error')} ">
	<label for="siteName">
		<g:message code="spiderSite.siteName.label" default="Site Name" />
		
	</label>
	<g:textField name="siteName" value="${spiderSiteInstance?.siteName}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: spiderSiteInstance, field: 'page', 'error')} ">
    <label for="siteName">
        <g:message code="spiderSite.page.label" default="page" />

    </label>
    <g:textField name="page" value="${spiderSiteInstance?.page}"/>
</div>

