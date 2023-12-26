
    $(".searchBtn").click(function () {
        searchList();
    })


    $.ajax({
        type: "post",
        url: "http://localhost:8080/SunshineAirlines/getCityNames",

        success(msg) {
            var json = JSON.parse(msg);
            var html = ""
            for (let i = 0; i < json.data.length; i++) {
                html += `<option value="${json.data[i].CityName}">${json.data[i].CityName}</option>`;
            }

            $(".fromCity").html(html);
            $(".toCity").html(html);

        }

    })

    $(".changeicon").click(function () {
        var fromCity = $(".fromCity").val();
        var toCity = $(".toCity").val();

        $(".fromCity").val(toCity);
        $(".toCity").val(fromCity);

    })




    function searchList() {
        var fromCity = $(".fromCity").val();
        var toCity = $(".toCity").val();
        var startDate = $(".startDate").val();
        var endDate = $(".endDate").val();
        // <td><input type='button' onclick=update(${json.data[i].ScheduleId},'${newstatus}ed') value='${newstatus}' /></td>

        $.ajax({
            type: "post",
            url: "http://localhost:8080/SunshineAirlines/getSchedule",
            data: { fromCity, toCity, startDate, endDate },

            success(msg) {
                json = JSON.parse(msg);
                var html = "";

                if (json.flag == "success") {
                    for (let i = 0; i < json.data.length; i++) {
                        var newstatus = json.data[i].Status == "Confirmed" ? 'Cancel' : 'Confirm';
                        html += `
                        <tr class="tdcolor1">                           
                        <td>${json.data[i].Date.substring(0, 10)}</td>
                        <td>${json.data[i].Time.substring(0, 5)}</td>
                        <td>${json.data[i].DepartCityName}/${json.data[i].DepartureAirportIATA}</td>
                        <td>${json.data[i].ArriveCityName}/${json.data[i].ArrivalAirportIATA}</td>
                        <td>${json.data[i].Name}</td>
                        <td>${json.data[i].EconomyPrice}</td>
                        <td>${json.data[i].FlightNumber}</td>
                        <td>${json.data[i].Gate}</td>
                        <td>${json.data[i].Status}</td>
                        <td><input type='button' onclick=update(${json.data[i].ScheduleId},'${newstatus}ed') value='${newstatus}' /></td>
                        </tr>
                        
                        `;

                    }
                    $(".resultList").html(html);

                }

            }


        })

    }


    function update(scheduleId, status) {
        $.ajax({
            type: "post",
            url: "http://localhost:8080/SunshineAirlines/updateSchedule",
            data: { scheduleId, status },

            success(msg) {
                var json = JSON.parse(msg);
                if (json.flag == "success") {
                    searchList();
                }

            }

        })
    }