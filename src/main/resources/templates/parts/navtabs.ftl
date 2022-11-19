<#include "references.ftl">
<#include "security.ftl">
<#import "/spring.ftl" as spring/>

<#macro infoPills activePill>
<#assign
    activePill = activePill!""
    directions = "Directions"
    calculator = "Calculator"
    fares = "Fares"
>
    <ul class="nav nav-pills">
        <li class="nav-item">
            <a class="nav-link <#if activePill == directions>active</#if>" href="${refInfo}" id="directionsPill"><@spring.message "lang.directions"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if activePill == fares>active</#if>" href="${refFares}" id="faresPill"><@spring.message "lang.fares"/></a>
        </li>
        <li class="nav-item">
            <a class="nav-link <#if activePill == calculator>active</#if>" href="${refDeliveryCostCalculator}" id="calculatorPill"><@spring.message "lang.delivery-cost"/></a>
        </li>

    </ul>
</#macro>