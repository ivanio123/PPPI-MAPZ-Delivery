<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<@c.page "Transaction Failed">
<#setting locale="en_US">
<#setting number_format="computer">
<script src="/static/js/localization.js"></script>
<div class="row">
    <div class="col d-flex justify-content-center">
        <div class="row">
            <h1 class="d-flex justify-content-center mb-4"><@spring.message "payment.transaction.failed"/></h1>
            <#if payingErrorMessage??>
            <div class="alert alert-danger">
                ${payingErrorMessage}
            </div>
            </#if>
        </div>
        <input name="rejectedFunds" id="rejectedFundsHiddenInput" value="${rejectedFunds!""}" hidden>
        <input name="price" id="priceHiddenInput" value="${price!""}" hidden>
    </div>
</div>
<script>addSwitchLanguageWithUrlClickListeners('${url}', ['rejectedFundsHiddenInput', 'priceHiddenInput'])</script>
</@c.page>