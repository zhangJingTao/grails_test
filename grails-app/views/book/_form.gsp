<%@ page import="hello.Book" %>



<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'uuid', 'error')} ">
    <label for="uuid">
        <g:message code="book.uuid.label" default="Uuid"/>

    </label>
    <g:textField name="uuid" value="${bookInstance?.uuid}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'size', 'error')} required">
    <label for="size">
        <g:message code="book.size.label" default="Size"/>
        <span class="required-indicator">*</span>
    </label>
    <g:field name="size" type="number" max="9999999" value="${bookInstance.size}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'catalog', 'error')} required">
    <label for="catalog">
        <g:message code="book.catalog.label" default="Catalog"/>
        <span class="required-indicator">*</span>
    </label>
    <g:select id="catalog" name="catalog.id" from="${hello.Catalog.list()}" optionKey="id" optionValue="name"
              required="" class="many-to-one"/>
    %{--对比：
        <table>
        <g:each in="${catalogs}" var="cat">
         <tr>
             <td>${cat.id}</td>
             <td>${cat.name}</td>
             <td>${cat.amount}</td>
         </tr>
        </g:each>
        </table>--}%

</div>

<div class="fieldcontain ${hasErrors(bean: bookInstance, field: 'title', 'error')} ">
    <label for="title">
        <g:message code="book.title.label" default="Title"/>

    </label>
    <g:textField name="title" value="${bookInstance?.title}"/>
</div>

