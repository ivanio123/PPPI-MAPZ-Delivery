<#import "parts/common.ftl" as c/>
<#import "parts/navtabs.ftl" as n/>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p/>
<#include "parts/references.ftl">
<#include "parts/security.ftl">

<@c.page "Profile">

    <#assign
    activePill = activePill!""
    notificationsPill = "pills-notifications-tab"
    applicationsPill = "pills-applications-tab"
    >

    <script src="/static/js/localization.js"></script>

    <div class="row">
        <div class="col-3">
            <h1 class="d-flex justify-content-center mb-4">Profile</h1>

            <ul class="list-group">
                <li class="list-group-item">
                    Login: ${user.login}
                </li>
                <li class="list-group-item">
                    Name: ${user.name!"-"}
                </li>
                <li class="list-group-item">
                    Surname: ${user.surname!"-"}
                </li>
                <li class="list-group-item">
                    Phone: ${user.phone!"-"}
                </li>
                <li class="list-group-item">
                    Email: ${user.email!"-"}
                </li>
                <li class="list-group-item">
                    Balance: ${user.cache!"0.0"} UAH
                </li>
            </ul>
        </div>
        <div class="col">
            <form action="${url}" method="get">
                <input name="lang" value="${lang!"en"}" id="langInput" hidden>
                <button type="submit" id="submitButton" hidden></button>
                <input name="activePill" id="activePillHiddenInput" value="${activePill!""}" hidden>
                <div class="row">
                    <ul class="nav nav-pills justify-content-center mt-2 mb-4" id="profileMenuItems">
                        <li class="nav-item">
                            <button class="nav-link <#if activePill == notificationsPill>active</#if>" id="pills-notifications-tab" data-bs-toggle="pill" data-bs-target="#pills-notifications" type="button" role="tab" aria-controls="pills-notifications" aria-selected="<#if activePill == notificationsPill>true<#else>false</#if>" >Notifications</button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link <#if activePill == applicationsPill>active</#if>" id="pills-applications-tab" data-bs-toggle="pill" data-bs-target="#pills-applications" type="button" role="tab" aria-controls="pills-applications" aria-selected="<#if activePill == applicationsPill>true<#else>false</#if>">Applications</button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="pills-receipts-tab">Receipts</button>
                        </li>
                    </ul>
                    <div class="tab-content" id="pills-tab-content">
                        <div class="tab-pane fade <#if activePill == notificationsPill>active show</#if>" id="pills-notifications" role="tabpanel" aria-labelledby="pills-notifications-tab">
                            There is no notifications for you yet.
                        </div>
                        <div class="tab-pane fade <#if activePill == applicationsPill>active show</#if>" id="pills-applications" role="tabpanel" aria-labelledby="pills-applications-tab">
                            <#list applications.content as application>
                                <#if application.deliveredBaggage??>
                                    <#assign
                                        baggage = application.deliveredBaggage
                                    >
                                </#if>
                                <div class="row alert alert-primary mb-2">
                                    <div class="col-1">
                                        #${application.id}
                                    </div>
                                    <div class="col">
                                        ${application.senderAddress.city.name} - ${application.receiverAddress.city.name}
                                    </div>
                                    <div class="col-auto">
                                        ${application.sendingDate} - ${application.sendingDate}
                                    </div>
                                    <div class="col-2">
                                        ${application.price} UAH
                                    </div>
                                    <div class="col-2">
                                        ${application.state}
                                    </div>
                                </div>

                            </#list>
                            <@p.pager url applications 'submitButton'/>
                        </div>

                    </div>
                </div>
            </form>
        </div>
    </div>


    <script>
        function clickSubmitButtonHandler(){
            clickSubmitButton('submitButton')
        }

        addEventListeners(clickSubmitButtonHandler)
    </script>

    <script>
        let childNodes = document.getElementById('profileMenuItems').childNodes;

        for(let child of childNodes){
            console.log(child)
            child.addEventListener('click', ()=>{
                let el = document.querySelector("ul[id=profileMenuItems] li.nav-item button.nav-link.active")
                activePillHiddenInput.setAttribute('value', el.id)
                console.log('id:' + el.id)
            })
        }

    </script>
</@c.page>
