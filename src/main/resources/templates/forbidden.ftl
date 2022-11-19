<#import "parts/common.ftl" as c>
<#import "/spring.ftl" as spring/>

<@c.page "Forbidden">
<script src="/static/js/localization.js"></script>
<div class="row">
    <div class="col d-flex justify-content-center">
        <div class="row">
            <h1 class="d-flex justify-content-center mb-4"><@spring.message "lang.forbidden"/></h1>
            <div class="alert alert-danger">
                <@spring.message "forbidden.no-permission"/>
            </div>
        </div>
    </div>
</div>
<script>addSwitchLanguageWithUrlClickListeners('${url}', [])</script>
</@c.page>