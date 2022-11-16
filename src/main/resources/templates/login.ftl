<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>

<@c.page "Login">
   <script src="/static/js/formSubmit.js"></script>
   <script src="/static/js/localization.js"></script>
   <@auth.login />

   <form action="/login" method="get">
      <input name="lang" value="${lang!"en"}" id="langInput" hidden>
      <button type="submit" id="submitButton" hidden></button>
   </form>

   <script>
        function clickSubmitButtonHandler(){
            clickSubmitButton('submitButton')
        }
        addEventListeners(clickSubmitButtonHandler)
    </script>

</@c.page>