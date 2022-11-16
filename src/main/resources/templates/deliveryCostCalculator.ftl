<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#import "/spring.ftl" as spring/>
<#include "parts/references.ftl">

<@c.page "Delivery Cost Calculator">
    <#setting locale="en_US">
    <#if distance??>
    <#assign
        selectedCitySenderId = distance.cityFrom.id
        selectedCityReceiverId = distance.cityTo.id
    >
    <#elseif calculatorRequest??>
    <#assign
        selectedCitySenderId = calculatorRequest.cityFromId!""
        selectedCityReceiverId = calculatorRequest.cityToId!""
    >
    </#if>

    <#if calculatorRequest??>
    <#assign
        weight = calculatorRequest.weight!""
    >
        <#if calculatorRequest.dimensions??>
        <#assign
            length = calculatorRequest.dimensions.length!""
            width = calculatorRequest.dimensions.width!""
            height = calculatorRequest.dimensions.height!""
        >
        </#if>
    </#if>

    <#if .data_model["dimensions.lengthErrorMessage"]??>
    <#assign
        lengthErrorMessage = .data_model["dimensions.lengthErrorMessage"]
    >
    </#if>

    <#if .data_model["dimensions.widthErrorMessage"]??>
    <#assign
        widthErrorMessage = .data_model["dimensions.widthErrorMessage"]
    >
    </#if>

    <#if .data_model["dimensions.heightErrorMessage"]??>
    <#assign
        heightErrorMessage = .data_model["dimensions.heightErrorMessage"]
    >
    </#if>

    <#if .data_model["widthErrorMessage"]??>
    <#assign
        widthErrorMessage = .data_model["widthErrorMessage"]
    >
    </#if>

    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>
    <script src="/static/js/validationError.js"></script>
    <script src="/static/js/deliveryCostCalculatorValidation.js"></script>

    <@n.infoPills "Calculator"/>

    <div class="row mb-2">
        <div class="col d-flex justify-content-center">
            <h1><@spring.message "delivery-cost-calculator.head"/></h1>
        </div>
    </div>

    <form action="${refDeliveryCostCalculator}" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label"><@spring.message "lang.route"/></label>
            </div>

            <div class="col-5 validation-container" id="routeSelectCol" <#if invalidCityDirectionErrorMessage??>data-error="${invalidCityDirectionErrorMessage}"</#if>>
                <div class="form-group row">
                    <div class="col">
                        <select class="form-select" name="cityFromId" id="cityFromSelect">
                            <#if cities??>
                                <#list cities as citySender>
                                    <#assign citySenderId = citySender.id>
                                    <option value="${citySender.id}" id="citySender${citySenderId}">${citySender.name}</option>
                                    <script>selectOption('citySender${citySenderId}', '${citySenderId}'==='${selectedCitySenderId!""}')</script>
                                </#list>
                            </#if>
                        </select>
                    </div>

                    <div class="col">
                        <select class="form-select" name="cityToId" id="cityToSelect">
                            <#if cities??>
                                <#list cities as cityReceiver>
                                    <#assign cityReceiverId = cityReceiver.id>
                                    <option value="${cityReceiver.id}" id="cityReceiver${cityReceiverId}">${cityReceiver.name}</option>
                                    <script>selectOption('cityReceiver${cityReceiverId}', '${cityReceiverId}' === '${selectedCityReceiverId!""}')</script>
                                </#list>
                            </#if>
                        </select>
                    </div>
                </div>
            </div>

        </div>

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label"><@spring.message "lang.dimensions"/></label>
            </div>

            <div class="col-5">
                <div class="form-group row">
                    <div class="col validation-container" <#if lengthErrorMessage??>data-error="${lengthErrorMessage}!"</#if>>
                        <input class="form-control" type="text" id="lengthInput" name="dimensions.length" value="${length!""}" placeholder="<@spring.message "placeholder.length"/>">
                    </div>

                    <div class="col validation-container" <#if widthErrorMessage??>data-error="${widthErrorMessage}!"</#if>>
                        <input class="form-control" type="text" id="widthInput" name="dimensions.width" value="${width!""}" placeholder="<@spring.message "placeholder.width"/>">
                    </div>

                    <div class="col validation-container" <#if heightErrorMessage??>data-error="${heightErrorMessage}!"</#if>>
                        <input class="form-control" type="text" id="heightInput" name="dimensions.height" value="${height!""}" placeholder="<@spring.message "placeholder.height"/>">
                    </div>

                    <div class="col">
                        <label class="col-form-label"><@spring.message "lang.cm"/></label>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group row d-flex justify-content-center mt-4">
            <div class="col-2">
                <label class="col-form-label"><@spring.message "lang.weight"/></label>
            </div>

            <div class="col-5">
                <div class="form-group row">
                    <div class="col-3 validation-container" <#if weightErrorMessage??>data-error="${weightErrorMessage}!"</#if>>
                        <input class="form-control" type="text" id="weightInput" name="weight" value="${weight!""}" placeholder="<@spring.message "placeholder.weight"/>">
                    </div>

                    <div class="col">
                        <label class="col-form-label"><@spring.message "lang.kg"/></label>
                    </div>
                </div>
            </div>
        </div>

        <div class="form-group row mt-4">
            <div class="d-flex justify-content-center">
                <button class="btn btn-primary" type="submit"><@spring.message "delivery-cost-calculator.calculate-button"/></button>
            </div>
        </div>
    </form>

    <#if cost??>
    <div class="row" id="costDeliveryRow">
        <div class="alert alert-success col-auto" role="alert" id="costDeliveryContent">
            <@spring.message "lang.delivery-cost"/> ${cost} <@spring.message "lang.UAH"/>
        </div>
    </div>
    <a class="btn btn-primary" data-bs-toggle="collapse" href="#details" role="button" aria-expanded="false" aria-controls="details">
        <@spring.message "lang.show-me-more"/>
    </a>
    </#if>

    <div class="mt-4 collapse" id="details">

        <#if distance??>
        <div class="row" id="routeRow">
            <div class="alert alert-primary col-auto" role="alert" id="routeAlert">
                <@spring.message "lang.route"/>:
                <#list distance.route as city>
                ${city.name} <#sep> - </#sep>
                </#list>
            </div>
        </div>

        <div class="row" id="distanceRow">
            <div class="alert alert-primary col-auto" role="alert" id="distanceAlert">
                <@spring.message "lang.distance"/> ${distance.distance} <@spring.message "lang.km"/>
            </div>
        </div>
        </#if>

    </div>

    <script>
        let inputs = ['lengthInput', 'widthInput', 'heightInput', 'weightInput', 'cityFromSelect', 'cityToSelect']
        let url = '${refDeliveryCostCalculator}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>

    <script>addRemoveErrorAttributeListener()</script>
    <script>

        addRemoveDataErrorTagSelectCityListeners('cityFromSelect', 'cityToSelect', ()=>removeDataErrorTag('routeSelectCol'))
    </script>
</@c.page>