$(function () {
    
    //当前时间
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    var strMonth = month<10?"0"+month:month;
    var date = year +"-" + strMonth;

    $(".startDate").prop("max",date);
    $(".endDate").prop("max",date);

    //开始时间必须大于结束时间
    $(".endDate").change(function () {

        var startDate = $(".startDate").val();
        var endDate = $(".endDate").val();
        
        if (new Date(startDate)>new Date(endDate)) {
            alert("统计开始时间，必须早于结束时间");
        }


    })


    $(".stat").click(function () {     
        var startDate = $(".startDate").val();
        var endDate = $(".endDate").val();
        
        $.ajax({
            type:"get",
            url:"http://localhost:8080/SunshineAirlines/getTicketStatistics",
            data:{startDate,endDate},

            success(msg) {
                var json = JSON.parse(msg);
                var html = "";
                for (let i = 0; i < json.data.length; i++) {
                    html += `

                    <tr class="tdcolor">
                    <td>${json.data[i].MONTH}</td>
                    <td>${json.data[i].FlightsAmount}</td>
                    <td>${json.data[i].TicketsAmount}</td>
                    <td>${json.data[i].TicketsRevenue}</td>        
                    </tr>
                    `;
                }

                $(".resultList").html(html);

            }




        })


    })


})