<#import "parts/common.ftl" as c>
<#import "parts/layouts.ftl" as l>
<#import "/spring.ftl" as spring/>

<#include "parts/references.ftl"/>

<@c.page "Receipt">
    <script src="/static/js/localization.js"></script>
    <#if receipt??>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card">
                    <div class="card-body" style="min-width: 480px;">
                        <h1 class="d-flex justify-content-center"><@spring.message "delivery.application.receipt.head"/></h1>
                        <hr>
                        <@l.deliveryReceipt receipt/>
                        <div class="row mt-4">
                            <div class="col d-flex justify-content-center">
                                <a class="btn btn-success" href="${refProfile}"><@spring.message "lang.OK"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </#if>
    <script>addSwitchLanguageWithUrlClickListeners('${url}', [])</script>
</@c.page>