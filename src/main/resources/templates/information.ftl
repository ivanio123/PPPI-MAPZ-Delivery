<#import "parts/common.ftl" as c/>
<#import "/spring.ftl" as spring/>
<#import "parts/pager.ftl" as p/>
<#import "parts/forms.ftl" as f/>
<#import "parts/navtabs.ftl" as n/>

<@c.page "Information Page">
    <@n.infoPills "Directions"/>
    <div class="row">
        <div class="col d-flex justify-content-center">
            <h1><@spring.message "info.directions-head"/></h1>
        </div>
    </div>

    <div class="row mt-3">
        <div class="col-2">
            <!--<label>here could be your ads</label>-->
        </div>
        <div class="col-5">
            <form method="get" action="${url}" id="filterForm" onsubmit="">
                <button class="btn btn-primary" type="submit" id="submitButton" hidden>Submit</button>
                <#assign
                options = [
                ["senderCity.name", "senderCityOption", "info.sort-direction-sender-city"],
                ["receiverCity.name", "receiverCityOption", "info.sort-direction-receiver-city"],
                ["distance", "distanceOption", "info.sort-direction-distance"]
                ]
                >
                <div class="mb-3">
                    <@f.sorting "filterForm" "submitButton" options order />
                </div>

                <div class="mb-3">
                    <@f.directionFilter senderCity!"" receiverCity!"" />
                </div>

                <table class="table table-bordered">
                    <thead class="table-primary">
                    <tr>
                        <th><@spring.message "lang.directions"/></th>
                        <th><@spring.message "lang.distance"/>, <@spring.message "lang.km"/></th>
                    </tr>
                    </thead>
                    <tbody class="table-light">
                    <#list directionsPage.content as direction>
                    <tr>
                        <td>${direction.senderCity.name} - ${direction.receiverCity.name}</td>
                        <td>${direction.distance}</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
                <@p.pager url directionsPage 'submitButton'/>

                <input name="lang" value="${lang}" id="langInput" hidden>
                <script>

                    function clickSubmitButtonHandler(){
                       clickSubmitButton('submitButton')
                    }

                    langEnglish.addEventListener('click', ()=>{langInput.setAttribute('value', 'en')})
                    langUkrainian.addEventListener('click', ()=>{langInput.setAttribute('value', 'uk')})

                    langEnglish.addEventListener('click', clickSubmitButtonHandler)
                    langUkrainian.addEventListener('click', clickSubmitButtonHandler)

                </script>
            </form>
        </div>
    </div>

</@c.page>
