<#import "parts/common.ftl" as c>
<#include "parts/references.ftl">

<@c.page "Applications Review">

    <script src="/static/js/localization.js"></script>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1>Applications Review</h1>
        </div>
    </div>

    <script>
        let inputs = []
        let url = '${refApplicationsReview}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>

</@c.page>