<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>
<#import "parts/layouts.ftl" as l>
<#import "parts/forms.ftl" as f>

<#include "parts/references.ftl">
<#include "parts/security.ftl">

<@c.page "Update Application">

<#setting locale="en_US">
<#setting number_format="computer">

<div class="row mb-2">
    <div class="col">
        <h1 class="d-flex justify-content-center"><@spring.message "lang.update"/></h1>
    </div>
</div>


<script src="/static/js/localization.js"></script>

<div class="row">
    <div class="col d-flex justify-content-center">
        <div class="card" style="min-width: 740px;">
            <div class="card-body">
                <form action="${url}" method="post" id="updateApplicationForm">
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                    <#if application??>
                        <#assign
                            applicationId = application.id!""
                        >
                        <@f.applicationUpdate application/>

                        <#if application.customer??>
                            <@l.customerInfo application.customer/>
                        </#if>
                    </#if>
                    <div class="row mt-4">
                        <div class="col d-flex justify-content-center">
                            <div class="col-auto me-4">
                                <button class="btn btn-outline-success" type="submit"><@spring.message "lang.update"/></button>
                            </div>
                            <div class="col-auto">
                                <a class="btn btn-outline-primary" href="/application/${applicationId}" type="submit"><@spring.message "lang.cancel"/></a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    addSwitchLanguageWithUrlClickListeners('${url}', [])
</script>
</@c.page>