



<div class="fieldcontain ${hasErrors(bean: zhihuCollectInstance, field: 'collectId', 'error')} ">
	<label for="collectId">
		<g:message code="zhihuCollect.collectId.label" default="Collect Id" />
		
	</label>
	<g:textField name="collectId" value="${zhihuCollectInstance?.collectId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: zhihuCollectInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="zhihuCollect.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${zhihuCollectInstance?.enabled}" />
</div>

