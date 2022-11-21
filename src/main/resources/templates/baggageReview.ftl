<#include "parts/references.ftl">
<#include "parts/security.ftl">

<#import "parts/common.ftl" as c>
<#import "parts/authorization.ftl" as auth>
<#import "/spring.ftl" as spring/>

<#setting number_format="computer">

<@c.page "Baggage Review">
    <script src="/static/js/formSubmit.js"></script>
    <script src="/static/js/localization.js"></script>

    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

        // Load the Visualization API and the corechart package.
        google.charts.load('current', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.charts.setOnLoadCallback(drawChart);

        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

            // Create the data table.
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Topping');
            data.addColumn('number', 'Slices');
            data.addRows([
                <#list amountBaggagePerTypeReport as baggagePerType>
                ['<@spring.message "baggage.type.${baggagePerType.type}"/>', ${baggagePerType.amount}]<#sep>,</#sep>
                </#list>
            ]);
            // Set chart options
            var options = {
                'title':'<@spring.message "report.baggage.pie.chart.head"/>',
                'width':500,
                'height':300,
                'chartArea':{'width':'90%'}
            };

            // Instantiate and draw our chart, passing in some options.
            var chart = new google.visualization.PieChart(document.getElementById('pie_chart'));
            chart.draw(data, options);
        }
    </script>

    <script>
        google.charts.load("current", {packages:["corechart"]});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ["Baggage type", "<@spring.message "lang.profit"/>", { role: "style" } ],
                ["<@spring.message "baggage.type.${profitBaggagePerTypeReport[0].type}"/>", ${profitBaggagePerTypeReport[0].profit}, "#b87333"],
                ["<@spring.message "baggage.type.${profitBaggagePerTypeReport[1].type}"/>", ${profitBaggagePerTypeReport[1].profit}, "silver"],
                ["<@spring.message "baggage.type.${profitBaggagePerTypeReport[2].type}"/>", ${profitBaggagePerTypeReport[2].profit}, "#be0000"],
                ["<@spring.message "baggage.type.${profitBaggagePerTypeReport[3].type}"/>", ${profitBaggagePerTypeReport[3].profit}, "#e5e4e2"]
            ]);

            var view = new google.visualization.DataView(data);
            view.setColumns([0, 1,
                { calc: "stringify",
                    sourceColumn: 1,
                    type: "string",
                    role: "annotation" },
                2]);

            var options = {
                title: "<@spring.message "report.baggage.bar.chart.head"/>",
                width: 600,
                height: 400,
                bar: {groupWidth: "95%"},
                legend: { position: "none" },
                chartArea:{ left:175 }
            };
            var chart = new google.visualization.BarChart(document.getElementById("barchart_values"));
            chart.draw(view, options);
        }
    </script>

    <div class="row mb-4">
        <h1 class="d-flex justify-content-center"><@spring.message "report.baggage.head"/></h1>
    </div>

    <div class="row">
        <div class="col">
            <div id="pie_chart"></div>
        </div>
        <div class="col">
            <div id="barchart_values"></div>
        </div>
    </div>

    <script>
        addSwitchLanguageWithUrlClickListeners('/baggage/review', [])
    </script>

</@c.page>