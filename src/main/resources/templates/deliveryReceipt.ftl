<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Make Receipt">

<#if customer.name?? && customer.surname??>
    <#assign
        firstNameLastName = customer.name + " " + customer.surname
    >
</#if>

<script src="/static/js/localization.js"></script>

<div class="row">
    <div class="col d-flex justify-content-center">
        <form action="/application/${application.id}/accept" method="post" style="min-width: 480px;">
            <input name="_csrf" value="${_csrf.token}" hidden>
            <div class="card">
                <div class="card-body">
                    <h1 class="d-flex justify-content-center"><@spring.message "delivery-receipt.head"/></h1>
                    <hr>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder"><@spring.message "delivery.application"/>: </div>
                        <div class="col"> <a href="${refApplication}/${application.id}">#${application.id}</a> </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder"><@spring.message "lang.customer"/>: </div>
                        <div class="col"> <a href="/profile/${customer.id}"><#if firstNameLastName??>${firstNameLastName}<#else>${customer.login!"Unknown"}</#if></a> </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder"><@spring.message "lang.manager"/>: </div>
                        <div class="col"> <a href="/profile/${manager.id}">${manager.name!"Unknown"} ${manager.surname!""}</a></div>
                    </div>
                    <div class="row mb-2">
                        <div class="col fs-5 fw-bolder"><@spring.message "delivery-receipt.total-price"/>:</div>
                        <div class="col">
                            <div class="row">
                                <div class="col">
                                    <input type="text" class="form-control" name="price" value="${price!""}" placeholder="<@spring.message "lang.price"/>">
                                </div>
                                <div class="col">
                                    <label class="col-form-label"><@spring.message "lang.UAH"/></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col d-flex justify-content-center">
                            <button class="btn btn-success"><@spring.message "delivery-receipt.make.button"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    addSwitchLanguageWithUrlClickListeners('${url}', [])
</script>

</@c.page>