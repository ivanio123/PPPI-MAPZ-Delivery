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
