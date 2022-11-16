<#assign
    authorized = Session.SPRING_SECURITY_CONTEXT??
>

<#if authorized>
<#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    isManager = user.isManager()
>
</#if>