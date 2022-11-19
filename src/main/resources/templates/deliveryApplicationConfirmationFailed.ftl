<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl">

<@c.page "Confirmation Failed">
<script src="/static/js/localization.js"></script>
<input name="applicationId" id="applicationIdHiddenInput" value="${applicationId!""}" hidden>
<div class="row">
    <div class="col d-flex justify-content-center">

        <div class="card" style="min-width: 480px;">
            <div class="card-body">
                <h2 class="d-flex justify-content-center"><@spring.message "delivery.application.confirmation.failed.head"/></h2>
                <hr>
                <#if applicationId??>
                <div class="row mb-2">
                    <div class="col">
                        <label><@spring.message "delivery.application.confirmation.failed.the-application"/> </label>
                        <a href="${refApplication}/${applicationId}">#${applicationId}</a>
                        <label><@spring.message "delivery.application.confirmation.failed.could-not-been-confirmed"/> </label>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label class="fw-bolder me-2"><@spring.message "delivery.application.confirmation.failed.application-state"/>:</label>
                        <label><@spring.message "delivery-application.state.${application.state}"/></label>
                    </div>
                </div>
                <hr>
                </#if>
                <div class="row">
                    <div class="col d-flex justify-content-center">
                        <a class="btn btn-outline-success" href="${refApplicationsReview}"><@spring.message "lang.OK"/> </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>addSwitchLanguageWithUrlClickListeners('${url}', ['applicationIdHiddenInput'])</script>
</@c.page>