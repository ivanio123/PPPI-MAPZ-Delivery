<#import "/spring.ftl" as spring/>

<#macro sorting formId submitButtonId options order>
<#assign
orderProperty = order.getProperty()
orderDirection = order.getDirection().toString()

>
<script src="/static/js/formSubmit.js"></script>
<div class="form-group row">
    <div class="col-2">
        <label class="col-form-label"><@spring.message "lang.sorting"/></label>
    </div>
    <div class="col-5">
        <select class="form-select" name="sort" id="sortCriterionSelect">
            <#list options as optionArr>
                <option value="${optionArr[0]}" id="${optionArr[1]}"><@spring.message "${optionArr[2]}"/></option>
                <script>setSortOptionSelected('${optionArr[1]}', '${orderProperty}')</script>
            </#list>
        </select>
    </div>
    <div class="col-5">
        <select class="form-select" id="sortOrderSelect">
            <option value="asc" id="ascOption"><@spring.message "sort.order-asc"/></option>
            <option value="desc" id="descOption"><@spring.message "sort.order-desc"/></option>
            <#list ['ascOption', 'descOption'] as id>
                <script>setSortOptionSelected('${id}', '${orderDirection}')</script>
            </#list>
        </select>
    </div>
</div>
<script>

    function setSortOptionOrderHandler(){
        setSortOptionOrder('sortOrderSelect', 'sortCriterionSelect')
    }

    function submitFormHandler(){
        formSubmit('${formId}')
    }

    function clickSubmitButtonHandler(){
        clickSubmitButton('${submitButtonId}')
    }

    form = document.getElementById('${formId}')
    form.addEventListener("submit", setSortOptionOrderHandler, false)

    sortCriterionSelect.addEventListener("change", clickSubmitButtonHandler, false)
    sortOrderSelect.addEventListener("change", clickSubmitButtonHandler, false)

</script>
</#macro>

<#macro directionFilter senderCity receiverCity>
<script src="/static/js/formSubmit.js"></script>
<div class="form-group row">

    <div class="col-2">
        <label class="col-form-label"><@spring.message "lang.direction"/></label>
    </div>

    <div class="col-4">
        <input type="text" class="form-control" name="senderCityName" id="senderCityNameId" value="${senderCity}" placeholder="<@spring.message "lang.from-city"/>">
    </div>

    <div class="col-4">
        <input type="text" class="form-control" name="receiverCityName" id="receiverCityNameId" value="${receiverCity}" placeholder="<@spring.message "lang.to-city"/>">
    </div>

    <div class="col-2">
        <button class="btn btn-primary" type="submit"><@spring.message "lang.filter"/></button>
    </div>

</div>
</#macro>

<#macro address selectCityName inputStreetName inputHouseNumberName selectCityId inputStreetId inputHouseNumberId streetNameErrorMessage houseNumberErrorMessage selectedCityId enteredStreet enteredHouseNumber>

<div class="row">
    <div class="col-5">
        <label class="col-form-label"><@spring.message "lang.city"/></label>
    </div>
    <div class="col">
        <select class="form-select" name="${selectCityName}" id="${selectCityId}">
            <#if cities??>
                <#list cities as city>
                    <option value="${city.id}" <#if city.id == selectedCityId>selected</#if>>${city.name}</option>
                </#list>
            </#if>
    </select>
</div>
</div>
<div class="row mt-2">
    <div class="col-5">
        <label class="col-form-label"><@spring.message "lang.street"/></label>
    </div>
    <div class="col validation-container" <#if streetNameErrorMessage != "" >data-error=${streetNameErrorMessage}</#if>>
        <input class="form-control" name="${inputStreetName}" id="${inputStreetId}" value="${enteredStreet!""}">
    </div>
</div>
<div class="row mt-2">
    <div class="col-5">
        <label class="col-form-label"><@spring.message "lang.house-number"/></label>
    </div>
    <div class="col validation-container" <#if houseNumberErrorMessage != "">data-error=${houseNumberErrorMessage}</#if>>
        <input class="form-control" name="${inputHouseNumberName}" id="${inputHouseNumberId}" value="${enteredHouseNumber!""}">
    </div>
</div>

</#macro>

<#macro applicationUpdate app>

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
                senderCityId = app.senderAddress.city.id!""
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
            receiverCityId = app.receiverAddress.city.id!""
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
<div class="row mb-3">
    <h2 class="d-flex justify-content-center"><@spring.message "lang.baggage"/></h2>
