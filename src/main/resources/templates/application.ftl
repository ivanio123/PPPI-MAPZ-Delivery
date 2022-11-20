<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/layouts.ftl" as l>

<#include "parts/references.ftl">
<#include "parts/security.ftl">

<@c.page "Application">

<script src="/static/js/localization.js"></script>

<#if application??>
    <div class="row">
        <div class="col d-flex justify-content-center">
            <div class="card" style="min-width: 740px;">
                <div class="card-body">
                    <@l.application application/>
                    <#if isManager?? && isManager>
                        <#if application.customer??>
                            <#assign
                                login = application.customer.login!""
                                id = application.customer.id!""
                                phone = application.customer.phone!""
                            >
                            <#if application.customer.name?? && application.customer.surname??>
                                <#assign
                                    firstNameLastName = application.customer.name + " " + application.customer.surname
                                >
                            </#if>
                            <#if application.customer.address??>
                                <#assign
                                    customerAddress = application.customer.address
                                    cityName = customerAddress.city.name
                                    street = customerAddress.street!""
                                    houseNumber = customerAddress.houseNumber!""
                                >

                            </#if>
                        </#if>
                        <div class="ms-4 me-4">
                            <div class="row">
                                <div class="col-2">
                                    <label class="fs-5 fw-bolder me-2"><@spring.message "lang.customer"/>:</label>
                                </div>
                                <div class="col-auto mt-1">
                                    <a href="/profile/${id}"><#if firstNameLastName??>${firstNameLastName}<#else>${login}</#if></a>.
                                </div>
                                <div class="col-auto mt-1">
                                    <label><#if cityName??><@spring.message "city.${cityName}"/></#if>, <@spring.message "address.street"/> ${street!""}, <@spring.message "address.house"/> ${houseNumber!""}</label>
                                </div>
                                <div class="col-auto mt-1">
                                    <label>${phone!""}</label>
                                </div>
                            </div>
                        </div>

                        <#if application.state == "SUBMITTED">

                        <div class="row mt-4">
                            <div class="col d-flex justify-content-center">
                                <div class="col-auto me-4">
                                    <form action="/application/${application.id}/accept" method="get">
                                        <button class="btn btn-success"><@spring.message "lang.accept"/></button>
                                    </form>
                                </div>
                                <div class="col-auto">
                                    <form action="/application/${application.id}/reject" method="post">
                                        <input name="_csrf" value="${_csrf.token}" hidden>
                                        <button class="btn btn-danger"><@spring.message "lang.reject"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>

                        <#elseif application.state = "CONFIRMED">
                        <div class="row mt-4">
                            <div class="col d-flex justify-content-center">
                                <div class="col-auto me-4">
                                    <form action="/application/${application.id}/complete" method="post">
                                        <input name="_csrf" value="${_csrf.token}" hidden>
                                        <button class="btn btn-success"><@spring.message "lang.complete"/></button>
                                    </form>
                                </div>
                                <div class="col-auto">
                                    <form action="/application/${application.id}/reject" method="post">
                                        <input name="_csrf" value="${_csrf.token}" hidden>
                                        <button class="btn btn-danger"><@spring.message "lang.reject"/></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <#else>
                            <div class="row mt-4">
                                <div class="col d-flex justify-content-center">
                                    <a class="btn btn-outline-success" href="/applications/review">OK</a>
                                </div>
                            </div>
                        </#if>

                    <#else>
                        <div class="row">
                            <div class="col d-flex justify-content-center">
                                <a class="btn btn-primary" href="${refProfile}"><@spring.message "ref.back-to-profile"/></a>
                            </div>
                        </div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</#if>

<script>
    let inputs = []
    let url = '${refApplication}/${application.id}'
    addSwitchLanguageWithUrlClickListeners(url, inputs)
</script>

</@c.page>