<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<@c.page "Error">
    <script src="/static/js/localization.js"></script>
    <div class="row">
        <div class="col d-flex justify-content-center">
            <div class="row">
                <h1 class="d-flex justify-content-center mb-4"><@spring.message "lang.error"/></h1>
                <div class="alert alert-danger">
                    <#if errorMessage??>
                        ${errorMessage}
                    <#else>
                        <@spring.message "unknown-error"/>
                    </#if>

                </div>
            </div>
        </div>
    </div>
    <script>addSwitchLanguageWithUrlClickListeners('/error_page', [])</script>
</@c.page>