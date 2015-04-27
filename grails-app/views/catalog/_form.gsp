<%@ page import="hello.Catalog" %>



<div class="fieldcontain ${hasErrors(bean: catalogInstance, field: 'name', 'error')} ">
    <label for="name">
        <g:message code="catalog.name.label" default="Name"/>

    </label>
    <g:textField name="name" value="${catalogInstance?.name}"/>
</div>

%{--<div class="fieldcontain ${hasErrors(bean: catalogInstance, field: 'amount', 'error')} required">
    <label for="amount">
        <g:message code="catalog.amount.label" default="Amount"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="amount" type="number" value="${catalogInstance.amount}" required=""/>
</div>--}%

