<#import "parts/common.ftl" as c/>
<#import "/spring.ftl" as spring/>
<#import "parts/forms.ftl" as f/>

<#include "parts/references.ftl">


<@c.page "Make Application">
    <#setting locale="en_US">
    <#setting number_format="computer">

<#if .data_model["deliveredBaggageRequest.volumeErrorMessage"]??>
    <#assign volumeErrorMessage = .data_model["deliveredBaggageRequest.volumeErrorMessage"]>
</#if>

<#if .data_model["deliveredBaggageRequest.weightErrorMessage"]??>
    <#assign weightErrorMessage = .data_model["deliveredBaggageRequest.weightErrorMessage"]>
</#if>

<#if .data_model["senderAddress.streetNameErrorMessage"]??>
    <#assign senderStreetErrorMessage = .data_model["senderAddress.streetNameErrorMessage"]>
</#if>

<#if .data_model["senderAddress.houseNumberErrorMessage"]??>
    <#assign senderHouseNumberErrorMessage = .data_model["senderAddress.houseNumberErrorMessage"]>
</#if>

<#if .data_model["receiverAddress.streetNameErrorMessage"]??>
    <#assign receiverStreetErrorMessage = .data_model["receiverAddress.streetNameErrorMessage"]>
</#if>

<#if .data_model["receiverAddress.houseNumberErrorMessage"]??>
    <#assign receiverHouseNumberErrorMessage = .data_model["receiverAddress.houseNumberErrorMessage"]>
</#if>

<#if .data_model["sendingDateErrorMessage"]??>
    <#assign sendingDateErrorMessage = .data_model["sendingDateErrorMessage"]>
</#if>

<#if .data_model["receivingDateErrorMessage"]??>
    <#assign receivingDateErrorMessage = .data_model["receivingDateErrorMessage"]>
</#if>

<#if deliveryApplicationRequest??>
    <#if deliveryApplicationRequest.deliveredBaggageRequest??>
        <#assign
            deliveredBaggage = deliveryApplicationRequest.deliveredBaggageRequest
            volume = deliveredBaggage.volume!""
            weight = deliveredBaggage.weight!""
            selectedType = deliveredBaggage.type!""
            description = deliveredBaggage.description!""
        >
    </#if>

    <#if deliveryApplicationRequest.senderAddress??>
        <#assign
                senderAddress = deliveryApplicationRequest.senderAddress
                senderCityId = senderAddress.cityId!""
                senderStreetName = senderAddress.streetName!""
                senderHouseNumber = senderAddress.houseNumber!""
        >
    </#if>

    <#if deliveryApplicationRequest.receiverAddress??>
        <#assign
                receiverAddress = deliveryApplicationRequest.receiverAddress
                receiverCityId = receiverAddress.cityId!""
                receiverStreetName = receiverAddress.streetName!""
                receiverHouseNumber = receiverAddress.houseNumber!""
        >
    </#if>

    <#if deliveryApplicationRequest.sendingDate??>
        <#assign
            sendingDate = deliveryApplicationRequest.sendingDate
        >
    </#if>

    <#if deliveryApplicationRequest.receivingDate??>
        <#assign
            receivingDate = deliveryApplicationRequest.receivingDate
        >
    </#if>

