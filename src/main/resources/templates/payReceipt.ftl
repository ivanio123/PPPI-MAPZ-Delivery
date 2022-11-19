<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Pay Receipt">
    <script src="/static/js/localization.js"></script>
    <#if receipt??>
    <form action="/receipt/${receipt.id}/pay" method="post">
        <input name="_csrf" value="${_csrf.token}" hidden>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card" style="min-width: 480px;">
                    <div class="card-body">
                        <h1 class="d-flex justify-content-center"><@spring.message "delivery.application.receipt.pay-receipt"/></h1>
                        <hr>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><@spring.message "delivery.application"/>:</label>
                            </div>
                            <div class="col">
                                <label><a href="${refApplication}/${receipt.application.id}">#${receipt.application.id}</a></label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><@spring.message "lang.customer"/>:</label>
                            </div>
                            <div class="col">
                                <label>${receipt.customer.name} ${receipt.customer.surname}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><@spring.message "lang.manager"/>:</label>
                            </div>
                            <div class="col">
                                <label>${receipt.manager.name} ${receipt.manager.surname}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><@spring.message "delivery.application.receipt.formation-date"/>:</label>
                            </div>
                            <div class="col">
                                <label>${receipt.formationDate}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><@spring.message "lang.price"/>:</label>
                            </div>
                            <div class="col">
                                <label>${receipt.totalPrice} <@spring.message "lang.UAH"/></label>
                            </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col d-flex justify-content-center">
                                <button class="btn btn-success" type="submit"><@spring.message "lang.pay"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    </#if>
    <script>addSwitchLanguageWithUrlClickListeners('${url}', [])</script>
</@c.page>