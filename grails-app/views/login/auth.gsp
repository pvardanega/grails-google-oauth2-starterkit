<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><g:message code="auth.page.title"/></title>
    <link rel="icon" href="${request.contextPath + '/static/images/favicon.ico'}" type="image/x-icon" />
    <link rel="shortcut icon" href="${request.contextPath + '/static/images/favicon.ico'}" type="image/x-icon" />
    <r:require modules="application, bootstrap"/>
    <r:layoutResources/>

<body style="background-color: #f5f5f5;">

<div class="container">

    <g:if test='${flash.message}'>
        <div class="alert alert-error">${flash.message}</div>
    </g:if>

    <form class="form-signin">
        <p>
            <g:img dir="images" file="xebia.png" />
        </p>
        <hr/>
        <p>
            <g:link controller="auth" action="signin" params="[provider: 'google']" class="btn btn-block">
                <g:message code="auth.with.google"/>
            </g:link>
        </p>
    </form>
</div>

<r:layoutResources/>

</body>
</html>