</div>

<div class="ms-4 me-4">

    <div class="row mb-2">
        <div class="col-3">
            <label class="fs-5 fw-bolder me-2"><@spring.message "lang.type"/>:</label>
        </div>
        <div class="col-3">
            <select class="form-select" name="deliveredBaggageRequest.type" id="baggageTypeSelect">
                <#if baggageTypes??>
                    <#list baggageTypes as baggageType>
                        <option value="${baggageType}"><@spring.message "baggage.type.${baggageType}"/></option>
                    </#list>
                </#if>
            </select>
        </div>
    </div>

    <div class="row mb-2">
        <div class="col">
            <div class="row">
                <div class="col-6">
                    <label class="fs-5 fw-bolder me-2"><@spring.message "lang.volume"/>:</label>
                </div>
                <div class="col-4">
                    <input type="text" class="form-control" name="deliveredBaggageRequest.volume" value="${volume!""}">
                </div>
                <div class="col-1">
                    <label><@spring.message "lang.cm"/><sup><small>3</small></sup></label>
                </div>
            </div>
        </div>
        <div class="col">
            <div class="row">
                <div class="col">
                    <label class="fs-5 fw-bolder me-2"><@spring.message "lang.weight"/>:</label>
                </div>
                <div class="col">
                    <input type="text" class="form-control" name="deliveredBaggageRequest.weight" value="${weight!""}">
                </div>
                <div class="col">
                    <label><@spring.message "lang.kg"/></label>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-3">
            <label class="fs-5 fw-bolder me-2"><@spring.message "lang.description"/>:</label>
        </div>
        <div class="col">
            <input type="text" class="form-control" name="deliveredBaggageRequest.description" value="${description!""}">
        </div>
    </div>
</div>

<hr>
<div class="row">
    <h2 class="d-flex justify-content-center"><@spring.message "lang.address"/></h2>
</div>

<div class="ms-4 me-4">
    <div class="row mb-2">
        <div class="col align-self-center">
            <label class="fs-5 fw-bolder me-2"><@spring.message "lang.sender"/>:</label>
        </div>

        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.city"/>">
            <select name="senderAddress.cityId" id="senderCitySelect" class="form-select">
                <#if cities??>
                    <#list cities as city>
                        <option value="${city.id}" <#if senderCityId?? && senderCityId == city.id> selected </#if>>${city.name}</option>
                    </#list>
                </#if>
            </select>
        </div>

        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.street"/>">
            <input class="form-control" type="text" name="senderAddress.streetName" value="${senderStreet!""}">
        </div>
        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.house-number"/>">
            <input class="form-control" type="text" name="senderAddress.houseNumber" value="${senderHouseNumber!""}">
        </div>
    </div>
    <div class="row">
        <div class="col align-self-center">
            <label class="fs-5 fw-bolder me-2"><@spring.message "lang.receiver"/>:</label>
        </div>
        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.city"/>">
            <select name="receiverAddress.cityId" id="receiverCitySelect" class="form-select">
                <#if cities??>
                    <#list cities as city>
                        <option value="${city.id}" <#if receiverCityId?? && receiverCityId == city.id> selected </#if>>${city.name}</option>
                    </#list>
                </#if>
            </select>
        </div>
        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.street"/>">
            <input class="form-control" type="text" name="receiverAddress.streetName" value="${receiverStreet!""}">
        </div>
        <div class="col pseudo-element" pseudo-content="<@spring.message "lang.house-number"/>">
            <input class="form-control" type="text" name="receiverAddress.houseNumber"  value="${receiverHouseNumber!""}">
        </div>
    </div>
</div>

<hr>
<div class="ms-4 me-4">
    <div class="row mb-2">
        <div class="col-4">
            <label class="fs-5 fw-bolder me-2"><@spring.message "delivery-application.page.sending-date"/>:</label>
        </div>
        <div class="col-4">
            <input type="date" class="form-control" name="sendingDate" value="${sendingDate!""}">
        </div>
    </div>
    <div class="row">
        <div class="col-4">
            <label class="fs-5 fw-bolder me-2"><@spring.message "delivery-application.page.receiving-date"/>:</label>
        </div>
        <div class="col-4">
            <input type="date" class="form-control" name="receivingDate" value="${receivingDate!""}">
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