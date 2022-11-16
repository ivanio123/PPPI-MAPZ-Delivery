<#import "/spring.ftl" as spring/>

<#macro pager url page submitButtonId>
    <#if page.getTotalPages() gt 4>
        <#assign
            totalPages = page.getTotalPages()
            pageNumber = page.getNumber() + 1

            head = []
            head = (pageNumber == 2 || pageNumber == 4)?then([1], head)
            head = (pageNumber  > 4)?then([1, -1], head)

            tail = []
            tail = (pageNumber == totalPages - 3 || pageNumber == totalPages - 1)?then([totalPages], tail)
            tail = (pageNumber < totalPages - 3)?then([-1, totalPages], tail)

            bodyBefore = (pageNumber >= 3 && pageNumber <= totalPages)?then([pageNumber - 2, pageNumber - 1], [])
            bodyAfter = (pageNumber < totalPages - 1)?then([pageNumber + 1, pageNumber + 2], [])

            body = head + bodyBefore + [pageNumber] + bodyAfter + tail

        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>

    <script src="/static/js/formSubmit.js"></script>
    <div class="mt-2">
        <ul class="pagination">
            <li class="page-item disabled">
                <a class="page-link" href="#"><@spring.message "pagination.pages"/></a>
            </li>
            <#assign
                pageNumber = page.getNumber()
            >
            <#list body as p>
            <#if (p - 1) == page.getNumber()>
            <li class="page-item active">
                <a class="page-link" href="#" tabindex="-1">${p}</a>
            </li>
            <#elseif p == -1>
            <li class="page-item disabled">
                <a class="page-link" href="#" tabindex="-1">...</a>
            </li>
            <#else>
            <li class="page-item">
                <a class="page-link" href="#" role="button" tabindex="-1" id="page${p}">${p}</a>
                <script>
                    page = document.getElementById('page${p}')
                    page.addEventListener('click', ()=>{setPageNumber('${p-1}')})
                    page.addEventListener('click', ()=>{setPageSize('${page.getSize()}')})
                    page.addEventListener('click', ()=>{clickSubmitButton('${submitButtonId}')})
                </script>
            </li>
            </#if>
            </#list>
        </ul>
        <input name="page" id="pageNumber" value="${pageNumber}" hidden>
        <input name="size" id="pageSize" value="${page.getSize()}" hidden>
    </div>
</#macro>