<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p/>
<#include "parts/references.ftl">
<#include "parts/security.ftl">


<@c.page "Profile Review">

<#assign
    activePill = activePill!""
    applicationsPill = "pills-applications-tab"
    receiptsPill = "pills-receipts-tab"
>

<#if customer??>
    <#if customer.address??>
        <#assign
            address = customer.address
            street = address.street!""
            houseNumber = address.houseNumber!""
        >
        <#if customer.address.city??>
            <#if customer.address.city.name??>
            <#assign
                cityName = customer.address.city.name
            >
        </#if>
        </#if>
    </#if>
</#if>

<script src="/static/js/localization.js"></script>
<script src="/static/js/profile.js"></script>

<div class="row">
    <div class="col-3">
        <h1 class="d-flex justify-content-center mb-4"><@spring.message "lang.profile"/></h1>

        <ul class="list-group">
            <li class="list-group-item">
                <@spring.message "lang.login"/>: ${customer.login}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.name"/>: ${customer.name!"-"}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.surname"/>: ${customer.surname!"-"}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.phone"/>: ${customer.phone!"-"}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.email"/>: ${customer.email!"-"}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.address"/>: <#if cityName??><@spring.message "city.${cityName}"/></#if>, <@spring.message "address.street"/> ${street!""}, <@spring.message "address.house"/> ${houseNumber!""}
            </li>
            <li class="list-group-item">
                <@spring.message "lang.balance"/>: ${customer.cash!"0.0"} <@spring.message "lang.UAH"/>
            </li>
        </ul>
    </div>
    <div class="col">
        <form action="${url}" method="get">
            <input name="lang" value="${lang!"en"}" id="langInput" hidden>
            <button type="submit" id="submitButton" hidden></button>
            <input name="activePill" id="activePillHiddenInput" value="${activePill!""}" hidden>
            <div class="row">
                <ul class="nav nav-pills justify-content-center mt-2 mb-4" id="profileMenuItems">
                    <li class="nav-item">
                        <button class="nav-link <#if activePill == applicationsPill>active</#if>" id="pills-applications-tab" data-bs-toggle="pill" data-bs-target="#pills-applications" type="button" role="tab" aria-controls="pills-applications" aria-selected="<#if activePill == applicationsPill>true<#else>false</#if>"><@spring.message "profile-menu-pills.applications"/></button>
                    </li>
                    <li class="nav-item">
                        <button class="nav-link <#if activePill == receiptsPill>active</#if>" id="pills-receipts-tab" data-bs-toggle="pill" data-bs-target="#pills-receipts" type="button" role="tab" aria-controls="pills-receipts" aria-selected="<#if activePill == receiptsPill>true<#else>false</#if>"><@spring.message "profile-menu-pills.receipts"/></button>
                    </li>
                </ul>
                <div class="tab-content" id="pills-tab-content">
                    <div class="tab-pane fade <#if activePill == applicationsPill>active show</#if>" id="pills-applications" role="tabpanel" aria-labelledby="pills-applications-tab">
                        <#list applications.content as application>
                        <#if application.deliveredBaggage??>
                            <#assign
                            baggage = application.deliveredBaggage
                            >
                        </#if>
                        <div class="row alert alert-primary mb-2">
                            <div class="col-1">
                                <a class="link" href="${refApplication}/${application.id}">#${application.id}</a>
                            </div>
                            <div class="col">
                                <@spring.message "city.${application.senderAddress.city.name}"/> - <@spring.message "city.${application.receiverAddress.city.name}"/>
                            </div>
                            <div class="col-auto">
                                ${application.sendingDate} - ${application.receivingDate}
                            </div>
                            <div class="col-2">
                                ${application.price} <@spring.message "lang.UAH"/>
                            </div>
                            <div class="col-2">
                                <@spring.message "delivery-application.state.${application.state}"/>
                            </div>
                        </div>
                        </#list>
                        <@p.qualifiedPager url applications 'applications' 'submitButton'/>
                    </div>
                    <div class="tab-pane fade <#if activePill == receiptsPill>active show</#if>" id="pills-receipts" role="tabpanel" aria-labelledby="pills-receipts-tab">
                        <div class="row alert alert-dark mb-2">
                            <div class="col-1">
                                #id
                            </div>
                            <div class="col-2">
                                <@spring.message "delivery.application"/>
                            </div>
                            <div class="col-3">
                                <@spring.message "lang.manager"/>
                            </div>
                            <div class="col-2" style="min-width: 110px;">
                                <@spring.message "delivery.application.receipt.formation-date"/>

                            </div>
                            <div class="col-2">
                                <@spring.message "lang.price"/>
                            </div>
                        </div>
                        <#list receipts.content as receipt>
                        <div class="row alert alert-primary mb-2">
                            <div class="col-1 d-flex align-self-center">
                                <a class="link" href="/receipt/${receipt.id}">#${receipt.id!""}</a>
                            </div>
                            <div class="col-2 d-flex align-self-center">
                                <a class="link" href="${refApplication}/${receipt.application.id}">#${receipt.application.id}</a>
                            </div>
                            <div class="col-3 d-flex align-self-center">
                                ${receipt.manager.name} ${receipt.manager.surname}
                            </div>
                            <div class="col-2 d-flex align-self-center" style="min-width: 110px;">
                                ${receipt.formationDate}
                            </div>
                            <div class="col-2 d-flex align-self-center">
                                ${receipt.totalPrice} <@spring.message "lang.UAH"/>
                            </div>
                            <div class="col d-flex justify-content-end me-2 align-self-center">
                                <#if receipt.paid>
                                <button class="btn btn-primary disabled"><@spring.message "lang.paid"/></button>
                                <#else>
                                <a class="btn btn-success" href="/receipt/${receipt.id}/pay"><@spring.message "lang.pay"/></a>
                            </#if>
                        </div>
                    </div>

                </#list>
                <@p.qualifiedPager url receipts 'receipts' 'submitButton'/>
            </div>
                </div>
            </div>
        </form>
    </div>

    <script>
        function clickSubmitButtonHandler(){
            clickSubmitButton('submitButton')
        }
        addEventListeners(clickSubmitButtonHandler)
    </script>

    <script>setChangingMenuItemsHandlers('profileMenuItems')</script>
</div>
</@c.page>