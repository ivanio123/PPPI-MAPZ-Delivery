<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>

<@c.page "Registration">
    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>
    <@auth.registration />

    <script>
        let inputs = ['nameInput', 'surnameInput', 'emailInput', 'phoneInput', 'streetInput', 'houseNumberInput', 'loginInput', 'cityInput']
        let url = '${refRegister}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>


</@c.page>