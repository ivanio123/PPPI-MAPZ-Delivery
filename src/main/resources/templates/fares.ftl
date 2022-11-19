<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#import "/spring.ftl" as spring/>
<#include "parts/references.ftl">

<@c.page "Fares">
    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>

    <@n.infoPills "Fares"/>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1><@spring.message "lang.fares"/></h1>
        </div>
    </div>

    <div class="row mt-2">
        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center"><@spring.message "fares-for-distance.head"/></h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th><@spring.message "lang.distance"/> (<@spring.message "lang.not-inclusive"/>)</th>
                        <th><@spring.message "lang.price"/>, <@spring.message "lang.UAH"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list distanceFares?sort_by("price") as distanceFare>
                    <tr>
                        <#if distanceFare?index+1 == distanceFares?size>
                            <td><@spring.message "lang.over"/> ${distanceFare.distanceTo}</td>
                        <#else>
                            <td><@spring.message "lang.up-to"/> ${distanceFare.distanceTo+1}</td>
                        </#if>
                        <td>${distanceFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center"><@spring.message "fares-for-weight.head"/></h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th><@spring.message "lang.weight"/> (<@spring.message "lang.not-inclusive"/>)</th>
                        <th><@spring.message "lang.price"/>, <@spring.message "lang.UAH"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list weightFares as weightFare>
                    <tr>
                        <#if weightFare?index+1 == weightFares?size>
                            <td><@spring.message "lang.for-every"/> ${weightFare.weightTo} <@spring.message "lang.over"/> ${weightFare.weightTo}</td>
                        <#else>
                            <td><@spring.message "lang.up-to"/> ${weightFare.weightTo+1}</td>
                        </#if>
                        <td>${weightFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col ms-4 me-4">
            <div class="row mb-2">
                <h2 class="d-flex justify-content-center"><@spring.message "fares-for-dimensions.head"/></h2>
            </div>
            <div class="row">
                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th><@spring.message "lang.volume"/>, <@spring.message "lang.cm"/><sup><small>3</small></sup> (<@spring.message "lang.not-inclusive"/>)</th>
                        <th><@spring.message "lang.price"/>, <@spring.message "lang.UAH"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list dimensionsFares as dimensionsFare>
                    <tr>
                        <#if dimensionsFare?index+1 == dimensionsFares?size>
                            <td><@spring.message "lang.for-every"/> ${dimensionsFare.dimensionsTo} <@spring.message "lang.over"/> ${dimensionsFare.dimensionsTo}</td>
                        <#else>
                            <td><@spring.message "lang.up-to"/> ${dimensionsFare.dimensionsTo+1}</td>
                        </#if>
                        <td>${dimensionsFare.price}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        let inputs = []
        let url = '${refFares}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>
</@c.page>