</#if>

    <script src="/static/js/localization.js"></script>
    <script src="/static/js/validationError.js"></script>

    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1><@spring.message "make-delivery-application.head"/></h1>
        </div>
    </div>

    <div class="row d-flex justify-content-center mt-4" >
        <form action="${refCreateApp}" method="post" style="max-width: 920px;">
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <div class="card">
                <div class="card-body">
                    <div class="form-group row">
                        <div class="col d-flex justify-content-center mb-2">
                            <h2><@spring.message "lang.baggage"/></h2>
                        </div>
                    </div>
                    <div class="form-group row justify-content-center mt-4 mb-2">
                        <div class="col-5 me-4">
                            <div class="row">
                                <div class="col-4">
                                    <label class="col-form-label"><@spring.message "lang.volume"/></label>
                                </div>
                                <div class="col validation-container" <#if volumeErrorMessage??>data-error="${volumeErrorMessage}"</#if>>
                                    <input class="form-control" name="deliveredBaggageRequest.volume" id="volumeInput" type="text" value="${volume!""}">
                                </div>
                                <div class="col-2">
                                    <label class="col-form-label"><@spring.message "lang.cm"/><sup><small>3</small></sup></label>
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-4">
                                    <label class="col-form-label"><@spring.message "lang.weight"/></label>
                                </div>
                                <div class="col validation-container" <#if weightErrorMessage??>data-error="${weightErrorMessage}"</#if>>
                                    <input class="form-control" name="deliveredBaggageRequest.weight" id="weightInput" type="text" value="${weight!""}">
                                </div>
                                <div class="col-2">
                                    <label class="col-form-label"><@spring.message "lang.kg"/></label>
                                </div>
                            </div>

                        </div>

                        <div class="col-5">
                            <div class="row">
                                <div class="col-4">
                                    <label class="col-form-label"><@spring.message "lang.type"/></label>
                                </div>
                                <div class="col">
                                    <select class="form-select" name="deliveredBaggageRequest.type" id="baggageTypeSelect" >
                                    <#if types??>
                                        <#list types as type>
                                            <option value="${type}" id="type${type?index}" <#if type == selectedType!"">selected</#if>><@spring.message "baggage.type.${type}"/></option>
                                        </#list>
                                    </#if>
                                    </select>
                                </div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-4">
                                    <label class="col-form-label"><@spring.message "lang.description"/></label>
                                </div>
                                <div class="col">
                                    <input class="form-control" name="deliveredBaggageRequest.description" id="descriptionInput" type="text" value="${description!""}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h2><@spring.message "lang.address"/></h2>
                        </div>
                    </div>

                    <div class="form-group row justify-content-center mt-2 mb-2">
                        <div class="col me-4">
                            <div class="row mb-4">
                                <h3 class="d-flex justify-content-center"><@spring.message "lang.sending"/></h3>
                            </div>
                            <@f.address 'senderAddress.cityId', 'senderAddress.streetName', 'senderAddress.houseNumber',
                            'senderAddressSelect', 'senderStreetInput', 'senderHouseNumberInput',
                            senderStreetErrorMessage!"", senderHouseNumberErrorMessage!"",
                            senderCityId!-1, senderStreetName!"", senderHouseNumber!""
                            />
                        </div>

                        <div class="col ms-4">
                            <div class="row mb-4">
                                <h3 class="d-flex justify-content-center"><@spring.message "lang.receiving"/></h3>
                            </div>
                            <@f.address 'receiverAddress.cityId', 'receiverAddress.streetName', 'receiverAddress.houseNumber',
                            'receiverAddressSelect', 'receiverStreetInput', 'receiverHouseNumberInput',
                            receiverStreetErrorMessage!"", receiverHouseNumberErrorMessage!"",
                            receiverCityId!-1, receiverStreetName!"", receiverHouseNumber!""
                            />
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center mb-2">
                            <h2><@spring.message "lang.date"/></h2>
                        </div>
                    </div>
                    <div class="form-group row justify-content-center mt-2 mb-2">
                        <div class="col-4 me-4">
                            <div class="row mb-2">
                                <h3 class="d-flex justify-content-center mb-4"><@spring.message "lang.sending"/></h3>

                                <div class="validation-container" <#if sendingDateErrorMessage??>data-error="${sendingDateErrorMessage}"</#if>>
                                    <input class="form-control" type="date" name="sendingDate" id="sendingDateInput" value="${sendingDate!""}">
                                </div>

                            </div>
                        </div>

                        <div class="col-4 ms-4">
                            <div class="row mb-2">
                                <h3 class="d-flex justify-content-center mb-4"><@spring.message "lang.receiving"/></h3>
                                <div class="validation-container" <#if receivingDateErrorMessage??>data-error="${receivingDateErrorMessage}"</#if>>
                                    <input class="form-control" type="date" name="receivingDate" id="receivingDateInput" value="${receivingDate!""}">
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group row">
                        <div class="d-flex justify-content-center">
                            <button class="btn btn-primary" type="submit"><@spring.message "make-delivery-application.apply-application"/></button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <script>
        let inputs = [
        'volumeInput', 'weightInput', 'descriptionInput', 'sendingDateInput', 'receivingDateInput',
        'baggageTypeSelect', 'senderAddressSelect', 'senderStreetInput', 'senderHouseNumberInput',
        'receiverAddressSelect', 'receiverStreetInput', 'receiverHouseNumberInput'
        ]

        let url = '${refCreateApp}'
        addSwitchLanguageWithUrlClickListeners(url, inputs)
    </script>

    <script>addRemoveErrorAttributeListener()</script>

</@c.page>
