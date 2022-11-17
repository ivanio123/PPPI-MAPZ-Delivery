<#import "/spring.ftl" as spring/>

<#macro application app>

    <#if app.deliveredBaggage??>
        <#assign
        baggage = app.deliveredBaggage
        type = baggage.type!""
        volume = baggage.volume!""
        weight = baggage.weight!""
        description = baggage.description!""
        >
    </#if>

    <#if app.senderAddress??>
        <#assign
        senderAddress = app.senderAddress
        senderStreet = senderAddress.street!""
        senderHouseNumber = senderAddress.houseNumber!""
        >
        <#if app.senderAddress.city??>
            <#assign
            senderCityName = app.senderAddress.city.name!""
            >
        </#if>
    </#if>

    <#if app.receiverAddress??>
        <#assign
        receiverAddress = app.receiverAddress
        receiverStreet = receiverAddress.street!""
        receiverHouseNumber = receiverAddress.houseNumber!""
        >
        <#if app.receiverAddress.city??>
            <#assign
            receiverCityName = app.receiverAddress.city.name!""
            >
        </#if>
    </#if>

    <#if app.sendingDate??>
        <#assign
        sendingDate = app.sendingDate!""
        >
    </#if>

    <#if app.receivingDate??>
        <#assign
        receivingDate = app.receivingDate!""
        >
    </#if>

    <div class="row">
        <h1 class="d-flex justify-content-center"><@spring.message "delivery-application.page.head"/> #${app.id!""}</h1>
    </div>
    <hr>
    <div class="row">
        <h2 class="d-flex justify-content-center"><@spring.message "lang.baggage"/></h2>
    </div>

    <div class="ms-4 me-4">

        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.type"/>:</label> <label><#if type??><@spring.message "baggage.type.${type}"/></#if></label>
            </div>
        </div>

        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.volume"/>:</label> <label>${volume!""} <@spring.message "lang.cm"/><sup><small>3</small></sup></label>
            </div>
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.weight"/>:</label> <label>${weight!""} <@spring.message "lang.kg"/></label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.description"/>:</label> <label>${description!""}</label>
            </div>
        </div>
    </div>

    <hr>
    <div class="row">
        <h2 class="d-flex justify-content-center"><@spring.message "lang.address"/></h2>
    </div>

    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.sender"/>:</label> <label><#if senderCityName??><@spring.message "city.${senderCityName}"/></#if>, <@spring.message "address.street"/> ${senderStreet!""}, <@spring.message "address.house"/> ${senderHouseNumber!""}</label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.receiver"/>:</label> <label><#if receiverCityName??><@spring.message "city.${receiverCityName}"/></#if>, <@spring.message "address.street"/> ${receiverStreet!""}, <@spring.message "address.house"/> ${receiverHouseNumber!""}</label>
            </div>
        </div>
    </div>

    <hr>
    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col-4">
                <label class="fs-5 fw-bolder me-2"><@spring.message "delivery-application.page.sending-date"/>:</label>
            </div>
            <div class="col-2">
                <label>${sendingDate!""}</label>
            </div>
        </div>
        <div class="row">
            <div class="col-4">
                <label class="fs-5 fw-bolder me-2"><@spring.message "delivery-application.page.receiving-date"/>:</label>
            </div>
            <div class="col-2">
                <label>${receivingDate!""}</label>
            </div>
        </div>
    </div>

    <hr>

    <div class="ms-4 me-4">
        <div class="row mb-2">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "delivery-application.state"/>:</label> <label><@spring.message "delivery-application.state.${app.state}"/></label>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <label class="fs-5 fw-bolder me-2"><@spring.message "lang.price"/>:</label> <label>${app.price!""} <@spring.message "lang.UAH"/></label>
            </div>
        </div>
    </div>
    <hr>

</#macro>